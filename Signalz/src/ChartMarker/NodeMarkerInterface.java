package ChartMarker;

import javax.swing.event.ChangeListener;

/**
 * Created by Przemek on 2016-03-11.
 */
public interface NodeMarkerInterface {

    void setNodeMarker(double startX, double endX, double startY, double endY);
    void setNodeMarker(ChartNode chartNode);
    double getNodeY();
    double getNodeX();

    void addChangeListener(ChangeListener changeListener);
    void removeChangeListener(ChangeListener changeListener);
    ChangeListener[] getChangeListeners();
}
