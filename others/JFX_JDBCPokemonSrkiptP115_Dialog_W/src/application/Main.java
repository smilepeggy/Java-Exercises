package application;

import java.util.List;

import db.DbDdLStatements;
import db.DbDmLStatements;
import entity.Pokemon;
import entity.Type;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import reader.PokemonCsvReader;
import view.NewPokemonDialog;

public class Main extends Application {
	private static TableView<Pokemon> pokemonTableView;

	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		root.getChildren().addAll(createMenu(), createPokemonTableView());
		updateTableView();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private Node createMenu() {
		Button addBtn = new Button("Add Pokemon");
		addBtn.setOnAction(this::handleAddBtn);
		return addBtn;
	}

	private void handleAddBtn(ActionEvent event) {
		new NewPokemonDialog();
		updateTableView();

	}

	@SuppressWarnings("unchecked")
	private static TableView<Pokemon> createPokemonTableView() {
		pokemonTableView = new TableView<Pokemon>();
		TableColumn<Pokemon, Integer> idKeyColumn = new TableColumn<>("ID_Key");
		TableColumn<Pokemon, Integer> idColumn = new TableColumn<>("ID");
		TableColumn<Pokemon, String> nameColumn = new TableColumn<>("Name");
		TableColumn<Pokemon, Type> typeOneColumn = new TableColumn<>("Type 1");
		TableColumn<Pokemon, Type> typeTwoColumn = new TableColumn<>("Type 2");
		TableColumn<Pokemon, Integer> totalColumn = new TableColumn<>("Total");
		TableColumn<Pokemon, Integer> hpColumn = new TableColumn<>("HP");
		TableColumn<Pokemon, Integer> attackColumn = new TableColumn<>("Attack");
		TableColumn<Pokemon, Integer> defenseColumn = new TableColumn<>("Defense");
		TableColumn<Pokemon, Integer> specialAttackColumn = new TableColumn<>("Special Attack");
		TableColumn<Pokemon, Integer> specialDefenseColumn = new TableColumn<>("Special Defense");
		TableColumn<Pokemon, Integer> speedColumn = new TableColumn<>("Speed");

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		idKeyColumn.setCellValueFactory(new PropertyValueFactory<>("idKey"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		typeOneColumn.setCellValueFactory(new PropertyValueFactory<>("type1"));
		typeTwoColumn.setCellValueFactory(new PropertyValueFactory<>("type2"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		hpColumn.setCellValueFactory(new PropertyValueFactory<>("hp"));
		attackColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));
		defenseColumn.setCellValueFactory(new PropertyValueFactory<>("defense"));
		specialAttackColumn.setCellValueFactory(new PropertyValueFactory<>("specialAttack"));
		specialDefenseColumn.setCellValueFactory(new PropertyValueFactory<>("specialDefense"));
		speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));

		pokemonTableView.getColumns().addAll(idKeyColumn, idColumn, nameColumn, typeOneColumn, typeTwoColumn,
				totalColumn, hpColumn, attackColumn, defenseColumn, specialAttackColumn, specialDefenseColumn,
				speedColumn);
		return pokemonTableView;
	}

	private void updateTableView() {
		pokemonTableView.setItems(FXCollections.observableArrayList(DbDmLStatements.getAllPokemonsFromDatabase()));

	}

	public static void main(String[] args) {
		DbDdLStatements.createPokemonDatabaseTable();
		List<Pokemon> pokemonList = PokemonCsvReader.readPokemonsFromCsv();
		DbDmLStatements.inserNewPokemonIntoDatabase(pokemonList);
		launch(args);
	}
}
