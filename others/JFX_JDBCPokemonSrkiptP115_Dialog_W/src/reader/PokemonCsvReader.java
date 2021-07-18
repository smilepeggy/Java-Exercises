package reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Pokemon;
import entity.Type;

public class PokemonCsvReader {

	private static final String FILE_PATH = "res/pokedex.csv";
	private static final String SPILT = ",";

	public PokemonCsvReader() {
	}

//	public static List<Pokemon> readPokemonsFromCsv() { // charset error
//		List<Pokemon> pokemonList = new ArrayList<>();
//		try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH), StandardCharsets.UTF_8)) {
//			String line = "";
//			String header = reader.readLine();
//			while ((line = reader.readLine()) != null) {
//				String[] pokemonAttributes = line.split(SPILT);
//				Pokemon pokemon = new Pokemon(Integer.parseInt(pokemonAttributes[0]), pokemonAttributes[1],
//						Type.fromString(pokemonAttributes[2]), Type.fromString(pokemonAttributes[3]),
//						Integer.parseInt(pokemonAttributes[4]), Integer.parseInt(pokemonAttributes[5]),
//						Integer.parseInt(pokemonAttributes[6]), Integer.parseInt(pokemonAttributes[7]),
//						Integer.parseInt(pokemonAttributes[8]), Integer.parseInt(pokemonAttributes[9]),
//						Integer.parseInt(pokemonAttributes[10]));
//				pokemonList.add(pokemon);
//				System.out.println("Pokemon " + pokemon.getId() + " " + pokemon.getName() + " loaded!");
//			}
//		} catch (FileNotFoundException ex) {
//			System.err.println("File is not found!");
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//
//		return pokemonList;
//	}

	public static List<Pokemon> readPokemonsFromCsv() {
		List<Pokemon> pokemonList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line = "";
			String header = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] pokemonAttributes = line.split(SPILT);
				Pokemon pokemon = new Pokemon(Integer.parseInt(pokemonAttributes[0]), pokemonAttributes[1],
						Type.fromString(pokemonAttributes[2]), Type.fromString(pokemonAttributes[3]),
						Integer.parseInt(pokemonAttributes[4]), Integer.parseInt(pokemonAttributes[5]),
						Integer.parseInt(pokemonAttributes[6]), Integer.parseInt(pokemonAttributes[7]),
						Integer.parseInt(pokemonAttributes[8]), Integer.parseInt(pokemonAttributes[9]),
						Integer.parseInt(pokemonAttributes[10]));
				pokemonList.add(pokemon);
				System.out.println("Pokemon " + pokemon.getId() + " " + pokemon.getName() + " loaded!");
			}
		} catch (FileNotFoundException ex) {
			System.err.println("Die angegebene CSV Datei kann nicht gefunden werden!");
			ex.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		return pokemonList;
	}
}
