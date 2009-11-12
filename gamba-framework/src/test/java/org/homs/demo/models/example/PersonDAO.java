package org.homs.demo.models.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class PersonDAO {

	private final Connection conn;

	public PersonDAO(final Connection conn) {
		this.conn = conn;
	}

	public void deleteAll() throws SQLException {
		conn.createStatement().execute(
			"DELETE FROM PERSON"
		);
	}

	public void delete(final Long id) throws SQLException {
		conn.createStatement().execute(
			"DELETE FROM PERSON WHERE ID=" + id
		);
	}

	public void insert(final Person person) throws SQLException {
		if (person.getId() == null) {
			conn.createStatement().execute(
				"INSERT INTO PERSON (NAME,AGE) VALUES ('"+person.getName()+"',"+person.getAge()+")"
			);
		} else {
			conn.createStatement().execute(
				"INSERT INTO PERSON (ID,NAME,AGE) VALUES ("+person.getId()+",'"+person.getName()+"',"+person.getAge()+")"
			);
		}
	}

	public void update(final Person person) throws SQLException {
		conn.createStatement().execute(
			"UPDATE PERSON SET NAME='"+person.getName()+"', AGE="+person.getAge()+" WHERE ID="+person.getId()
		);
	}

	public Person findById(final Long id) throws SQLException {

		final PreparedStatement ps = conn.prepareStatement(
			"SELECT ID,NAME,AGE FROM PERSON WHERE ID=" + id
		);
		final ResultSet rs = ps.executeQuery();

		if (!rs.next()) {
			return null;
		}
		final Person person = new Person();
		person.setId(rs.getLong("ID"));
		person.setName(rs.getString("NAME"));
		person.setAge(rs.getInt("AGE"));

		return person;
	}

	public List<Person> findAll() throws SQLException {

		final PreparedStatement ps = conn.prepareStatement(
			"SELECT ID,NAME,AGE FROM PERSON"
		);
		final ResultSet rs = ps.executeQuery();


		final List<Person> l = new ArrayList<Person>();

		while(rs.next()) {
    		final Person person = new Person();
    		person.setId(rs.getLong("ID"));
    		person.setName(rs.getString("NAME"));
    		person.setAge(rs.getInt("AGE"));
    		l.add(person);
		}
		return l;
	}

//	public Long personsCount() {
//		return Long.valueOf(Query.integerCount("SELECT COUNT(*) FROM PERSON"));
//	}


}
