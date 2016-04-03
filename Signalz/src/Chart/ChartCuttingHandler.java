package Chart;

import Frame.SignalzFrame;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import java.util.ArrayList;
import ChartMarker.*;
/**
 * Created by Przemek on 2016-02-24.
 */
public class ChartCuttingHandler {

    public int cuttingMode;
    private ArrayList<XYChart.Series<Number, Number>> seriesList;
    protected static ObservableList<XYChart.Data<Number, Number>> seriesData;
    private Chart chart;
    private NodeMarker firstMarker;
    private NodeMarker secondMarker;
    private Number firstNodeX;
    private Number secondNodeX;
    private Number firstNodeY;
    private Number secondNodeY;
    private int firstNodeIndex = 0;
    private int secondNodeIndex = 0;

    public ChartCuttingHandler(Chart chart) {
        this.chart = chart;
        this.seriesList = Chart.seriesList;
        this.cuttingMode = 1;
    }

    private void init() {
        this.firstMarker = chart.getFirstNodeMarker();
        this.secondMarker = chart.getSecondNodeMarker();
        this.firstNodeX = firstMarker.getNodeX();
        this.secondNodeX = secondMarker.getNodeX();
        this.firstNodeY = firstMarker.getNodeY();
        this.secondNodeY = secondMarker.getNodeY();
    }

    public void removeRangeChartNodes() {
        init();
        SignalzFrame.logger.info("Removing values from: " + firstNodeX + " to: "+ secondNodeX);
        for (int i = 0; i < seriesList.size(); i++) {//
            seriesData = seriesList.get(i).getData();//pobieram z pierwszej serii liste danych
            for (int j = 0; j < seriesData.size(); j++) {//dla kazdego elementu w serii
                // gdy markery w tym samym miejscu, porównuje tez Y i usuwa tylko dla danej serii
                if (secondNodeX.equals(firstNodeX) && seriesData.get(j).getXValue().equals(firstNodeX) && seriesData.get(j).getYValue().equals(firstNodeY)) {
                    firstNodeIndex = j;
                    seriesData.remove(firstNodeIndex);
                    UpdateChart(firstNodeIndex);
                    break;
                }
                if ((double) firstNodeX < (double) secondNodeX) {
                    if (cuttingMode == 2) {
                        if (removeSeriesNodes(firstNodeX, firstNodeY, secondNodeX, secondNodeY, j)) {
                            break;
                        }
                    } else if (cuttingMode == 1) {
                        if (removeNodes(firstNodeX, secondNodeX, j)) break;
                    }

                } else if ((double) firstNodeX > (double) secondNodeX) {
                    if (cuttingMode == 2) {
                        if (removeSeriesNodes(secondNodeX, secondNodeY, firstNodeX, firstNodeY, j)) {
                            break;
                        }
                    } else if (cuttingMode == 1) {
                        if (removeNodes(firstNodeX, secondNodeX, j)) break;
                    }
                }
            }
        }
    }

    private boolean removeSeriesNodes(Number firstNodeX, Number firstNodeY, Number secondNodeX, Number secondNodeY, int j) {
        SignalzFrame.logger.info("Removing values from: " + firstNodeX + " to: "+ secondNodeX);
        if (seriesData.get(j).getXValue().equals(firstNodeX) && seriesData.get(j).getYValue().equals(firstNodeY)) {//
            firstNodeIndex = j + 1;//nie usuwaj zaznaczonego, tylko od nastepnego
            //System.out.println("firstXind " + firstNodeIndex);
        } else if ((seriesData.get(j).getXValue().equals(secondNodeX)) && firstNodeIndex != 0 && seriesData.get(j).getYValue().equals(secondNodeY)) {//
            secondNodeIndex = j;
            //System.out.println("secondXind " + secondNodeIndex);
            seriesData.remove(firstNodeIndex, secondNodeIndex);
            UpdateChart(firstNodeIndex);
            return true;
        }
        return false;
    }

    private boolean removeNodes(Number firstNodeX, Number secondNodeX, int j) {
        if (seriesData.get(j).getXValue().equals(firstNodeX)) {//
            firstNodeIndex = j + 1;//nie usuwaj zaznaczonego, tylko od nastepnego
            //System.out.println("firstXind " + firstNodeIndex);
        } else if ((seriesData.get(j).getXValue().equals(secondNodeX)) && firstNodeIndex != 0) {//
            secondNodeIndex = j;
            //System.out.println("secondXind " + secondNodeIndex);
            seriesData.remove(firstNodeIndex, secondNodeIndex);
            UpdateChart(firstNodeIndex);
            return true;
        }
        return false;
    }

    private void UpdateChart(int firstNodeIndex) {
        int iterator = firstNodeIndex;
        double previousNodeX = (double) seriesData.get(firstNodeIndex - 1).getXValue();
        while (iterator != seriesData.size()) {
            previousNodeX += AddDataToChart.getDeltaT();
            seriesData.get(iterator).setXValue(previousNodeX);//wartosc wyswietlana
            ChartNode node = (ChartNode) seriesData.get(iterator).getNode();
            node.setX(previousNodeX);//wartosc w ChartNode
            seriesData.get(iterator).setNode(node);
            iterator++;
        }
        chart.getFirstNodeMarker().setNodeMarker(0, 0, 0, 0);
        chart.getSecondNodeMarker().setNodeMarker(0, 0, 0, 0);
        chart.getFragmentMarker().setFragmentMarker(0, 0, 0, 0);
    }

    public void setCuttingMode(int cuttingMode) {
        this.cuttingMode = cuttingMode;
    }
}