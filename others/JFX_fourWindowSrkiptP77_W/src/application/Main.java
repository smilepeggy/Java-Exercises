package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import window.AddWindow;
import window.ImportWindow;
import window.OverviewWindow;
import window.StatisticWindow;

public class Main extends Application {
	private static final String ADD = "Add";
	private static final String IMPORT = "Import";
	private static final String OVERVIEW = "Overview";
	private static final String STATISTIC = "Statistic";

	@Override
	public void start(Stage primaryStage) {

		GridPane btnGridPane = new GridPane();

		CustomizedButton addBtn = new CustomizedButton(ADD);
		CustomizedButton importBtn = new CustomizedButton(IMPORT);
		CustomizedButton overviewBtn = new CustomizedButton(OVERVIEW);
		CustomizedButton statisticBtn = new CustomizedButton(STATISTIC);

		// button
		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String btnString = ((Button) event.getSource()).getText();
				System.out.println(btnString);
				switch (btnString) {
				case ADD:
					new AddWindow(btnString);
					break;
				case IMPORT:
					new ImportWindow(btnString);
					break;
				case OVERVIEW:
					new OverviewWindow(btnString);
					break;
				case STATISTIC:
					new StatisticWindow(btnString);
					break;

				}
			}
		};

		addBtn.setOnAction(buttonHandler);
		importBtn.setOnAction(buttonHandler);
		overviewBtn.setOnAction(buttonHandler);
		statisticBtn.setOnAction(buttonHandler);

		btnGridPane.add(addBtn, 0, 0);
		btnGridPane.add(importBtn, 0, 1);
		btnGridPane.add(overviewBtn, 1, 0);
		btnGridPane.add(statisticBtn, 1, 1);

		Scene scene = new Scene(btnGridPane);
		primaryStage.sizeToScene();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
