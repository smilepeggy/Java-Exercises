package db;

import java.sql.SQLException;
import java.sql.Statement;

public class DbDdlStatements {

	public DbDdlStatements() {
	}

	public static void createPokemonDatabaseTable() {

		try (Statement createPokemonTableStatement = DbConnection.getConnection().createStatement()) {
			String createPokemonTableSql = "CREATE TABLE IF NOT EXISTS POKEMON (NAME VARCHAR(50), TYPE1 VARCHAR(10), TYPE2 VARCHAR(10), TOTAL INTEGER, HP INTEGER, ATTACK INTEGER, DEFENSE INTEGER, SPECIAL_ATTACK INTEGER, SPECIAL_DEFENSE INTEGER, SPEED INTEGER);";
			createPokemonTableStatement.executeUpdate(createPokemonTableSql);
			System.out.println("Table created!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
