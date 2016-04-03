package ChartMarker;

import javafx.scene.paint.Color;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by Przemek on 2016-03-01.
 */
public class NodeMarker extends JComponent implements ChangeListener {

    private NodeMarkerModel model;

    public NodeMarker() {
        init(new NodeMarkerModel());
    }

    public NodeMarker(NodeMarkerModel model) {
        init(model);
    }


    protected void init(NodeMarkerModel model) {
        setModel(model);
        updateMarkerOnChart();
        updateUI();
    }

    public void setUI(NodeMarkerUI ui) {
        super.setUI(ui);
    }

    public void updateUI() {
        setUI((NodeMarkerUI) UIManager.getUI(this));
        invalidate();
    }

    public String getUIClassID() {
        return NodeMarkerUI.UI_CLASS_ID;
    }


    public void setModel(NodeMarkerModel model) {
        NodeMarkerModel old = this.model;
        if (old != null) old.removeChangeListener(this);
        if (model == null) model = new NodeMarkerModel();
        else this.model = model;
        this.model.addChangeListener(this);
        firePropertyChange("model", old, this.model);
    }

    public NodeMarkerModel getModel() {
        return model;
    }


    public void setNodeMarker(double startX, double endX, double startY, double endY) {
        setStartX(startX);
        setEndX(endX);
        setStartY(startY);
        setEndY(endY);

    }

    public void setNodeMarker(ChartNode chartNode) {
        model.setNodeMarker(chartNode);
    }

    public void updateMarkerOnChart() {
        model.getChart().addContent(model.getMarker());
    }

    public double getNodeY() {
        return model.getNodeY();
    }

    public double getNodeX() {
        return model.getNodeX();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        repaint();
    }

    public double getEndY() {
        return model.getEndY();
    }

    public void setEndY(double endY) {
        double old = model.getEndY();
        if (endY != old) model.setEndY(endY);
        firePropertyChange("endY", old, endY);
    }

    public double getStartY() {
        return model.getStartY();
    }

    public void setStartY(double startY) {
        double old = model.getStartY();
        if (startY != old) model.setStartY(startY);
        firePropertyChange("startY", old, startY);
    }

    public double getEndX() {
        return model.getEndX();
    }

    public void setEndX(double endX) {
        double old = model.getEndX();
        if (endX != old) model.setEndX(endX);
        firePropertyChange("endX", old, endX);
    }

    public double getStartX() {
        return model.getStartX();
    }

    public void setStartX(double startX) {
        double old = model.getStartX();
        if (startX != old) model.setStartX(startX);
        firePropertyChange("startX", old, startX);
    }

    public void setColor(Color c) {
        model.setColor(c);
    }

    public void setThickness(double thickness) {
        this.model.setThickness(thickness);
    }

    public void hideMarker() {
        model.hideMarker();
    }
}
