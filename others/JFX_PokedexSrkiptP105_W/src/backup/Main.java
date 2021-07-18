package backup;
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
	private ObservableList<Pokedex> pokedexList;
	private ObservableList<Pokedex> pokedexListFiltered = FXCollections.observableArrayList();
	private TableView<Pokedex> pokedexTableView;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		Label resultLabel = new Label("Result: ");
		TextField textFieldName = new TextField();
		textFieldName.setPromptText("Name");
		TextField textFieldNumber = new TextField();
		textFieldNumber.setPromptText("Value");
// ComboBox
		ComboBox<Type> type1ComboBox = new ComboBox<>();
		ComboBox<Type> type2ComboBox = new ComboBox<>();
		Type[] typeValues = Type.values();
		for (Type values : typeValues) {
			type1ComboBox.getItems().add(values);
			type2ComboBox.getItems().add(values);
		}
//		type1ComboBox.getSelectionModel().selectFirst();
//		type2ComboBox.getSelectionModel().selectFirst();
		ComboBox<String> attributeCb = new ComboBox<>();
		Attribute[] attributes = Attribute.values();
		for (Attribute attribute : attributes) {
			attributeCb.getItems().add(attribute.getAttributeName());
		}
//		attributeCb.getItems().addAll("Total", "HP", "Attack", "Defense", "Special Attack", "Special Defense", "Speed");

		ComboBox<String> operator = new ComboBox<>();
		operator.getItems().addAll(">", "<", "=");
// Button
		Button filterBtn = new Button("Filter");

		filterBtn.setOnAction(event -> {

			Stream<Pokedex> pokemonStream = this.pokedexList.stream();
			pokemonStream = pokemonStream.filter(p -> {
//				System.out.println("filter");
				return p.getName().toLowerCase().startsWith(textFieldName.getText().toLowerCase());

			});
//			pokemonStream = pokemonStream
//					.filter(p -> p.getName().toLowerCase().startsWith(textFieldName.getText().toLowerCase()));
//Type cType1=type1ComboBox.getSelectionModel().getSelectedItem() ;
			if (type1ComboBox.getSelectionModel().getSelectedItem() != null) {
				pokemonStream = pokemonStream.filter(p -> p.getType1() == type1ComboBox.getValue());
			}
			if (type2ComboBox.getSelectionModel().getSelectedItem() != null) {
				pokemonStream = pokemonStream.filter(p -> p.getType2() == type2ComboBox.getValue());
			}

//			String attribute = attributeCb.getSelectionModel().getSelectedItem();
//			System.out.println(attribute);
//			switch (attribute) {
//
//			}
			// lambda and method reference are just for FunctionalInterface
			// every Lambda could be written in Method R.
			// look into toCollection()
			this.pokedexListFiltered = pokemonStream
					.collect(Collectors.toCollection(() -> FXCollections.observableArrayList())); // same as
																									// FXCollections::observableArrayList)
			this.pokedexTableView.setItems(this.pokedexListFiltered);
		});
// table view
		this.pokedexTableView = new TableView<Pokedex>();

		TableColumn<Pokedex, String> nameCol = new TableColumn<>("Name");
		TableColumn<Pokedex, Type> type1Col = new TableColumn<>("Type1");
		TableColumn<Pokedex, Type> type2Col = new TableColumn<>("Type2");
		TableColumn<Pokedex, Integer> totalCol = new TableColumn<>("Total");
		TableColumn<Pokedex, Integer> hpCol = new TableColumn<>("HP");
		TableColumn<Pokedex, Integer> attackCol = new TableColumn<>("Attack");
		TableColumn<Pokedex, Integer> defenseCol = new TableColumn<>("Defense");
		TableColumn<Pokedex, Integer> specialAttackCol = new TableColumn<>("Special Attack");
		TableColumn<Pokedex, Integer> specialDefenseCol = new TableColumn<>("Special Defense");
		TableColumn<Pokedex, Integer> speedCol = new TableColumn<>("Speed");

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
		pokedexTableView.setItems(this.pokedexList);

		hBox.getChildren().addAll(textFieldName, type1ComboBox, type2ComboBox, attributeCb, operator, textFieldNumber,
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

				this.pokedexList
						.add(new Pokedex(Integer.parseInt(items[0]), items[1], type1, type2, Integer.parseInt(items[4]),
								Integer.parseInt(items[5]), Integer.parseInt(items[6]), Integer.parseInt(items[7]),
								Integer.parseInt(items[8]), Integer.parseInt(items[9]), Integer.parseInt(items[10])));
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
