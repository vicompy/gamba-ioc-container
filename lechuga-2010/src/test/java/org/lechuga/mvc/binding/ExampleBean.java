package org.lechuga.mvc.binding;

public class ExampleBean {

	public String name;
	public int age;
	public Float[] members;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	public Float[] getMembers() {
		return members;
	}

	public void setMembers(final Float[] members) {
		this.members = members;
	}

	@Override
	public String toString() {
		String r = name + "-" + age + "-";

		if (members != null) {
			for (final float f : members) {
				r += f + ",";
			}
		}

		return r;
	}

}
