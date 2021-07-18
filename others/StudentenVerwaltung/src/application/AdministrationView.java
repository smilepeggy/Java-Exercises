package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

public class AdministrationView extends GridPane {

	public static final String FILE_BEGINNING = ".\\Studenten\\";
	public static final String FILE_PREFIX = "Student";
	private static final String FILE_EXTENSION = ".csv";

	private StudentsView studentsView;
	private Label statusBarLbl = createLabel("Statuszeile: leer");
	private TextField studentMatriculationNumberTF = createTextField();
	private TextField studentNameTF = createTextField();
	private TextField studentFirstnameTF = createTextField();
	private TextField studentStreetTF = createTextField();
	private TextField studentResidenceTF = createTextField();
	private TextField studentPhoneTF = createTextField();

	public AdministrationView() {
		createStudentView();
		createView();
	}

	private TextField createTextField() {
		TextField tf = new TextField();
		GridPane.setHgrow(tf, Priority.ALWAYS);
		return tf;
	}

	private void createStudentView() {
		Button editBtn = new Button("Student bearbeiten");
		editBtn.setOnAction(e -> {
			Student s = this.studentsView.getSelectedStudent();
			this.studentMatriculationNumberTF.setText(s.getMatriculationNumber());
			this.studentNameTF.setText(s.getSurname());
			this.studentFirstnameTF.setText(s.getFirstname());
			this.studentStreetTF.setText(s.getStreet());
			this.studentResidenceTF.setText(s.getResidence());
			this.studentPhoneTF.setText(s.getTelefon());
			this.studentsView.toggleShowView();

		});
		this.studentsView = new StudentsView(editBtn);
	}

	private void createView() {
//		this.add(this.statusBarLbl, 0, 0);
		this.add(this.statusBarLbl, 0, 0, 2, 1);
		this.add(createLabel("Matrikelnummer:"), 0, 1);
		this.add(createLabel("Name:"), 0, 2);
		this.add(createLabel("Vorname:"), 0, 3);
		this.add(createLabel("Strasse:"), 0, 4);
		this.add(createLabel("Wohnort:"), 0, 5);
		this.add(createLabel("Telefonnummer:"), 0, 6);

		this.add(this.studentMatriculationNumberTF, 1, 1);
		this.add(this.studentNameTF, 1, 2);
		this.add(this.studentFirstnameTF, 1, 3);
		this.add(this.studentStreetTF, 1, 4);
		this.add(this.studentResidenceTF, 1, 5);
		this.add(this.studentPhoneTF, 1, 6);
		this.add(createBtnHBox(), 0, 7, 3, 1);

		this.setHgap(20);
		this.setPadding(new Insets(10));
	}

	private Label createLabel(String text) {
		Label lbl = new Label(text);
		lbl.setFont(new Font(20));
		return lbl;
	}

	private HBox createBtnHBox() {
		HBox hBox = new HBox();
		hBox.setSpacing(5);
		hBox.setPadding(new Insets(10, 0, 0, 0));
		Button saveBtn = new Button("Speichern");
		Button deleteBtn = new Button("Loeschen");
		Button readStudentsBtn = new Button("Lesen");
		Button resetBtn = new Button("Reset");
		Button exitBtn = new Button("Exit");
		saveBtn.setOnAction(e -> saveStudent());
		deleteBtn.setOnAction(e -> deleteStudent());
		readStudentsBtn.setOnAction(e -> readStudents());
		resetBtn.setOnAction(e -> resetTextField());
		exitBtn.setOnAction(e -> closeProgramm());
		// size
		saveBtn.setPrefWidth(90);
		deleteBtn.setPrefWidth(90);
		readStudentsBtn.setPrefWidth(90);
		resetBtn.setPrefWidth(90);
		exitBtn.setPrefWidth(90);
		hBox.getChildren().addAll(saveBtn, readStudentsBtn, deleteBtn, resetBtn, exitBtn);
		return hBox;
	}

	private void saveStudent() {
		if (this.studentMatriculationNumberTF.getText().isEmpty() || this.studentNameTF.getText().isEmpty()
				|| this.studentFirstnameTF.getText().isEmpty() || this.studentStreetTF.getText().isEmpty()
				|| this.studentResidenceTF.getText().isEmpty() || this.studentPhoneTF.getText().isEmpty()) {
			this.statusBarLbl.setText("Bitte alle Textfelder ausfuellen");
			return;
		}
		Student s = new Student(this.studentMatriculationNumberTF.getText(), this.studentNameTF.getText(),
				this.studentFirstnameTF.getText(), this.studentStreetTF.getText(), this.studentResidenceTF.getText(),
				this.studentPhoneTF.getText());

		if (createDialog(s.getMatriculationNumber(), "Moechten Sie die Datei wirklich Überschreiben?",
				"Student gespeichert!", "Datei wurde überschrieben!", "Datei wurde nicht überschrieben!")) {

		}
		String matriculationNumber = this.studentMatriculationNumberTF.getText();

		String fileName = FILE_BEGINNING + FILE_PREFIX + matriculationNumber + FILE_EXTENSION;

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {

			writer.write(s.toString());

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void deleteStudent() {
		String matriculationNumber = this.studentMatriculationNumberTF.getText();
		createDialog(matriculationNumber, "Moechten Sie die Datei wirklich loeschen?",
				"Ungültige Matrikelnummer. Datei existiert nicht!", "Datei wurde geloescht!",
				"Datei wurde nicht geloescht!");
	}

	private boolean createDialog(String matriculationNumber, String text, String textFileNotExists,
			String textSuccessful, String textCanceled) {
		String fileName = FILE_BEGINNING + FILE_PREFIX + matriculationNumber + FILE_EXTENSION;
		File file = new File(fileName);
		ButtonType result = ButtonType.YES;

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.YES, ButtonType.NO);
		if (!file.exists()) {
			this.statusBarLbl.setText(textFileNotExists);
		} else {
			result = alert.showAndWait().orElse(ButtonType.NO);
			if (result.equals(ButtonType.YES)) {
				file.delete();
				this.statusBarLbl.setText(textSuccessful);
			} else {
				this.statusBarLbl.setText(textCanceled);

			}
		}
		return result.equals(ButtonType.YES);
	}

	private void readStudents() {
		this.studentsView.toggleShowView();
		this.statusBarLbl.setText("Student geladen!");
	}

	private void resetTextField() {
		this.studentFirstnameTF.setText("");
		this.studentMatriculationNumberTF.setText("");
		this.studentNameTF.setText("");
		this.studentPhoneTF.setText("");
		this.studentResidenceTF.setText("");
		this.studentStreetTF.setText("");
		this.statusBarLbl.setText("TextField cleared!");
	}

	private void closeProgramm() {
		System.exit(0);
	}
}
