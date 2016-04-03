package Data;

import Frame.FxSignalPanel;
import Frame.SignalzFrame;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.text.html.parser.Parser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.LinkedList;

public class SAXParseXML extends SwingWorker<Integer, Void> {

    private String filename;
    private MeasurementData measurementData;
    private LinkedList<LinkedList> data;
    private SAXHandler saxHandler;
    private SAXParser parser;
    private boolean isEnabled;

    public SAXParseXML(String filename) {
        this.filename = filename;
        this.isEnabled = false;
    }

    public Integer doInBackground(){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        saxHandler = new SAXHandler();
        try {
            parser = factory.newSAXParser();
            data = saxHandler.readDataFromXML(filename, parser);
        }
        catch (ParserConfigurationException|SAXException|IOException e){
            SignalzFrame.logger.info("Cannot get data from SAX Parser.");
        }

        measurementData = FxSignalPanel.getNewMeasurementData(data.size());
        measurementData.setMeasurementsList(data);
        //readDataFromXML zwraca liste z danymi ktore zostaly pobramne
        // z pliku xmL gdzie tagiem jest 'measurement'

        return saxHandler.getSeriesCount();
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

    public SAXHandler getSaxHandler() {
        return saxHandler;
    }
}
