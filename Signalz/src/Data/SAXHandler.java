package Data;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import Frame.Application;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SAXHandler extends DefaultHandler {

    private LinkedList<LinkedList> data;
    private String currentElement = "";
    private StringBuilder currentText;
    private int seriesCount;

    private double amount;
    private int id;
    public LinkedList<LinkedList> readDataFromXML(String filename, SAXParser parser) throws SAXException, IOException, ParserConfigurationException {
        parser.parse(new File(filename), this);
        return data;
    }

    @Override
    public void startDocument() throws SAXException {
        //metoda wywoływana przy root tagu
        //lista na dane z pliku xml
        //System.out.println("Start document");
        this.seriesCount = 0;
        this.data = new LinkedList<>();
    }

    @Override
    public void endDocument() throws SAXException {
        //metoda wywoływana przy końcu root tagu
        //System.out.println("End document");
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //metoda wywoływana przy tagu
        currentElement = qName;

        switch(currentElement){
            case "root": //root tag, nic nie robie
                break;
            case "measurement": //tag customer, stad chce pobrac dane
                id = Integer.parseInt(attributes.getValue(Application.appProperties.getProperty("tagMeasurementIdAttribute")));//id, amount to atrybuty z taga measurement
                amount = Double.parseDouble(attributes.getValue(Application.appProperties.getProperty("tagMeasurementAmountAttribute")));
                data.add(new LinkedList());
                seriesCount++;
            default:
                currentText = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //currentElement = ""; //reset stringa przechowującego nazwe aktualnego taga
        //jesli opuszczam root lub measurement, nic mnie nie obchodzi i wracam
        if(currentElement.equals(Application.appProperties.getProperty("rootTag")) ||
                currentElement.equals(Application.appProperties.getProperty("xmlMeasurementTag")))
            return;

        String content = currentText.toString(); //aktualny element, np value1, sprawdzam go przed opuszczeniem

        for(int i=0 ; i<amount ; i++){
            String valueNumber = "value"+i;
            if(currentElement.equals(valueNumber)){
                data.get(id-1).add(Double.parseDouble(content));
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (currentText != null){
            currentText.append(ch, start, length);
        }
    }

    public int getSeriesCount() {
        return seriesCount;
    }
}
