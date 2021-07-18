package window;

import application.Car;
import application.Person;
import application.Storage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class OverviewWindow extends WindowManagement {

	private ListView<Person> personListView;
	private VBox pageVB; // private VBox pageVB = new VBox(); is not that good
	private TableView<Car> carTableView;
	private ObservableList<Car> carListCurrent;

	public OverviewWindow(String text) {
		super(text, 600, 600);
		pageVB = new VBox();
		carTableView = new TableView<>();
		personListView = new ListView<>(Storage.personList);
		personListView.setPrefHeight(100);
		personListView.getSelectionModel().getSelectedItem();
		personListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {

			@Override
			public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
				carListCurrent = FXCollections.observableArrayList();
				System.out.println("clicked");
				for (Car car : Storage.carList) {
					if (newValue.equals(car.getPerson())) {
						System.out.println("new Value: " + newValue);
						carListCurrent.add(car);
						System.out.println("add car");
					}
				}
				carTableView.setItems(carListCurrent);
			}
		});

		// car tableView

		TableColumn<Car, String> brandCol = new TableColumn<>("Brand");
		TableColumn<Car, String> modelCol = new TableColumn<>("Model");
		TableColumn<Car, String> colorCol = new TableColumn<>("Color");
		TableColumn<Car, Integer> mileageCol = new TableColumn<>("Mileage");
		TableColumn<Car, String> fuelCol = new TableColumn<>("Fuel");
		TableColumn<Car, Integer> producedYearCol = new TableColumn<>("Produced Year");

		brandCol.setCellValueFactory(new PropertyValueFactory<>("brand")); // don't forget in Car class write getter
		modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
		colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
		mileageCol.setCellValueFactory(new PropertyValueFactory<>("mileage"));
		fuelCol.setCellValueFactory(new PropertyValueFactory<>("fuel"));
		producedYearCol.setCellValueFactory(new PropertyValueFactory<>("producedYear"));

		carTableView.getColumns().add(brandCol);
		carTableView.getColumns().add(modelCol);
		carTableView.getColumns().add(colorCol);
		carTableView.getColumns().add(mileageCol);
		carTableView.getColumns().add(fuelCol);
		carTableView.getColumns().add(producedYearCol);

		pageVB.getChildren().addAll(personListView, carTableView);
		rootNode.setTop(pageVB);
//		rootNode.setCenter(carTableView);
	}

}
