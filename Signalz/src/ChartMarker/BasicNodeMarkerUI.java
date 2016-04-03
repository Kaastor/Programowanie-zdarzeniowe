package ChartMarker;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

/**
 * Created by Przemek on 2016-03-11.
 */
public class BasicNodeMarkerUI extends NodeMarkerUI {

    public static ComponentUI createUI(JComponent c) {
        return new BasicNodeMarkerUI();
    }


    public void installUI(JComponent c) {
        NodeMarker nodeMarker = (NodeMarker) c;
    }

    public void uninstallUI(JComponent c) {
        NodeMarker nodeMarker = (NodeMarker) c;
    }

    public void paint(Graphics g, JComponent c) {
        update((NodeMarker) c);
    }

    public void update(NodeMarker nodeMarker) {
        nodeMarker.updateMarkerOnChart();
    }

}
