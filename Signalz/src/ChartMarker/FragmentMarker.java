package ChartMarker;

import Frame.FxSignalPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Przemek on 2016-03-01.
 */
public class FragmentMarker extends Rectangle {

    private NodeMarker marker;
    private NodeMarker marker2;

    public FragmentMarker(double x, double y, double width, double height) {
        super(x, y, width, height);
        setStroke(Color.TRANSPARENT);
        setFill(Color.BLUE.deriveColor(1, 1, 1, 0.2));
    }

    public void setFragmentMarker(double startX, double startY, double width, double height) {
        setX(startX);
        setY(startY);
        setWidth(width);
        setHeight(height);
    }

    public void setFragmentMarker(ChartNode chartNode) {
        NodeMarker marker = getMarker();
        NodeMarker marker2 = getMarker2();
        //LPM -> PPM
        if (marker.getStartX() != marker2.getStartX()) {
            if (marker.getStartX() < FxSignalPanel.getAxisX().getDisplayPosition(chartNode.getX())) {
                setLeftToRightRectangle(marker, chartNode);
            }
            if (marker.getStartX() > FxSignalPanel.getAxisX().getDisplayPosition(chartNode.getX())) {
                setRightToLeftRectangle(marker, marker2, chartNode);
            }
            //PPM -> LPM
            if (marker2.getStartX() < FxSignalPanel.getAxisX().getDisplayPosition(chartNode.getX())) {
                setLeftToRightRectangle(marker2, chartNode);
            }
            if (marker2.getStartX() > FxSignalPanel.getAxisX().getDisplayPosition(chartNode.getX())) {
                setRightToLeftRectangle(marker2, marker, chartNode);
            }
        } else {
            setFragmentMarker(0, 0, 0, 0);
        }
    }

    private void setLeftToRightRectangle(NodeMarker nodeMarker, ChartNode chartNode) {
        double startX, startY, width, height;
        startX = nodeMarker.getStartX();
        startY = nodeMarker.getStartY();
        width = Math.abs(FxSignalPanel.getAxisX().getDisplayPosition(chartNode.getX())) - Math.abs(nodeMarker.getEndX());
        height = Math.sqrt(Math.pow(nodeMarker.getStartY(), 2) + Math.pow(nodeMarker.getEndY(), 2));
        setFragmentMarker(startX, startY, width, height);
    }

    private void setRightToLeftRectangle(NodeMarker nodeMarker, NodeMarker nodeMarker2, ChartNode chartNode) {
        double startX, startY, width, height;
        startX = nodeMarker2.getStartX();
        startY = nodeMarker2.getStartY();
        width = Math.abs(nodeMarker.getStartX() - Math.abs(FxSignalPanel.getAxisX().getDisplayPosition(chartNode.getX())));
        height = Math.sqrt(Math.pow(nodeMarker.getStartY(), 2) + Math.pow(nodeMarker.getEndY(), 2));
        setFragmentMarker(startX, startY, width, height);
    }

    public NodeMarker getMarker() {
        return marker;
    }

    public void setMarker(NodeMarker marker) {
        this.marker = marker;
    }

    public NodeMarker getMarker2() {
        return marker2;
    }

    public void setMarker2(NodeMarker marker2) {
        this.marker2 = marker2;
    }
}