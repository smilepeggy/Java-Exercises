package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entity.Pokemon;
import entity.Type;
import javafx.collections.ObservableList;

public class DbDmlStatements {

	public DbDmlStatements() {
	}

//	public static List<Pokemon> getAllPokemonFromDatabase() {
//		List<Pokemon> pokemonList = new ArrayList<>();
//		Type type1;
//		Type type2;
//	
//		try (Statement selectAllPokemonStatement = DbConnection.getConnection().createStatement()) {
//			String selectAllPokemonSql = "SELECT * FROM POKEMON";
//			ResultSet allPokemonFromResultSet = selectAllPokemonStatement.executeQuery(selectAllPokemonSql);
//			while (allPokemonFromResultSet.next()) {
//type1=allPokemonFromResultSet.getString("TYPE1").equals("")? Type.NONE:Type.valueOf(arg0)
//				pokemonList.add(new Pokemon(allPokemonFromResultSet.getString("NAME"),
//						allPokemonFromResultSet.getString("TYPE1"), Type.valueOf(allPokemonFromResultSet.getString("TYPE1"),
//						allPokemonFromResultSet.getString("TOTAL"), allPokemonFromResultSet.getString("HP"),
//						allPokemonFromResultSet.getString("ATTACK"), allPokemonFromResultSet.getString("DEFENSE"),
//						allPokemonFromResultSet.getString("SPECIAL_ATTACK"),
//						allPokemonFromResultSet.getString("SPECIAL_DEFENSE"),
//						allPokemonFromResultSet.getString("SPEED")));
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//		}
//	}

	public static void getAllPokemonsFromDatabase() {
		List<Pokemon> pokemonList = new ArrayList<>();

		try (Statement selectAllPokemonStatement = DbConnection.getConnection().createStatement()) {
			String selectAllPokemonSql = "SELECT * FROM POKEMON";
			ResultSet allPokemonFromResultSet = selectAllPokemonStatement.executeQuery(selectAllPokemonSql);

			while (allPokemonFromResultSet.next()) {
				pokemonList.add(new Pokemon(allPokemonFromResultSet.getString("NAME"),
						Type.valueOf(allPokemonFromResultSet.getString("TYPE1")),
						Type.valueOf(allPokemonFromResultSet.getString("TYPE2")),
						allPokemonFromResultSet.getInt("TOTAL"), allPokemonFromResultSet.getInt("HP"),
						allPokemonFromResultSet.getInt("ATTACK"), allPokemonFromResultSet.getInt("DEFENSE"),
						allPokemonFromResultSet.getInt("SPECIAL_ATTACK"),
						allPokemonFromResultSet.getInt("SPECIAL_DEFENSE"), allPokemonFromResultSet.getInt("SPEED")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void insertNewPokemonIntoDatabase(ObservableList<Pokemon> pokedexList) {

		try (PreparedStatement insertNewPokemonStatement = DbConnection.getConnection().prepareStatement(
				"INSERT INTO POKEMON (NAME , TYPE1 , TYPE2 , TOTAL , HP , ATTACK , DEFENSE , SPECIAL_ATTACK , SPECIAL_DEFENSE , SPEED ) VALUES (?,?,?,?,?,?,?,?,?,?);")) {

			Iterator<Pokemon> iterator = pokedexList.iterator();
			while (iterator.hasNext()) {
				Pokemon pokemon = iterator.next();
				insertNewPokemonStatement.setString(1, pokemon.getName());
				insertNewPokemonStatement.setString(2, pokemon.getType1().name());
				insertNewPokemonStatement.setString(3, pokemon.getType2().name());
				insertNewPokemonStatement.setInt(4, pokemon.getTotal());
				insertNewPokemonStatement.setInt(5, pokemon.getHp());
				insertNewPokemonStatement.setInt(6, pokemon.getAttack());
				insertNewPokemonStatement.setInt(7, pokemon.getDefense());
				insertNewPokemonStatement.setInt(8, pokemon.getSpecialAttack());
				insertNewPokemonStatement.setInt(9, pokemon.getSpecialDefense());
				insertNewPokemonStatement.setInt(10, pokemon.getSpeed());
				insertNewPokemonStatement.execute();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
