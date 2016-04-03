package Frame;

import Chart.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;
import Data.*;

/**
 * Created by Przemek on 2016-02-18.
 */
public class FxSignalPanel extends JFXPanel {

    private static MeasurementData measurementData;
    private static final int MAX_CHART_XPOINTS;
    static {
        MAX_CHART_XPOINTS = 500;
    }

    private static NumberAxis xAxis;
    static {
        xAxis = new NumberAxis(0, MAX_CHART_XPOINTS, 10);
    }

    private static NumberAxis yAxis;
    static {
        yAxis = new NumberAxis(0, 5, 0.1);
    }

    private Chart chart;
    private Scene scene;
    private Stage stage;

    public FxSignalPanel() {
    }

    public void initFX() {
        chart = new Chart(xAxis, yAxis);
        scene = new Scene(chart);
        stage = new Stage();
        stage.setTitle(Application.appProperties.getProperty("appTitle"));
        stage.setScene(scene);
        this.setScene(scene);
        measurementData = new MeasurementData(SignalzFrame.getDataBaseDriver().getTableCount());
    }

    public static NumberAxis getAxisX() {
        return xAxis;
    }

    public static NumberAxis getAxisY() {
        return yAxis;
    }

    public static int getMaxChartXpoints() {
        return MAX_CHART_XPOINTS;
    }

    public Chart getChart() {
        return chart;
    }

    public Scene getScene() {
        return scene;
    }

    public static MeasurementData getNewMeasurementData(int tableCount) {
        measurementData = null;
        measurementData = new MeasurementData(tableCount);
        return measurementData;
    }

    public static MeasurementData getMeasurementData() {
        return measurementData;
    }

    public void clearChartAndResetDataLists(){
        getChart().getFirstNodeMarker().hideMarker();
        getChart().getSecondNodeMarker().hideMarker();
        getChart().getData().clear();
        xAxis.setLowerBound(0);
        getChart().clearSeriesLists();//wyczyscic listy
        xAxis.setUpperBound(FxSignalPanel.getMaxChartXpoints());
        SignalzFrame.logger.info("Chart cleared.");
    }

    public void clearChart(){
        getChart().getFirstNodeMarker().hideMarker();
        getChart().getSecondNodeMarker().hideMarker();
        getChart().stopAnimation();//
        getChart().setIsAnimated(false);//
        SignalzFrame.getDataBaseDriver().setEnabled(false);
        SignalzFrame.getJdomParseXML().setEnabled(false);
        SignalzFrame.getSaxParseXML().setEnabled(false);
        SignalzFrame.getFileFromWebsite().setEnabled(false);
        FxSignalPanel.getMeasurementData().clearDataLists(); //czysci measurement Data
        getChart().getData().clear();
        getChart().clearSeriesLists();//wyczyscic listy
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(FxSignalPanel.getMaxChartXpoints());
        SignalzFrame.logger.info("Chart cleared.");
    }
}

