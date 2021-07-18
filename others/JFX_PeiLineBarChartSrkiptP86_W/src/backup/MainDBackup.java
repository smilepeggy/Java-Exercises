package backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainDBackup extends Application {
//	private static final String PEI_CHART_PATH = "res/PieChartData.txt";
	private static final String PEI_CHART_PATH = "C:\\Users\\VW9GM3F\\Desktop\\Peggy\\java\\JavaFx\\JFX_ExerciseSrkiptP86_W\\res\\PieChartData.txt";
//	private static final String LINE_CHART_PATH = "C:\\Users\\VW9GM3F\\Desktop\\Peggy\\java\\JavaFx\\JFX_ExerciseSrkiptP86_W\\res\\LineChartData.txt";
	private static final String LINE_CHART_PATH = "res/LineChartData.txt";
	private static final String BAR_CHART_PATH = "res/BarChartData.txt";

	private static final String DATA_SPLIT = ",";
//	private ObservableList<PieChart.Data> pieCharDataList;
	private PieChart pieChartView;

	@Override
	public void start(Stage primaryStage) {
		HBox hBoxRoot = new HBox();

		// pie chart
		this.pieChartView = getPieChartView();
		// line chart
//		LineChart<Number, Number> lineChartView = getLineChartView(); // cannot worl

		// bar chart
		BarChart<String, Number> barChartView = getBarChartView();

//		hBoxRoot.getChildren().addAll(pieChartView, lineChartView);
//		hBoxRoot.getChildren().add(pieChartView);
//		hBoxRoot.getChildren().add(lineChartView);
		hBoxRoot.getChildren().add(barChartView);
		Scene scene = new Scene(hBoxRoot, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	// pie chart read data

	public PieChart getPieChartView() {
		this.pieChartView = new PieChart(readPieChartData(PEI_CHART_PATH));
		this.pieChartView.setTitle("Cake distribution");
		return this.pieChartView;
	}

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

	// line chart read data

	public LineChart<Number, Number> getLineChartView() {
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Competition date");
		ArrayList<XYChart.Series<Number, Number>> competitionSeries = readLineChartData(LINE_CHART_PATH);
//		ArrayList<XYChart.Series<String, Number>> competitionSeries = importLineChartData();

		for (XYChart.Series<Number, Number> item : competitionSeries) {
			lineChart.getData().add(item);
		}
//		for (XYChart.Series<Number, Number> item : competitionSeries) {
//			lineChart.getData().add(item);
//		}

		xAxis.setLabel("Position");
		yAxis.setLabel("Competition date");

		return lineChart;
	}

	private ArrayList<XYChart.Series<Number, Number>> readLineChartData(String filePath) {
		ArrayList<XYChart.Series<Number, Number>> competitionSeriesList = new ArrayList<Series<Number, Number>>();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
			String line = "";
			while ((line = reader.readLine()) != null) {

				XYChart.Series<Number, Number> competitionSeriesListTemp = new XYChart.Series<>();

				String[] items = line.split(DATA_SPLIT);

				for (int i = 0; i < items.length; i++) {
					competitionSeriesListTemp.setName(items[i]);
					competitionSeriesListTemp.getData()
							.add(new XYChart.Data<Number, Number>(i, Integer.parseInt(items[i].trim())));
					competitionSeriesList.add(competitionSeriesListTemp);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return competitionSeriesList;
	}

	private ArrayList<Series<String, Number>> importLineChartData() {
		ArrayList<Series<String, Number>> seriesList = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File(LINE_CHART_PATH))) {
			while (scanner.hasNextLine()) {
				String[] splittedLine = scanner.nextLine().split(DATA_SPLIT);
				Series<String, Number> series = new Series<>();
				series.setName(splittedLine[0]);
				for (int i = 1; i < splittedLine.length; i++) {
					series.getData().add(
							new XYChart.Data<String, Number>("Spieltag " + i, Double.valueOf(splittedLine[i].trim())));
				}
				seriesList.add(series);
				System.out.println("Series für den Verein " + splittedLine[0] + " erfolgreich importiert");
			}
			System.out.println("Alle Daten wurden erfolgreich importiert!");
		} catch (IOException e) {
			System.out.println("FEHLER: Beim Einlesen der Datei!!");
			e.printStackTrace();

		}

		return seriesList;
	}

	// bar chart read data
	public BarChart<String, Number> getBarChartView() {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		barChart.setTitle("Electric consumtion");
		ArrayList<XYChart.Series<String, Number>> seriesList = readBarChartData(BAR_CHART_PATH);
		System.out.println(seriesList);
		return barChart;
	}

	private ArrayList<XYChart.Series<String, Number>> readBarChartData(String filePath) {
		ArrayList<XYChart.Series<String, Number>> seriesList = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(DATA_SPLIT);
				XYChart.Series<String, Number> seriesTemp = new XYChart.Series<>();
				seriesTemp.setName(items[0]);

				for (int i = 0; i < items.length; i++) {
					seriesTemp.getData()
							.add(new XYChart.Data<String, Number>("" + i, Double.parseDouble(items[i].trim())));
					seriesList.add(seriesTemp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return seriesList;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
