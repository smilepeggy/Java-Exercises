package entity;

public class Pokemon {
	private int id;
	private static int counter = 0;
	private String name;
	private Type type1;
	private Type type2;
	private int total;
	private int hp;
	private int attack;
	private int defense;
	private int specialAttack;
	private int specialDefense;
	private int speed;
//	private int pokemonNumber;

	public Pokemon(String name, Type type1, Type type2, int total, int hp, int attack, int defense, int specialAttack,
			int specialDefense, int speed) {
		this.id = ++counter;
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.total = total;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.specialAttack = specialAttack;
		this.specialDefense = specialDefense;
		this.speed = speed;

	}

	public String getName() {
		return this.name;
	}

	public Type getType1() {
		return this.type1;
	}

	public Type getType2() {
		return this.type2;
	}

	public int getTotal() {
		return this.total;
	}

	public int getHp() {
		return this.hp;
	}

	public int getAttack() {
		return this.attack;
	}

	public int getDefense() {
		return this.defense;
	}

	public int getSpecialAttack() {
		return this.specialAttack;
	}

	public int getSpecialDefense() {
		return this.specialDefense;
	}

	public int getSpeed() {
		return this.speed;
	}

	public int getId() {
		return this.id;
	}
//	public String[] getAttibutes() {
//		return {}
//	}
}
