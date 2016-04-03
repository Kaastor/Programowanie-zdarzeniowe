package Data; /**
 * Created by Przemek on 2016-03-07.
 */
import Frame.Application;
import Frame.SignalzFrame;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JDOMReader {

    private LinkedList<LinkedList> data;
    private File file;
    private SAXBuilder saxBuilder;
    private Document document = null;
    private String filename;
    private int seriesCount;

    public JDOMReader(String filename){
        data = new LinkedList<>();
        file = new File(filename);
        this.filename = filename;
        saxBuilder = new SAXBuilder();
    }

    public LinkedList<LinkedList> getDataFromXML() throws DataConversionException {
        try {
            document = saxBuilder.build(file); //document zawiera tree xml
        } catch (JDOMException | IOException e) {
            SignalzFrame.logger.error("Cannot build JDOM document from file.");
            return null;
        }

        Element root = document.getRootElement();
        seriesCount = root.getChildren().size();

        //szukam wszystkich elementow o tagu measurement
        List<Element> customerElements = root.getChildren(Application.appProperties.getProperty("xmlMeasurementTag"));

        for (int i = 0; i < seriesCount; i++) {
            //przejscie po tagach customer
            //dodanie listy na values pomiaru
            data.add(new LinkedList<>());
            Element element = customerElements.get(i); //element to jeden z tagów measurement
            //zaczyna od 1 bo pomijam atrybut ID (element.getChildren().size())
            for(int j = 1; j<= element.getChildren().size() ; j++){
                data.get(i).add(Double.parseDouble(element.getChildText(Application.appProperties.getProperty("tagMeasurementValueName")+j)));
            }
        }
        return data;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getSeriesCount() {
        return seriesCount;
    }

}

