package gamba.container.parser;

import gamba.container.exception.GambaException;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * TODO
 *
 * @author mhoms
 */
public class GambaPropertiesParser extends AbstractGambaParser {

	/**
	 * objecte lector de propietats
	 */
	private final Properties props;

	/**
	 * construeix el parser, ja definint el fitxer amb les definicions amb les
	 * que treballar.
	 *
	 * @param propertiesFileName
	 * @throws GambaException
	 */
	public GambaPropertiesParser(final String propertiesFileName) throws GambaException {
		super();
		try {
			props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName));
		} catch (final IOException e) {
			throw new GambaException("properties file not found: " + propertiesFileName, e);
		}
	}

	/**
	 * Obté el valor d'una propietat, utilitzant com a clau l'identificador de
	 * bean.
	 *
	 * @see gamba.container.parser.AbstractGambaParser#getBeanDefinition(java.lang.String)
	 */
	@Override
	protected String getBeanDefinition(final String beanId) {
		final String propValue = props.getProperty(beanId);
		if (propValue == null) {
			throw new GambaException("bean identifier nou found: " + beanId);
		}
		return propValue;
	}

	/**
	 * Obté un <tt>Set</tt> de les claus trobades en el fitxer de propietats.
	 *
	 * @see gamba.container.parser.AbstractGambaParser#getAllBeanIdentifiers()
	 */
	@Override
	protected Set<Object> getAllBeanIdentifiers() {
		return props.keySet();
	}

}
