/**
 * Created by Przemek on 2016-02-18.
 */
package Chart;
import Frame.Application;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.effect.BlendMode;
import java.util.ArrayList;
import ChartMarker.*;
import javafx.scene.paint.Color;

public class Chart extends LineChart<Number, Number> {

    private static int seriesCount;
    private String seriesName = Application.appProperties.getProperty("seriesName");
    private XYChart.Series<Number, Number> seria;
    protected static ArrayList<Series<Number, Number>> seriesList = new ArrayList<>(); //lista z seriami
    private StringBuilder stringBuilder = new StringBuilder();
    private boolean isAnimated;
    private boolean isSelecting;
    private boolean isAutoRange;
    private NumberAxis yAxis;
    private NumberAxis xAxis;
    private ChartCuttingHandler chartCuttingHandler;

    private NodeMarker firstNodeMarker;
    private NodeMarker secondNodeMarker;
    private FragmentMarker fragmentMarker;

    private ChartShiftingHandler shiftingHandler;
    private AddDataToChart addDataToChart;
    private ZoomHandler zoomHandler;

    public Chart(NumberAxis xAxis, NumberAxis yAxis) {
        super(xAxis, yAxis);
        this.yAxis = yAxis;
        this.xAxis = xAxis;
        seriesCount = 2;
        initSeriesLists(seriesCount);
        isSelecting = false;
        isAnimated = false;
        isAutoRange = true;

        //yAxis.setAutoRanging(isAutoRange); //AUTO przez ktore jest taki zoom
        customChart();
        chartCuttingHandler = new ChartCuttingHandler(this);
        firstNodeMarker = new NodeMarker(new NodeMarkerModel(this));
        firstNodeMarker.setColor(Color.ORANGERED);
        secondNodeMarker = new NodeMarker(new NodeMarkerModel(this));
        fragmentMarker = new FragmentMarker(0, 0, 0, 0);
        fragmentMarker.setMarker(firstNodeMarker);
        fragmentMarker.setMarker2(secondNodeMarker);
        this.getPlotChildren().add(fragmentMarker);

        addDataToChart = new AddDataToChart(this);

        shiftingHandler = new ChartShiftingHandler(addDataToChart);
        setOnMouseDragged(shiftingHandler);
        zoomHandler = new ZoomHandler(this);
    }

    public void initSeriesLists(int seriesCount) {
        for (int i = 0; i < seriesCount; i++) { //tworzenie listy serii
            stringBuilder.append(seriesName);
            stringBuilder.append(i + 1);
            seriesName = stringBuilder.toString();
            seria = new XYChart.Series<>();
            seria.setName(seriesName);
            seriesList.add(seria);
            seriesName = "Signal ";
            stringBuilder.setLength(0);
        }
        getData().addAll(seriesList);
    }

    public void clearSeriesLists() {
        getData().removeAll();
        seriesList.clear();
    }

    public ZoomHandler getZoomHandler() {
        return zoomHandler;
    }

    public static void setSeriesCount(int seriesCount) {
        Chart.seriesCount = seriesCount;
    }

    public static int getSeriesCount() {
        return Chart.seriesCount;
    }

    public boolean getIsAnimated() {
        return isAnimated;
    }

    public void setIsAnimated(boolean b) {
        isAnimated = b;
    }

    public boolean getIsSelecting() {
        return isSelecting;
    }

    public void setIsSelecting(boolean b) {
        isSelecting = b;
        if (!b) {
            firstNodeMarker.hideMarker();
            secondNodeMarker.hideMarker();
            fragmentMarker.setFragmentMarker(0, 0, 0, 0);
        }
    }

    public void customChart(){
        yAxis.setAutoRanging(isAutoRange);
        xAxis.setForceZeroInRange(false);
        xAxis.setMinorTickVisible(false);
        setHorizontalGridLinesVisible(true);
        setVerticalGridLinesVisible(false);
        setCreateSymbols(false);//bez kropek
        setBlendMode(BlendMode.EXCLUSION);
        setAnimated(false);
    }
    public boolean getIsAutoRanging() {
        return isAutoRange;
    }

    public NodeMarker getFirstNodeMarker() {
        return firstNodeMarker;
    }

    public NodeMarker getSecondNodeMarker() {
        return secondNodeMarker;
    }

    public FragmentMarker getFragmentMarker() {
        return fragmentMarker;
    }

    public void setAutoRangeAxisY(boolean b) {
        this.yAxis.setAutoRanging(b);
        this.isAutoRange = b;
    }

    public ChartCuttingHandler getChartCuttingHandler() {
        return chartCuttingHandler;
    }

    public void setChartShiftingHandler(boolean b) {
        if (!b) {
            setOnMouseDragged(null);
        } else {
            setOnMouseDragged(shiftingHandler);
        }
    }

    public void stopAnimation() {
        addDataToChart.stop();
    }

    public void startAnimation() {
        addDataToChart.start();
    }

    public AddDataToChart getAddDataToChart() {
        return addDataToChart;
    }

    public void startDrawing() {
        startAnimation();
        setIsAnimated(true);
        setOnScroll(null);
        setIsSelecting(false); //usuwa zaznaczenie
    }

    public void stopDrawing() {
        stopAnimation();
        setIsAnimated(false);
    }

    public NumberAxis getyAxis() {
        return yAxis;
    }

    public NumberAxis getxAxis() {
        return xAxis;
    }

    public void addContent(Node node) {
        this.getPlotChildren().add(node);
    }


    public void finalize(){
        chartCuttingHandler = null;
    }
}