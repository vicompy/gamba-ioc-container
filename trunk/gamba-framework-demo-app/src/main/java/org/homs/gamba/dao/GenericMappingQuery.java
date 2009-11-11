package org.homs.gamba.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.homs.gamba.binding.BindingException;

abstract class GenericMappingQuery {

	protected Map<String, String[]> getValueMap(final ResultSet rs) {
		final Map<String, String[]> r = new HashMap<String, String[]>();

		final String[] names = getColumnNames(rs);
		for (int i = 0; i < names.length; i++) {
			final String name = names[i];
			try {
				System.out.println("mapping: "+name+" as " +rs.getString(i + 1));
				r.put(name, new String[] { rs.getString(i + 1) });
			} catch (final SQLException exc) {
				throw new BindingException(exc);
			}
		}

		return r;
	}

	private String[] getColumnNames(final ResultSet rs) {
		int numColumns;
		try {

			numColumns = rs.getMetaData().getColumnCount();
			final String[] names = new String[numColumns];

			for (int i = 0; i < numColumns; i++) {
				names[i] = rs.getMetaData().getColumnName(i + 1);
			}

			return names;

		} catch (final SQLException exc) {
			throw new BindingException(exc);
		}
	}

}

