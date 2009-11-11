package org.homs.demo.models;

import java.util.List;

public interface IPersonBO {

	void insert(final Person person);

	public Person findById(final Long id);

	public List<Person> findAll();

	// public Long personsCount();
	public void delete(final Person person);

	public void deleteAll();

	public void update(final Person person);

	public void deletePersons(final Person...persons);

	// TODO for test ONLY
	public void insertTwice(final Person person1, final Person person2);

}