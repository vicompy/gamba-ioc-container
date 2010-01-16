package org.gro.orm.dao;

import org.gro.orm.annotation.Query;

public interface DogDao {

	@Query("SELECT ID_DOG,NAME,AGE FROM DOG")
	Dog[] loadAll();

	@Query("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES ($0.idDog,$0.name,$0.age)")
	int create(Dog entity);

	@Query("SELECT ID_DOG,NAME,AGE FROM DOG WHERE ID_DOG=$0")
	Dog loadById(int id);

	@Query("DELETE FROM DOG WHERE ID_DOG=$0.idDog")
	int delete(Dog dog);

	@Query("UPDATE DOG SET NAME=$0.name, AGE=$0.age WHERE ID_DOG=$0.idDog")
	int update(Dog entity);

	//

	@Query("SELECT COLOR.* FROM DOG,COLOR WHERE DOG.ID_COLOR=COLOR.ID_COLOR AND DOG.ID_DOG=$0.idDog")
	Color[] joinWithColor(Dog entity);

}
