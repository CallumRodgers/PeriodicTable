package com.callumrodgers.tabela;

import com.callumrodgers.tabela.ui.HyperlinkButton;
import com.callumrodgers.tabela.ui.UIDefaults;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.callumrodgers.tabela.ui.UIDefaults.*;

public class OptionsPanel {

    private JPanel glassPane;
    private JPanel mainPanel;

    private final Color transparent = new Color(0x00000000, true),
                        background = new Color(0x55000000, true),
                        borderColor = new Color(150, 150, 150, 100);

    private boolean isShowing;

    public static final String  CELSIUS_KEY     = "Celsius (°C)",
                                FAHRENHEIT_KEY  = "Fahrenheit (°F)",
                                KELVIN_KEY      = "Kelvin (K)";

    private static String defaultUnit = CELSIUS_KEY;

    private PeriodicTable table;

    public OptionsPanel(JFrame mainFrame, PeriodicTable table) {
        this.glassPane = new JPanel(new MigLayout("insets 0 0 0 0", "[grow]", "[]0[]")) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(transparent);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                super.paintComponent(g);
            }

            @Override
            public boolean contains(int x, int y) {
                Component[] components = getComponents();
                for (Component component : components) {
                    Point containerPoint = SwingUtilities.convertPoint(
                            this,
                            x, y,
                            component);
                    if (component.contains(containerPoint)) {
                        return true;
                    }
                }
                return false;
            }
        };
        this.mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(background);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                super.paintComponent(g);
                g.setColor(borderColor);
                g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
            }
        };
        this.table = table;
        glassPane.setOpaque(false);
        mainPanel.setOpaque(false);
        createLayout();
        createUI();
        createButton();
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                glassPane.setSize(mainFrame.getWidth(), mainPanel.getPreferredSize().height);
                glassPane.setLocation(0, 0);
            }
        });
        mainFrame.setGlassPane(glassPane);
        glassPane.setVisible(true);
    }

    private void createLayout() {
        MigLayout layout = new MigLayout("insets 10 5 10 5");
        layout.setRowConstraints("[grow]");
        layout.setColumnConstraints("[]30[]30[grow]");
        mainPanel.setLayout(layout);
    }

    private void createUI() {
        JLabel groupLabel = new JLabel("Agrupar Tabela por:");
        groupLabel.setFont(DEFAULT_FONT_15);
        groupLabel.setForeground(Color.WHITE);
        JComboBox<String> groupComboBox = new JComboBox<>();
        groupComboBox.setFont(DEFAULT_FONT_13);
        groupComboBox.addItem("Família");
        groupComboBox.addItem("Massa Atômica");
        groupComboBox.addItem("Densidade");
        groupComboBox.addItem("Eletronegatividade");
        groupComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                switch ((String) (e.getItem())) {
                    case "Família" -> table.groupTable(PeriodicTable.GROUP_FAMILY);
                    case "Eletronegatividade" -> table.groupTable(PeriodicTable.GROUP_ELECTRONEGATIVITY);
                    case "Massa Atômica" -> table.groupTable(PeriodicTable.GROUP_MASS);
                    case "Densidade" -> table.groupTable(PeriodicTable.GROUP_DENSITY);
                }
            }
        });

        JLabel unitLabel = new JLabel("Unidade de Temperatura Padrão:");
        unitLabel.setFont(DEFAULT_FONT_15);
        unitLabel.setForeground(Color.WHITE);
        JComboBox<String> unitComboBox = new JComboBox<>();
        unitComboBox.setFont(DEFAULT_FONT_13);

        unitComboBox.addItem(CELSIUS_KEY);
        unitComboBox.addItem(FAHRENHEIT_KEY);
        unitComboBox.addItem(KELVIN_KEY);
        unitComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                defaultUnit = (String) e.getItem();
            }
        });
        groupComboBox.setLightWeightPopupEnabled(false);
        groupComboBox.setFocusable(false);
        unitComboBox.setLightWeightPopupEnabled(false);
        unitComboBox.setFocusable(false);

        HyperlinkButton button = new HyperlinkButton("https://github.com/CallumRodgers/PeriodicTable", "Explorar o Código");
        button.setFont(DEFAULT_FONT_15);

        mainPanel.add(groupLabel, "cell 0 0, split 2");
        mainPanel.add(groupComboBox, "grow");

        mainPanel.add(unitLabel, "cell 1 0, split 2");
        mainPanel.add(unitComboBox, "grow");

        if (Desktop.isDesktopSupported()) {
            mainPanel.add(button, "cell 2 0, right");
        }

        mainPanel.addMouseListener(new MouseAdapter() {});
    }

    private void createButton() {
        JPanel button = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                int w = this.getWidth();
                int h = this.getHeight();

                g.setColor(background);
                g.fillRect(0, 0, w, h);

                // Drawing arrow.
                int x1, x2, x3;
                int y1, y2;

                x1 = (int) (w * (1.0 / 4.0));
                x2 = (int) (w * (2.0 / 4.0));
                x3 = (int) (w * (3.0 / 4.0));

                if (isShowing) {
                    y1 = (int) (h * (3.0 / 4.0));
                    y2 = (int) (h * (1.0 / 4.0));
                } else {
                    y1 = (int) (h * (1.0 / 4.0));
                    y2 = (int) (h * (3.0 / 4.0));
                }

                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawLine(x1, y1, x2, y2);
                g2d.drawLine(x2, y2, x3, y1);

                g.setColor(borderColor);
                g.drawRect(0, 0, w - 1, h -1);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(70, 30);
            }
        };

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (isShowing) {
                        Timer timer = new Timer(8, e1 -> {
                            mainPanel.setLocation(mainPanel.getX(), mainPanel.getY() - 1);
                            button.setLocation(button.getX(), mainPanel.getY() + mainPanel.getHeight());
                            if (mainPanel.getY() <= - mainPanel.getHeight()) {
                                mainPanel.setVisible(false);
                                glassPane.remove(mainPanel);
                                glassPane.invalidate();
                                button.setLocation(button.getX(), 0);
                                ((Timer) e1.getSource()).stop();
                            }
                        });
                        timer.start();
                    } else {
                        glassPane.add(mainPanel, "cell 0 0, growx");
                        mainPanel.setVisible(true);
                        mainPanel.setLocation(0, - (mainPanel.getPreferredSize().height));
                        glassPane.updateUI();
                        AtomicInteger variable = new AtomicInteger(- mainPanel.getPreferredSize().height);
                        Timer timer = new Timer(8, e1 -> {
                            mainPanel.setLocation(0, variable.getAndIncrement());
                            button.setLocation(button.getX(), mainPanel.getY() + mainPanel.getHeight());
                            if (mainPanel.getY() >= 0) {
                                mainPanel.setLocation(0, 0);
                                button.setLocation(button.getX(), mainPanel.getHeight());
                                ((Timer) e1.getSource()).stop();
                            }
                            glassPane.repaint();
                        });
                        timer.start();
                    }
                    isShowing = !isShowing;
                });
            }
        });

        button.setCursor(UIDefaults.HAND_CURSOR);
        glassPane.add(button, "cell 0 1, center");
        glassPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int y = isShowing ? mainPanel.getHeight() : 0;
                button.setLocation(button.getX(), y);
            }
        });
    }

    public static String getDefaultUnit() {
        return defaultUnit;
    }
}
