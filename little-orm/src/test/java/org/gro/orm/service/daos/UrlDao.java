package org.gro.orm.service.daos;

import org.gro.orm.annotation.Query;
import org.gro.orm.service.ents.Url;
import org.gro.orm.service.ents.User;

public interface UrlDao {

	@Query("SELECT ID_URL,URL FROM URL WHERE ID_URL=$0")
	Url loadById(Integer id);

	@Query(
		"SELECT U.ID_URL,U.URL " +
		"FROM URL U, USER_URL " +
		"WHERE USER_URL.ID_USER=$0.idUser AND USER_URL.ID_URL=U.ID_URL")
	Url[] loadByUser(User user);

}
