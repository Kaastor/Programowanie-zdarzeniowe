package ChartMarker;

import Frame.FxSignalPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import Chart.*;
/**
 * Created by Przemek on 2016-03-11.
 */
public class NodeMarkerModel implements NodeMarkerInterface {

    protected EventListenerList listenerList = new EventListenerList();
    protected ChangeListener changeListener = null;
    protected transient ChangeEvent changeEvent;

    private Chart chart;
    private Line marker = new Line();
    private double thickness;

    private double nodeY;
    private double nodeX;

    public NodeMarkerModel() {
        setMarker(0,0,0,0);
        setThickness(2);
    }
    public NodeMarkerModel(Chart chart) {
        this.chart = chart;
        setMarker(0,0,0,0);
        setThickness(2);
    }
    public NodeMarkerModel(Chart chart, double startX, double endX, double startY, double endY) {
        this.chart = chart;
        setThickness(2);
        setMarker(startX,endX,startY,endY);
    }

    @Override
    public void setNodeMarker(double startX, double endX, double startY, double endY) {
        setMarker(startX,endX,startY,endY);
        fireStateChanged();
    }

    @Override
    public void setNodeMarker(ChartNode chartNode) {
        nodeY = chartNode.getY();
        nodeX = chartNode.getX();
        marker.setStartX(chartNode.getChart().getxAxis().getDisplayPosition(chartNode.getX()));
        marker.setEndX(chartNode.getChart().getxAxis().getDisplayPosition(chartNode.getX()));
        marker.setStartY(chartNode.getChart().getyAxis().getDisplayPosition(chartNode.getChart().getyAxis().getUpperBound()));
        marker.setEndY(chartNode.getChart().getyAxis().getDisplayPosition(chartNode.getChart().getyAxis().getLowerBound()));
        fireStateChanged();
    }

    private void setMarker(double startX, double endX, double startY, double endY){//wspolrzedne wykresu -> Display
        marker.setStartX(FxSignalPanel.getAxisX().getDisplayPosition(startX));
        marker.setEndX(FxSignalPanel.getAxisX().getDisplayPosition(endX));
        marker.setStartY(FxSignalPanel.getAxisY().getDisplayPosition(startY));
        marker.setEndY(FxSignalPanel.getAxisY().getDisplayPosition(endY));
        fireStateChanged();
    }


    @Override
    public double getNodeY() {
        return nodeY;
    }

    @Override
    public double getNodeX() {
        return nodeX;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }


    @Override
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    @Override
    public ChangeListener[] getChangeListeners() { return listenerList.getListeners(ChangeListener.class); }

    protected void fireStateChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChangeListener.class) {
                // Lazily create the event:
                if (changeEvent == null)
                    changeEvent = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }
    public double getThickness() {
        return marker.getStrokeWidth();
    }

    public void setThickness(double thickness) {
        this.marker.setStrokeWidth(thickness);
    }

    public void setColor(Color c){
        marker.setStroke(c);
    }

    public double getEndY() {
        return marker.getEndY();
    }

    public void setEndY(double endY) {
        this.marker.setEndY(FxSignalPanel.getAxisY().getDisplayPosition(endY));
        fireStateChanged();
    }

    public double getStartY() {
        return marker.getStartY();
    }

    public void setStartY(double startY) {
        this.marker.setStartY(FxSignalPanel.getAxisY().getDisplayPosition(startY));
        fireStateChanged();
    }

    public double getEndX() {
        return marker.getEndX();
    }

    public void setEndX(double endX) {
        this.marker.setEndX(FxSignalPanel.getAxisX().getDisplayPosition(endX));
        fireStateChanged();
    }

    public double getStartX() { return marker.getStartX(); }

    public void setStartX(double startX) {
        this.marker.setStartX(FxSignalPanel.getAxisX().getDisplayPosition(startX));
        fireStateChanged();
    }

    public void hideMarker(){
        marker.setStartX(0);
        marker.setEndX(0);
        marker.setStartY(0);
        marker.setEndY(0);
        fireStateChanged();
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Line getMarker() {
        return marker;
    }

    public void setMarker(Line marker) {
        this.marker = marker;
        fireStateChanged();
    }
}
