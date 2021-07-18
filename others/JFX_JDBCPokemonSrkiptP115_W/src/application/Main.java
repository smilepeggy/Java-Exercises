package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import db.DbConnection;
import db.DbDmlStatements;
import entity.Pokemon;
import entity.Type;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private static final String FILE_PATH = "res/pokedex.csv";
	private static final String SPILT = ",";
	private ObservableList<Pokemon> pokedexList;
	private ObservableList<Pokemon> pokedexListFiltered = FXCollections.observableArrayList();
	private TableView<Pokemon> pokedexTableView;
	private TextField textFieldName;
	private ComboBox<Type> type1ComboBox;
	private ComboBox<Type> type2ComboBox;
	private NumberField textFieldNumber;
	private ComboBox<String> attributeCb;
	private Attribute[] attributes;
	private ComboBox<Character> operatorCb;

	public static void main(String[] args) {
		launch(args);
		DbConnection.getConnection();
//		DbDdlStatements.createPokemonDatabaseTable();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		Label resultLabel = new Label("Result: ");

		this.textFieldName = new TextField();
		this.textFieldName.setPromptText("Name");
		this.textFieldNumber = new NumberField();
		textFieldNumber.setPromptText("Value");
// ComboBox
		this.type1ComboBox = new ComboBox<>();
		this.type2ComboBox = new ComboBox<>();
		Type[] typeValues = Type.values();
		for (Type values : typeValues) {
			this.type1ComboBox.getItems().add(values);
			this.type2ComboBox.getItems().add(values);
		}
//		type1ComboBox.getSelectionModel().selectFirst();
//		type2ComboBox.getSelectionModel().selectFirst();
		this.attributeCb = new ComboBox<>();
		this.attributes = Attribute.values();
		attributeCb.getItems().add("");
		for (Attribute attribute : attributes) {
			attributeCb.getItems().add(attribute.getAttributeName());
		}

		this.operatorCb = new ComboBox<>();
		operatorCb.getItems().addAll('>', '<', '=');
		operatorCb.getSelectionModel().select(2);
// Button
		Button filterBtn = new Button("Filter");

		filterBtn.setOnAction(event -> {
			filterPokedex();
			resultLabel.setText("Result: " + this.pokedexListFiltered.size());
		});

// table view
		this.pokedexTableView = new TableView<Pokemon>();

		TableColumn<Pokemon, String> nameCol = new TableColumn<>("Name");
		TableColumn<Pokemon, Type> type1Col = new TableColumn<>("Type1");
		TableColumn<Pokemon, Type> type2Col = new TableColumn<>("Type2");
		TableColumn<Pokemon, Integer> totalCol = new TableColumn<>("Total");
		TableColumn<Pokemon, Integer> hpCol = new TableColumn<>("HP");
		TableColumn<Pokemon, Integer> attackCol = new TableColumn<>("Attack");
		TableColumn<Pokemon, Integer> defenseCol = new TableColumn<>("Defense");
		TableColumn<Pokemon, Integer> specialAttackCol = new TableColumn<>("Special Attack");
		TableColumn<Pokemon, Integer> specialDefenseCol = new TableColumn<>("Special Defense");
		TableColumn<Pokemon, Integer> speedCol = new TableColumn<>("Speed");

		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		type1Col.setCellValueFactory(new PropertyValueFactory<>("type1"));
		type2Col.setCellValueFactory(new PropertyValueFactory<>("type2"));
		totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		hpCol.setCellValueFactory(new PropertyValueFactory<>("hp"));
		attackCol.setCellValueFactory(new PropertyValueFactory<>("attack"));
		defenseCol.setCellValueFactory(new PropertyValueFactory<>("defense"));
		specialAttackCol.setCellValueFactory(new PropertyValueFactory<>("specialAttack"));
		specialDefenseCol.setCellValueFactory(new PropertyValueFactory<>("specialDefense"));
		speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));

		pokedexTableView.getColumns().add(nameCol);
		pokedexTableView.getColumns().add(type1Col);
		pokedexTableView.getColumns().add(type2Col);
		pokedexTableView.getColumns().add(totalCol);
		pokedexTableView.getColumns().add(hpCol);
		pokedexTableView.getColumns().add(attackCol);
		pokedexTableView.getColumns().add(defenseCol);
		pokedexTableView.getColumns().add(specialAttackCol);
		pokedexTableView.getColumns().add(specialDefenseCol);
		pokedexTableView.getColumns().add(speedCol);

		readPokedexCSV(FILE_PATH);
		DbDmlStatements.insertNewPokemonIntoDatabase(this.pokedexList);

		pokedexTableView.setItems(this.pokedexList);
		resultLabel.setText("Result: " + this.pokedexList.size());

		hBox.getChildren().addAll(textFieldName, type1ComboBox, type2ComboBox, attributeCb, operatorCb, textFieldNumber,
				filterBtn);
		vBox.getChildren().addAll(hBox, resultLabel, pokedexTableView);

		Scene scene = new Scene(vBox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void readPokedexCSV(String filePath) { // if ObservableList<Pokedex> is defined as global then here just
													// return void
		this.pokedexList = FXCollections.observableArrayList();

		try {
			FileInputStream fileInputStream = new FileInputStream(new File(filePath));
			CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
			decoder.onMalformedInput(CodingErrorAction.IGNORE);
			InputStreamReader reader = new InputStreamReader(fileInputStream, decoder);
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line = "";
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				Type type1;
				Type type2;
				String[] items = line.split(SPILT);
				if (items.length != 11) {
					System.out.println("Illegal line: " + items);
					continue;
				}
				type1 = items[2].equals("") ? Type.NONE : Type.valueOf(items[2].toUpperCase());
				type2 = items[3].isEmpty() ? Type.NONE : Type.valueOf(items[3].toUpperCase());

				this.pokedexList.add(new Pokemon(items[1], type1, type2, Integer.parseInt(items[4]),
						Integer.parseInt(items[5]), Integer.parseInt(items[6]), Integer.parseInt(items[7]),
						Integer.parseInt(items[8]), Integer.parseInt(items[9]), Integer.parseInt(items[10])));
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void filterPokedex() {
		// pokemonStream
		Stream<Pokemon> pokemonStream = this.pokedexList.stream();
		pokemonStream = pokemonStream.filter(p -> {
			return p.getName().toLowerCase().startsWith(this.textFieldName.getText().toLowerCase());
		});
		if (type1ComboBox.getSelectionModel().getSelectedItem() != null) {
			pokemonStream = pokemonStream.filter(p -> p.getType1() == type1ComboBox.getValue());
		}
		if (type2ComboBox.getSelectionModel().getSelectedItem() != null) {
			pokemonStream = pokemonStream.filter(p -> p.getType2() == type2ComboBox.getValue());
		}
		if ((type1ComboBox.getSelectionModel().getSelectedItem() == Type.NONE)
				&& (type2ComboBox.getSelectionModel().getSelectedItem() == Type.NONE)) {
			pokemonStream = this.pokedexList.stream();
		}

		final String attribute = attributeCb.getSelectionModel().getSelectedItem();
		final char operator = operatorCb.getSelectionModel().getSelectedItem();
		final int value = textFieldNumber.getNumber();
		if ((attribute != null) && (operator != ' ') && (value != 0)) {
			pokemonStream = filterAttribute(pokemonStream, attribute, operator, value);
		}
		this.pokedexListFiltered = pokemonStream
				.collect(Collectors.toCollection(() -> FXCollections.observableArrayList())); // same as
																								// FXCollections::observableArrayList)
		this.pokedexTableView.setItems(this.pokedexListFiltered);
	}

	private Stream<Pokemon> filterAttribute(Stream<Pokemon> pokemonStream, String attribute, char operator, int value) {

		switch (attribute) {
		case "Total":
			if (operator == '=') {
				pokemonStream = pokemonStream.filter(p -> p.getTotal() == value);
			}
			if (operator == '>') {
				pokemonStream = pokemonStream.filter(p -> p.getTotal() > value);
			}
			if (operator == '<') {
				pokemonStream = pokemonStream.filter(p -> p.getTotal() < value);
			}
			break;
		case "HP":
			if (operator == '=') {
				pokemonStream = pokemonStream.filter(p -> p.getHp() == value);
			}
			if (operator == '>') {
				pokemonStream = pokemonStream.filter(p -> p.getHp() > value);
			}
			if (operator == '<') {
				pokemonStream = pokemonStream.filter(p -> p.getHp() < value);
			}
			break;
		case "Attack":
			if (operator == '=') {
				pokemonStream = pokemonStream.filter(p -> p.getAttack() == value);
			}
			if (operator == '>') {
				pokemonStream = pokemonStream.filter(p -> p.getAttack() > value);
			}
			if (operator == '<') {
				pokemonStream = pokemonStream.filter(p -> p.getAttack() < value);
			}
			break;
		case "Defense":
			if (operator == '=') {
				pokemonStream = pokemonStream.filter(p -> p.getDefense() == value);
			}
			if (operator == '>') {
				pokemonStream = pokemonStream.filter(p -> p.getDefense() > value);
			}
			if (operator == '<') {
				pokemonStream = pokemonStream.filter(p -> p.getDefense() < value);
			}
			break;
		case "Special Attack":
			if (operator == '=') {
				pokemonStream = pokemonStream.filter(p -> p.getSpecialAttack() == value);
			}
			if (operator == '>') {
				pokemonStream = pokemonStream.filter(p -> p.getSpecialAttack() > value);
			}
			if (operator == '<') {
				pokemonStream = pokemonStream.filter(p -> p.getSpecialAttack() < value);
			}
			break;
		case "Special Defense":
			if (operator == '=') {
				pokemonStream = pokemonStream.filter(p -> p.getSpecialDefense() == value);
			}
			if (operator == '>') {
				pokemonStream = pokemonStream.filter(p -> p.getSpecialDefense() > value);
			}
			if (operator == '<') {
				pokemonStream = pokemonStream.filter(p -> p.getSpecialDefense() < value);
			}
			break;
		case "Speed":
			if (operator == '=') {
				pokemonStream = pokemonStream.filter(p -> p.getSpeed() == value);
			}
			if (operator == '>') {
				pokemonStream = pokemonStream.filter(p -> p.getSpeed() > value);
			}
			if (operator == '<') {
				pokemonStream = pokemonStream.filter(p -> p.getSpeed() < value);
			}
			break;
		}
		return pokemonStream;

	}

}
