package window;

import application.Car;
import application.Person;
import application.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;

public class StatisticWindow extends WindowManagement {

	// need to ask the code structure
	private PieChart piechart;
	private BarChart<String, Number> barChart; // person name vs Car amount
	private HBox hbox;

	public StatisticWindow(String text) {
		super(text, 600, 500);
		createPieChart();
		this.hbox = new HBox();
		this.barChart = createBarchart();

		this.hbox.getChildren().addAll(this.piechart, this.barChart);
		rootNode.setCenter(this.hbox);

	}

	private void createPieChart() {
		ObservableList<PieChart.Data> pieChatData = FXCollections.observableArrayList();
		pieChatData.add(new PieChart.Data("Person", Storage.personList.size()));
		pieChatData.add(new PieChart.Data("Car", Storage.carList.size()));
		this.piechart = new PieChart(pieChatData); // or write it in Constructor
		this.piechart.setTitle("Person vs Car amount");
	}

	private BarChart<String, Number> createBarchart() {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Person Name");
		yAxis.setLabel("Car amount");
		this.barChart = new BarChart<>(xAxis, yAxis);
		ObservableList<XYChart.Series<String, Number>> barChartSortedDataList = sortBarChartData();
		for (XYChart.Series<String, Number> series : barChartSortedDataList) {
			this.barChart.getData().add(series);
		}
		return this.barChart;

	}

	private ObservableList<XYChart.Series<String, Number>> sortBarChartData() {
		ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();
		if (Storage.personList.size() != 0) {
			for (Person person : Storage.personList) {

				int countCar = 0;
				XYChart.Series<String, Number> seriesTemp = new XYChart.Series<>();
//				seriesTemp.setName(person.getFirstName() + " " + person.getLastName());
				seriesTemp.setName(person.toString());
				for (Car car : Storage.carList) {
					if (person.equals(car.getPerson())) {
						countCar++;
					}
				}
				seriesTemp.getData().add(new XYChart.Data<String, Number>(person.toString(), countCar));
				System.out.println(person.toString() + " " + "Count Car: " + countCar);
				barChartData.add(seriesTemp);
			}
		}
		return barChartData;
	}
}
