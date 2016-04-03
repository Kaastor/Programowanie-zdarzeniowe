package Frame;

import javax.swing.*;
import ChartMarker.*;

import java.io.IOException;

public class Application {
    public static AppProperties appProperties;
//TODO


    static {
        try {
            appProperties = new AppProperties("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UIManager.put(NodeMarkerUI.UI_CLASS_ID, BasicNodeMarkerUI.class.getName());
                SignalzFrame frame = new SignalzFrame(appProperties);

                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                        if (JOptionPane.showConfirmDialog(frame,
                                "Are you sure ?", "Exit",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                            frame.safeExit();
                        }
                    }
                });
            }
        });
    }
}