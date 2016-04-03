package Chart;

import ChartMarker.*;
import Data.MeasurementData;
import Frame.Application;
import Frame.FxSignalPanel;
import Frame.SignalzFrame;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.ArrayList;

/**
 * Created by Przemek on 2016-02-19.
 */
public class AddDataToChart extends AnimationTimer {

    private static int maxChartXpoints;
    private static double chartMaxY;
    private static double chartMinY;
    private static double chartMaxX;
    protected static ArrayList<XYChart.Series<Number, Number>> seriesList; //lista z seriami
    private static double deltaT = Double.parseDouble(Application.appProperties.getProperty("xAxisTick")); //AxisX tick
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private MeasurementData measurementData;
    private Chart chart;

    public AddDataToChart(Chart chart) {
        this.xAxis = FxSignalPanel.getAxisX();
        this.yAxis = FxSignalPanel.getAxisY();
        this.chart = chart;
        maxChartXpoints = FxSignalPanel.getMaxChartXpoints();
        seriesList = Chart.seriesList;
        this.measurementData = FxSignalPanel.getMeasurementData();
    }

    public void handle(long now) {// Every frame to take any data from queue and add to chart
        double X;  Number Y;  int lastIndexOfData;
        ObservableList<XYChart.Data<Number, Number>> d;

        this.measurementData = FxSignalPanel.getMeasurementData();

        if (!measurementData.getMeasurementsList().get(0).isEmpty()) {
            for (int j = 0; j < Chart.getSeriesCount(); j++) {// dla kazdej serii, dodanie dowej danej - X Y
                //powiekszajace sie X - xSeriesData++, Y - pobiera i usuwa
                d = seriesList.get(j).getData();
                if (d.size() == 0) { //jesli pierwsza wartość, zainicjuj zerem
                    X = 0;
                } else {
                    lastIndexOfData = d.size() - 1;
                    X = (double) d.get(lastIndexOfData).getXValue() + deltaT;
                }

                int index = measurementData.getAndIncIterator(j); //pobieram iterator dla serii i go inkrementuje
                Y = (double)measurementData.getMeasurementsList().get(j).get(index); // pobieram kolejną daną dla danej serii
                if (index == measurementData.getMeasurementsList().get(j).size() - 1) //jesli doszedłem do końca serii, zaczynam od początku
                    measurementData.setIterator(j, 0);

                XYChart.Data data = new XYChart.Data<>(X, Y);
                data.setNode(
                        new ChartNode(
                                chart, (double) Y, X
                        )
                );
                SignalzFrame.logger.info("Value added to chart: " + Y);
                seriesList.get(j).getData().add(data);

                try{//opozniam zeby nie rysowało bardzo szybko
                    Thread.sleep(15);
                }catch(InterruptedException e){
                    SignalzFrame.logger.error("AddDataToChart thread interrupted.");
                }
            }
        }
        chartMaxY = findMaxY();
        chartMinY = findMinY();
        chartMaxX = findMaxX();
        if (chartMaxX >= maxChartXpoints) {
            xAxis.setLowerBound(chartMaxX - maxChartXpoints);
            xAxis.setUpperBound(chartMaxX);
        }
        if (chart.getIsAutoRanging() == false) {
            yAxis.setUpperBound(chartMaxY);
            yAxis.setLowerBound(chartMinY);
        }

        chart.getFirstNodeMarker().hideMarker();
        chart.getSecondNodeMarker().hideMarker();
    }

    private double findMaxY() {//na podstawie wartosci w Nodzie!
        double max = 0;
        for (int i = 0; i < Chart.getSeriesCount(); i++) {
            for (XYChart.Data<Number, Number> value : seriesList.get(i).getData()) {
                ChartNode node = (ChartNode) value.getNode();
                double y = node.getY();
                if (y > max) {
                    max = y;
                }
            }
        }
        return max;
    }

    private double findMinY() {//na podstawie wartosci w Nodzie!
        double min = 0;
        for (int i = 0; i < Chart.getSeriesCount(); i++) {
            for (XYChart.Data<Number, Number> value : seriesList.get(i).getData()) {
                ChartNode node = (ChartNode) value.getNode();
                double y = node.getY();
                if (y < min) {
                    min = y;
                }
            }
        }
        return min;
    }

    private double findMaxX() {//na podstawie wartosci w Nodzie!
        double max = 0;
        for (int i = 0; i < Chart.getSeriesCount(); i++) {
            for (XYChart.Data<Number, Number> value : seriesList.get(i).getData()) {
                ChartNode node = (ChartNode) value.getNode();
                double x = node.getX();
                if (x > max) {
                    max = x;
                }
            }
        }
        return max;
    }

    public double getChartMaxY() {
        return chartMaxY;
    }

    public double getChartMinY() {
        return chartMinY;
    }

    public double getChartMaxX() {
        return chartMaxX;
    }

    public static double getDeltaT() {
        return deltaT;
    }

}