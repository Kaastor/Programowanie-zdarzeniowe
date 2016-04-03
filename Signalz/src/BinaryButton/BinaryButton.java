package BinaryButton;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import java.awt.*;
import Frame.*;

/**
 * Created by Przemek on 2016-03-09.
 */
public class BinaryButton extends JToggleButton {

    private ImageIcon off = new ImageIcon(Application.appProperties.getProperty("BinaryButtonIconOff"));
    private ImageIcon on = new ImageIcon(Application.appProperties.getProperty("BinaryButtonIconOn"));
    private String label;

    public BinaryButton(String label, boolean selected) {
        super();
        this.label = label;
        if (selected) {
            setIcon(on);
        } else {
            setIcon(off);
        }
        setEnabled(true);
        setSelected(selected);
        setBorderPainted(false);
        setBorder(null);
        setFocusable(false);
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setSelectedIcon(on);
        setDisabledIcon(off);
        setDisabledSelectedIcon(off);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font font = new Font(Application.appProperties.getProperty("BinaryButtonFontName"), Font.BOLD, 15);
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(label, 5, 20);

    }
    @Override
    public void setText(String label) {
        String oldValue = this.label;
        this.label = label;
        firePropertyChange(TEXT_CHANGED_PROPERTY, oldValue, label);

        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                    AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                    oldValue, label);
        }
        if (label == null || oldValue == null || !label.equals(oldValue)) {
            revalidate();
            repaint();
        }
    }
}
