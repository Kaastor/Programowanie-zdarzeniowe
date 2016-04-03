package Data;

import Frame.FxSignalPanel;
import Frame.SignalzFrame;
import org.jdom2.DataConversionException;

import javax.swing.*;
import java.text.ParseException;
import java.util.LinkedList;

/**
 * Created by Przemek on 2016-03-07.
 */
public class JDOMParseXML extends SwingWorker<Integer, Void> {
    private String filename;
    private MeasurementData measurementData;
    private JDOMReader reader;
    private boolean isEnabled;
    private LinkedList<LinkedList> data;

    public JDOMParseXML(String filename) {
        this.filename = filename;
        reader = new JDOMReader(filename);

        this.isEnabled = false;
    }

    public Integer doInBackground() {
        try {
            data = reader.getDataFromXML();
        } catch (DataConversionException e) {
            SignalzFrame.logger.error("Cannot get data from XML.");
        }
        measurementData = FxSignalPanel.getNewMeasurementData(data.size());
        measurementData.setMeasurementsList(data);

        return reader.getSeriesCount();
    }

    public MeasurementData getMeasurementData() {
        return measurementData;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public JDOMReader getReader() {
        return reader;
    }
}