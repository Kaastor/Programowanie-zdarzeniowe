package Data;

import Frame.Application;
import Frame.FxSignalPanel;
import Frame.SignalzFrame;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Przemek on 2016-03-02.
 */
public class DataBaseDriver extends SwingWorker {

    private boolean isEnabled = false;
    private static int tableCount = 0;
    private Connection connection;
    private Statement statement;
    private DatabaseMetaData metaData;
    private ResultSet tableSet;
    private ResultSet resultTableSet;
    private MeasurementData measurementData;
    private String dataBaseUser = Application.appProperties.getProperty("dataBaseUser");
    private String dataBasePassword = Application.appProperties.getProperty("dataBasePassword");
    private String dataBaseURL = Application.appProperties.getProperty("dataBaseURL");

    public DataBaseDriver() {
        try {
            connection = DriverManager.getConnection(dataBaseURL, dataBaseUser, dataBasePassword);//1. connection to database
            statement = connection.createStatement();//2.obiekt do zapytań
            metaData = connection.getMetaData(); //metadane, tabele itp
            tableSet = metaData.getTables(null, null, "%", null); //tabele
        } catch (SQLException ex) {
            SignalzFrame.logger.error("Cannot connect to database.");
        }
    }

    public Void doInBackground() {
        try {
            tableCount = getTableCount();
            measurementData = FxSignalPanel.getNewMeasurementData(getTableCount()); //tyle list danych ile tabel
            //ładowanie danych do measurement Data obj.
            for (int i = 1; i <= tableCount; i++) {
                resultTableSet = statement.executeQuery("SELECT * from measurement" + i);
                while (resultTableSet.next()) {
                    //i-1, bo tabele numerowane od 1 a nie 0 jak listy
                    measurementData.addDataToMeasurementList(i - 1, resultTableSet.getDouble(Application.appProperties.getProperty("tableValueName")));
                }
            }
        } catch (SQLException ex) {
            SignalzFrame.logger.error("Cannot execute SQL query.");
        }
        return null;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getTableCount() {
        int tablesCount = 0;
        try {
            tableSet = null;
            tableSet = metaData.getTables(null, null, "%", null); //tabele
            while (tableSet.next()) {
                tablesCount++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tablesCount;
    }
}