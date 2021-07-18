package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PeiChart extends Application {
//	private static final String PEI_CHART_PATH = "res/PieChartData.txt";
	private static final String PEI_CHART_PATH = "C:\\Users\\VW9GM3F\\Desktop\\Peggy\\java\\JavaFx\\JFX_ExerciseSrkiptP86_W\\res\\PieChartData.txt";
	private static final String Line_CHART_PATH = "res/LineChartData.txt";

	private static final String DATA_SPLIT = ",";
//	private ObservableList<PieChart.Data> pieCharDataList;

	@Override
	public void start(Stage primaryStage) {
		HBox hBoxRoot = new HBox();

		// pie chart
		PieChart pieChart = new PieChart(readPieChartData(PEI_CHART_PATH));
		pieChart.setTitle("Cake distribution");

		// line chart
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();

		xAxis.setLabel("Position");
		yAxis.setLabel("Competition date");
		LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

		hBoxRoot.getChildren().add(pieChart);
		Scene scene = new Scene(hBoxRoot, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// pie chart read data
	private ObservableList<PieChart.Data> readPieChartData(String filePath) {
		ObservableList<PieChart.Data> pieCharDataList = FXCollections.observableArrayList();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {

			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(DATA_SPLIT);
				if (items.length != 2) {
					System.out.println("illegal line: " + items);
					continue;
				}
				pieCharDataList.add(new PieChart.Data(items[0], Double.parseDouble(items[1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
		return pieCharDataList;
	}

	private XYChart.Series<String, Number> readLineChartData(String filePath) {
		XYChart.Series<String, Number> competitionSeries = new XYChart.Series<String, Number>();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(Line_CHART_PATH))) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(DATA_SPLIT);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return competitionSeries;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
