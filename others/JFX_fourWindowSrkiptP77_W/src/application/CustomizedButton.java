package application;

import javafx.scene.control.Button;

public class CustomizedButton extends Button {

	private final static int SIZE = 100;

	public CustomizedButton() {
		this.setPrefSize(SIZE, SIZE);
	}

	public CustomizedButton(String text) {
		super(text);
		this.setPrefSize(SIZE, SIZE);
	}
}
