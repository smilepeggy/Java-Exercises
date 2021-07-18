package window;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import application.Person;
import application.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class ImportWindow extends WindowManagement {
	private static final String PERSON_FILE_PATH = "res/Person.txt";
	private static final String DATA_SPLIT = ",";
	private ListView<Person> personListView;
	private Button importBtn;
	private Button saveBtn;
	private HBox hbox;

	private ObservableList<Person> personList;

	public ImportWindow(String text) {
		super(text, 600, 500);
		this.personList = FXCollections.observableArrayList();
		this.hbox = new HBox();
		this.personListView = new ListView<Person>();
		this.importBtn = new Button("Import Person");
		this.saveBtn = new Button("Save Peron data");
		importBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ImportWindow.this.personList = readPersonData(PERSON_FILE_PATH);
				for (Person person : personList) {
					ImportWindow.this.personListView.getItems().add(person);
				}
			}
		});

		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (Person person : ImportWindow.this.personList) {
					Storage.personList.add(person);
				}

				System.out.println("Save Person Data");
			}

		});
		hbox.getChildren().addAll(this.importBtn, this.saveBtn);
		rootNode.setCenter(personListView);
		rootNode.setTop(hbox);
	}

	private ObservableList<Person> readPersonData(String filePath) {
//		ObservableList<Person> personList = FXCollections.observableArrayList();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(DATA_SPLIT);
				if (items.length != 5) {
					System.out.println("Illigal line: " + items);
				}
				this.personList.add(new Person(items[0].trim(), items[1].trim(), items[2].trim(), items[3].trim(),
						Integer.parseInt(items[4].trim())));
				System.out.println("Person: " + items[0].trim() + " " + items[1].trim() + " imported!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.personList;
	}
}
