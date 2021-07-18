package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:~/Database/myPokemonNewnew.db";
	private static final String DB_TCP_URL = "jdbc:h2:tcp://localhost/~/Database/myPokemonNew.db";
	private static final String USERNAME = "user";
	private static final String PASSWORD = "123";
	private static Connection connection;
//	DriverManager.getConnection("jdbc:h2:tcp://localhost/server~/dbname","username","password");

	public DbConnection() {
	}

	private static void createConnectionToDatabase() {
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, "", "");
		} catch (ClassNotFoundException ex) {
			System.err.println("Cannot load the H2 Database");
		} catch (SQLException ex2) {
			System.err.println("Cannot connect to database, did you close the database?");
			ex2.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (connection == null) {
			createConnectionToDatabase();
		}
		return connection;
	}

}
