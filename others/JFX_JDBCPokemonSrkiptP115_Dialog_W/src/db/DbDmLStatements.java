package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Pokemon;
import entity.Type;

public class DbDmLStatements {

	public DbDmLStatements() {
	}

	public static void deletePokemonFromDatabase(Pokemon pokemon) {
		String deletepokemonSql = "DELETE FROM pokemon WHERE id=? AND name=?;";
		try (PreparedStatement deletePokemonStatement = DbConnection.getConnection()
				.prepareStatement(deletepokemonSql)) {
			deletePokemonStatement.setInt(1, pokemon.getId());
			deletePokemonStatement.setString(2, pokemon.getName());
			deletePokemonStatement.executeUpdate();
		} catch (SQLException ex) {
			System.err.println("Fail to delete Pokemon!");
		}
	}

	public static void updatePokemonInDAtabase(Pokemon pokemonBeforeUpdate, Pokemon pokemonToUpdate) {
		String udpatePokemonSql = "UPDATE POKEMON SET id=?, name=?, type1=?, type2=?, total=?, hp=?, "
				+ "attack=?, defense=?, special_attack=?, special_defense=?, speed=?, " + "WHERE id=?, AND name=?;";
		try (PreparedStatement updatePokemonStatement = DbConnection.getConnection()
				.prepareStatement(udpatePokemonSql)) {
			updatePokemonStatement.setInt(1, pokemonToUpdate.getId());
			updatePokemonStatement.setString(2, pokemonToUpdate.getName());
			updatePokemonStatement.setString(3, pokemonToUpdate.getType1().name());
			updatePokemonStatement.setString(4, pokemonToUpdate.getType2().name());
			updatePokemonStatement.setInt(5, pokemonToUpdate.getTotal());
			updatePokemonStatement.setInt(6, pokemonToUpdate.getHp());
			updatePokemonStatement.setInt(7, pokemonToUpdate.getAttack());
			updatePokemonStatement.setInt(8, pokemonToUpdate.getDefense());
			updatePokemonStatement.setInt(9, pokemonToUpdate.getSpecialAttack());
			updatePokemonStatement.setInt(10, pokemonToUpdate.getSpecialDefense());
			updatePokemonStatement.setInt(11, pokemonToUpdate.getSpeed());
			updatePokemonStatement.setInt(12, pokemonBeforeUpdate.getSpeed());
			updatePokemonStatement.setString(13, pokemonBeforeUpdate.getName());
			updatePokemonStatement.executeUpdate();
		} catch (SQLException ex) {
			System.err.println("Fail to update Pokemon!");
			ex.printStackTrace();
		}
	}

	public static List<Pokemon> getAllPokemonsFromDatabase() {
		List<Pokemon> pokemonsList = new ArrayList<>();
		try (Statement getAllPokemonsStatement = DbConnection.getConnection().createStatement()) {
			String getAllPokemonsSql = "SELECT * FROM pokemon;";
			ResultSet getAllPokemonResults = getAllPokemonsStatement.executeQuery(getAllPokemonsSql);
			while (getAllPokemonResults.next()) {
				Pokemon pokemon = new Pokemon(getAllPokemonResults.getInt("id"), getAllPokemonResults.getString("NAME"),
						Type.fromString(getAllPokemonResults.getString("type1")),
						Type.fromString(getAllPokemonResults.getString("type2")), getAllPokemonResults.getInt("total"),
						getAllPokemonResults.getInt("hp"), getAllPokemonResults.getInt("attack"),
						getAllPokemonResults.getInt("defense"), getAllPokemonResults.getInt("special_attack"),
						getAllPokemonResults.getInt("special_defense"), getAllPokemonResults.getInt("speed"));
				pokemonsList.add(pokemon);
			}
		} catch (SQLException ex) {
			System.err.println("Fail to read Pokemon!");
		}
		return pokemonsList;
	}

	public static void inserNewPokemonIntoDatabase(List<Pokemon> pokemonList) {

		pokemonList.forEach(pokemon -> {
			String insertPokemonSql = "INSERT INTO pokemon (id,name,type1,type2,total,hp,attack,defense,special_attack,special_defense,speed) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
			try (PreparedStatement insertPokemonIntoDatabaseStatement = DbConnection.getConnection()
					.prepareStatement(insertPokemonSql)) {
				insertPokemonIntoDatabaseStatement.setInt(1, pokemon.getId());
				insertPokemonIntoDatabaseStatement.setString(2, pokemon.getName());
				insertPokemonIntoDatabaseStatement.setString(3, pokemon.getType1().name());
				insertPokemonIntoDatabaseStatement.setString(4, pokemon.getType2().name());
				insertPokemonIntoDatabaseStatement.setInt(5, pokemon.getTotal());
				insertPokemonIntoDatabaseStatement.setInt(6, pokemon.getHp());
				insertPokemonIntoDatabaseStatement.setInt(7, pokemon.getAttack());
				insertPokemonIntoDatabaseStatement.setInt(8, pokemon.getDefense());
				insertPokemonIntoDatabaseStatement.setInt(9, pokemon.getSpecialAttack());
				insertPokemonIntoDatabaseStatement.setInt(10, pokemon.getSpecialDefense());
				insertPokemonIntoDatabaseStatement.setInt(11, pokemon.getSpeed());

				int updateValue = insertPokemonIntoDatabaseStatement.executeUpdate();
//				System.out.println(updateValue + " Updated!");
			} catch (SQLException ex) {
				System.err.println("Fail to load Pokemons in Database!");

			}
		});
	}
}
