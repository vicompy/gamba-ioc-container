package gamba.container.parser;

import gamba.container.entities.BeanDef;
import gamba.container.entities.ConstructorInj;
import gamba.container.entities.InjElem;
import gamba.container.entities.MethodInj;
import gamba.container.entities.InjElem.EInjType;
import gamba.container.exception.GambaException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO
 *
 * @author mhoms
 */
public abstract class AbstractGambaParser {

	/**
	 * hash de definicions de beans que interessa obtenir.
	 */
	private final Map<String, BeanDef> hashBeanDefs;

	/**
	 * donat un identificador, retornar la corresponent declaració de bean
	 *
	 * @param beanId identificador del bean la declaració del qual es vol
	 *        obtenir.
	 * @return
	 */
	abstract protected String getBeanDefinition(String beanId);

	/**
	 * @return un <tt>Set</tt> amb tots els identificadors de bean obtinguts del
	 *         medi de context.
	 */
	abstract protected Set<Object> getAllBeanIdentifiers();

	protected AbstractGambaParser() {
		hashBeanDefs = new HashMap<String, BeanDef>();
	}

	/**
	 * mètode pesat; itera sobre totes les definicions de beans, les parseja, i
	 * en retorna la hash de totes elles, acabant aquí el cicle de vida d'aquest
	 * objecte.
	 *
	 * @return la hash amb totes les definicions de bean parsejades
	 */
	public Map<String, BeanDef> computeHashBeanDefs() {
		// parseja tots els beans definits en fitxer de propietats,
		// desant-los en hash
		for (final Object beanId : getAllBeanIdentifiers()) {
			try {
				obtainParsedBeanDef(beanId.toString());
			} catch (final Exception e) {
				throw new GambaException("error parsing bean identified by: " + beanId, e);
			}
		}
		return hashBeanDefs;
	}

	/**
	 * Aquest mètode permet el parseig recursiu de definicions; la implementació
	 * ha d'obtenir la definició donada per <tt>beanId</tt>, i cridar al mètode
	 * {@link #parseBeanDef}.
	 *
	 * @param beanId identificador de bean, referenciat per l'actual definició,
	 *        i que es necessita obtenir.
	 * @return l'entitat de definició parsejada
	 * @throws GambaException si error en parsing
	 */
	protected final BeanDef obtainParsedBeanDef(final String beanId) throws GambaException {
		BeanDef cachedBeanDef = hashBeanDefs.get(beanId);
		if (cachedBeanDef == null) {
			final Tokenizer tokenizer = new Tokenizer(getBeanDefinition(beanId));
			try {
				cachedBeanDef = parseBeanDef(beanId, tokenizer);
				hashBeanDefs.put(beanId, cachedBeanDef);
			} catch (final Exception e) {
				throw new GambaException("parsing expression: " + tokenizer.toString(), e);
			}

			return cachedBeanDef;
		}
		return cachedBeanDef;
	}

	/**
	 * parseja l'entitat {@link BeanDef}.
	 *
	 * @param beanId
	 * @param tokenizer
	 * @return
	 * @throws GambaException
	 */
	// cuidau que un nested-bean-anònim cridarà parseBeanDef(null, t)
	private BeanDef parseBeanDef(final String beanId, final Tokenizer tokenizer) throws GambaException {

		// parseja ["~"]
		boolean isSingleton = false;
		if (tokenizer.isSymbol()) {
			final String sym = tokenizer.nextToken();
			isSingleton = true;
			tokenizer.gambaAssert("~", sym);
		}

		// parseja CLASS
		Class<?> beanClass = null;
		final String beanClassName = tokenizer.nextToken();
		try {
			beanClass = Class.forName(beanClassName);
		} catch (final ClassNotFoundException e) {
			throw new GambaException("class not found: " + beanClassName, e);
		}

		if (!tokenizer.isSymbol()) {
			tokenizer.gambaThrowExpected("':', '(', ';'", tokenizer.nextToken());
		}

		// parseja [<CONSTRUCTORINJ>]
		String sym = tokenizer.nextTokenQuiet();
		ConstructorInj constrInj = null;
		if ("(".equals(sym)) {
			constrInj = parseConstrInjRep(beanClass, tokenizer);
		}

		// parseja <METHODINJREP>
		sym = tokenizer.nextTokenQuiet();
		MethodInj[] methodInj = null;
		if (":".equals(sym)) {
			methodInj = parseMethodInjRep(tokenizer, beanClass);
		}

		// parseja ";"
		tokenizer.gambaAssert(";");

		return new BeanDef(beanId, isSingleton, beanClass, constrInj, methodInj);
	}

	/**
	 * parseja l'entitat {@link ConstructorInj}.
	 *
	 * @param beanClass
	 * @param tokenizer
	 * @return
	 * @throws GambaException
	 */
	private ConstructorInj parseConstrInjRep(final Class<?> beanClass, final Tokenizer tokenizer) throws GambaException {
		tokenizer.gambaAssert("(");

		final List<InjElem> injList = new ArrayList<InjElem>();

		injList.add(parseInjElement(tokenizer));
		while (tokenizer.isSymbol()) {
			final String sym = tokenizer.nextToken();
			if (")".equals(sym)) {
				break;
			}
			tokenizer.gambaAssert(",", sym);

			injList.add(parseInjElement(tokenizer));
		}

		final InjElem[] injElems = injList.toArray(new InjElem[injList.size()]);

		final Class<?>[] classArgsList = new Class<?>[injList.size()];
		for (int i = 0; i < injList.size(); i++) {
			classArgsList[i] = injList.get(i).getInjElementClass();
		}

		Constructor<?> constructor = null;

		final Constructor<?>[] constructors = beanClass.getConstructors();

		boolean founded = true;
		for (final Constructor<?> c : constructors) {
			if (c.getParameterTypes().length == classArgsList.length) {

				founded = true;
				for (int i = 0; i < classArgsList.length; i++) {
					if (!areAssignable(c.getParameterTypes()[i], classArgsList[i])) {
						founded = false;
						break;
					}
				}

				if (founded) {
					constructor = c;
					break;
				}
			}
		}

		if (!founded) {
			final StringBuffer strb = new StringBuffer("constructor not found: ");
			strb.append(beanClass);
			strb.append('(');
			for (final Class<?> param : classArgsList) {
				strb.append(param);
				strb.append(", ");
			}
			strb.append(')');
			throw new GambaException(strb.toString());
		}

		return new ConstructorInj(injElems, constructor);
	}

	/**
	 * parseja la llista d'entitats {@link ConstructorInj}[].
	 *
	 * @param tokenizer
	 * @param beanClass
	 * @return
	 * @throws GambaException
	 */
	private MethodInj[] parseMethodInjRep(final Tokenizer tokenizer, final Class<?> beanClass) throws GambaException {
		final List<MethodInj> injList = new ArrayList<MethodInj>();
		while (":".equals(tokenizer.nextTokenQuiet())) {
			injList.add(parseMethodInj(tokenizer, beanClass));
		}
		return injList.toArray(new MethodInj[injList.size()]);
	}

	/**
	 * parseja l'entitat {@link ConstructorInj}.
	 *
	 * @param tokenizer
	 * @param beanClass
	 * @return
	 * @throws GambaException
	 */
	private MethodInj parseMethodInj(final Tokenizer tokenizer, final Class<?> beanClass) throws GambaException {
		tokenizer.nextToken(); // chupa ":"

		final String methodName = tokenizer.nextToken();
		tokenizer.gambaAssert("<-");

		final InjElem injElem = parseInjElement(tokenizer);

		Method method = null;
		final Class<?> methodArgClass = injElem.getInjElementClass();
		final Method[] methods = beanClass.getMethods();
		for (final Method m : methods) {
			if (m.getName().equals(methodName) && m.getParameterTypes().length == 1
					&& areAssignable(m.getParameterTypes()[0], methodArgClass)) {
				method = m;
				break;
			}
		}

		if (method == null) {
			final StringBuffer strb = new StringBuffer("method not found: ");
			strb.append(beanClass.getName());
			strb.append('.');
			strb.append(methodName);
			strb.append('(');
			strb.append(methodArgClass);
			strb.append(')');
			throw new GambaException(strb.toString());
		}

		return new MethodInj(method, injElem);
	}

	/**
	 * parseja l'entitat {@link InjElem}.
	 *
	 * @param tokenizer
	 * @return
	 * @throws GambaException
	 */
	private InjElem parseInjElement(final Tokenizer tokenizer) throws GambaException {
		if (tokenizer.isSymbol()) {
			if ("{".equals(tokenizer.nextTokenQuiet())) {
				// la referència és un bean anònim intern
				tokenizer.nextToken(); // chupa {
				final BeanDef nestedBeanDef = parseBeanDef(null, tokenizer);
				tokenizer.gambaAssert("}");
				return new InjElem(EInjType.NESTED_ANONYMOUS_BEANDEF, nestedBeanDef);
			} else {
				tokenizer.gambaThrowExpected("'<-' or '{'", tokenizer.nextToken());
			}
		} else {
			if (tokenizer.nextTokenQuiet().charAt(0) == '"') {
				// la referència és un String inmediat
				final String stringToken = tokenizer.nextToken();
				final String stringValue = stringToken.substring(1, stringToken.length() - 1);
				return new InjElem(stringValue);
			} else {
				// la referència és un beanId
				return new InjElem(EInjType.BEANREF, obtainParsedBeanDef(tokenizer.nextToken()));
			}
		}
		return null;
	}

	/**
	 * performa <tt>isAssignableFrom</tt>, convertint el tipus de classe destí;
	 * si interessa injectar a un primitiu <tt>int</tt> un <tt>Integer</tt> és
	 * correcte, però <tt>isAssignableFrom</tt> retornarà <tt>false</tt>. Així
	 * doncs aquest mètode aplica la corresponent conversió de tipus primitius a
	 * classe Wrapper primitiva, per tal re reconsiderar
	 * <tt>isAssignableFrom</tt>.
	 *
	 * @param dst tipus destí de l'assignació
	 * @param org tipus origen de l'assignació
	 * @return si tal assignació és possible
	 */
	private boolean areAssignable(final Class<?> dst, final Class<?> org) {
		Class<?> dst2 = dst;
		if (dst.isPrimitive()) {
			if (dst.equals(int.class)) {
				dst2 = Integer.class;
			}
			if (dst.equals(long.class)) {
				dst2 = Long.class;
			}
			if (dst.equals(float.class)) {
				dst2 = Float.class;
			}
			if (dst.equals(double.class)) {
				dst2 = Double.class;
			}
			if (dst.equals(boolean.class)) {
				dst2 = Boolean.class;
			}
			if (dst.equals(char.class)) {
				dst2 = Character.class;
			}
			if (dst.equals(byte.class)) {
				dst2 = Byte.class;
			}
			if (dst.equals(short.class)) {
				dst2 = Short.class;
			}
		}

		return dst2.isAssignableFrom(org);
	}

}
