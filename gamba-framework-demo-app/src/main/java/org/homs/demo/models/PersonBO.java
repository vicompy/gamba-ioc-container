package org.homs.demo.models;

import java.util.List;

import org.homs.gamba.bo.GambaGenericBO;

public class PersonBO extends GambaGenericBO implements IPersonBO {

	public void insert(final Person person) {
		try {
			new PersonDAO(getConnection()).insert(person);
		} catch (final Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public Person findById(final Long id) {
		try {
			return new PersonDAO(getConnection()).findById(id);
		} catch (final Exception exc) {
			throw new RuntimeException(exc);
		}
	}

//	public Long personsCount() {
//		try {
//			return new PersonDAO(getConnection()).personsCount();
//		} catch (final Exception exc) {
//			throw new RuntimeException(exc);
//		}
//	}

	public List<Person> findAll() {
		try {
			return new PersonDAO(getConnection()).findAll();
		} catch (final Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public void delete(final Person person) {
		try {
			new PersonDAO(getConnection()).delete(person);
		} catch (final Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public void deleteAll() {
		try {
			new PersonDAO(getConnection()).deleteAll();
		} catch (final Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public void update(final Person person) {
		try {
			new PersonDAO(getConnection()).update(person);
		} catch (final Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public void deletePersons(final Person...persons) {
		for (final Person p : persons) {
			delete(p);
		}
	}

	// TODO for test ONLY
	public void insertTwice(final Person person1, final Person person2) {
		try {
			final PersonDAO personDao = new PersonDAO(getConnection());
			personDao.insert(person1);
			personDao.insert(person2);
		} catch (final Exception exc) {
			throw new RuntimeException(exc);
		}
	}

}
