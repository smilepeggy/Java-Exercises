package application;
public class Album {
	private String name;
	private String artist;
	private int trackAmount;
	private int duration; // minute
	private int releaseYear;
	private double price; // US
	private static final String SEMICOLON = ";";

	public Album(String name, String artist, int trackAmount, int duration, int releaseYear, double price) {
		this.name = name;
		this.artist = artist;
		this.trackAmount = trackAmount;
		this.duration = duration;
		this.releaseYear = releaseYear;
		this.price = price;
	}

	public String getName() {
		return this.name;
	}

	public String getArtist() {
		return this.artist;
	}

	public int getTrackAmount() {
		return this.trackAmount;
	}

	public int getDuration() {
		return this.duration;
	}

	public int getReleaseYear() {
		return this.releaseYear;
	}

	public double getPrice() {
		return this.price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setTrackAmount(int trackAmount) {
		this.trackAmount = trackAmount;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
//		return this.name + SEMICOLON + this.artist + SEMICOLON + this.trackAmount + SEMICOLON + this.duration
//				+ SEMICOLON + this.releaseYear + SEMICOLON + this.price; // + System.getProperty("line.separator")
		return toDECsvFormat();
	}

	public String toDECsvFormat() {
		return this.name + SEMICOLON + this.artist + SEMICOLON + this.trackAmount + SEMICOLON + this.duration
				+ SEMICOLON + this.releaseYear + SEMICOLON + this.price; // +\n
	}

}
