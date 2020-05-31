package application;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.JCalendarCombo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.JTipOfTheDay;
import com.l2fprod.common.swing.tips.DefaultTip;
import com.l2fprod.common.swing.tips.DefaultTipModel;

/**
 *  Klasa odpowiedzialna za wygląd oraz działanie aplikacji.
 *
 * @author Grzegorz Ciosek (VashRaX)
 * @version 1.4.1
 *
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ApplicationGui implements ActionListener{


    JFrame frame;
    JPanel panelCentral;
    JTaskPane taskPane;
    JPanel panelNorth;
    JPanel panelSouth;
    JPanel panelWest;
    JPanel panelWykres;
    JCalendarCombo calendarCombo;

    Tips tips = new Tips();

    JLabel textLabelValue;
    JTextField textFieldValue;

    JButton writeToFileButton;
    JButton readFromFileButton;
    JButton setZeroToAllTable;
    JButton setToTable;
    JButton setRandomValuesButton;

    JButton helpButton;
    JButton saveButton;
    JButton loadButton;

    JScrollPane scrollPane;
    JTable table = new JTable();

    ChartPanel chartPanel;

    JTextArea textAreaValue;

    JSlider slider_1;
    JSlider slider;

    JList operationList;

    JToolBar toolBar;
    JMenuBar menuBar;

    JMenu menuHelp;
    JMenu menuEdit;
    JMenu menuFile;

    JMenuItem menuHelpItem;
    JMenuItem menuAuthorItem;
    JMenuItem menuTipsItem;

    JMenuItem menuCloseItem;
    JMenuItem menuSaveItem;
    JMenuItem menuLoadItem;


    JMenuItem menuZeroItem;
    JMenuItem menuRandomItem;

    /**
     * Stała odpowiedzialna za zapis do logów.
     */

    private static final Logger logger = LogManager.getLogger(ApplicationGui.class);

    /**
     * Konstruktor bezwarunkowy wyświetlający aplikację.
     */

    public ApplicationGui() {
        logger.info("Program Tabelka.");
        logger.info("Wstępne ustawienia, wczytywanie paneli.");
        setFrameAndPanel();

        logger.info("Wizualizacja okienka, funkcje.");
        initializeApp();

        frame.setVisible(true);
        showTips(tips);
    }

    /**
     * Inicjacja właściwości oraz paneli aplikacji.
     */

    private void setFrameAndPanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame();
        frame.setTitle("Tabelka.");
        frame.setResizable(false);
        frame.setBounds((int) screenSize.getWidth() / 2 - 500, (int) screenSize.getHeight() / 2 - 200, 1000, 484);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        panelNorth = new JPanel();
        frame.getContentPane().add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new BorderLayout(0, 0));

        panelSouth = new JPanel();
        frame.getContentPane().add(panelSouth, BorderLayout.SOUTH);

        panelWest = new JPanel();
        frame.getContentPane().add(panelWest, BorderLayout.WEST);

        taskPane = new JTaskPane();
        frame.getContentPane().add(taskPane, BorderLayout.EAST);

        panelCentral = new JPanel();
        frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
        panelCentral.setLayout(null);
    }

    /**
     *  <p> Metoda odpowiedzialna za wygląd aplikacji </p>
     *  <p> W niej zawarte są konfiguracje wszystkich komponentów.</p>
     */

    private void initializeApp() {

        //------------------------------------------------

        textLabelValue = new JLabel("Wartość [format: **.**] : ");
        textLabelValue.setBounds(250, 11, 150, 15);
        panelCentral.add(textLabelValue);


        textFieldValue = new JTextField();
        textFieldValue.setText("0");
        textFieldValue.setBounds(390, 7, 80, 23);
        panelCentral.add(textFieldValue);
        textFieldValue.setColumns(10);


        textAreaValue = new JTextArea();
        textAreaValue.setBounds(232, 238, 188, 88);
        panelCentral.add(textAreaValue);
        textAreaValue.setColumns(10);


        //------------------------------------------------


        setToTable = new JButton("Wstaw");
        setToTable.setBounds(480, 7, 106, 23);
        panelCentral.add(setToTable);
        setToTable.addActionListener(this::actionPerformed);


        setZeroToAllTable = new JButton("Zerowanie");
        setZeroToAllTable.setBounds(437, 306, 149, 23);
        panelCentral.add(setZeroToAllTable);
        setZeroToAllTable.addActionListener(this::actionPerformed);

        setRandomValuesButton = new JButton("Wartości losowe");
        setRandomValuesButton.setBounds(437, 340, 149, 23);
        setRandomValuesButton.addActionListener(this::actionPerformed);
        panelCentral.add(setRandomValuesButton);


        readFromFileButton = new JButton("Wczytaj z pliku");
        readFromFileButton.setBounds(437, 272, 149, 23);
        panelCentral.add(readFromFileButton);
        readFromFileButton.addActionListener(this::actionPerformed);


        writeToFileButton = new JButton("Zapisz od pliku");
        writeToFileButton.setBounds(437, 238, 149, 23);
        panelCentral.add(writeToFileButton);
        writeToFileButton.addActionListener(this::actionPerformed);


        //------------------------------------------------


        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(57, 90, 530, 137);
        panelCentral.add(scrollPane);

        table.setEnabled(false);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.setRowHeight(23);
        tableSetModel();
        scrollPane.setViewportView(table);
        table.changeSelection(0, 0, false, false);

        slider = new JSlider();
        slider.setValue(1);
        slider.setToolTipText("");
        slider.setSnapToTicks(true);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMaximum(5);
        slider.setMinimum(1);
        slider.setBounds(98, 37, 442, 45);
        slider.addChangeListener(e -> table.changeSelection(slider_1.getValue() - 1, slider.getValue() - 1, false, false));
        panelCentral.add(slider);

        slider_1 = new JSlider();
        slider_1.setOrientation(SwingConstants.VERTICAL);
        slider_1.setMajorTickSpacing(1);
        slider_1.setMinorTickSpacing(1);
        slider_1.setValue(1);
        slider_1.setMaximum(5);
        slider_1.setMinimum(1);
        slider_1.setSnapToTicks(true);
        slider_1.setPaintLabels(true);
        slider_1.setPaintTicks(true);
        slider_1.setBounds(10, 113, 37, 79);
        slider_1.setInverted(true);
        slider_1.addChangeListener(e -> table.changeSelection(slider_1.getValue() - 1, slider.getValue() - 1, false, false));
        panelCentral.add(slider_1);


        //------------------------------------------------

        panelWykres = new JPanel();

        JTaskPaneGroup taskPaneGroup;
        taskPaneGroup = new JTaskPaneGroup();
        taskPaneGroup.setTitle("Wykres");
        taskPaneGroup.add(panelWykres);
        panelWykres.add(createBarChart());
        taskPaneGroup.setExpanded(true);
        taskPane.add(taskPaneGroup);

        //------------------------------------------------

        ImageIcon random = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("refresh.png")));
        ImageIcon zero = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("close.png")));
        ImageIcon unlike = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("hand_thumbsdown.png")));
        ImageIcon cloud = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("upload.png")));
        ImageIcon help = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("address_book.png")));
        ImageIcon save = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("diskette.png")));
        ImageIcon load = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("download2.png")));

        //------------------------------------------------

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        panelNorth.add(toolBar, BorderLayout.SOUTH);

        helpButton = new JButton(help);
        helpButton.addActionListener(this::actionPerformed);
        toolBar.add(helpButton);

        saveButton = new JButton(save);
        saveButton.addActionListener(this::actionPerformed);
        toolBar.add(saveButton);

        loadButton = new JButton(load);
        loadButton.addActionListener(this::actionPerformed);
        toolBar.add(loadButton);

        //------------------------------------------------

        menuBar = new JMenuBar();
        panelNorth.add(menuBar, BorderLayout.NORTH);

        menuHelp = new JMenu("Pomoc");
        menuEdit = new JMenu("Edycja");
        menuFile = new JMenu("Plik");

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuHelp);

        menuSaveItem = new JMenuItem("Zapisz do pliku", save);
        KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        menuFile.add(menuSaveItem);
        menuSaveItem.setAccelerator(f2);
        menuSaveItem.addActionListener(this::actionPerformed);

        menuLoadItem = new JMenuItem("Wczytaj z pliku", load);
        KeyStroke f3 = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0);
        menuFile.add(menuLoadItem);
        menuLoadItem.setAccelerator(f3);
        menuLoadItem.addActionListener(this::actionPerformed);

        menuCloseItem = new JMenuItem("Zamknij");
        menuFile.add(menuCloseItem);
        KeyStroke f4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0);
        menuCloseItem.setAccelerator(f4);
        menuCloseItem.addActionListener(this::actionPerformed);

        menuHelpItem = new JMenuItem("Pomoc :)", help);
        KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        menuHelp.add(menuHelpItem);
        menuHelpItem.setAccelerator(f1);
        menuHelpItem.addActionListener(this::actionPerformed);

        menuAuthorItem = new JMenuItem("O programie", unlike);
        KeyStroke hme = KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0);
        menuHelp.add(menuAuthorItem);
        menuAuthorItem.setAccelerator(hme);
        menuAuthorItem.addActionListener(this::actionPerformed);

        menuTipsItem = new JMenuItem("Wyświetl tips", cloud);
        KeyStroke end = KeyStroke.getKeyStroke(KeyEvent.VK_END, 0);
        menuHelp.add(menuTipsItem);
        menuTipsItem.setAccelerator(end);
        menuTipsItem.addActionListener(this::actionPerformed);

        menuZeroItem = new JMenuItem("Zerowanie tabeli", zero);
        KeyStroke f5 = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
        menuEdit.add(menuZeroItem);
        menuZeroItem.setAccelerator(f5);
        menuZeroItem.addActionListener(this::actionPerformed);

        menuRandomItem = new JMenuItem("Wartosci losowe", random);
        KeyStroke f6 = KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0);
        menuEdit.add(menuRandomItem);
        menuRandomItem.setAccelerator(f6);
        menuRandomItem.addActionListener(this::actionPerformed);

        //------------------------------------------------

        calendarCombo = new JCalendarCombo();
        calendarCombo.setBounds(5, 5, 200, 20);
        calendarCombo.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        calendarCombo.addDateListener(this::dateChanged);
        panelCentral.add(calendarCombo);

        //------------------------------------------------

        String[] listItems = {"Suma elementów", "Średnia elementów", "Wartość max", "Wartość min"};
        operationList = new JList(listItems);
        operationList.setVisibleRowCount(4);
        operationList.setValueIsAdjusting(true);
        operationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        operationList.setBounds(57, 238, 165, 88);
        operationList.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int option = operationList.getSelectedIndex();
                switch (option) {
                    case 0: //Suma
                        double suma = 0;
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                suma += Double.parseDouble(table.getValueAt(i, j).toString());
                            }
                        }
                        textAreaValue.setText(String.valueOf(suma));
                        break;

                    case 1: //Srednia
                        double srednia = 0;
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                srednia += Double.parseDouble(table.getValueAt(i, j).toString()) / 25;
                            }
                        }
                        textAreaValue.setText(String.valueOf(new DecimalFormat("##.##").format(srednia)));
                        break;

                    case 2: //Max
                        double max = Double.parseDouble(table.getValueAt(0, 0).toString());
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (max < Double.parseDouble(table.getValueAt(i, j).toString()))
                                    max = Double.parseDouble(table.getValueAt(i, j).toString());
                            }
                        }
                        textAreaValue.setText(String.valueOf(max));
                        break;

                    case 3: //Min
                        double min = Double.parseDouble(table.getValueAt(0, 0).toString());
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (min > Double.parseDouble(table.getValueAt(i, j).toString()))
                                    min = Double.parseDouble(table.getValueAt(i, j).toString());
                            }
                        }
                        textAreaValue.setText(String.valueOf(min));
                        break;

                    default:
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        panelCentral.add(operationList);

        //------------------------------------------------

        logger.info("Start aplikacji.");
        tips = new Tips();


    }

    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------

    /**
     * Metoda tworząca i zwracająca ciało wykresu.
     * <p>
     *      Pobiera dane z tabeli, tworzy tabelę wartości oraz ilości i na tej podstawie generuje wykres.
     * </p>
     * @return Wykres.
     */

    ChartPanel createBarChart() {

        XYSeries series1 = new XYSeries("Wartości");

        TreeMap<Double, Integer> values = new TreeMap();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (values.containsKey(Double.parseDouble(table.getValueAt(i, j).toString()))) {
                    values.replace(Double.parseDouble(table.getValueAt(i, j).toString()), values.get(Double.parseDouble(table.getValueAt(i, j).toString())) + 1);
                } else
                    values.put(Double.parseDouble(table.getValueAt(i, j).toString()), 1);

            }
        }

        for (Map.Entry<Double, Integer> entry : values.entrySet()) {
            double key = entry.getKey();
            int value = entry.getValue();
            series1.add(key, value);
        }

        IntervalXYDataset dataset = new XYBarDataset(new XYSeriesCollection(series1), 0.25);
        chartPanel = new ChartPanel(ChartFactory.createXYBarChart("Histogram pionowy", "Wartość", false, "Ilość", dataset));
        chartPanel.setPreferredSize(new java.awt.Dimension(300, 200));
        logger.info("Generowanie flipchartu.");
        return chartPanel;

    }

    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------

    /**
     *  Metoda służąca do przeładowania wykresu w czasie rzeczywistym.
     */

    void reloadChart() {
        logger.info("Przeładowanie tabeli.");
        panelWykres.remove(chartPanel);
        panelWykres.revalidate();
        panelWykres.add(createBarChart());
    }

    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------

    /**
     * Metoda służaca do wyświetlania "Tipsów"
     * @param tips - obiekt zawierający dane do wyświetlenia.
     */

    void showTips(Tips tips) {
        SingleTip tip;
        DefaultTipModel tipsModel = new DefaultTipModel();
        if (tips.getTips().isEmpty())
            tipsModel.add(new DefaultTip("Default", "Niestety nie mamy dla Ciebie żadnych wskazówek."));
        else {
            for (SingleTip singleTip : tips.getTips()) {
                tip = singleTip;
                tipsModel.add(new DefaultTip(tip.getTitle(), tip.getContent()));
            }
        }
        JTipOfTheDay tipsWindow = new JTipOfTheDay(tipsModel);
        Dimension tipsWindowDimension = new Dimension();
        tipsWindowDimension.setSize(300,250);
        tipsWindow.setPreferredSize(tipsWindowDimension);

        tipsWindow.showDialog(null);
    }

    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------

    /**
     * Wyświetlanie w komunikacie informacji o autorze.
     */

    void showAuthorMessageDialog() {
        JOptionPane.showMessageDialog(null, "Autor : Grzegorz  Ciosek", "O programie", JOptionPane.INFORMATION_MESSAGE);
    }

    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------

    /**
     * Konfiguracja tabeli jak i również jej zerowanie.
     */

    void tableSetModel() {
        Object tableValue = new Object[][]{{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        String[] tableText = new String[]{"1", "2", "3", "4", "5"};
        logger.info("Wczytywanie tabeli.");
        table.setModel(new DefaultTableModel((Object[][]) tableValue, tableText));
    }

    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------

    /**
     * Wypełnianie tabeli losowymi wartościami z zakresu podanego argumentem bounds.
     * @param bounds - zakres losowanych danych
     */

    void tableSetRandomValues(int bounds) {
        Random generator = new Random();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                table.setValueAt(generator.nextInt(bounds), i, j);
            }
        }
        logger.info("Losowanie wartości w tabeli.");
        reloadChart();
    }

    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------

    /**
     * Metoda odpowiedzialna za wyświetlanie pomocy [javadoc]
     */

    void showHelp() {
        if (Desktop.isDesktopSupported()) {
            try {
                File helpFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
                String path = helpFile.getParentFile().getAbsolutePath();
                logger.info(path);
                Desktop.getDesktop().open(new File(path + "/Docs/index.html"));
                logger.info("Odczytano plik pomocy.");
            } catch (URISyntaxException | IOException | IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Nie udało się wczytać pliku pomocy.");
                logger.error(e + " Nie udało się wczytać pliku pomocy.");
            }
        }
    }


    //*********************************************************************************
    //*********************************************************************************
    //*********                     dateChanged dla kalendarza.             ***********

    /**
     * Obsługa zdarzeń związanych z kalendarzem.
     * @param dateEvent - zawiera dane odnośnie obiektu kalendarza.
     */

    private void dateChanged(DateEvent dateEvent) {
        textAreaValue.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendarCombo.getDate()));
    }

    //*********************************************************************************
    //*********************************************************************************
    //********* ACTION PERFORMED - > odpowiedzialne za działanie aplikacji. ***********

    /**
     * <p>Metoda odpowiedzialna za działanie aplikacji.</p>
     * <p>Znajdują się w niej wszystkie akcje wywoływane ingerencją użytkownika.</p>
     *
     * @param e Zawiera informacje odnośnie obiektu, który wywołuje metodę.
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        // ZEROWANIE TABELI

        if(e.getSource() == menuZeroItem || e.getSource() == setZeroToAllTable ){
                logger.info("Zerowanie tabeli.");
                tableSetModel();
                reloadChart();
                textAreaValue.setText("");
            }

        // ZAPIS ELEMENTU DO PLIKU

        else if(e.getSource() == menuSaveItem || e.getSource() == saveButton || e.getSource() == writeToFileButton){
            FileOperations.saveToFile(table.getValueAt(slider_1.getValue() - 1, slider.getValue() - 1).toString(), logger);
        }

        //WCZYTANIE ELEMENTU Z PLIKU

        else if(e.getSource() == menuLoadItem || e.getSource() == loadButton || e.getSource() == readFromFileButton){
            table.setValueAt(FileOperations.loadFromFile(table.getValueAt(slider_1.getValue() - 1, slider.getValue() - 1).toString(), logger), slider_1.getValue() - 1, slider.getValue() - 1);
            reloadChart();
        }

        // RANDOMOWE WARTOSCI DO TABELI

        else if(e.getSource() == menuRandomItem || e.getSource() == setRandomValuesButton){
            tableSetRandomValues(10);
        }

        // ZAMYKANIE APLIKACJI

        else if(e.getSource() == menuCloseItem){
            System.exit(1);
        }

        // WYSWIETLANIE PLIKU HELP [JavaDoc]

        else if(e.getSource() == helpButton || e.getSource() == menuHelpItem){
            showHelp();
        }

        // INFORMACJE O AUTORZE

        else if(e.getSource() == menuAuthorItem){
            showAuthorMessageDialog();
        }

        // WSTAWIANIE WARTOSCI DO TABELI

        else if(e.getSource() == setToTable){
            try{
                double value = Double.parseDouble(textFieldValue.getText());
                table.setValueAt(value, slider_1.getValue() - 1, slider.getValue() - 1);
                reloadChart();
                textAreaValue.setText("");
            } catch(NumberFormatException err){
                logger.error(err + " Wartość nieprawidłowa !!!!");
                JOptionPane.showMessageDialog(null,"Wartość wstawiana do tabeli musi być liczbą w formacie [**.**]");
            }
        }

        // WYŚWIETLANIE TIPÓW

        else if(e.getSource() == menuTipsItem){
            showTips(tips);
        }
    }
}
