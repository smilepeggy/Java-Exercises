package application;
import javafx.scene.control.TextField;

public class NumberField extends TextField {

	@Override
	public void replaceText(int start, int end, String text) {
		if (text.matches("[0-9]*")) {
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text) {
		if (text.matches("[0-9]*")) {
			super.replaceSelection(text);
		}
	}

	public int getNumber() {
		try {
			return Integer.parseInt(super.getText());

		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
