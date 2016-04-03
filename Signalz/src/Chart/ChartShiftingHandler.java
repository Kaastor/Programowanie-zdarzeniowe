package Chart;

import Frame.FxSignalPanel;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.MouseEvent;
/**
 * Created by Przemek on 2016-02-19.
 */
public class ChartShiftingHandler implements EventHandler<MouseEvent> {

    private SimpleDoubleProperty oldMouseX = new SimpleDoubleProperty();
    private SimpleDoubleProperty oldMouseY = new SimpleDoubleProperty();
    private double shiftMeasureX; // o ile sie przesunie
    private double shiftMeasureY; // o ile sie przesunie
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private AddDataToChart addDataToChart;

    public ChartShiftingHandler(AddDataToChart addDataToChart) {
        this.addDataToChart = addDataToChart;
        this.shiftMeasureX = FxSignalPanel.getMaxChartXpoints() * 0.05;
        this.xAxis = FxSignalPanel.getAxisX();
        this.yAxis = FxSignalPanel.getAxisY();
    }

    public void handle(MouseEvent mouseEvent) {
        this.shiftMeasureY = (Math.abs(addDataToChart.getChartMaxY()) + Math.abs(addDataToChart.getChartMinY())) * 0.01;
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {//jesli przeciagam myszka (wykresem)
            //Axis X

            double newLowerX = xAxis.getLowerBound();
            double newUpperX = xAxis.getUpperBound();
            double newLowerY = yAxis.getLowerBound();
            double newUpperY = yAxis.getUpperBound();

            if (newLowerX > 0 && oldMouseX.get() > mouseX) { //wykres w lewo
                newLowerX = xAxis.getLowerBound() - shiftMeasureX;
                newUpperX = xAxis.getUpperBound() - shiftMeasureX;
            }
            if (newUpperX < addDataToChart.getChartMaxX() && oldMouseX.get() < mouseX) { // wykres w prawo
                newLowerX = xAxis.getLowerBound() + shiftMeasureX;
                newUpperX = xAxis.getUpperBound() + shiftMeasureX;
            }
            if (newUpperY < addDataToChart.getChartMaxY() && oldMouseY.get() > mouseY) { // wykres w prawo
                newLowerY = yAxis.getLowerBound() + shiftMeasureY;
                newUpperY = yAxis.getUpperBound() + shiftMeasureY;
            }
            if (newLowerY > addDataToChart.getChartMinY() && oldMouseY.get() < mouseY) { // wykres w prawo
                newLowerY = yAxis.getLowerBound() - shiftMeasureY;
                newUpperY = yAxis.getUpperBound() - shiftMeasureY;
            }

            if (newLowerX < 0) newLowerX = 0;
            if (newLowerY < addDataToChart.getChartMinY())
                newLowerY = addDataToChart.getChartMinY();
            xAxis.setLowerBound(newLowerX);
            xAxis.setUpperBound(newUpperX);
            yAxis.setLowerBound(newLowerY);
            yAxis.setUpperBound(newUpperY);
            oldMouseX.set(mouseX);
            oldMouseY.set(mouseY);

        }
    }
}