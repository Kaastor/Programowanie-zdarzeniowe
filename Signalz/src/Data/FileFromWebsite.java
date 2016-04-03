package Data;

/**
 * Created by Przemek on 2016-03-20.
 */
import Frame.*;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Scanner;


public class FileFromWebsite extends SwingWorker<Integer, Void> {

    private LinkedList<LinkedList> dataLists;
    private String url;
    private String fileName;
    private MeasurementData measurementData;
    private int seriesCount =0;
    private boolean isEnabled = false;

    public FileFromWebsite(String url, String fileName){
        dataLists = new LinkedList();
        this.url = url;
        this.fileName = fileName;
        init();
    }

    public void init() {
        try {// zapiisanie ze strony internetowej
                saveFile(new URL(url), fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer doInBackground() {

        //otwarcie do odczytu
        File f = new File("data.txt");
        try {
            Scanner input = new Scanner(f);
            String s = input.nextLine();
            for(int i = 0; i < s.length(); i++) {
                s = s.replaceAll("\\s+$", "");
                s = s.replaceAll("^\\s+", "");
                s = s.replaceAll("\\s+", " ");
                if(Character.isWhitespace(s.charAt(i))) seriesCount++;
            }
            seriesCount++;
            //System.out.println(seriesCount);
            input = new Scanner(f);
            for (int i = 0; i < seriesCount; i++) { // init list tyle ile kolumn
                dataLists.add(new LinkedList());
            }
            while (input.hasNext()) {
                for (int i = 0; i < seriesCount; i++) {
                    dataLists.get(i).add(Double.parseDouble(input.next()));
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        measurementData = FxSignalPanel.getNewMeasurementData(seriesCount);
        measurementData.setMeasurementsList(dataLists);
        return seriesCount;
    }

    public static boolean saveFile(URL fileURL, String fileSavePath) {

        boolean isSucceed = true;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(fileURL.toString());
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity fileEntity = httpResponse.getEntity();
            if (fileEntity != null) {
                FileUtils.copyInputStreamToFile(fileEntity.getContent(), new File(fileSavePath));
            }
        } catch (IOException e) {
            isSucceed = false;
            SignalzFrame.logger.error("Cannot save data to file from web link.");
        }
        httpGet.releaseConnection();
        return isSucceed;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getSeriesCount() {
        return seriesCount;
    }

}


