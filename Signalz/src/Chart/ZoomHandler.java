package Chart;

import Frame.FxSignalPanel;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.ScrollEvent;
import javafx.event.EventHandler;
/**
 * Created by Przemek on 2016-02-19.
 */
public class ZoomHandler implements EventHandler<ScrollEvent> {

    private static NumberAxis xAxis;
    private static NumberAxis yAxis;
    private double zoomTickX;
    private double zoomTickY;
    private double stopZoom;
    private Chart chart;
    private boolean isZooming = false;

    public ZoomHandler(Chart chart) {
        this.chart = chart;
        this.zoomTickX = Math.abs(FxSignalPanel.getAxisX().getUpperBound()) * 0.1;
        this.zoomTickY = Math.abs(FxSignalPanel.getAxisY().getUpperBound()) * 0.1;
        this.xAxis = FxSignalPanel.getAxisX();
        this.yAxis = FxSignalPanel.getAxisY();
        this.stopZoom = FxSignalPanel.getAxisX().getUpperBound();
    }

    public void handle(ScrollEvent event) {
        this.zoomTickX = Math.abs(FxSignalPanel.getAxisX().getUpperBound()) * 0.1;
        this.zoomTickY = Math.abs(FxSignalPanel.getAxisY().getUpperBound()) * 0.1;
        double Y = event.getDeltaY();
        if (!chart.getIsAnimated()) {
            if (Y > 0 && (stopZoom > zoomTickX)) {
                xAxis.setLowerBound(xAxis.getLowerBound() + zoomTickX);
                xAxis.setUpperBound(xAxis.getUpperBound() - zoomTickX);
                yAxis.setUpperBound(yAxis.getUpperBound() - zoomTickY);
                yAxis.setLowerBound(yAxis.getLowerBound() + zoomTickY);
            }
            if (Y < 0 && (stopZoom < 2 * FxSignalPanel.getMaxChartXpoints())) {
                xAxis.setLowerBound(xAxis.getLowerBound() - zoomTickX);
                xAxis.setUpperBound(xAxis.getUpperBound() + zoomTickX);
                yAxis.setUpperBound(yAxis.getUpperBound() + zoomTickY);
                yAxis.setLowerBound(yAxis.getLowerBound() - zoomTickY);
            }
            stopZoom = xAxis.getUpperBound() - xAxis.getLowerBound();
        }
    }

    public void zoomReset() {
        double newUpperBoundX = FxSignalPanel.getAxisX().getUpperBound();
        //System.out.print(newUpperBoundX);
        double newLowerBoundX = FxSignalPanel.getAxisX().getLowerBound();
        //System.out.print(newLowerBoundX);
        while (newUpperBoundX - Math.abs(newLowerBoundX) < FxSignalPanel.getMaxChartXpoints()) {
            if (newLowerBoundX > zoomTickX) {
                newLowerBoundX -= zoomTickX;
            }
            newUpperBoundX += zoomTickX;
        }
        xAxis.setLowerBound(newLowerBoundX);
        xAxis.setUpperBound(newUpperBoundX);
        // }
        if (!chart.getIsAutoRanging()) {//druga wersja zoomu wymaga tego
            yAxis.setUpperBound(chart.getAddDataToChart().getChartMaxY());
            yAxis.setLowerBound(chart.getAddDataToChart().getChartMinY());
        }
    }

    public boolean isZooming() {
        return isZooming;
    }

    public void setZooming(boolean zooming) {
        isZooming = zooming;
    }
}