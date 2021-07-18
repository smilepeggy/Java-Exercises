package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListViewAtoF extends Application {
//	private static String filePath = "Series.txt";
//	private static String filePath = "C:\\Users\\VW9GM3F\\Desktop\\Peggy\\java\\JavaFx\\ExerciseSrkiptP70_W\\Series.txt";
	private static final String FILE_PATH_ORIGINAL = "res/Series.txt";
	private static final String FILE_PATH_NEW = "res/MyOwnSeries.txt";
	private static final String FILE_DELIMITER = ";";
	private ObservableList<String> seriesList = FXCollections.observableArrayList();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox vbRoot = new VBox();
		HBox buttonsHBox = new HBox(); // branch node
		ListView<String> items = new ListView<String>(readFile(getPathInt()));
		items.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		Label label = new Label("Series");
		Button addBtn = new Button("Add series");
		Button deleteBtn = new Button("Delete series");
		Button saveBtn = new Button("Save series");
		buttonsHBox.getChildren().addAll(addBtn, deleteBtn, saveBtn);
		TextField seriesText = new TextField();

		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					doValidate();
					ListViewAtoF.this.seriesList.add(seriesText.getText());
					System.out.println(seriesText.getText() + " is added! ");

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			private void doValidate() throws BusinessException {
				if (seriesText.getText().isEmpty()) {
					throw new BusinessException("Please enter text");
				}
				for (String string : ListViewAtoF.this.seriesList) {
					if (string.toLowerCase().equals((seriesText.getText().toLowerCase()))) { // check lowercase
						throw new BusinessException(seriesText.getText() + " already exists!");
					}
				}

			}
		});

		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ListViewAtoF.this.seriesList.removeAll(items.getSelectionModel().getSelectedItems());

			}
		});

		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				saveToFile(ListViewAtoF.this.seriesList, FILE_PATH_NEW);
			}
		});

		vbRoot.getChildren().addAll(items, label, buttonsHBox, seriesText);

		Scene scene = new Scene(vbRoot);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private ObservableList<String> readFile(String filePath) {

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(getPathInt()))) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(";");
				for (String item : items) {
					this.seriesList.add(item);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.seriesList;
	}

	private void saveToFile(ObservableList<String> list, String filePath) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
			for (int i = 0; i < list.size(); i++) {
				writer.write(list.get(i) + FILE_DELIMITER);
				if (i != list.size() - 1) {
					writer.newLine();
				}

			}
		} catch (Exception e) {
			System.err.printf("Cannot save to the file %s", filePath);
		}
	}

	private String getPathInt() {
		return Files.exists(Paths.get(FILE_PATH_NEW)) ? FILE_PATH_NEW : FILE_PATH_ORIGINAL;
	}

}
