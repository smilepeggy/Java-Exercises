package window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class WindowManagement extends Stage {

	protected BorderPane rootNode; // private is better?

	public WindowManagement(String text, int width, int height) {
		rootNode = new BorderPane();
		System.out.println("WindowManagement");
		Button backBtn = new Button("Back");
		Label pageText = new Label(text);

		backBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				WindowManagement.this.close();

			}
		});
		rootNode.setTop(pageText);
		rootNode.setBottom(backBtn);
//		rootNode.getChildren().addAll(pageText, backBtn);
		Scene scene = new Scene(rootNode, width, height);
		this.setTitle(text);
		this.setScene(scene);
		this.show();
	}

}
