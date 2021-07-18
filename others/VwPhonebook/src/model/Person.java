package model;

import javafx.beans.property.SimpleStringProperty;

public class Person implements Comparable<Person> {

	private final SimpleStringProperty firstName;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty phone;
	private static final String FILE_SPILT = ";";

	public Person(String fName, String lName) {
		this.firstName = new SimpleStringProperty(fName);
		this.lastName = new SimpleStringProperty(lName);
		this.phone = new SimpleStringProperty("");
	}

	public Person(String fName, String lName, String phone) {
		this.firstName = new SimpleStringProperty(fName);
		this.lastName = new SimpleStringProperty(lName);
		this.phone = new SimpleStringProperty(phone);
	}

	public String getFirstName() {
		return this.firstName.get();
	}

	public void setFirstName(String fName) {
		this.firstName.set(fName);
	}

	public String getLastName() {
		return this.lastName.get();
	}

	public void setLastName(String fName) {
		this.lastName.set(fName);
	}

	public String getPhone() {
		return this.phone.get();
	}

	public void setPhone(String phone) {
		this.phone.set(phone);
	}

	@Override
	public int compareTo(Person o) {
		return this.firstName.get().compareToIgnoreCase(o.getFirstName());
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + "]";
	}

	public String saveToCSV() {
		return this.getFirstName() + FILE_SPILT + this.getLastName() + FILE_SPILT + this.getPhone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

}