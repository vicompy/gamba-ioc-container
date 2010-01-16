package org.gro.orm.service.daos;

import org.gro.orm.annotation.Query;
import org.gro.orm.annotation.Retrieve;
import org.gro.orm.service.ents.Tag;
import org.gro.orm.service.ents.Url;

public interface TagDao {

	@Query(	"SELECT T.ID_TAG,T.NAME " +
			"FROM TAG T, URL_TAG UT " +
			"WHERE UT.ID_URL=$0.idUrl AND UT.ID_TAG=T.ID_TAG")
	Tag[] loadByUrl(Url url);

	@Query("SELECT * FROM TAG WHERE ID_TAG=$0")
	Tag loadById(int id);

	@Query("INSERT INTO TAG (NAME) VALUES ($0.name)")
	@Retrieve("SELECT ID_TAG FROM TAG WHERE NAME=$0.name")
	Tag create(Tag tag);

	@Query("SELECT * FROM TAG")
	Tag[] loadAll();

}
