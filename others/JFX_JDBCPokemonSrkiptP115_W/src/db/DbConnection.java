package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:~/Database/myPokemon02.db";
	private static final String USERNAME = "user";
	private static final String PASSWORD = "123";
	private static Connection connection;

	public DbConnection() {

	}

	private static void createDbConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

		} catch (ClassNotFoundException ex) {
			System.err.println("You cannot find the Driver!");
		} catch (SQLException ex) {
			System.err.println("Cannot connect to database, is your database still open?");
		}
	}

	public static Connection getConnection() {
		if (connection == null) {
			createDbConnection();
		}
		return connection;
	}

}
