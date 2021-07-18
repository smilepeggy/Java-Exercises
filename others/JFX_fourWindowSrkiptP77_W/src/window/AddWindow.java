package window;

import application.Car;
import application.Person;
import application.Storage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddWindow extends WindowManagement {

	// person
	private ComboBox<Person> personCB = new ComboBox<>(); // write here or in constructor
	private VBox personVB = new VBox();
//	private TextField firstNameText = new TextField();
//	private TextField lastNameText = new TextField();
//	private DatePicker birthdayText = new DatePicker();
//	private TextField cityText = new TextField();
//	private TextField phoneNumberText = new TextField();
	private Button personSave = new Button("Create Person");

	// for testing person
	private TextField firstNameText = new TextField("Peggy");
	private TextField lastNameText = new TextField("Wang");
	private DatePicker birthdayText = new DatePicker();
	private TextField cityText = new TextField("KAO");
	private TextField phoneNumberText = new TextField("134566778");

	// car
	private VBox carVB = new VBox();
	private Label addCarLabel = new Label("Add car to Person");
//	private TextField brandText = new TextField();
//	private TextField modelText = new TextField();
//	private TextField colorText = new TextField();
//	private TextField mileageText = new TextField();
	private ComboBox<String> fuelCB = new ComboBox<>();
//	private TextField producedYearText = new TextField();
	private Button carSave = new Button("Save Car to Person");

	// for testing car
	private TextField brandText = new TextField("VW");
	private TextField modelText = new TextField("Passat");
	private TextField colorText = new TextField("Red");
	private TextField mileageText = new TextField("30000");
	private TextField producedYearText = new TextField("2012");

	public AddWindow(String text) {
		super(text, 600, 400);

		// person
		if ((Storage.personList.size()) != 0) {
			System.out.println("size not 0");
			for (Person person : Storage.personList) {
				this.personCB.getItems().add(person);
			}
		}
		firstNameText.setPromptText("First Name");
		lastNameText.setPromptText("Last Name");
		birthdayText.setPromptText("Birthday");
		cityText.setPromptText("City");
		phoneNumberText.setPromptText("Phone Number");
		personSave = new Button("Save");
		personVB.getChildren().addAll(this.personCB, firstNameText, lastNameText, birthdayText, cityText,
				phoneNumberText, personSave);
		personSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(birthdayText.getValue());
				Person person = new Person(firstNameText.getText(), lastNameText.getText(),
						birthdayText.getValue().toString(), cityText.getText(),
						Integer.parseInt(phoneNumberText.getText()));
				personCB.getItems().add(person);
				Storage.personList.add(person);
				System.out.println("Person " + person);
			}

			// need to doValidate
		});

		// car
		brandText.setPromptText("Brand");
		modelText.setPromptText("Model");
		colorText.setPromptText("Color");
		mileageText.setPromptText("Mileage");
		fuelCB.getItems().addAll("Gasonline", "Diesel", "Electric");
		producedYearText.setPromptText("producedYear");
		carVB.getChildren().addAll(addCarLabel, personCB, brandText, modelText, colorText, mileageText, fuelCB,
				producedYearText, carSave);
		carSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Car car = new Car(personCB.getValue(), brandText.getText(), modelText.getText(), colorText.getText(),
						Integer.parseInt(mileageText.getText()), fuelCB.getValue(),
						Integer.parseInt(producedYearText.getText()));
				Storage.carList.add(car);
				System.out.println(Storage.carList);
			}
		});

		rootNode.setTop(personVB);
		rootNode.setCenter(carVB);
	}

}
