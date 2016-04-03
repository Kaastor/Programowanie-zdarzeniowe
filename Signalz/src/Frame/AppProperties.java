package Frame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Przemek on 2016-03-12.
 */
public class AppProperties extends Properties {

    private InputStream fileInputStream;

    public AppProperties(String fileName) throws FileNotFoundException, IOException{
        super();
        fileInputStream = new FileInputStream(fileName);
        this.load(fileInputStream);
    }


}
