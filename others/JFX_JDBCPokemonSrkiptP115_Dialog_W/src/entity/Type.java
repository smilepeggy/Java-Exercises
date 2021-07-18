package entity;

public enum Type {
	NONE, BUG, DRAGON, FAIRY, FIRE, GHOST, GROUND, NORMAL, PSYCHIC, STEEL, DARK, ELECTRIC, FIGHTING, FLYING, GRASS, ICE,
	POISON, ROCK, WATER;

	public static Type fromString(String text) {
		switch (text.toLowerCase()) {
		case "bug":
			return Type.BUG;
		case "dragon":
			return Type.DRAGON;
		case "fairy":
			return Type.FAIRY;
		case "fire":
			return Type.FIRE;
		case "ghost":
			return Type.GHOST;
		case "ground":
			return Type.GROUND;
		case "normal":
			return Type.NORMAL;
		case "psychic":
			return Type.PSYCHIC;
		case "steel":
			return Type.STEEL;
		case "dark":
			return Type.DARK;
		case "electric":
			return Type.ELECTRIC;
		case "fighting":
			return Type.FIGHTING;
		case "flying":
			return Type.FLYING;
		case "grass":
			return Type.GRASS;
		case "ice":
			return Type.ICE;
		case "poison":
			return Type.POISON;
		case "rock":
			return Type.ROCK;
		case "water":
			return Type.WATER;

		default:
			return Type.NONE;
		}
	}
}
