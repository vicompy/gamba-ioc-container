package org.gro.orm.service;

import java.sql.Connection;

public interface Transactional {

	void setConnection(final Connection c);

}
