package view;

import java.util.ArrayList;
import java.util.List;

import db.DbDmLStatements;
import entity.Pokemon;
import entity.Type;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NewPokemonDialog {

	private Stage stage;
	private TextField idField;
	private TextField nameField;
	private ComboBox<Type> type1CB;
	private ComboBox<Type> type2CB;
	private TextField totalField;
	private TextField hpField;
	private TextField attackField;
	private TextField defenseField;
	private TextField specialAttackField;
	private TextField specialDefenseField;
	private TextField speedField;
	private List<Pokemon> pokemonList;

	public NewPokemonDialog() {
		init();
		System.out.println("Call new Pokemon Dialog");
	}

	private void init() {
		this.stage = new Stage();
		this.stage.setTitle("New Pokemon");
		this.stage.setResizable(false);
		this.stage.setScene(createScene());
//		this.stage.show();
		this.stage.showAndWait();

	}

	private Scene createScene() {
		GridPane gridPaneView = new GridPane();
		int count = 0;
		this.idField = new TextField();
		gridPaneView.add(new Label("ID: "), 0, count);
		gridPaneView.add(this.idField, 1, count);
		count++;
		this.nameField = new TextField();
		gridPaneView.add(new Label("Name: "), 0, count);
		gridPaneView.add(this.nameField, 1, count);
		count++;
		this.type1CB = new ComboBox<>();
		this.type1CB.getItems().addAll(Type.values());
		this.type1CB.getSelectionModel().select(Type.NONE);
		gridPaneView.add(new Label("Type 1: "), 0, count);
		gridPaneView.add(this.type1CB, 1, count);
		count++;
		this.type2CB = new ComboBox<>();
		this.type2CB.getItems().addAll(Type.values());
		this.type2CB.getSelectionModel().select(Type.NONE);
		gridPaneView.add(new Label("Type 2: "), 0, count);
		gridPaneView.add(this.type2CB, 1, count);
		count++;
		this.totalField = new TextField();
		gridPaneView.add(new Label("Total: "), 0, count);
		gridPaneView.add(this.totalField, 1, count);
		count++;
		this.hpField = new TextField();
		gridPaneView.add(new Label("Hp: "), 0, count);
		gridPaneView.add(this.hpField, 1, count);
		count++;
		this.attackField = new TextField();
		gridPaneView.add(new Label("Attack: "), 0, count);
		gridPaneView.add(this.attackField, 1, count);
		count++;
		this.defenseField = new TextField();
		gridPaneView.add(new Label("Defense: "), 0, count);
		gridPaneView.add(this.defenseField, 1, count);
		count++;
		this.specialAttackField = new TextField();
		gridPaneView.add(new Label("special Attack: "), 0, count);
		gridPaneView.add(this.specialAttackField, 1, count);
		count++;
		this.specialDefenseField = new TextField();
		gridPaneView.add(new Label("special Defense: "), 0, count);
		gridPaneView.add(this.specialDefenseField, 1, count);
		count++;
		this.speedField = new TextField();
		gridPaneView.add(new Label("Speed: "), 0, count);
		gridPaneView.add(this.speedField, 1, count);
		count++;

		Button saveBtn = new Button("Save");
//		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				Pokemon pokemon = new Pokemon(textFieldToInt(idField), nameField.toString(),
//						type1CB.getSelectionModel().getSelectedItem(), type2CB.getSelectionModel().getSelectedItem(),
//						textFieldToInt(totalField), textFieldToInt(hpField), textFieldToInt(attackField),
//						textFieldToInt(defenseField), textFieldToInt(specialAttackField),
//						textFieldToInt(specialDefenseField), textFieldToInt(speedField));
//				pokemonList.add(pokemon);
//				DbDmLStatements.inserNewPokemonIntoDatabase(pokemonList);
//			}
//		});
		saveBtn.setOnAction(this::handleAddBtn);
		gridPaneView.add(saveBtn, 1, count);
		return new Scene(gridPaneView, 400, 400);

	}

	private void handleAddBtn(ActionEvent event) {
		this.pokemonList = new ArrayList<>();
		Pokemon pokemon = new Pokemon(textFieldToInt(this.idField), this.nameField.getText(),
				this.type1CB.getSelectionModel().getSelectedItem(), this.type2CB.getSelectionModel().getSelectedItem(),
				textFieldToInt(this.totalField), textFieldToInt(this.hpField), textFieldToInt(this.attackField),
				textFieldToInt(this.defenseField), textFieldToInt(this.specialAttackField),
				textFieldToInt(this.specialDefenseField), textFieldToInt(this.speedField));
		this.pokemonList.add(pokemon);
		System.out.println("Pokemon " + textFieldToInt(this.idField) + " " + this.nameField.getText() + " loaded!");

		DbDmLStatements.inserNewPokemonIntoDatabase(this.pokemonList);
		this.stage.close();
	}

	private int textFieldToInt(TextField text) {
		try {
			return Integer.parseInt(text.getText());
		} catch (NumberFormatException ex) {
			System.err.println("Please enter numbers!");
			throw new RuntimeException(ex); // why this one?
		}
	}
}
