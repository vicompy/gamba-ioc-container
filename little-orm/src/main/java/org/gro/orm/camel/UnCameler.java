package org.gro.orm.camel;

public final class UnCameler {

//	private static final char DASH_SEPARATOR = '-';
	private static final char SQL_SEPARATOR = '_';

	private UnCameler() {

	}

	/**
	 * <tt>BENDITO_SEAS_HIJO_MIO ==> benditoSeasHijoMio</tt>
	 *
	 * @param s
	 * @return
	 */
	public static String camelizeLo(final String s) {

		final StringBuffer strb = new StringBuffer(s.length());

		strb.append(Character.toLowerCase(s.charAt(0)));

		for (int i = 1; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == SQL_SEPARATOR) {
				i++;
				c = s.charAt(i);
				strb.append(Character.toUpperCase(c));
			} else {
				strb.append(Character.toLowerCase(c));
			}
		}

		return strb.toString();
	}

//	/**
//	 * <tt>BENDITO_SEAS_HIJO_MIO ==> BenditoSeasHijoMio</tt>
//	 *
//	 * @param s
//	 * @return
//	 */
//	public static String camelizeHi(final String s) {
//
//		final StringBuffer strb = new StringBuffer(s.length());
//
//		strb.append(Character.toUpperCase(s.charAt(0)));
//
//		for (int i = 1; i < s.length(); i++) {
//			char c = s.charAt(i);
//			if (c == SQL_SEPARATOR) {
//				i++;
//				c = s.charAt(i);
//				strb.append(Character.toUpperCase(c));
//			} else {
//				strb.append(Character.toLowerCase(c));
//			}
//		}
//
//		return strb.toString();
//	}
//
//	/**
//	 * <tt>BenditoSeasHijoMio ==> bendito-seas-hijo-mio</tt><br>
//	 * <tt>benditoSeasHijoMio ==> bendito-seas-hijo-mio</tt><br>
//	 *
//	 * @param s
//	 * @return
//	 */
//	private static String deCamelize(final String s) {
//
//		final StringBuffer strb = new StringBuffer(s.length() + 10);
//
//		strb.append(Character.toLowerCase(s.charAt(0)));
//
//		for (int i = 1; i < s.length(); i++) {
//			final char c = s.charAt(i);
//			if (Character.isUpperCase(c)) {
//				strb.append(DASH_SEPARATOR);
//				strb.append(Character.toLowerCase(c));
//			} else {
//				strb.append(c);
//			}
//		}
//
//		return strb.toString();
//	}
//
//	/**
//	 * <tt>BenditoSeasHijoMio ==> BENDITO_SEAS_HIJO_MIO</tt><br>
//	 * <tt>benditoSeasHijoMio ==> BENDITO_SEAS_HIJO_MIO</tt><br>
//	 *
//	 * @param s
//	 * @return
//	 */
//	public static String deCamelizeToSql(final String s) {
//		return deCamelize(s).replace(DASH_SEPARATOR, SQL_SEPARATOR).toUpperCase();
//	}

}
