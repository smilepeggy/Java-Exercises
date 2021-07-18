package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StudentsView extends Stage {

	private ListView<Student> studentListView = new ListView<>();
	private Button editBtn;

	public StudentsView(Button editBtn) {
		this.editBtn = editBtn;
		createView();
		this.setTitle("Alle Studenten");
		this.setResizable(false);
	}

	private void createView() {
		BorderPane bp = new BorderPane();
		bp.setCenter(this.studentListView);
		bp.setBottom(this.editBtn);
		this.setScene(new Scene(bp, 400, 400));
	}

	public void toggleShowView() {
		if (this.isShowing()) {
			this.hide();
		} else {
			readStudents();
			this.show();
		}
	}

	private void readStudents() {
		// method1
		File folder = new File(AdministrationView.FILE_BEGINNING);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			System.out.println(file);
			try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath()))) {

				String line = "";
				while ((line = reader.readLine()) != null) {
					String[] studentItems = line.split(";");
					this.studentListView.getItems().add(new Student(studentItems[0], studentItems[1], studentItems[2],
							studentItems[3], studentItems[4], studentItems[5]));
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		// method2
//		try {
//			List<File> filesInFolder = Files.walk(Paths.get(AdministrationView.FILE_BEGINNING))
//					.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
//			for (File file : filesInFolder) {
//				System.out.println(file.getPath());
//				try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath()))) {
//					
//					String line = "";
//					while ((line = reader.readLine()) != null) {
//						String[] studentItems = line.split(";");
//						this.studentListView.getItems().add(new Student(studentItems[0], studentItems[1],
//								studentItems[2], studentItems[3], studentItems[4], studentItems[5]));
//					}
//					
//				} catch (IOException ex) {
//					ex.printStackTrace();
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public Student getSelectedStudent() {
		return this.studentListView.getSelectionModel().getSelectedItem();
	}
}
