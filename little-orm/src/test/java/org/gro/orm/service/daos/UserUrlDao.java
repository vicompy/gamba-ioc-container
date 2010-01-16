package org.gro.orm.service.daos;

import org.gro.orm.annotation.Query;
import org.gro.orm.service.ents.UserUrl;

public interface UserUrlDao {

	@Query("SELECT ID_USER_URL,TITLE,DESCRIPTION,REGISTERED FROM USER_URL")
	UserUrl[] loadAll();

}
