package application;

public class Student {

	private String matriculationNumber;
	private String surname;
	private String firstname;
	private String street;
	private String residence;
	private String telefon;

	public Student(String matriculationNumber, String surname, String firstname, String street, String residence,
			String telefon) {
		this.matriculationNumber = matriculationNumber;
		this.surname = surname;
		this.firstname = firstname;
		this.street = street;
		this.residence = residence;
		this.telefon = telefon;
	}

	public String getMatriculationNumber() {
		return this.matriculationNumber;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public String getStreet() {
		return this.street;
	}

	public String getResidence() {
		return this.residence;
	}

	public String getTelefon() {
		return this.telefon;
	}

	@Override
	public String toString() {
		return this.matriculationNumber + ";" + this.surname + ";" + this.firstname + ";" + this.street + ";"
				+ this.residence + ";" + this.telefon;
	}

}
