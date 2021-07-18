package application;

public class Car {
	private String brand;
	private String model;
	private String color;
	private int mileage;
	private String fuel;
	private int producedYear;
	private Person person;

	public Car(Person person, String brand, String model, String color, int mileage, String fuel, int producedYear) {
		this.person = person;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.mileage = mileage;
		this.fuel = fuel;
		this.producedYear = producedYear;
	}

	@Override
	public String toString() {
		return "Car [brand=" + brand + ", model=" + model + ", color=" + color + ", mileage=" + mileage + ", fuel="
				+ fuel + ", producedYear=" + producedYear + ", person=" + person + "]";
	}

//	@Override
//	public String toString() {
//		return this.person + " " + this.brand + " " + this.model + " " + this.color + " " + this.mileage + " "
//				+ this.producedYear;
//	}
	public Person getPerson() {
		return this.person;
	}

	public String getBrand() {
		return this.brand;
	}

	public String getModel() {
		return this.model;
	}

	public String getColor() {
		return this.color;
	}

	public int getMileage() {
		return this.mileage;
	}

	public String getFuel() {
		return this.fuel;
	}

	public int getProducedYear() {
		return this.producedYear;
	}

}
