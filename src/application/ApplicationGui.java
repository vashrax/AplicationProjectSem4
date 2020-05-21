package application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
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
 * @author VashRaX
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ApplicationGui {


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

    JScrollPane scrollPane;
    JTable table = new JTable();

    ChartPanel chartPanel;
    JTaskPaneGroup taskPaneGroup_2;

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

    JMenuItem menuCloseItem;
    JMenuItem menuSaveItem;
    JMenuItem menuLoadItem;
    JMenuItem menuExportItem;

    JMenuItem menuZeroItem;
    JMenuItem menuRandomItem;


    File file = new File("Number.txt");

    private static final Logger logger = LogManager.getLogger(ApplicationGui.class);


    /**
     * Launch the application.
     *
     *
     * @param args
     */

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ApplicationGui window = new ApplicationGui();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public ApplicationGui() {
        setFrameAndPanel();
        initializeApp();
        frame.setVisible(true);
        showTips(tips);
    }

    private static void showMessageDialog(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Autor : Grzegorz  Ciosek", "O programie", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Initialize the contents of the frame.
     */


    private void setFrameAndPanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame();
        frame.setTitle("miniExel");
        frame.setResizable(false);
        frame.setBounds((int) screenSize.getWidth() / 2 - 500, (int) screenSize.getHeight() / 2 - 200, 1000, 464);
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

        //panelEast = new JPanel();
        //frame.getContentPane().add(panelEast, BorderLayout.EAST);

        panelCentral = new JPanel();
        frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
        panelCentral.setLayout(null);
    }


    private void initializeApp() {

        if (!file.exists()) {
            try {

                file.createNewFile();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Błąd podczas tworzenia pliku");
            }
        }

        //------------------------------------------------


        textLabelValue = new JLabel("Wartość : ");
        textLabelValue.setBounds(335, 11, 135, 15);
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
        setToTable.addActionListener(actionEvent -> {
            table.setValueAt(textFieldValue.getText(), slider_1.getValue() - 1, slider.getValue() - 1);
            reloadChart();
            textAreaValue.setText("");
        });


        setZeroToAllTable = new JButton("Zerowanie");
        setZeroToAllTable.setBounds(437, 306, 149, 23);
        panelCentral.add(setZeroToAllTable);
        setZeroToAllTable.addActionListener(actionEvent -> {
            tableSetModel();
            reloadChart();
            textAreaValue.setText("");
        });


        readFromFileButton = new JButton("Wczytaj z pliku");
        readFromFileButton.setBounds(437, 272, 149, 23);
        panelCentral.add(readFromFileButton);
        readFromFileButton.addActionListener(actionEvent -> {
            loadFromFile();
            textAreaValue.setText("");
        });


        writeToFileButton = new JButton("Zapisz od pliku");
        writeToFileButton.setBounds(437, 238, 149, 23);
        panelCentral.add(writeToFileButton);
        writeToFileButton.addActionListener(actionEvent -> {
            saveToFile();
            textAreaValue.setText("");
        });


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
        taskPaneGroup.setExpanded(false);
        taskPane.add(taskPaneGroup);


        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        panelNorth.add(toolBar, BorderLayout.SOUTH);

        ImageIcon help = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("address_book.png")));
        JButton helpButton = new JButton(help);
        helpButton.addActionListener(actionEvent -> showHelp());
        toolBar.add(helpButton);

        ImageIcon save = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("diskette.png")));
        JButton saveButton = new JButton(save);
        saveButton.addActionListener(actionEvent -> saveToFile());
        toolBar.add(saveButton);

        ImageIcon load = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("download2.png")));
        JButton loadButton = new JButton(load);
        loadButton.addActionListener(actionEvent -> loadFromFile());
        toolBar.add(loadButton);

        ImageIcon random = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("refresh.png")));

        ImageIcon zero = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("close.png")));

        ImageIcon unlike = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("hand_thumbsdown.png")));

        ImageIcon cloud = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("upload.png")));

        menuBar = new JMenuBar();
        panelNorth.add(menuBar, BorderLayout.NORTH);

        menuHelp = new JMenu("Pomoc");
        menuEdit = new JMenu("Edycja");
        menuFile = new JMenu("Plik");

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuHelp);

        menuSaveItem = new JMenuItem("Zapisz do pliku", save);
        menuFile.add(menuSaveItem);
        menuSaveItem.addActionListener(actionEvent -> saveToFile());

        menuLoadItem = new JMenuItem("Wczytaj z pliku", load);
        menuFile.add(menuLoadItem);
        menuLoadItem.addActionListener(actionEvent -> loadFromFile());

        menuExportItem = new JMenuItem("Export do chmury", cloud);
        menuFile.addSeparator();
        menuFile.add(menuExportItem);
        menuFile.addSeparator();
        menuExportItem.addActionListener(actionEvent -> JOptionPane.showMessageDialog(null, "Jeszcze kurwa czego -,-"));

        menuCloseItem = new JMenuItem("Zamknij");
        menuFile.add(menuCloseItem);
        menuCloseItem.addActionListener(actionEvent -> System.exit(1));

        menuHelpItem = new JMenuItem("Pomoc :)", help);
        menuHelp.add(menuHelpItem);
        menuHelpItem.addActionListener(actionEvent -> showHelp());

        menuAuthorItem = new JMenuItem("O programie", unlike);
        menuHelp.add(menuAuthorItem);
        menuAuthorItem.addActionListener(ApplicationGui::showMessageDialog);

        menuZeroItem = new JMenuItem("Zerowanie tabeli", zero);
        menuZeroItem.addActionListener(actionEvent -> {
            tableSetModel();

            textAreaValue.setText("");
        });
        menuEdit.add(menuZeroItem);
        menuRandomItem = new JMenuItem("Wartosci losowe", random);
        menuEdit.add(menuRandomItem);
        menuRandomItem.addActionListener(actionEvent -> tableSetRandomValues(5));
        calendarCombo = new JCalendarCombo();
        calendarCombo.setBounds(5, 5, 250, 20);
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
                        textAreaValue.setText(String.valueOf(srednia));
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

        JButton btnNewButton = new JButton("RANDOM");
        btnNewButton.setBounds(437, 340, 89, 23);
        btnNewButton.addActionListener(e -> tableSetRandomValues(5));
        panelCentral.add(btnNewButton);


        //------------------------------------------------

        logger.info("Start aplikacji.");

        tips = new Tips();
        tips.loadTips();

        //------------------------------------------------

    }

    ChartPanel createBarChart() {

        XYSeries series1 = new XYSeries("Tabela");

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
            System.out.println(key + " " + value);
            series1.add(key, value);
        }

        IntervalXYDataset dataset = new XYBarDataset(new XYSeriesCollection(series1), 0.25);
        chartPanel = new ChartPanel(ChartFactory.createXYBarChart("Histogram pionowy", "Wartość", false, "Ilość", dataset));
        chartPanel.setPreferredSize(new java.awt.Dimension(300, 200));
        return chartPanel;

    }

    void reloadChart() {
        panelWykres.remove(chartPanel);
        panelWykres.revalidate();
        panelWykres.add(createBarChart());
    }

    void showTips(Tips tips) {
        SingleTip tip;
        DefaultTipModel tipsModel = new DefaultTipModel();
        if (tips.getTips().isEmpty())
            tipsModel.add(new DefaultTip("tip", "Treść"));
        else {
            for (SingleTip singleTip : tips.getTips()) {
                tip = singleTip;
                tipsModel.add(new DefaultTip(tip.getTitle(), tip.getContent()));
            }
        }

        JTipOfTheDay tipsWindow = new JTipOfTheDay(tipsModel);
        tipsWindow.showDialog(null);

    }

    void tableSetModel() {
        Object tableValue = new Object[][]{{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        String[] tableText = new String[]{"1", "2", "3", "4", "5"};
        table.setModel(new DefaultTableModel((Object[][]) tableValue, tableText));
    }

    void tableSetRandomValues(int bounds) {
        Random generator = new Random();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                table.setValueAt(generator.nextInt(bounds), i, j);
            }
        }
        reloadChart();
    }


    void saveToFile() {
        if (file.exists())
            file.delete();

        try {
            file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(file);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOut));
            bufferedWriter.write(table.getValueAt(slider_1.getValue() - 1, slider.getValue() - 1).toString());
            bufferedWriter.close();
            JOptionPane.showMessageDialog(null,"Zapisano wartość "+table.getValueAt(slider_1.getValue() - 1, slider.getValue() - 1).toString() +" do pliku.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Nie udało się zapisać wartości do pliku.");

        }
    }


    void loadFromFile() {
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileIn));
                table.setValueAt(bufferedReader.readLine(), slider_1.getValue() - 1, slider.getValue() - 1);
                bufferedReader.close();
                reloadChart();
                JOptionPane.showMessageDialog(null,"Wczytano wartość "+table.getValueAt(slider_1.getValue() - 1, slider.getValue() - 1).toString() +" z pliku.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Nie udało się wczytać wartości z pliku.");
            }

        }
    }

    private void dateChanged(DateEvent dateEvent) {
        textAreaValue.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendarCombo.getDate()));
    }


    void showHelp() {
        if (Desktop.isDesktopSupported()) {
            try {
                File helpFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
                String path = helpFile.getParentFile().getAbsolutePath();
                Desktop.getDesktop().open(new File(path + "/Docs/javadoc/index.html"));
                logger.info("Odczytano plik pomocy.");
            } catch (URISyntaxException | IOException | IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Nie udało się wczytać pliku pomocy.");
                logger.info("Nie udało się wczytać pliku pomocy.");
            }
        }
    }
}
