package ChartMarker;
import Chart.Chart;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * Created by Przemek on 2016-02-21.
 */
public class ChartNode extends StackPane {

    private double X;
    private double Y;
    private ChartNode chartNode;
    private Chart chart;

    public ChartNode(Chart chart, double value, double x) {
        setPrefSize(5, 5);
        this.chart = chart;
        this.X = x;
        this.Y = value;
        this.chartNode = this;

        final Label label = createDataThresholdLabel(value);

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getChildren().setAll(label);
                setCursor(Cursor.NONE);
                toFront();

                setOnMousePressed(new EventHandler<MouseEvent>() {//tu pobiera wspolzedne NODA i moze cos z nimi zrobic po kliknieciu np
                    @Override
                    //tu słuchacz markera,
                    public void handle(MouseEvent mouseEvent) {//obsluga zaznaczania po nacisnieciu w node'a
                        if (chart.getIsSelecting() == true) {
                            if (mouseEvent.isPrimaryButtonDown()) { //LPM
                                chart.getFirstNodeMarker().setNodeMarker(chartNode);
                                chart.getFragmentMarker().setFragmentMarker(chartNode);
                            }
                            if (mouseEvent.isSecondaryButtonDown()) { //LPM
                                chart.getSecondNodeMarker().setNodeMarker(chartNode);
                                chart.getFragmentMarker().setFragmentMarker(chartNode);
                            }
                        }
                    }
                });
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getChildren().clear();
                setCursor(Cursor.CROSSHAIR);
            }
        });
    }

    private Label createDataThresholdLabel(double value) {
        final Label label = new Label(""  + value );
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }

    public void setX(double d) {
        this.X = d;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public Chart getChart() {
        return chart;
    }
}





