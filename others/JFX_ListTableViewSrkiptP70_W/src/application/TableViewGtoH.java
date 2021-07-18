package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableViewGtoH extends Application {
	private static final String FILE_PATH_ORIGINAL = "res/Albums.txt";
	private static final String FILE_PATH_NEW = "res/MyOwnAlbums.txt";
//	private static final String FILE_PATH_NEW = "res/MyOwnAlbums.csv";

	private static final String SEMICOLON = ";"; // for DE CSV format
	private final ObservableList<Album> albumsList = FXCollections.observableArrayList();

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox vbRoot = new VBox();
		HBox buttonsHBox = new HBox(); // branch node
		Label label = new Label("Albums");
		Label addFormat = new Label("Format: Album Name;Artist;Track Amount;Duration (min);Release Year;Price");
		Button addBtn = new Button("Add Album");
		Button deleteBtn = new Button("Delete Album");
		Button saveBtn = new Button("Save Album");
		TextField albumText = new TextField();

		TableView<Album> albumTableView = new TableView<>(readFile(getPathInt()));
//		TableView<Album> albumTableView = new TableView<>();
		albumTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		albumTableView.setEditable(true);

		// table column
		TableColumn<Album, String> nameCol = new TableColumn<>("Album Name");
		TableColumn<Album, String> artistCol = new TableColumn<>("Artist");
		TableColumn<Album, Integer> trackAmountCol = new TableColumn<>("Track Amount");
		TableColumn<Album, Integer> durationCol = new TableColumn<>("Duration (min)");
		TableColumn<Album, Integer> releaseYearCol = new TableColumn<>("Release Year");
		TableColumn<Album, Double> priceCol = new TableColumn<>("Price");

		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
		artistCol.setCellFactory(TextFieldTableCell.<Album>forTableColumn());
		trackAmountCol.setCellValueFactory(new PropertyValueFactory<>("trackAmount"));
		durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
		releaseYearCol.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

		// edit table
		artistCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Album, String>>() {

			@Override
			public void handle(CellEditEvent<Album, String> event) {
//				System.out.println(event.getTableView().getItems().get(event.getTablePosition().getRow())); // old value
//				System.out.println(event.getNewValue()); // new value
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setArtist(event.getNewValue());
			}
		});

		albumTableView.getColumns().add(nameCol);
		albumTableView.getColumns().add(artistCol);
		albumTableView.getColumns().add(trackAmountCol);
		albumTableView.getColumns().add(durationCol);
		albumTableView.getColumns().add(releaseYearCol);
		albumTableView.getColumns().add(priceCol);

		albumTableView.setItems(this.albumsList); // albumsList = this.albumsList but with "this" is better

		// button action
		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String[] fields = albumText.getText().split(SEMICOLON);

				try {
					doValidate(fields);
					System.out.println("fiels " + fields[0]);
					Album album = new Album(fields[0].trim(), fields[1].trim(), Integer.parseInt(fields[2].trim()),
							Integer.parseInt(fields[3].trim()), Integer.parseInt(fields[4].trim()),
							Double.parseDouble(fields[5].trim()));
					albumsList.add(album);
					System.out.println(fields[0] + " is added! ");

				} catch (BusinessException e) {
					e.printStackTrace();
				}

			}

			private void doValidate(String[] fiels) throws BusinessException {

				if (albumText.getText().isEmpty()) {
					throw new BusinessException("Please enter text");
				}
				for (Album item : albumsList) {
					if (item.getName().equalsIgnoreCase(fiels[0])) {
						throw new BusinessException(fiels[0] + " already exists!");
					}
				}
			}
		});

		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				albumsList.removeAll(albumTableView.getSelectionModel().getSelectedItems());
				// MainGtoH.this.albumsList = albumsList
			}

		});
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				saveToFile(albumsList, FILE_PATH_NEW);
			}
		});

		buttonsHBox.getChildren().addAll(addBtn, deleteBtn, saveBtn);
		vbRoot.getChildren().addAll(albumTableView, label, addFormat, buttonsHBox, albumText);
		Scene scene = new Scene(vbRoot);
//		primaryStage.sizeToScene();
		primaryStage.setTitle("Albums");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private ObservableList<Album> readFile(String filePath) {

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(SEMICOLON);
				if (fields.length < 6) { // it is good for array to prove if it is an Array
					System.out.println("illegal line: " + fields);
					continue;
				}
				try {

					Album album = new Album(fields[0], fields[1], Integer.parseInt(fields[2]),
							Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), Double.parseDouble(fields[5]));
					this.albumsList.add(album);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException e) { // FileNotFoundException extends IOException
			e.printStackTrace();
		}
		return this.albumsList;

	}

	private void saveToFile(ObservableList<Album> list, String filePath) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH_NEW))) {
//			for (Album item : list) {
//				writer.write(item.toDECsvFormat());
//				if (list.indexOf(item) != list.size() - 1) {  // not a good method. it is slow than for loop, cuz within indexOf() still have another loop
//					writer.newLine();
//				}
//			}
			for (int i = 0; i < list.size(); i++) {
				writer.write(list.get(i).toDECsvFormat());
				if (i != list.size() - 1) {
					writer.newLine(); // but actually with newline is not so bad. w
				}
			}
			System.out.println("Saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getPathInt() {
//		System.out.println(Files.exists(Paths.get(FILE_PATH_NEW)) ? FILE_PATH_NEW : FILE_PATH_ORIGINAL);
		return Files.exists(Paths.get(FILE_PATH_NEW)) ? FILE_PATH_NEW : FILE_PATH_ORIGINAL;
	}

}
