package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Person;

public class PhoneBookView extends Application {

	private static final String USER_HOME = "user.home";
	private static final String FILE_PATH = System.getProperty(USER_HOME) + File.separator + "filteredData.csv";
	private static final String FILE_SPILT = ";";
	private TableView<Person> table = new TableView<Person>();
	// for Filter
	private ObservableList<Person> filteredList = FXCollections.observableArrayList();

	private final ObservableList<Person> data = FXCollections.observableArrayList(new Person("Hans", "Meier", "123"),
			new Person("Kurt", "Schumacher", "456"), new Person("Robert", "Kennedy"));
	final HBox hb1 = new HBox();
	final HBox hb2 = new HBox();

	public static void main(String[] args) {
		launch(args);
	}

	public void filterPerson(Person filteringPerson) {
		// method 01
		Stream<Person> personStream = data.stream(); // remember method 01 for exam
		this.filteredList = personStream.filter(p -> p.getFirstName().equals(filteringPerson.getFirstName()))
				.filter(p -> p.getLastName().equals(filteringPerson.getLastName()))
				.collect(Collectors.toCollection(FXCollections::observableArrayList));

		// method 02
//		this.filteredList.clear();// is better than this.filteredList = null;
//		this.filteredList.addAll(data.filtered(p -> p.getLastName().equals(filteringPerson.getLastName())
//				&& p.getFirstName().equals(filteringPerson.getFirstName())));

		this.table.setItems(this.filteredList);

	}

	// use phone number to find firstname and lastname
	public void filterPhoneNumber(String phoneNumber) { // searchPersonButton (Person suchen)

		this.filteredList.clear();// is better than this.filteredList = null;
		Stream<Person> personStream = data.stream();
		this.filteredList = personStream.filter(p -> p.getPhone().equals(phoneNumber))
				.collect(Collectors.toCollection(FXCollections::observableArrayList));
		this.table.setItems(this.filteredList);
	}

	public void exportFilteredListIntoCSV() {
		System.out.println(this.filteredList);
		System.out.println("exportFilteredListIntoCSV");
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
			writer.write("gefilterte Liste\n");
			writer.newLine();
			writer.write("Vorname" + FILE_SPILT + "Nachname" + FILE_SPILT + "Telefonummer");
			writer.newLine();

			for (int i = 0; i < this.filteredList.size(); i++) {
				writer.write(this.filteredList.get(i).saveToCSV());
				if (i != this.filteredList.size()) {
					writer.newLine();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Telefonbuch");
		stage.setWidth(335);
		stage.setHeight(570);
		stage.setResizable(false);

		final Label label = new Label("Telefonbuch");
		label.setFont(new Font("Arial", 20));

		this.table.setEditable(false);
		TableColumn<Person, String> firstNameCol = new TableColumn<>("Vorname");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(CellEditEvent<Person, String> t) {
				((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFirstName(t.getNewValue());
			}
		});

		TableColumn<Person, String> lastNameCol = new TableColumn<>("Nachname");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(CellEditEvent<Person, String> t) {
				((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());
			}
		});

		TableColumn<Person, String> phoneNoCol = new TableColumn<>("Telefonnummer");
		phoneNoCol.setMinWidth(100);
		phoneNoCol.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));

		phoneNoCol.setCellFactory(TextFieldTableCell.forTableColumn());
		phoneNoCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(CellEditEvent<Person, String> t) {
				((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPhone(t.getNewValue());
			}
		});

		this.table.setItems(filteredList);
		this.table.setEditable(true);

		this.filteredList.addAll(this.data);

		this.table.getColumns().add(firstNameCol);
		this.table.getColumns().add(lastNameCol);
		this.table.getColumns().add(phoneNoCol);

		final TextField firstNameField = new TextField();
		firstNameField.setPromptText("Vorname");
		firstNameField.setMaxWidth(firstNameCol.getPrefWidth());

		final TextField lastNameField = new TextField();
		lastNameField.setPromptText("Nachname");
		lastNameField.setMaxWidth(firstNameCol.getPrefWidth());

		// button
		final Button searchPhoneNumberButton = new Button("Telefonnummer suchen");
		searchPhoneNumberButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("searchPhoneNumberButton");
				Person personToSearch = new Person(firstNameField.getText(), lastNameField.getText());
				System.out.println(personToSearch);
				filterPerson(personToSearch);
			}
		});

		final TextField phoneNumberField = new TextField();
		phoneNumberField.setPromptText("Telefonnummer");
		phoneNumberField.setMinWidth(firstNameCol.getPrefWidth() * 2.65);

		final Button searchPersonButton = new Button("Person suchen");
		searchPersonButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("searchPersonButton");
				String phoneNumberToSearch = new String(phoneNumberField.getText());
				filterPhoneNumber(phoneNumberToSearch);
			}
		});

		final Button savePeronBtn = new Button("gefilterete Liste in ein csv-File exportieren");
		savePeronBtn.setOnAction(event -> exportFilteredListIntoCSV());
//
//		savePeronBtn.setOnAction(new EventHandler<ActionEvent>() { // don't forget to delete unused code!!!
//
//			@Override
//			public void handle(ActionEvent event) {
//				exportFilteredListIntoCSV();
//			}
//		});

//		savePeronBtn.setPrefWidth(firstNameCol.getPrefWidth() * 3.8);
		savePeronBtn.setPrefWidth(Double.MAX_VALUE);
		this.hb1.getChildren().addAll(firstNameField, lastNameField, searchPhoneNumberButton);
		this.hb1.setSpacing(3);

		this.hb2.getChildren().addAll(phoneNumberField, searchPersonButton);
		this.hb2.setSpacing(3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, this.table, this.hb1, this.hb2, savePeronBtn);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}

}