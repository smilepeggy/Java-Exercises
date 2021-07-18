package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// for saving person-car
public class Storage {
	public static ObservableList<Person> personList = FXCollections.observableArrayList();
	public static ObservableList<Car> carList = FXCollections.observableArrayList();
}
