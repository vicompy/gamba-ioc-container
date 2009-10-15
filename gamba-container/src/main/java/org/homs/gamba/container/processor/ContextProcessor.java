package org.homs.gamba.container.processor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homs.gamba.container.ents.BeanDef;
import org.homs.gamba.container.ents.ConstructorInj;
import org.homs.gamba.container.ents.InjectableElement;
import org.homs.gamba.container.ents.MethodInj;
import org.homs.gamba.container.exception.GambaConfigurationException;
import org.homs.gamba.container.xmlparser.ents.BeanTag;
import org.homs.gamba.container.xmlparser.ents.ConstrTag;
import org.homs.gamba.container.xmlparser.ents.MethodTag;

public class ContextProcessor {

	private final List<BeanTag> beanDefs;
	private final Map<String, BeanDef> beanHash;

	public ContextProcessor(final List<BeanTag> beanDefs) {
		this.beanHash = new HashMap<String, BeanDef>();
		this.beanDefs = beanDefs;
	}

	public Map<String, BeanDef> translate() throws GambaConfigurationException {
		for (final BeanTag b : beanDefs) {
			final BeanDef bd = translateBean(b);
			if (beanHash.get(bd.beanId) != null) {
				throw new GambaConfigurationException("duplicated bean identifier: " + bd.beanId);
			}
			beanHash.put(bd.beanId, bd);
		}
		return this.beanHash;
	}

	private BeanDef translateBean(final BeanTag b) {
		Class<?> beanClass = null;
		final String beanClassName = b.className;
		try {
			beanClass = Class.forName(beanClassName);
		} catch (final ClassNotFoundException e) {
			throw new GambaConfigurationException("class not found: " + beanClassName, e);
		}

		final boolean singleton = b.singleton != null && "true".equals(b.singleton.toLowerCase());
		final ConstructorInj constrInj = parseConstrInjs(b.constrTags, beanClass);
		final MethodInj[] methodInj = parseMethods(b.methodTags, beanClass);

		return new BeanDef(b.id, singleton, beanClass, constrInj, methodInj);
	}

	private ConstructorInj parseConstrInjs(final List<ConstrTag> constrTag, final Class<?> beanClass) {

		final InjectableElement[] injElems = new InjectableElement[constrTag.size()];
		final Class<?>[] injClass = new Class<?>[constrTag.size()];

		for (int i = 0; i < constrTag.size(); i++) {
			injElems[i] = parseInjElem(constrTag.get(i));
			injClass[i] = injElems[i].getInjElementClass();
		}

		final Constructor<?> constructor = findConstructor(beanClass, injClass);
		return new ConstructorInj(injElems, constructor);
	}

	private MethodInj[] parseMethods(final List<MethodTag> ms, final Class<?> beanClass) {
		final MethodInj[] r = new MethodInj[ms.size()];
		for (int i = 0; i < ms.size(); i++) {
			r[i] = parseMethod(ms.get(i), beanClass);
		}

		return r;
	}

	private MethodInj parseMethod(final MethodTag methodTag, final Class<?> beanClass) {

		final InjectableElement injElem = parseInjElem(methodTag);

		Method method = null;
		final Class<?> methodArgClass = injElem.getInjElementClass();
		final Method[] methods = beanClass.getMethods();
		for (final Method m : methods) {
			if (m.getName().equals(methodTag.methodName) && m.getParameterTypes().length == 1
					&& areAssignable(m.getParameterTypes()[0], methodArgClass)) {
				method = m;
				break;
			}
		}

		if (method == null) {
			final StringBuffer strb = new StringBuffer("method not found: ");
			strb.append(beanClass.getName());
			strb.append('.');
			strb.append(methodTag.methodName);
			strb.append('(');
			strb.append(methodArgClass);
			strb.append(')');
			throw new GambaConfigurationException(strb.toString());
		}

		return new MethodInj(method, parseInjElem(methodTag));
	}

	private InjectableElement parseInjElem(final ConstrTag m) {

		if (m.ref != null) {
			// la dependència és una referència a un altre bean definit
			final BeanDef refBeanDef = this.beanHash.get(m.ref);
			if (refBeanDef == null) {
				throw new GambaConfigurationException("referenced bean not defined: " + m.ref);
			}
			return new InjectableElement(refBeanDef);
		} else {
			Class<?> typeClass = null;
			if (m.type != null) {

				// la dependència és un singleton construït per String directe
				try {
					typeClass = Class.forName(m.type);
				} catch (final ClassNotFoundException e) {
					throw new GambaConfigurationException("dependency class not found: " + m.type, e);
				}

				final Constructor<?> c = findConstructor(typeClass, new Class<?>[] { String.class });
				Object typeInstance = null;
				try {
					typeInstance = c.newInstance(m.value);
				} catch (final Exception e) {
					throw new GambaConfigurationException("error instantiating dependency: " + m.type + "(\"" + m.value
							+ "\")", e);
				}
				return new InjectableElement(m.value, typeClass, typeInstance);
			}
			// la dependència és un String directe
			return new InjectableElement(m.value, typeClass, m.value);
		}
	}

	private Constructor<?> findConstructor(final Class<?> targetClass, final Class<?>[] classArgsList) {
		final Constructor<?>[] constructors = targetClass.getConstructors();

		Constructor<?> constructor = null;

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
			strb.append(targetClass);
			strb.append('(');
			for (final Class<?> param : classArgsList) {
				strb.append(param);
				strb.append(", ");
			}
			strb.append(')');
			throw new GambaConfigurationException(strb.toString());
		}
		return constructor;
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
			} else if (dst.equals(long.class)) {
				dst2 = Long.class;
			} else if (dst.equals(float.class)) {
				dst2 = Float.class;
			} else if (dst.equals(double.class)) {
				dst2 = Double.class;
			} else if (dst.equals(boolean.class)) {
				dst2 = Boolean.class;
			} else if (dst.equals(char.class)) {
				dst2 = Character.class;
			} else if (dst.equals(byte.class)) {
				dst2 = Byte.class;
			} else if (dst.equals(short.class)) {
				dst2 = Short.class;
			}
		}

		return dst2.isAssignableFrom(org);
	}

}
