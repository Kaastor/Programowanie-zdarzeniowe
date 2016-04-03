package Frame; /**
 * Created by Przemek on 2015-12-14.
 */
import Chart.*;
import Data.DataBaseDriver;
import Data.FileFromWebsite;
import Data.JDOMParseXML;
import Data.SAXParseXML;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.effect.BlendMode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import BinaryButton.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class SignalzFrame extends JFrame {

    private Locale currentLocale;
    private ResourceBundle messages;
    public static Logger logger ;
    private AppProperties appProperties;
    private String xmlSAXFileName;
    private String xmlJDOMFileName;
    private String websiteURL;
    private String websiteFileName;
    private static DataBaseDriver dataBaseDriver;
    private static SAXParseXML saxParseXML;
    private static JDOMParseXML jdomParseXML;
    private static FileFromWebsite fileFromWebsite;
    private JMenuBar menuBar;
    private JMenu dataMenu;
    private JMenu settingsMenu;
    private JMenu languageMenu;
    private JMenuItem languagePolish;
    private JMenuItem languageEnglish;
    private JMenu xmlMenu;
    private JMenu zoomMenu;
    private JMenu cuttingMenu;
    private JMenuItem dbDataFile;
    private JMenuItem websiteFile;
    private JMenuItem exitFile;
    private JMenuItem xmlSaxDataFile;
    private JRadioButtonMenuItem zoomMode1;
    private JRadioButtonMenuItem zoomMode2;
    private JRadioButtonMenuItem cuttingMode1;
    private JRadioButtonMenuItem cuttingMode2;
    private JMenuItem xmlDomDataFile;
    private BinaryButton zoomButton;
    private JMenuItem zoomResetButton;
    private BinaryButton selectButton;
    private BinaryButton runButton;
    private JMenuItem cutButton;
    private JMenuItem clearChartButton;
    private JPanel rightButtons;
    private JCheckBoxMenuItem animationCheckBox;
    private JCheckBoxMenuItem darkModeCheckBox;
    private FxSignalPanel signalPanel;
    private final JFXPanel chartPanel;
    private JFrame frame;

    private String runButtonLabel;
    private String zoomButtonLabel;
    private String selectButtonLabel;

    public SignalzFrame(AppProperties appProperties) {
        super(appProperties.getProperty("appTitle"));
        PropertyConfigurator.configure("log4j.properties");
        logger = Logger.getLogger("Logger");
        logger.info("Application started.");
        this.frame = this;
        this.appProperties = appProperties;
        currentLocale = new Locale(appProperties.getProperty("languageEN"),
                appProperties.getProperty("countryEN"));
        messages = ResourceBundle.getBundle(appProperties.getProperty("languageBundle"), currentLocale);

        xmlSAXFileName = appProperties.getProperty("xmlSAXFileName");
        xmlJDOMFileName = appProperties.getProperty("xmlJDOMFileName");
        websiteURL = appProperties.getProperty("fileURL");
        websiteFileName = appProperties.getProperty("outputFileName");

        fileFromWebsite = new FileFromWebsite(websiteURL, websiteFileName);
        dataBaseDriver = new DataBaseDriver();// ---------DATA BASE DRIVER
        saxParseXML = new SAXParseXML(xmlSAXFileName);
        jdomParseXML = new JDOMParseXML(xmlJDOMFileName);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(menuBar = new JMenuBar());
        menuBar.setBackground(Color.decode("#FFAB11"));

        dataMenu = new JMenu();
        settingsMenu = new JMenu();
        languageMenu = new JMenu();
        languageEnglish = new JMenuItem();
        languagePolish = new JMenuItem();
        xmlMenu = new JMenu();
        zoomMenu = new JMenu();
        cuttingMenu = new JMenu();
        dbDataFile = new JMenuItem();
        websiteFile = new JMenuItem();
        xmlDomDataFile = new JMenuItem();
        xmlSaxDataFile = new JMenuItem();
        zoomMode1 = new JRadioButtonMenuItem();
        zoomMode1.setSelected(true);
        zoomMode2 = new JRadioButtonMenuItem();
        cuttingMode1 = new JRadioButtonMenuItem();
        cuttingMode2 = new JRadioButtonMenuItem();
        cuttingMode1.setSelected(true);
        exitFile = new JMenuItem();
        animationCheckBox = new JCheckBoxMenuItem();
        animationCheckBox.setSelected(false);
        darkModeCheckBox = new JCheckBoxMenuItem();
        darkModeCheckBox.setSelected(true);
        cutButton = new JMenuItem();
        cutButton.setEnabled(false);
        clearChartButton = new JMenuItem();
        zoomResetButton = new JMenuItem();
        chartPanel = new JFXPanel();
        runButtonLabel = new String(messages.getString("runButtonLabel"));
        zoomButtonLabel = new String(messages.getString("zoomButtonLabel"));
        selectButtonLabel = new String(messages.getString("selectButtonLabel"));
        runButton = new BinaryButton(runButtonLabel, false);
        runButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomButton = new BinaryButton(zoomButtonLabel, false);
        zoomButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton = new BinaryButton(selectButtonLabel, false);
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadStrings();

        ButtonGroup zoomGroup = new ButtonGroup();
        zoomGroup.add(zoomMode1);
        zoomGroup.add(zoomMode2);

        ButtonGroup cuttingGroup = new ButtonGroup();
        cuttingGroup.add(cuttingMode1);
        cuttingGroup.add(cuttingMode2);

        menuBar.add(dataMenu);
        menuBar.add(settingsMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(languageMenu);
        languageMenu.add(languageEnglish);
        languageMenu.add(languagePolish);
        settingsMenu.add(clearChartButton);
        settingsMenu.add(animationCheckBox);
        settingsMenu.add(darkModeCheckBox);
        settingsMenu.add(zoomMenu);
        settingsMenu.add(zoomResetButton);
        settingsMenu.add(cuttingMenu);
        settingsMenu.add(cutButton);
        zoomMenu.add(zoomMode1);
        zoomMenu.add(zoomMode2);
        cuttingMenu.add(cuttingMode1);
        cuttingMenu.add(cuttingMode2);
        dataMenu.add(dbDataFile);
        dataMenu.add(websiteFile);
        dataMenu.add(xmlMenu);
        dataMenu.addSeparator();
        dataMenu.add(exitFile);
        xmlMenu.add(xmlSaxDataFile);
        xmlMenu.add(xmlDomDataFile);

        runButton.setMnemonic(KeyEvent.VK_S);
        dataMenu.setMnemonic(KeyEvent.VK_D);
        settingsMenu.setMnemonic(KeyEvent.VK_T);
        languageMenu.setMnemonic(KeyEvent.VK_L);
        exitFile.setMnemonic(KeyEvent.VK_L);
        dbDataFile.setMnemonic(KeyEvent.VK_D);
        xmlMenu.setMnemonic(KeyEvent.VK_X);
        xmlSaxDataFile.setMnemonic(KeyEvent.VK_S);
        xmlDomDataFile.setMnemonic(KeyEvent.VK_J);
        animationCheckBox.setMnemonic(KeyEvent.VK_A);
        darkModeCheckBox.setMnemonic(KeyEvent.VK_M);

        Container contentPane = this.getContentPane(); //obszar
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(java.awt.Color.darkGray);
        rightButtons = new JPanel();
        rightButtons.setLayout(new BoxLayout(rightButtons, BoxLayout.Y_AXIS));
        rightButtons.setBackground(Color.BLACK);//
        contentPane.add(rightButtons, BorderLayout.EAST);
        rightButtons.add(runButton);
        rightButtons.add(zoomButton);
        rightButtons.add(selectButton);


        signalPanel = new FxSignalPanel();
        Platform.runLater(new Runnable() {  //call the initFX method to create the JavaFX scene on the JavaFX application thread
            @Override
            public void run() {
                signalPanel.initFX();
            }
        });
        this.add(signalPanel); //dodanie panelu

        contentPane.add(signalPanel, BorderLayout.CENTER);

        exitFile.addActionListener(new ActionListener() { //zamykanie aplikacji przyciskiem Exit z menu
            public void actionPerformed(ActionEvent event) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    safeExit();
                }
            }
        });

        runButton.addActionListener(new ActionListener() { //wznowienie animacji/rysowania
            public void actionPerformed(ActionEvent event) {
                if (!FxSignalPanel.getMeasurementData().getMeasurementsList().isEmpty()) {
                    if (runButton.isSelected()) {
                        signalPanel.getChart().getZoomHandler().zoomReset();
                        runButton.setSelected(true);
                        signalPanel.getChart().setIsAnimated(true);
                        zoomButton.setEnabled(false);
                        signalPanel.getChart().startDrawing();
                        activateShifting();
                        logger.info("Chart is running...");
                    }
                    if (!runButton.isSelected()) {
                        logger.info("Chart stopped.");
                        runButton.setSelected(false);
                        signalPanel.getChart().setIsAnimated(false);
                        zoomButton.setEnabled(true);
                        signalPanel.getChart().stopDrawing();
                    }
                }
            }
        });
        animationCheckBox.addItemListener(new ItemListener() { //zatrzymanie animacji/rysowania
            public void itemStateChanged(ItemEvent event) {
                int state = event.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    signalPanel.getChart().setAnimated(true);
                    logger.info("Animation on.");
                } else {
                    signalPanel.getChart().setAnimated(false);
                    logger.info("Animation off.");
                }
            }
        });
        darkModeCheckBox.addItemListener(new ItemListener() { //zatrzymanie animacji/rysowania
            public void itemStateChanged(ItemEvent event) {
                int state = event.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    signalPanel.getChart().setBlendMode(BlendMode.EXCLUSION);
                    logger.info("Dark mode on.");
                } else {
                    signalPanel.getChart().setBlendMode(null);
                    logger.info("Dark mode off.");
                }
            }
        });

        zoomButton.addActionListener(new ActionListener() { //wznowienie animacji/rysowania
            public void actionPerformed(ActionEvent event) {
                if (zoomButton.isSelected()) {
                    logger.info("Zooming enabled.");
                    zoomButton.setSelected(true);
                    signalPanel.getChart().setOnScroll(signalPanel.getChart().getZoomHandler());
                    signalPanel.getChart().getZoomHandler().setZooming(true);
                    signalPanel.getChart().setIsSelecting(false); //usuwa zaznaczenie
                    activateShifting();
                }
                if (!zoomButton.isSelected()) {
                    logger.info("Zooming disabled.");
                    zoomButton.setSelected(false);
                    signalPanel.getChart().getZoomHandler().zoomReset();
                    signalPanel.getChart().getZoomHandler().setZooming(false);
                    signalPanel.getChart().setOnScroll(null);
                }
            }
        });

        zoomResetButton.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                if (signalPanel.getChart().getOnScroll() != null) {
                    signalPanel.getChart().getZoomHandler().zoomReset();
                    logger.info("Zoom reseted.");
                    activateShifting();
                }
            }
        });
        zoomMode1.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                signalPanel.getChart().setAutoRangeAxisY(true);
                logger.info("Zoom mode #1 enabled.");
            }
        });
        zoomMode2.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                signalPanel.getChart().setAutoRangeAxisY(false);
                logger.info("Zoom mode #2 enabled.");
            }
        });

        selectButton.addActionListener(new ActionListener() { //wznowienie animacji/rysowania
            public void actionPerformed(ActionEvent event) {
                if (!FxSignalPanel.getMeasurementData().getMeasurementsList().isEmpty()) {
                    if (selectButton.isSelected()) {
                        logger.info("Selection is enabled.");
                        selectButton.setSelected(true);
                        if (signalPanel.getChart().getIsAnimated()) {
                            signalPanel.getChart().stopAnimation();
                            signalPanel.getChart().setIsAnimated(false);
                        }
                        deactivateZoomAndShifting();
                        managmentButtons(false);
                    }
                    disableSelect();
                }
            }
        });

        cutButton.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                if (!signalPanel.getChart().getAnimated()) {
                    signalPanel.getChart().setAnimated(true);
                    //nie usuwa jak nie ustawione markery
                    if ((Math.sqrt(Math.pow(signalPanel.getChart().getFirstNodeMarker().getStartY(), 2) + Math.pow(signalPanel.getChart().getFirstNodeMarker().getEndY(), 2)) != 0) &&
                            (Math.sqrt(Math.pow(signalPanel.getChart().getSecondNodeMarker().getStartY(), 2) + Math.pow(signalPanel.getChart().getSecondNodeMarker().getEndY(), 2)) != 0)) {
                        signalPanel.getChart().getChartCuttingHandler().removeRangeChartNodes();
                        signalPanel.getChart().getFirstNodeMarker().hideMarker();
                        signalPanel.getChart().getSecondNodeMarker().hideMarker();
                    }
                    signalPanel.getChart().setAnimated(false);
                } else {
                    //nie usuwa jak nie ustawione markery
                    if ((Math.sqrt(Math.pow(signalPanel.getChart().getFirstNodeMarker().getStartY(), 2) + Math.pow(signalPanel.getChart().getFirstNodeMarker().getEndY(), 2)) != 0) &&
                            (Math.sqrt(Math.pow(signalPanel.getChart().getSecondNodeMarker().getStartY(), 2) + Math.pow(signalPanel.getChart().getSecondNodeMarker().getEndY(), 2)) != 0)) {
                        signalPanel.getChart().getChartCuttingHandler().removeRangeChartNodes();
                        signalPanel.getChart().getFirstNodeMarker().hideMarker();
                        signalPanel.getChart().getSecondNodeMarker().hideMarker();
                    }

                }
            }
        });
        clearChartButton.addActionListener(new ActionListener() { //zatrzymanie animacji/rysowania
            public void actionPerformed(ActionEvent event) {
                if (!signalPanel.getChart().getIsAnimated()) {
                    unSelectRightButtons();
                    Platform.runLater(new Runnable() {  //call the initFX method to create the JavaFX scene on the JavaFX application thread
                        @Override
                        public void run() {
                            signalPanel.clearChart();
                        }
                    });
                }
            }
        });

        dbDataFile.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN NIEPOTRZEBNE?
            public void actionPerformed(ActionEvent event) {
                if (!dataBaseDriver.isEnabled())
                    unSelectRightButtons();
                loadDataFromSource(dataBaseDriver);
            }
        });

        websiteFile.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN NIEPOTRZEBNE?
            public void actionPerformed(ActionEvent event) {
                if (!fileFromWebsite.isEnabled())
                    unSelectRightButtons();
                loadDataFromSource(fileFromWebsite);
            }
        });

        xmlSaxDataFile.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                if (!saxParseXML.isEnabled())
                    unSelectRightButtons();
                loadDataFromSource(saxParseXML);
            }
        });

        xmlDomDataFile.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                if (!jdomParseXML.isEnabled())
                    unSelectRightButtons();
                loadDataFromSource(jdomParseXML);
            }
        });
        cuttingMode1.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                signalPanel.getChart().getChartCuttingHandler().setCuttingMode(1);
                logger.info("Cutting mode #1 enabled");
            }
        });
        cuttingMode2.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                signalPanel.getChart().getChartCuttingHandler().setCuttingMode(2);
                logger.info("Cutting mode #2 enabled");
            }
        });

        languageEnglish.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                currentLocale = new Locale(appProperties.getProperty("languageEN"),
                        appProperties.getProperty("countryEN"));
                messages = ResourceBundle.getBundle(appProperties.getProperty("languageBundle"), currentLocale);
                logger.info("Language changed to english.");
                loadStrings();
            }
        });

        languagePolish.addActionListener(new ActionListener() { //wznowienie animacji/rysowania IN
            public void actionPerformed(ActionEvent event) {
                currentLocale = new Locale(appProperties.getProperty("languagePL"),
                        appProperties.getProperty("countryPL"));
                logger.info("Language changed to polish.");
                messages = ResourceBundle.getBundle(appProperties.getProperty("languageBundle"), currentLocale);
                loadStrings();
            }
        });

        this.setSize(new Dimension(Integer.parseInt(appProperties.getProperty("windowSizeWidth")),
                Integer.parseInt(appProperties.getProperty("widnowSizeHight"))));
        this.setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        centreWindow(this);
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public void safeExit() {
        signalPanel.getChart().stopAnimation();
        signalPanel.getChart().setIsAnimated(false);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        logger.info("Application ends..");
        System.exit(0);
    }

    private void activateShifting() {
        signalPanel.getChart().setChartShiftingHandler(true);
        if (signalPanel.getChart().getZoomHandler().isZooming())
            signalPanel.getChart().setOnScroll(signalPanel.getChart().getZoomHandler());
    }

    private void deactivateZoomAndShifting() {
        signalPanel.getChart().setOnScroll(null);
        signalPanel.getChart().setChartShiftingHandler(false);
    }

    public void disableSelect(){
        if (!selectButton.isSelected()) {
            logger.info("Selection is disabled.");
            selectButton.setSelected(false);
            activateShifting();
            managmentButtons(true);
        }
    }

    public static DataBaseDriver getDataBaseDriver() {
        return dataBaseDriver;
    }

    public static SAXParseXML getSaxParseXML() {
        return saxParseXML;
    }

    public static JDOMParseXML getJdomParseXML() {
        return jdomParseXML;
    }

    public static FileFromWebsite getFileFromWebsite() {
        return fileFromWebsite;
    }

    public void loadDataFromSource(Object source) {
        int seriesCount;
        if (!dataBaseDriver.isEnabled() && source.getClass() == DataBaseDriver.class) {
            disableSelect();
            if (signalPanel.getChart().getIsAnimated())
                signalPanel.getChart().stopDrawing();
            runButton.setSelected(false);
            jdomParseXML.setEnabled(false);
            saxParseXML.setEnabled(false);
            fileFromWebsite.setEnabled(false);
            new DataBaseDriver().execute();
            seriesCount = dataBaseDriver.getTableCount();
            Chart.setSeriesCount(seriesCount);
            reInitChart(seriesCount);
            dataBaseDriver.setEnabled(true);
            logger.info("Data from database loaded.");

        } else if (!saxParseXML.isEnabled() && source.getClass() == SAXParseXML.class) {
            disableSelect();
            if (signalPanel.getChart().getIsAnimated())
                signalPanel.getChart().stopDrawing();
            runButton.setSelected(false);
            dataBaseDriver.setEnabled(false); //wyłączenie sterownika bazy danych
            jdomParseXML.setEnabled(false);
            fileFromWebsite.setEnabled(false);
            saxParseXML = new SAXParseXML(xmlSAXFileName);
            saxParseXML.execute();
            seriesCount = initSeriesCount(saxParseXML);
            Chart.setSeriesCount(seriesCount);
            reInitChart(seriesCount);
            saxParseXML.setEnabled(true);
            logger.info("Data from xml file by saxParser loaded.");

        } else if (!jdomParseXML.isEnabled() && source.getClass() == JDOMParseXML.class) {
            disableSelect();
            if (signalPanel.getChart().getIsAnimated())
                signalPanel.getChart().stopDrawing();
            runButton.setSelected(false);
            dataBaseDriver.setEnabled(false); //wyłączenie sterownika bazy danych
            saxParseXML.setEnabled(false);
            fileFromWebsite.setEnabled(false);
            jdomParseXML = new JDOMParseXML(xmlJDOMFileName);
            jdomParseXML.execute();
            seriesCount = initSeriesCount(jdomParseXML);
            Chart.setSeriesCount(seriesCount);
            reInitChart(seriesCount);
            jdomParseXML.setEnabled(true);
            logger.info("Data from xml file by JDOM Parser loaded.");

        } else if (!fileFromWebsite.isEnabled() && source.getClass() == FileFromWebsite.class) {
            disableSelect();
            if (signalPanel.getChart().getIsAnimated())
                signalPanel.getChart().stopDrawing();
            runButton.setSelected(false);
            dataBaseDriver.setEnabled(false); //wyłączenie sterownika bazy danych
            saxParseXML.setEnabled(false);
            jdomParseXML.setEnabled(false);
            fileFromWebsite = null;
            fileFromWebsite = new FileFromWebsite(websiteURL, websiteFileName);
            fileFromWebsite.execute();
            seriesCount = initSeriesCount(fileFromWebsite);
            Chart.setSeriesCount(seriesCount);
            reInitChart(seriesCount);
            fileFromWebsite.setEnabled(true);
            logger.info("Data from website link loaded.");
        }

    }

    public int initSeriesCount(Object source) {
        try {
            if (source.getClass() == SAXParseXML.class)
                return saxParseXML.get();
            if (source.getClass() == JDOMParseXML.class)
                return jdomParseXML.get();
            if (source.getClass() == FileFromWebsite.class)
                return fileFromWebsite.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void reInitChart(int seriesCount) {
        Platform.runLater(new Runnable() {  //call the initFX method to create the JavaFX scene on the JavaFX application thread
            @Override
            public void run() {
                signalPanel.clearChartAndResetDataLists();
                signalPanel.getChart().initSeriesLists(seriesCount); // inicjacja list
                signalPanel.getChart().setAutoRangeAxisY(true);
                zoomMode1.setSelected(true);
            }
        });
    }

    public void unSelectRightButtons() {
        selectButton.setSelected(false);
        runButton.setSelected(false);
        zoomButton.setSelected(false);
    }

    public void managmentButtons(boolean mode) {
        if (mode) {
            runButton.setSelected(false);
        }
        runButton.setEnabled(mode);
        zoomButton.setEnabled(mode);
        zoomResetButton.setEnabled(mode);
        signalPanel.getChart().setIsSelecting(!mode);
        cutButton.setEnabled(!mode);
        setResizable(mode);
    }

    public void loadStrings() {
        logger.info("Loading strings for buttons and labels..");
        dataMenu.setText(messages.getString("dataMenuLabel"));
        settingsMenu.setText(messages.getString("settingsMenuLabel"));
        languageMenu.setText(messages.getString("languageMenuLabel"));
        xmlMenu.setText(messages.getString("xmlMenuLabel"));
        zoomMenu.setText(messages.getString("zoomMenuLabel"));
        cuttingMenu.setText(messages.getString("cuttingMenuLabel"));
        dbDataFile.setText(messages.getString("dbDataFileLabel"));
        xmlDomDataFile.setText(messages.getString("xmlDomDataFileLabel"));
        websiteFile.setText(messages.getString("websiteFileLabel"));
        xmlSaxDataFile.setText(messages.getString("xmlSaxDataFileLabel"));
        zoomMode1.setText(messages.getString("zoomMode1Label"));
        zoomMode2.setText(messages.getString("zoomMode2Label"));
        cuttingMode1.setText(messages.getString("cuttingMode1Label"));
        cuttingMode2.setText(messages.getString("cuttingMode2Label"));
        exitFile.setText(messages.getString("exitFileLabel"));
        animationCheckBox.setText(messages.getString("animationCheckBoxLabel"));
        darkModeCheckBox.setText(messages.getString("darkModeCheckBoxLabel"));
        cutButton.setText(messages.getString("cutButtonLabel"));
        clearChartButton.setText(messages.getString("clearChartButtonLabel"));
        zoomResetButton.setText(messages.getString("zoomResetButtonLabel"));
        runButton.setText(messages.getString("runButtonLabel"));
        zoomButton.setText(messages.getString("zoomButtonLabel"));
        selectButton.setText(messages.getString("selectButtonLabel"));
        languagePolish.setText(messages.getString("languagePolishLabel"));
        languageEnglish.setText(messages.getString("languageEnglishLabel"));
        dbDataFile.setAccelerator(KeyStroke.getKeyStroke(messages.getString("dbDataFileAccelerator")));
        exitFile.setAccelerator(KeyStroke.getKeyStroke(messages.getString("exitFileAccelerator")));
        zoomMode1.setAccelerator(KeyStroke.getKeyStroke(messages.getString("zoomMode1Accelerator")));
        zoomMode2.setAccelerator(KeyStroke.getKeyStroke(messages.getString("zoomMode2Accelerator")));
        cuttingMode1.setAccelerator(KeyStroke.getKeyStroke(messages.getString("cuttingMode1Accelerator")));
        cuttingMode2.setAccelerator(KeyStroke.getKeyStroke(messages.getString("cuttingMode2Accelerator")));
        cutButton.setAccelerator(KeyStroke.getKeyStroke(messages.getString("cutButtonAccelerator")));
    }

}