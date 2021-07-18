package application;
public enum Attribute {
	TOTAL("Total"), HP("HP"), ATTACK("Attack"), DEFENSE("Defense"), SPECIAL_ATTACK("Special Attack"),
	SPECIAL_DEFENSE("Special Defense"), SPEED("Speed");

	private String attribute;

	private Attribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttributeName() {
//		System.out.println(this.attribute);
		return this.attribute;
	}
}
