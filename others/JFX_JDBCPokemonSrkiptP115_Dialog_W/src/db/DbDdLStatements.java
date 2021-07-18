package db;

import java.sql.SQLException;
import java.sql.Statement;

public class DbDdLStatements {

	public DbDdLStatements() {
	}

	public static void createPokemonDatabaseTable() {
		try (Statement createPokemonTableStatement = DbConnection.getConnection().createStatement()) {
			String createPokemonTableSql = "CREATE TABLE IF NOT EXISTS POKEMON "
					+ "(id_Key INTEGER AUTO_INCREMENT NOT NULL, id INTEGER, name VARCHAR(50), type1 VARCHAR(20), type2 VARCHAR(20), "
					+ "total INTEGER, hp INTEGER, attack INTEGER, defense INTEGER, "
					+ "special_attack INTEGER, special_defense INTEGER, speed INTEGER, PRIMARY KEY (id_Key));";
			createPokemonTableStatement.execute(createPokemonTableSql);
			System.out.println("Table created! ");
		} catch (SQLException ex) {
			System.err.println("Fail to create Pokemon table!");
			ex.printStackTrace();
		}
	}

}
