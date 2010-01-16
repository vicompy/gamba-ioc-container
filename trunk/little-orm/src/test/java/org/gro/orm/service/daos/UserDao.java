package org.gro.orm.service.daos;

import org.gro.orm.annotation.Query;
import org.gro.orm.service.ents.User;

public interface UserDao {

	@Query("SELECT ID_USER,NAME,PASS FROM USER WHERE NAME=$0")
	User loadByName(String name);

}
