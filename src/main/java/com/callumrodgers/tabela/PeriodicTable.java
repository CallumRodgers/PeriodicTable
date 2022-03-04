package com.callumrodgers.tabela;

import com.callumrodgers.tabela.ui.AtomVisualization;
import com.callumrodgers.tabela.ui.PanelButton;
import com.callumrodgers.tabela.util.FontUtils;
import com.callumrodgers.tabela.util.Utils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

import java.awt.event.*;
import java.util.*;
import java.util.List;

// TODO: Original size 851
public class PeriodicTable extends JPanel {

    // Amount of elements.
    private static final int TABLE_LENGTH = 118;

    // Amount of columns on the table.
    private static final int COLUMN_LENGTH = 19;

    // Amount of rows on the table.
    private static final int ROW_LENGTH = 11;

    public static final int GROUP_FAMILY               = 0,
                            GROUP_ELECTRONEGATIVITY    = 1,
                            GROUP_MASS                 = 2,
                            GROUP_DENSITY              = 3;

    private final JLayeredPane layeredPane;
    private final JPanel innerPane;

    private final JPanel table;
    private final JScrollPane tableScrollPane;

    private JScrollPane glassPaneHolder;
    private JPanel glassPane;

    private final Map<JPanel, Boolean> enabledMap;

    private final List<JPanel> elementPanels = new LinkedList<>();

    private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);

    private static final Font defaultFont = new Font("Segoe UI", Font.PLAIN, 12);

    private static final Color postTransitionColor = new Color(150, 200, 250),
            nonMetalColor = new Color(250, 250, 125),
            actinideColor = new Color(200, 125, 250),
            halogenColor = new Color(125, 250, 125),
            transitionMetalColor = new Color(100, 100, 250),
            nobleGasColor = new Color(250, 100, 100),
            alkaliEarthColor = new Color(250, 150, 100),
            metalloidColor = new Color(100, 250, 200),
            lanthanideColor = new Color(250, 150, 250),
            alkaliColor = new Color(250, 200, 100);

    private static final Color  EN_COLOR_0 = new Color(220, 220, 255),
                                EN_COLOR_1 = new Color(190, 190, 255),
                                EN_COLOR_2 = new Color(160, 160, 255),
                                EN_COLOR_3 = new Color(130, 130, 255),
                                EN_COLOR_4 = new Color(100, 100, 255),
                                EN_COLOR_5 = new Color(70, 70, 255),
                                EN_COLOR_6 = new Color(40, 40, 230),
                                EN_COLOR_7 = new Color(230, 160, 160),
                                EN_COLOR_8 = new Color(150, 150, 150);

    private static final Color  MASS_COLOR_0 = new Color(255, 230, 230),
                                MASS_COLOR_1 = new Color(255, 220, 220),
                                MASS_COLOR_2 = new Color(255, 200, 200),
                                MASS_COLOR_3 = new Color(255, 180, 180),
                                MASS_COLOR_4 = new Color(255, 160, 160),
                                MASS_COLOR_5 = new Color(255, 150, 150),
                                MASS_COLOR_6 = new Color(255, 130, 130),
                                MASS_COLOR_7 = new Color(255, 110, 110),
                                MASS_COLOR_8 = new Color(255, 90, 90),
                                MASS_COLOR_9 = new Color(255, 70, 70),
                                MASS_COLOR_10 = new Color(255, 50, 50),
                                MASS_COLOR_11 = new Color(255, 30, 30),
                                MASS_COLOR_12 = new Color(255, 10, 10),
                                MASS_COLOR_13 = new Color(220, 0, 0),
                                MASS_COLOR_14 = new Color(160, 0, 0),
                                MASS_COLOR_15 = new Color(120, 0,0);

    private static final Color  DENSITY_COLOR_0 = new Color(220, 255, 220),
                                DENSITY_COLOR_1 = new Color(200, 255, 200),
                                DENSITY_COLOR_2 = new Color(180, 255, 180),
                                DENSITY_COLOR_3 = new Color(160, 255, 160),
                                DENSITY_COLOR_4 = new Color(140, 255, 140),
                                DENSITY_COLOR_5 = new Color(120, 255, 120),
                                DENSITY_COLOR_6 = new Color(100, 255, 100),
                                DENSITY_COLOR_7 = new Color(80, 255, 80),
                                DENSITY_COLOR_8 = new Color(60, 255, 60),
                                DENSITY_COLOR_9 = new Color(50, 220, 50),
                                DENSITY_COLOR_10 = new Color(30, 180, 30),
                                DENSITY_COLOR_11 = new Color(0, 150, 0);

    private final PanelButton togglePanel1 = new PanelButton(),
                                togglePanel2 = new PanelButton(),
                                togglePanel3 = new PanelButton(),
                                togglePanel4 = new PanelButton(),
                                togglePanel5 = new PanelButton(),
                                togglePanel6 = new PanelButton(),
                                togglePanel7 = new PanelButton(),
                                togglePanel8 = new PanelButton(),
                                togglePanel9 = new PanelButton(),
                                togglePanel10 = new PanelButton(),
                                togglePanel11 = new PanelButton(),
                                togglePanel12 = new PanelButton(),
                                togglePanel13 = new PanelButton(),
                                togglePanel14 = new PanelButton(),
                                togglePanel15 = new PanelButton(),
                                togglePanel16 = new PanelButton();

    private List<PanelButton> toggles;

    private PanelButton clickedPanel;
    private JLabel clickedLabel;

    private JPanel selectedElementPanel;
    private Element selectedElement;

    private final List<JLabel> numberLabels = new ArrayList<>();

    private Border cellBorder;

    private JPanel lanthanoidsPanel;
    private JPanel actinoidsPanel;

    private JPanel legendPanel;

    private int activeAttribute;

    public PeriodicTable() {
        this.layeredPane = new JLayeredPane();
        this.innerPane = new JPanel(new BorderLayout());
        this.table = new JPanel();
        this.tableScrollPane = new JScrollPane();
        this.enabledMap = new HashMap<>();
        this.toggles = new LinkedList<>();
        toggles.add(togglePanel1);
        toggles.add(togglePanel2);
        toggles.add(togglePanel3);
        toggles.add(togglePanel4);
        toggles.add(togglePanel5);
        toggles.add(togglePanel6);
        toggles.add(togglePanel7);
        toggles.add(togglePanel8);
        toggles.add(togglePanel9);
        toggles.add(togglePanel10);
        toggles.add(togglePanel11);
        toggles.add(togglePanel12);
        toggles.add(togglePanel13);
        toggles.add(togglePanel14);
        toggles.add(togglePanel15);
        toggles.add(togglePanel16);
        createUI();
    }

    private void createUI() {
        createTable();
        createGlassPane();
        tableScrollPane.setViewportView(table);
        tableScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        tableScrollPane.getHorizontalScrollBar().setUnitIncrement(8);
        tableScrollPane.setBorder(null);
        innerPane.add(tableScrollPane, BorderLayout.CENTER);
        layeredPane.add(innerPane, Integer.valueOf(1));
        Main.contentPane.add(layeredPane, BorderLayout.CENTER);
        Main.contentPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                layeredPane.setSize(Main.contentPane.getSize());
                innerPane.setSize(Main.contentPane.getSize());
                innerPane.validate();
                innerPane.updateUI();
                glassPane.setSize(Main.contentPane.getSize());
                glassPane.setLocation(0, 0);
                glassPaneHolder.setSize(Main.contentPane.getSize());
                glassPaneHolder.setLocation(0, 0);
                glassPaneHolder.validate();
                glassPaneHolder.updateUI();
            }
        });
        Main.contentPane.setSize(Main.contentPane.getPreferredSize());
        groupTable(GROUP_FAMILY);
    }

    private void createTable() {
        table.setBackground(Color.DARK_GRAY);

        // Creating the layout manager
        MigLayout layout = new MigLayout("");
        StringBuilder builder = new StringBuilder();
        String cell = "[50!]2";

        builder.append("[grow]");
        builder.append(cell.repeat(COLUMN_LENGTH));
        builder.deleteCharAt(builder.length() - 1);
        builder.append("[grow]");
        layout.setColumnConstraints(builder.toString());

        builder.delete(0, builder.length());

        builder.append("[grow]");
        builder.append(cell);
        builder.append("[15!]2");
        builder.append(cell.repeat(ROW_LENGTH - 1));
        builder.append("[grow]");
        layout.setRowConstraints(builder.toString());

        cellBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border hoverCellBorder = BorderFactory.createLineBorder(Color.CYAN, 2);
        Font symbolFont = new Font("Quicksand Medium", Font.PLAIN,  15);
        Font atomicNumberFont = new Font("Segoe UI", Font.BOLD,  11);
        for (int i = 0; i < TABLE_LENGTH; i++) {
            Element element = Element.get(i + 1);
            PanelButton elementPanel = new PanelButton();
            elementPanel.setLayout(new BorderLayout());
            elementPanel.setBorder(cellBorder);

            JLabel symbolLabel = new JLabel(element.getSymbol(), JLabel.CENTER);
            symbolLabel.setFont(symbolFont);
            symbolLabel.setForeground(Color.WHITE);

            JLabel atomicNumberLabel = new JLabel(String.valueOf(element.getAtomicNumber()), JLabel.CENTER);
            atomicNumberLabel.setFont(atomicNumberFont);
            atomicNumberLabel.setForeground(Color.WHITE);

            elementPanel.add(symbolLabel, BorderLayout.CENTER);
            elementPanel.add(atomicNumberLabel, BorderLayout.NORTH);
            elementPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!enabledMap.get(elementPanel)) {
                        return;
                    }
                    selectedElement = element;
                    selectedElementPanel = elementPanel;
                    elementPanel.setBorder(activeBorder);
                    updateInfoPanel(element);
                }

                Border activeBorder;

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!enabledMap.get(elementPanel)) {
                        return;
                    }
                    activeBorder = elementPanel.getBorder();
                    elementPanel.setBorder(hoverCellBorder);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!enabledMap.get(elementPanel)) {
                        return;
                    }
                    if (selectedElement == null) {
                        elementPanel.setBorder(activeBorder);
                        return;
                    }
                    if (!selectedElement.equals(element)) {
                        elementPanel.setBorder(activeBorder);
                    }
                }
            });
            elementPanel.setCursor(HAND_CURSOR);
            elementPanels.add(elementPanel);
        }

        table.setLayout(layout);

        // Since the periodic table has a pretty specific layout, we aren't
        // able to loop through the cells adding the panels.
        // We (very unfortunately) have to add each cell individually.

        // First row.
        table.add(elementPanels.get(0), "cell 2 3, grow");
        table.add(elementPanels.get(1), "cell 19 3, grow");

        // Second row.
        table.add(elementPanels.get(2), "cell 2 4, grow");
        table.add(elementPanels.get(3), "cell 3 4, grow");
        table.add(elementPanels.get(4), "cell 14 4, grow");
        table.add(elementPanels.get(5), "cell 15 4, grow");
        table.add(elementPanels.get(6), "cell 16 4, grow");
        table.add(elementPanels.get(7), "cell 17 4, grow");
        table.add(elementPanels.get(8), "cell 18 4, grow");
        table.add(elementPanels.get(9), "cell 19 4, grow");

        // Third row.
        table.add(elementPanels.get(10), "cell 2 5, grow");
        table.add(elementPanels.get(11), "cell 3 5, grow");
        table.add(elementPanels.get(12), "cell 14 5, grow");
        table.add(elementPanels.get(13), "cell 15 5, grow");
        table.add(elementPanels.get(14), "cell 16 5, grow");
        table.add(elementPanels.get(15), "cell 17 5, grow");
        table.add(elementPanels.get(16), "cell 18 5, grow");
        table.add(elementPanels.get(17), "cell 19 5, grow");

        // Fourth row.
        table.add(elementPanels.get(18), "cell 2 6, grow");
        table.add(elementPanels.get(19), "cell 3 6, grow");
        table.add(elementPanels.get(20), "cell 4 6, grow");
        table.add(elementPanels.get(21), "cell 5 6, grow");
        table.add(elementPanels.get(22), "cell 6 6, grow");
        table.add(elementPanels.get(23), "cell 7 6, grow");
        table.add(elementPanels.get(24), "cell 8 6, grow");
        table.add(elementPanels.get(25), "cell 9 6, grow");
        table.add(elementPanels.get(26), "cell 10 6, grow");
        table.add(elementPanels.get(27), "cell 11 6, grow");
        table.add(elementPanels.get(28), "cell 12 6, grow");
        table.add(elementPanels.get(29), "cell 13 6, grow");
        table.add(elementPanels.get(30), "cell 14 6, grow");
        table.add(elementPanels.get(31), "cell 15 6, grow");
        table.add(elementPanels.get(32), "cell 16 6, grow");
        table.add(elementPanels.get(33), "cell 17 6, grow");
        table.add(elementPanels.get(34), "cell 18 6, grow");
        table.add(elementPanels.get(35), "cell 19 6, grow");

        // Fifth row.
        table.add(elementPanels.get(36), "cell 2 7, grow");
        table.add(elementPanels.get(37), "cell 3 7, grow");
        table.add(elementPanels.get(38), "cell 4 7, grow");
        table.add(elementPanels.get(39), "cell 5 7, grow");
        table.add(elementPanels.get(40), "cell 6 7, grow");
        table.add(elementPanels.get(41), "cell 7 7, grow");
        table.add(elementPanels.get(42), "cell 8 7, grow");
        table.add(elementPanels.get(43), "cell 9 7, grow");
        table.add(elementPanels.get(44), "cell 10 7, grow");
        table.add(elementPanels.get(45), "cell 11 7, grow");
        table.add(elementPanels.get(46), "cell 12 7, grow");
        table.add(elementPanels.get(47), "cell 13 7, grow");
        table.add(elementPanels.get(48), "cell 14 7, grow");
        table.add(elementPanels.get(49), "cell 15 7, grow");
        table.add(elementPanels.get(50), "cell 16 7, grow");
        table.add(elementPanels.get(51), "cell 17 7, grow");
        table.add(elementPanels.get(52), "cell 18 7, grow");
        table.add(elementPanels.get(53), "cell 19 7, grow");

        // Sixth row (with Lanthanoids).
        table.add(elementPanels.get(54), "cell 2 8, grow");
        table.add(elementPanels.get(55), "cell 3 8, grow");
        table.add(elementPanels.get(56), "cell 4 11, grow");
        table.add(elementPanels.get(57), "cell 5 11, grow");
        table.add(elementPanels.get(58), "cell 6 11, grow");
        table.add(elementPanels.get(59), "cell 7 11, grow");
        table.add(elementPanels.get(60), "cell 8 11, grow");
        table.add(elementPanels.get(61), "cell 9 11, grow");
        table.add(elementPanels.get(62), "cell 10 11, grow");
        table.add(elementPanels.get(63), "cell 11 11, grow");
        table.add(elementPanels.get(64), "cell 12 11, grow");
        table.add(elementPanels.get(65), "cell 13 11, grow");
        table.add(elementPanels.get(66), "cell 14 11, grow");
        table.add(elementPanels.get(67), "cell 15 11, grow");
        table.add(elementPanels.get(68), "cell 16 11, grow");
        table.add(elementPanels.get(69), "cell 17 11, grow");
        table.add(elementPanels.get(70), "cell 18 11, grow");
        table.add(elementPanels.get(71), "cell 5 8, grow");
        table.add(elementPanels.get(72), "cell 6 8, grow");
        table.add(elementPanels.get(73), "cell 7 8, grow");
        table.add(elementPanels.get(74), "cell 8 8, grow");
        table.add(elementPanels.get(75), "cell 9 8, grow");
        table.add(elementPanels.get(76), "cell 10 8, grow");
        table.add(elementPanels.get(77), "cell 11 8, grow");
        table.add(elementPanels.get(78), "cell 12 8, grow");
        table.add(elementPanels.get(79), "cell 13 8, grow");
        table.add(elementPanels.get(80), "cell 14 8, grow");
        table.add(elementPanels.get(81), "cell 15 8, grow");
        table.add(elementPanels.get(82), "cell 16 8, grow");
        table.add(elementPanels.get(83), "cell 17 8, grow");
        table.add(elementPanels.get(84), "cell 18 8, grow");
        table.add(elementPanels.get(85), "cell 19 8, grow");

        lanthanoidsPanel = new JPanel(new BorderLayout());
        lanthanoidsPanel.setBorder(cellBorder);
        lanthanoidsPanel.setBackground(lanthanideColor);
        JLabel numLabel = new JLabel("57-71");
        JLabel textLabel = new JLabel("La-Lu");
        numLabel.setFont(atomicNumberFont);
        textLabel.setFont(symbolFont);
        numLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        lanthanoidsPanel.add(numLabel, BorderLayout.NORTH);
        lanthanoidsPanel.add(textLabel, BorderLayout.CENTER);
        table.add(lanthanoidsPanel, "cell 4 8, grow");

        // Seventh row (with Actinoids).
        table.add(elementPanels.get(86), "cell 2 9, grow");
        table.add(elementPanels.get(87), "cell 3 9, grow");
        table.add(elementPanels.get(88), "cell 4 12, grow");
        table.add(elementPanels.get(89), "cell 5 12, grow");
        table.add(elementPanels.get(90), "cell 6 12, grow");
        table.add(elementPanels.get(91), "cell 7 12, grow");
        table.add(elementPanels.get(92), "cell 8 12, grow");
        table.add(elementPanels.get(93), "cell 9 12, grow");
        table.add(elementPanels.get(94), "cell 10 12, grow");
        table.add(elementPanels.get(95), "cell 11 12, grow");
        table.add(elementPanels.get(96), "cell 12 12, grow");
        table.add(elementPanels.get(97), "cell 13 12, grow");
        table.add(elementPanels.get(98), "cell 14 12, grow");
        table.add(elementPanels.get(99), "cell 15 12, grow");
        table.add(elementPanels.get(100), "cell 16 12, grow");
        table.add(elementPanels.get(101), "cell 17 12, grow");
        table.add(elementPanels.get(102), "cell 18 12, grow");
        table.add(elementPanels.get(103), "cell 5 9, grow");
        table.add(elementPanels.get(104), "cell 6 9, grow");
        table.add(elementPanels.get(105), "cell 7 9, grow");
        table.add(elementPanels.get(106), "cell 8 9, grow");
        table.add(elementPanels.get(107), "cell 9 9, grow");
        table.add(elementPanels.get(108), "cell 10 9, grow");
        table.add(elementPanels.get(109), "cell 11 9, grow");
        table.add(elementPanels.get(110), "cell 12 9, grow");
        table.add(elementPanels.get(111), "cell 13 9, grow");
        table.add(elementPanels.get(112), "cell 14 9, grow");
        table.add(elementPanels.get(113), "cell 15 9, grow");
        table.add(elementPanels.get(114), "cell 16 9, grow");
        table.add(elementPanels.get(115), "cell 17 9, grow");
        table.add(elementPanels.get(116), "cell 18 9, grow");
        table.add(elementPanels.get(117), "cell 19 9, grow");

        actinoidsPanel = new JPanel(new BorderLayout());
        actinoidsPanel.setBorder(cellBorder);
        actinoidsPanel.setBackground(actinideColor);
        JLabel numLabel2 = new JLabel("89-103");
        JLabel textLabel2 = new JLabel("Ac-Lr");
        numLabel2.setFont(atomicNumberFont);
        textLabel2.setFont(symbolFont);
        numLabel2.setHorizontalAlignment(JLabel.CENTER);
        textLabel2.setHorizontalAlignment(JLabel.CENTER);
        actinoidsPanel.add(numLabel2, BorderLayout.NORTH);
        actinoidsPanel.add(textLabel2, BorderLayout.CENTER);
        table.add(actinoidsPanel, "cell 4 9, grow");

        Font numberFont = new Font("Segoe UI", Font.BOLD, 15);
        // Column Numbers
        for (int i = 0; i < 18; i++) {
            JLabel numberLabel = new JLabel(String.valueOf(i + 1));
            numberLabel.setFont(numberFont);
            numberLabel.setHorizontalAlignment(JLabel.CENTER);
            numberLabel.setOpaque(true);
            numberLabel.setBackground(table.getBackground());
            numberLabel.setForeground(Color.WHITE);
            int finalI = i;
            numberLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    resetCells();
                    if (clickedLabel == numberLabel) {
                        numberLabel.setForeground(Color.WHITE);
                        clickedLabel = null;
                        return;
                    }
                    for (JPanel p : elementPanels) {
                        String constraints = layout.getComponentConstraints(p).toString();
                        int offset = "cell ".length();
                        int length = constraints.indexOf(" ", offset);
                        String lineNumber = constraints.substring(offset, length);
                        if (Integer.parseInt(lineNumber) != finalI + 2) {
                            disableCell(p);
                        }
                        JLabel numberLabel = (JLabel) p.getComponent(1);
                        Element element = Element.get(Integer.parseInt(numberLabel.getText()));
                        if (element.isLanthanide() || element.isActinide()) {
                            disableCell(p);
                        }
                        disableCell(lanthanoidsPanel);
                        disableCell(actinoidsPanel);
                    }
                    clickedLabel = numberLabel;
                    numberLabel.setForeground(Color.WHITE);
                    for (JLabel l : numberLabels) {
                        if (l != numberLabel) {
                            l.setBackground(table.getBackground());
                            l.setForeground(Color.WHITE);
                        }
                    }
                }

                boolean isPressed, isHovered;

                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    if (clickedLabel != numberLabel) {
                        numberLabel.setBackground(table.getBackground().darker());
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    if (clickedLabel != numberLabel) {
                        if (!isPressed) {
                            numberLabel.setBackground(table.getBackground());
                        }
                    }
                }

                Point pointPressed;

                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    numberLabel.setBackground(numberLabel.getBackground().darker());
                    pointPressed = e.getPoint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isHovered) {
                            numberLabel.setBackground(table.getBackground().darker());
                        } else {
                            if (clickedLabel != numberLabel) {
                                numberLabel.setBackground(table.getBackground());
                            } else {
                                numberLabel.setBackground(table.getBackground().darker());
                            }
                        }
                    });
                    if (Utils.isPointOnComponent(e.getLocationOnScreen(), numberLabel) && !e.getPoint().equals(pointPressed)) {
                        this.mouseClicked(e);
                    }
                }

            });
            numberLabel.setCursor(HAND_CURSOR);
            table.add(numberLabel, "cell " + (i + 2) + " 1, grow");

            numberLabels.add(numberLabel);
        }

        // Row numbers
        for (int i = 0; i < 7; i++) {
            JLabel numberLabel = new JLabel(String.valueOf(i + 1));
            numberLabel.setFont(numberFont);
            numberLabel.setHorizontalAlignment(JLabel.CENTER);
            numberLabel.setOpaque(true);
            numberLabel.setBackground(table.getBackground());
            numberLabel.setForeground(Color.WHITE);
            int finalI = i;
            numberLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    resetCells();

                    if (clickedLabel == numberLabel) {
                        clickedLabel = null;
                        numberLabel.setForeground(Color.WHITE);
                        return;
                    }
                    for (JPanel p : elementPanels) {
                        Object constraints = layout.getComponentConstraints(p);
                        int offset = constraints.toString().indexOf(" ", 5);
                        int length = constraints.toString().indexOf(",", offset);
                        String lineNumber = constraints.toString().substring(offset + 1, length);
                        int num = Integer.parseInt(lineNumber);
                        if (num != finalI + 3) {
                            if (finalI + 3 == 8) {
                                if (num != finalI + 3 + 3) {
                                    disableCell(p);
                                }
                                disableCell(actinoidsPanel);
                            } else if (finalI + 3 == 9) {
                                if (num != finalI + 3 + 3) {
                                    disableCell(p);
                                }
                                disableCell(lanthanoidsPanel);
                            } else {
                                disableCell(p);
                                disableCell(lanthanoidsPanel);
                                disableCell(actinoidsPanel);
                            }
                        }
                    }
                    clickedLabel = numberLabel;
                    numberLabel.setForeground(Color.WHITE);
                    for (JLabel l : numberLabels) {
                        if (l != numberLabel) {
                            l.setBackground(table.getBackground());
                            l.setForeground(Color.WHITE);
                        }
                    }
                }

                boolean isPressed, isHovered;

                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    if (clickedLabel != numberLabel) {
                        numberLabel.setBackground(table.getBackground().darker());
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    if (clickedLabel != numberLabel) {
                        if (!isPressed) {
                            numberLabel.setBackground(table.getBackground());
                        }
                    }
                }

                Point pointPressed;

                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    numberLabel.setBackground(numberLabel.getBackground().darker());
                    pointPressed = e.getPoint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    SwingUtilities.invokeLater(() -> {
                            if (isHovered) {
                                numberLabel.setBackground(table.getBackground().darker());
                            } else {
                                if (clickedLabel != numberLabel) {
                                    numberLabel.setBackground(table.getBackground());
                                } else {
                                    numberLabel.setBackground(table.getBackground().darker());
                                }
                            }
                    });
                    if (Utils.isPointOnComponent(e.getLocationOnScreen(), numberLabel) && !e.getPoint().equals(pointPressed)) {
                        this.mouseClicked(e);
                    }
                }
            });
            numberLabel.setCursor(HAND_CURSOR);
            table.add(numberLabel, "cell 1 " + (i + 3) + ", grow");

            numberLabels.add(numberLabel);
        }

        String[] groups = {"IA", "IIA",
                "IIIB", "IVB", "VB", "VIB", "VIIB", "VIIIB", "VIIIB", "VIIIB", "IB", "IIB",
                "IIIA", "IVA", "VA", "VIA", "VIIA", "VIIIA"};

        for (int i = 0; i < groups.length; i++) {
            JLabel groupLabel = new JLabel(groups[i], JLabel.CENTER);
            groupLabel.setVerticalAlignment(JLabel.BOTTOM);
            groupLabel.setFont(defaultFont.deriveFont(11.0f));
            groupLabel.setForeground(Color.WHITE);
            if ((i + 1) == 2 || ((i + 1) >= 13 && (i + 1) < 18)) {
                table.add(groupLabel, "cell " + (i + 2) + " 3, grow");
            }
            if ((i + 1) >= 3 && (i + 1) < 13) {
                table.add(groupLabel, "cell " + (i + 2) + " 5, grow, gaptop 0");
            }
            if ((i + 1) == 1 || (i + 1) == 18) {
                table.add(groupLabel, "cell " + (i + 2) + " 2, grow");
            }
        }

        createLegendPanel();
    }

    private void createGlassPane() {
        glassPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 200));
                g2d.fillRect(0, 0, glassPane.getWidth(), glassPane.getHeight());
            }
        };
        glassPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        glassPane.setOpaque(false);
        // Listener to obstruct the table's mouse listeners
        glassPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        glassPaneHolder = new JScrollPane();
        JViewport viewport = new JViewport() {

            final Color transparent = new Color(0, 0, 0, 0);

            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(transparent);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        };
        viewport.setOpaque(false);
        glassPaneHolder.setOpaque(false);
        viewport.setView(glassPane);
        glassPaneHolder.setViewport(viewport);
        glassPaneHolder.getVerticalScrollBar().setUnitIncrement(8);
        glassPaneHolder.setBounds(0, 0, this.getWidth(), this.getHeight());
    }

    private void updateInfoPanel(Element e) {
        JPanel closeButton = new JPanel() {

            final Color borderColor = new Color(150, 150, 150, 100);

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int width = this.getWidth();
                int height = this.getHeight();

                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(this.getBackground());
                g2d.fillRect(0, 0, width, height);

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2.0f));
                int squareRadius = 10;

                // Top-left
                int x1 = (width / 2) - squareRadius;
                int y1 = (height / 2) - squareRadius;

                // Bottom-left
                int x2 = (width / 2) - squareRadius;
                int y2 = (height / 2) + squareRadius;

                // Top-right
                int x3 = (width / 2) + squareRadius;
                int y3 = (height / 2) - squareRadius;

                // Bottom-right
                int x4 = (width / 2) + squareRadius;
                int y4 = (height / 2) + squareRadius;

                g2d.drawLine(x1, y1, x4, y4);
                g2d.drawLine(x2, y2, x3, y3);

                // Drawing outline
                g2d.setColor(borderColor);
                g2d.drawRect(0, 0, width - 1, height - 1);
            }
        };
        Color hoverBackground = new Color(255, 255, 255, 30);
        Color pressBackground = new Color(255, 255, 255, 60);
        closeButton.addMouseListener(new MouseAdapter() {

            boolean isPressed, isHovered;

            Border activeBorder = selectedElementPanel.getBorder();

            @Override
            public void mouseClicked(MouseEvent e) {
                glassPaneHolder.setEnabled(false);
                glassPaneHolder.setVisible(false);
                glassPane.removeAll();
                layeredPane.remove(glassPaneHolder);
                selectedElement = null;
                selectedElementPanel.setBorder(activeBorder);
                selectedElementPanel = null;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                closeButton.setBackground(pressBackground);
                isPressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isHovered) {
                    closeButton.setBackground(hoverBackground);
                } else {
                    closeButton.setBackground(new Color(255, 255, 255, 10));
                }
                isPressed = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(hoverBackground);
                isHovered = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isPressed) {
                    closeButton.setBackground(new Color(255, 255, 255, 10));
                }
                isHovered = false;
            }
        });
        closeButton.setOpaque(false);
        closeButton.setBackground(new Color(255, 255, 255, 10));
        JLabel titleLabel = new JLabel(e.getName(), JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));

        MigLayout layout = new MigLayout("insets 20 20 150 20");
        layout.setColumnConstraints("[][180][][grow]");
        layout.setRowConstraints("[50][]50[]10[]10[]10[]10[]10[]10[][]20[]10[]10[]10[]");
        glassPane.setLayout(layout);

        glassPane.add(closeButton, "cell 0 0, w 50!, h 50!");
        glassPane.add(titleLabel, "cell 0 1, spanx, grow");

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);
        Font valueFont = new Font("Segoe UI", Font.BOLD, 30);

        JLabel symbolLabel = new JLabel("Símbolo:", JLabel.RIGHT);
        JLabel latinNameLabel = new JLabel("Nome em Latim:", JLabel.RIGHT);
        JLabel atomicNumberLabel = new JLabel("Número Atômico:", JLabel.RIGHT);
        JLabel atomicMassLabel = new JLabel("Massa Atômica:", JLabel.RIGHT);
        JLabel densityLabel = new JLabel("Densidade:", JLabel.RIGHT);
        symbolLabel.setFont(labelFont);
        latinNameLabel.setFont(labelFont);
        atomicNumberLabel.setFont(labelFont);
        atomicMassLabel.setFont(labelFont);
        densityLabel.setFont(labelFont);

        glassPane.add(atomicNumberLabel, "cell 0 2, grow");
        glassPane.add(symbolLabel, "cell 0 3, grow");
        glassPane.add(latinNameLabel, "cell 0 4, grow");
        glassPane.add(atomicMassLabel, "cell 0 5, grow");
        glassPane.add(densityLabel, "cell 0 6, grow");

        JLabel symbolValue = new JLabel(e.getSymbol());
        JLabel latinNameValue = new JLabel(e.getLatinName());
        JLabel atomicNumberValue = new JLabel(String.valueOf(e.getAtomicNumber()));
        JLabel atomicMassValue = new JLabel(e.getAtomicMass() + " u (g/mol)");
        JLabel densityValue = new JLabel(e.getDensity() + " g/cm³");
        symbolValue.setFont(valueFont);
        latinNameValue.setFont(valueFont);
        atomicNumberValue.setFont(valueFont);
        atomicMassValue.setFont(valueFont);
        densityValue.setFont(valueFont);

        glassPane.add(atomicNumberValue, "cell 2 2, grow");
        glassPane.add(symbolValue, "cell 2 3, grow");
        glassPane.add(latinNameValue, "cell 2 4, grow");
        glassPane.add(atomicMassValue, "cell 2 5, grow");
        glassPane.add(densityValue, "cell 2 6, grow");

        JLabel structureLabel = new JLabel("Estrutura Padrão:", JLabel.CENTER);
        JLabel protonsValue = new JLabel(String.valueOf(e.getProtons()), JLabel.RIGHT);
        JLabel protonsLabel = new JLabel("Prótons", JLabel.LEFT);
        JLabel neutronsValue = new JLabel(String.valueOf(e.getNeutrons()), JLabel.RIGHT);
        JLabel neutronsLabel = new JLabel("Nêutrons", JLabel.LEFT);
        JLabel electronsValue = new JLabel(String.valueOf(e.getElectrons()), JLabel.RIGHT);
        JLabel electronsLabel = new JLabel("Elétrons", JLabel.LEFT);
        structureLabel.setFont(FontUtils.changeSize(labelFont, 25));
        protonsValue.setFont(valueFont);
        neutronsValue.setFont(valueFont);
        electronsValue.setFont(valueFont);

        protonsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        protonsLabel.setVerticalAlignment(JLabel.BOTTOM);
        neutronsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        neutronsLabel.setVerticalAlignment(JLabel.BOTTOM);
        electronsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        electronsLabel.setVerticalAlignment(JLabel.BOTTOM);

        glassPane.add(structureLabel, "cell 1 7, grow");
        glassPane.add(protonsValue, "cell 0 8, grow, split 2");
        glassPane.add(protonsLabel, "gapright 30");
        glassPane.add(neutronsValue, "cell 1 8, grow, split 2");
        glassPane.add(neutronsLabel, "grow");
        glassPane.add(electronsValue, "cell 2 8, split 2, gapleft 30");
        glassPane.add(electronsLabel, "grow");

        JLabel meltingPointLabel = new JLabel("Ponto de Fusão:", JLabel.RIGHT);
        JLabel boilingPointLabel = new JLabel("Ponto de Ebulição:", JLabel.RIGHT);
        meltingPointLabel.setFont(labelFont);
        boilingPointLabel.setFont(labelFont);

        glassPane.add(meltingPointLabel, "cell 0 9, grow");
        glassPane.add(boilingPointLabel, "cell 0 10, grow");

        JLabel meltingPointValue = new JLabel();
        JLabel boilingPointValue = new JLabel();

        JComboBox<String> meltingPointComboBox = new JComboBox<>();
        JComboBox<String> boilingPointComboBox = new JComboBox<>();
        meltingPointComboBox.addItem("°C");
        meltingPointComboBox.addItem("°F");
        meltingPointComboBox.addItem("K");
        boilingPointComboBox.addItem("°C");
        boilingPointComboBox.addItem("°F");
        boilingPointComboBox.addItem("K");

        switch (OptionsPanel.getDefaultUnit()) {
            case OptionsPanel.CELSIUS_KEY -> {
                meltingPointValue.setText(e.getFusionPoint(Units.CELSIUS, 5) + " ");
                boilingPointValue.setText(e.getBoilingPoint(Units.CELSIUS, 5) + " ");
                meltingPointComboBox.setSelectedIndex(0);
                boilingPointComboBox.setSelectedIndex(0);
            }
            case OptionsPanel.FAHRENHEIT_KEY -> {
                meltingPointValue.setText(e.getFusionPoint(Units.FAHRENHEIT, 5) + " ");
                boilingPointValue.setText(e.getBoilingPoint(Units.FAHRENHEIT, 5) + " ");
                meltingPointComboBox.setSelectedIndex(1);
                boilingPointComboBox.setSelectedIndex(1);
            }
            case OptionsPanel.KELVIN_KEY -> {
                meltingPointValue.setText(e.getFusionPoint(Units.KELVIN, 5) + " ");
                boilingPointValue.setText(e.getBoilingPoint(Units.KELVIN, 5) + " ");
                meltingPointComboBox.setSelectedIndex(2);
                boilingPointComboBox.setSelectedIndex(2);
            }
        }

        meltingPointComboBox.addItemListener(e1 -> {
            if (e1.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) meltingPointComboBox.getSelectedItem();
                if (item == null) throw new NullPointerException();
                if (Double.isNaN(e.getFusionPoint(Units.KELVIN, 5))) {
                    return;
                }
                switch (item) {
                    case "°C" -> meltingPointValue.setText(e.getFusionPoint(Units.CELSIUS, 5) + " ");
                    case "°F" -> meltingPointValue.setText(e.getFusionPoint(Units.FAHRENHEIT, 5) + " ");
                    case "K" -> meltingPointValue.setText(e.getFusionPoint(Units.KELVIN, 5) + " ");
                }
            }
        });

        boilingPointComboBox.addItemListener(e1 -> {
            if (e1.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) boilingPointComboBox.getSelectedItem();
                if (item == null) throw new NullPointerException();
                if (Double.isNaN(e.getBoilingPoint(Units.KELVIN, 5))) {
                    return;
                }
                switch (item) {
                    case "°C" -> boilingPointValue.setText(e.getBoilingPoint(Units.CELSIUS, 5) + " ");
                    case "°F" -> boilingPointValue.setText(e.getBoilingPoint(Units.FAHRENHEIT, 5) + " ");
                    case "K" -> boilingPointValue.setText(e.getBoilingPoint(Units.KELVIN, 5) + " ");
                }
            }
        });

        meltingPointValue.setFont(valueFont);
        boilingPointValue.setFont(valueFont);
        meltingPointComboBox.setFont(valueFont.deriveFont(23.0f));
        boilingPointComboBox.setFont(valueFont.deriveFont(23.0f));

        glassPane.add(meltingPointValue, "cell 2 9, split 2");
        glassPane.add(meltingPointComboBox, "left");
        glassPane.add(boilingPointValue, "cell 2 10, split 2");
        glassPane.add(boilingPointComboBox, "left");

        JLabel electronegativityLabel = new JLabel("Eletronegatividade:", JLabel.RIGHT);
        JLabel ionPotentialLabel = new JLabel("Potencial de Ionização:", JLabel.RIGHT);
        JLabel ionsLabel = new JLabel("Principais Íons:", JLabel.RIGHT);
        electronegativityLabel.setFont(labelFont);
        ionPotentialLabel.setFont(labelFont);
        ionsLabel.setFont(labelFont);

        JLabel electronegativityValue = new JLabel(String.valueOf(e.getElectronegativity()));
        JLabel ionPotentialValue = new JLabel(e.getIonisationPotential() + " eV");
        JLabel ionsValue = new JLabel(superscript(e.getIons()));
        electronegativityValue.setFont(valueFont);
        ionPotentialValue.setFont(valueFont);
        ionsValue.setFont(valueFont);

        glassPane.add(electronegativityLabel, "cell 0 11, grow");
        glassPane.add(electronegativityValue, "cell 2 11, grow");

        glassPane.add(ionPotentialLabel, "cell 0 12, grow");
        glassPane.add(ionPotentialValue, "cell 2 12, grow");

        glassPane.add(ionsLabel, "cell 0 13, grow");
        glassPane.add(ionsValue, "cell 2 13, grow");

        glassPane.add(new AtomVisualization(e), "cell 3 2 1 6, grow");

        JLabel shellLabel = new JLabel("<html><u>" + e.getElectronShells() + "</u></html>", JLabel.CENTER);
        shellLabel.setFont(valueFont);
        glassPane.add(shellLabel, "cell 3 7 1 2, grow");




        for (Component c : glassPane.getComponents()) {
            if (c instanceof JLabel label) {
                if (label.getText().startsWith("NaN")) {
                    label.setText("---");
                }
                c.setForeground(Color.WHITE);
            }
        }

        glassPaneHolder.setEnabled(true);
        glassPaneHolder.setVisible(true);

        layeredPane.add(glassPaneHolder, Integer.valueOf(2));
        layeredPane.validate();
    }

    private void resetCells() {
        disableCell(lanthanoidsPanel);
        disableCell(actinoidsPanel);
        lanthanoidsPanel.setBorder(cellBorder);
        actinoidsPanel.setBorder(cellBorder);
        for (JPanel p : elementPanels) {
            JLabel atomicNumberLabel = (JLabel) p.getComponent(1);
            Element e = Element.get(Integer.parseInt(atomicNumberLabel.getText()));

            if (activeAttribute == GROUP_FAMILY) {
                if (e.isNonMetal()) {
                    p.setBackground(nonMetalColor);
                } else if (e.isNobleGas()) {
                    p.setBackground(nobleGasColor);
                } else if (e.isMetalloid()) {
                    p.setBackground(metalloidColor);
                } else if (e.isTransitionMetal()) {
                    p.setBackground(transitionMetalColor);
                } else if (e.isHalogen()) {
                    p.setBackground(halogenColor);
                } else if (e.isPostTransitionMetal()) {
                    p.setBackground(postTransitionColor);
                } else if (e.isActinide()) {
                    p.setBackground(actinideColor);
                } else if (e.isLanthanide()) {
                    p.setBackground(lanthanideColor);
                } else if (e.isAlkalineMetal()) {
                    p.setBackground(alkaliColor);
                } else if (e.isAlkalineEarthMetal()) {
                    p.setBackground(alkaliEarthColor);
                } else {
                    p.setBackground(Color.GRAY);
                }
            } else if (activeAttribute == GROUP_ELECTRONEGATIVITY) {
                double en = e.getElectronegativity();
                if (Double.isNaN(en)) {
                    p.setBackground(EN_COLOR_8);
                }
                if (e.isNobleGas()) {
                    p.setBackground(EN_COLOR_7);
                }
                if (en < 1.0) {
                    p.setBackground(EN_COLOR_0);
                } else if (en < 1.5) {
                    p.setBackground(EN_COLOR_1);
                } else if (en < 2.0) {
                    p.setBackground(EN_COLOR_2);
                } else if (en < 2.5) {
                    p.setBackground(EN_COLOR_3);
                } else if (en < 3.0) {
                    p.setBackground(EN_COLOR_4);
                } else if (en < 3.5) {
                    p.setBackground(EN_COLOR_5);
                } else if (en <= 3.98) {
                    p.setBackground(EN_COLOR_6);
                }
            } else if (activeAttribute == GROUP_MASS) {
                double mass = e.getAtomicMass();
                if (mass < 18) {
                    p.setBackground(MASS_COLOR_0);
                } else if (mass < 36) {
                    p.setBackground(MASS_COLOR_1);
                } else if (mass < 54) {
                    p.setBackground(MASS_COLOR_2);
                } else if (mass < 72) {
                    p.setBackground(MASS_COLOR_3);
                } else if (mass < 90) {
                    p.setBackground(MASS_COLOR_4);
                } else if (mass < 108) {
                    p.setBackground(MASS_COLOR_5);
                } else if (mass < 126) {
                    p.setBackground(MASS_COLOR_6);
                } else if (mass < 144) {
                    p.setBackground(MASS_COLOR_7);
                } else if (mass < 162) {
                    p.setBackground(MASS_COLOR_8);
                } else if (mass < 180) {
                    p.setBackground(MASS_COLOR_9);
                } else if (mass < 198) {
                    p.setBackground(MASS_COLOR_10);
                } else if (mass < 216) {
                    p.setBackground(MASS_COLOR_11);
                } else if (mass < 234) {
                    p.setBackground(MASS_COLOR_12);
                } else if (mass < 252) {
                    p.setBackground(MASS_COLOR_13);
                } else if (mass < 270) {
                    p.setBackground(MASS_COLOR_14);
                } else if (mass >= 270) {
                    p.setBackground(MASS_COLOR_15);
                }
            } else if (activeAttribute == GROUP_DENSITY) {
                double density = e.getDensity();
                if (density < 0.001) {
                    p.setBackground(DENSITY_COLOR_0);
                } else if (density < 0.1) {
                    p.setBackground(DENSITY_COLOR_1);
                } else if (density < 1.0) {
                    p.setBackground(DENSITY_COLOR_2);
                } else if (density < 3.0) {
                    p.setBackground(DENSITY_COLOR_3);
                } else if (density < 6.0) {
                    p.setBackground(DENSITY_COLOR_4);
                } else if (density < 9.0) {
                    p.setBackground(DENSITY_COLOR_5);
                } else if (density < 12.0) {
                    p.setBackground(DENSITY_COLOR_6);
                } else if (density < 15.0) {
                    p.setBackground(DENSITY_COLOR_7);
                } else if (density < 18.0) {
                    p.setBackground(DENSITY_COLOR_8);
                } else if (density < 21.0) {
                    p.setBackground(DENSITY_COLOR_9);
                } else if (density < 24.0) {
                    p.setBackground(DENSITY_COLOR_10);
                } else if (density >= 24.0) {
                    p.setBackground(DENSITY_COLOR_11);
                }
                if (Double.isNaN(density)) {
                    p.setBackground(EN_COLOR_8);
                }
            }

            enableCell(p);
            p.setBorder(cellBorder);
        }
        if (activeAttribute == GROUP_FAMILY) {
            enableCell(lanthanoidsPanel);
            enableCell(actinoidsPanel);
            lanthanoidsPanel.setBackground(lanthanideColor);
            actinoidsPanel.setBackground(actinideColor);
        }
    }

    private void createLegendPanel() {
        legendPanel = new JPanel();
        legendPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        legendPanel.setBackground(new Color(50, 50, 50));

        JLabel legendLabel = new JLabel("Legenda:");
        legendLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        legendLabel.setForeground(Color.WHITE);

        togglePanel1.setCursor(HAND_CURSOR);
        togglePanel2.setCursor(HAND_CURSOR);
        togglePanel3.setCursor(HAND_CURSOR);
        togglePanel4.setCursor(HAND_CURSOR);
        togglePanel5.setCursor(HAND_CURSOR);
        togglePanel6.setCursor(HAND_CURSOR);
        togglePanel7.setCursor(HAND_CURSOR);
        togglePanel8.setCursor(HAND_CURSOR);
        togglePanel9.setCursor(HAND_CURSOR);
        togglePanel10.setCursor(HAND_CURSOR);
        togglePanel11.setCursor(HAND_CURSOR);
        togglePanel12.setCursor(HAND_CURSOR);
        togglePanel13.setCursor(HAND_CURSOR);
        togglePanel14.setCursor(HAND_CURSOR);
        togglePanel15.setCursor(HAND_CURSOR);
        togglePanel16.setCursor(HAND_CURSOR);

        legendPanel.add(legendLabel, "cell 0 0, span");

        table.add(legendPanel, "cell 4 2 10 3, grow");

        for (JPanel p : elementPanels) {
            enabledMap.put(p, true);
        }
    }

    public void groupTable(int attribute) {
        if (attribute < 0 || attribute > 3) throw new IllegalArgumentException();
        activeAttribute = attribute;
        updateLegendPanel(attribute);
        resetCells();
    }

    private void updateLegendPanel(int attribute) {
        legendPanel.removeAll();
        JLabel legendLabel = new JLabel();
        legendLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        legendLabel.setForeground(Color.WHITE);
        //legendPanel.add(legendLabel, "cell 0 0, grow");
        for (PanelButton toggle : toggles) {
            Arrays.asList(toggle.getMouseListeners()).forEach(toggle::removeMouseListener);
            toggle.setInteractionListenerEnabled(true);
        }
        switch (attribute) {
            case GROUP_FAMILY -> {
                legendLabel.setText("Famílias:");
                MigLayout layout = new MigLayout("", "[grow][grow][grow][grow]",
                        "[grow][grow][grow][grow]");
                legendPanel.setLayout(layout);

                togglePanel1.setBackground(nonMetalColor);
                togglePanel2.setBackground(metalloidColor);
                togglePanel3.setBackground(postTransitionColor);
                togglePanel4.setBackground(alkaliColor);
                togglePanel5.setBackground(alkaliEarthColor);
                togglePanel6.setBackground(transitionMetalColor);
                togglePanel7.setBackground(lanthanideColor);
                togglePanel8.setBackground(actinideColor);
                togglePanel9.setBackground(halogenColor);
                togglePanel10.setBackground(nobleGasColor);
                togglePanel1.setBorder(BorderFactory.createLineBorder(nonMetalColor.brighter()));
                togglePanel2.setBorder(BorderFactory.createLineBorder(metalloidColor.brighter()));
                togglePanel3.setBorder(BorderFactory.createLineBorder(postTransitionColor.brighter()));
                togglePanel4.setBorder(BorderFactory.createLineBorder(alkaliColor.brighter()));
                togglePanel5.setBorder(BorderFactory.createLineBorder(alkaliEarthColor.brighter()));
                togglePanel6.setBorder(BorderFactory.createLineBorder(transitionMetalColor.brighter()));
                togglePanel7.setBorder(BorderFactory.createLineBorder(lanthanideColor.brighter()));
                togglePanel8.setBorder(BorderFactory.createLineBorder(actinideColor.brighter()));
                togglePanel9.setBorder(BorderFactory.createLineBorder(halogenColor.brighter()));
                togglePanel10.setBorder(BorderFactory.createLineBorder(nobleGasColor.brighter()));

                String[] labels = {"Não metais", "Semimetais", "Outros Metais", "Metais Alcalinos",
                                    "Metais Alcalino-terrosos", "Metais de Transição", "Lantanídeos",
                                    "Actinídeos", "Halogênios", "Gases Nobres"};

                String constraintEnding = ", w 30!, h 30!, split 2";

                for (int i = 0; i < 10; i++) {
                    JLabel label = new JLabel(labels[i]);
                    label.setFont(defaultFont);
                    label.setForeground(Color.WHITE);

                    PanelButton togglePanel = toggles.get(i);
                    togglePanel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            highlightFamily(togglePanel);
                        }
                    });
                    String cellConstraint = "cell " + (i / 3) + " " + ((i % 3) + 1);
                    legendPanel.add(togglePanel, cellConstraint + constraintEnding);
                    legendPanel.add(label, cellConstraint);
                }
            }
            case GROUP_ELECTRONEGATIVITY -> {
                legendLabel.setText("Eletronegatividade:");
                MigLayout layout = new MigLayout("", "[grow][grow][grow]", "[grow][grow][grow]");
                legendPanel.setLayout(layout);

                togglePanel1.setBackground(EN_COLOR_0);
                togglePanel2.setBackground(EN_COLOR_1);
                togglePanel3.setBackground(EN_COLOR_2);
                togglePanel4.setBackground(EN_COLOR_3);
                togglePanel5.setBackground(EN_COLOR_4);
                togglePanel6.setBackground(EN_COLOR_5);
                togglePanel7.setBackground(EN_COLOR_6);
                togglePanel8.setBackground(EN_COLOR_7);
                togglePanel9.setBackground(EN_COLOR_8);
                togglePanel1.setBorder(BorderFactory.createLineBorder(EN_COLOR_0.brighter()));
                togglePanel2.setBorder(BorderFactory.createLineBorder(EN_COLOR_1.brighter()));
                togglePanel3.setBorder(BorderFactory.createLineBorder(EN_COLOR_2.brighter()));
                togglePanel4.setBorder(BorderFactory.createLineBorder(EN_COLOR_3.brighter()));
                togglePanel5.setBorder(BorderFactory.createLineBorder(EN_COLOR_4.brighter()));
                togglePanel6.setBorder(BorderFactory.createLineBorder(EN_COLOR_5.brighter()));
                togglePanel7.setBorder(BorderFactory.createLineBorder(EN_COLOR_6.brighter()));
                togglePanel8.setBorder(BorderFactory.createLineBorder(EN_COLOR_7.brighter()));
                togglePanel9.setBorder(BorderFactory.createLineBorder(EN_COLOR_8.brighter()));

                /*
        ELECTRONEGATIVITY:
        0.79-0.99   2.00-2.49   3.50-3.98
        1.00-1.49   2.50-2.99   Gases Nobres
        1.50-1.99   3.00-3.49   Desconhecido
     */

                String[] labels = {"0.79-1.0", "1.0-1.5", "1.5-2.0", "2.0-2.5",
                                   "2.5-3.0", "3.0-3.5", "3.5-3.98", "Gases Nobres", "Valor Nulo/Desconhecido"};

                String constraintEnding = ", w 30!, h 30!, split 2";

                for (int i = 0; i < 9; i++) {
                    JLabel label = new JLabel(labels[i]);
                    label.setFont(defaultFont);
                    label.setForeground(Color.WHITE);

                    PanelButton togglePanel = toggles.get(i);
                    togglePanel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            highlightElectronegativity(togglePanel);
                        }
                    });
                    String cellConstraint = "cell " + (i / 3) + " " + ((i % 3) + 1);
                    legendPanel.add(togglePanel, cellConstraint + constraintEnding);
                    legendPanel.add(label, cellConstraint);
                }
            }
            case GROUP_MASS -> {
                legendLabel.setText("Massa Atômica (g/mol):");
                MigLayout layout = new MigLayout("", "[grow][grow][grow][grow]", "[grow][grow][grow][grow]");
                legendPanel.setLayout(layout);

                togglePanel1.setBackground(MASS_COLOR_0);
                togglePanel2.setBackground(MASS_COLOR_1);
                togglePanel3.setBackground(MASS_COLOR_2);
                togglePanel4.setBackground(MASS_COLOR_3);
                togglePanel5.setBackground(MASS_COLOR_4);
                togglePanel6.setBackground(MASS_COLOR_5);
                togglePanel7.setBackground(MASS_COLOR_6);
                togglePanel8.setBackground(MASS_COLOR_7);
                togglePanel9.setBackground(MASS_COLOR_8);
                togglePanel10.setBackground(MASS_COLOR_9);
                togglePanel11.setBackground(MASS_COLOR_10);
                togglePanel12.setBackground(MASS_COLOR_11);
                togglePanel13.setBackground(MASS_COLOR_12);
                togglePanel14.setBackground(MASS_COLOR_13);
                togglePanel15.setBackground(MASS_COLOR_14);
                togglePanel16.setBackground(MASS_COLOR_15);

                togglePanel1.setBorder(BorderFactory.createLineBorder(MASS_COLOR_0.brighter()));
                togglePanel2.setBorder(BorderFactory.createLineBorder(MASS_COLOR_1.brighter()));
                togglePanel3.setBorder(BorderFactory.createLineBorder(MASS_COLOR_2.brighter()));
                togglePanel4.setBorder(BorderFactory.createLineBorder(MASS_COLOR_3.brighter()));
                togglePanel5.setBorder(BorderFactory.createLineBorder(MASS_COLOR_4.brighter()));
                togglePanel6.setBorder(BorderFactory.createLineBorder(MASS_COLOR_5.brighter()));
                togglePanel7.setBorder(BorderFactory.createLineBorder(MASS_COLOR_6.brighter()));
                togglePanel8.setBorder(BorderFactory.createLineBorder(MASS_COLOR_7.brighter()));
                togglePanel9.setBorder(BorderFactory.createLineBorder(MASS_COLOR_8.brighter()));
                togglePanel10.setBorder(BorderFactory.createLineBorder(MASS_COLOR_9.brighter()));
                togglePanel11.setBorder(BorderFactory.createLineBorder(MASS_COLOR_10.brighter()));
                togglePanel12.setBorder(BorderFactory.createLineBorder(MASS_COLOR_11.brighter()));
                togglePanel13.setBorder(BorderFactory.createLineBorder(MASS_COLOR_12.brighter()));
                togglePanel14.setBorder(BorderFactory.createLineBorder(MASS_COLOR_13.brighter()));
                togglePanel15.setBorder(BorderFactory.createLineBorder(MASS_COLOR_14.brighter()));
                togglePanel16.setBorder(BorderFactory.createLineBorder(MASS_COLOR_15.brighter()));

                /*
        ATOMIC MASS:
        1.0  - 18.0     72.0  - 90.0    144.0 - 162.0   216.0 - 234.0
        18.0 - 36.0     90.0  - 108.0   162.0 - 180.0   234.0 - 252.0
        36.0 - 54.0     108.0 - 126.0   180.0 - 198.0   252.0 - 270.0
        54.0 - 72.0     126.0 - 144.0   198.0 - 216.0   +270.0
     */

                String[] labels = {"1-18", "18-36", "36-54", "54-72",
                                   "72-90", "90-108", "108-126", "126-144",
                                   "144-162", "162-180", "180-198", "198-216",
                                   "216-234", "234-252", "252-270", "> 270"};

                String constraintEnding = ", w 20!, h 20!, split 2";

                for (int i = 0; i < 16; i++) {
                    JLabel label = new JLabel(labels[i]);
                    label.setFont(defaultFont);
                    label.setForeground(Color.WHITE);

                    PanelButton togglePanel = toggles.get(i);
                    togglePanel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            highlightMass(togglePanel);
                        }
                    });
                    String cellConstraint = "cell " + (i / 4) + " " + ((i % 4) + 1);
                    legendPanel.add(togglePanel, cellConstraint + constraintEnding);
                    legendPanel.add(label, cellConstraint);
                }
            }
            case GROUP_DENSITY -> {
                legendLabel.setText("Densidade (g/cm³):");
                MigLayout layout = new MigLayout("", "[grow][grow][grow][grow]", "[][grow][grow][grow]");
                legendPanel.setLayout(layout);

                togglePanel1.setBackground(DENSITY_COLOR_0);
                togglePanel2.setBackground(DENSITY_COLOR_1);
                togglePanel3.setBackground(DENSITY_COLOR_2);
                togglePanel4.setBackground(DENSITY_COLOR_3);
                togglePanel5.setBackground(DENSITY_COLOR_4);
                togglePanel6.setBackground(DENSITY_COLOR_5);
                togglePanel7.setBackground(DENSITY_COLOR_6);
                togglePanel8.setBackground(DENSITY_COLOR_7);
                togglePanel9.setBackground(DENSITY_COLOR_8);
                togglePanel10.setBackground(DENSITY_COLOR_9);
                togglePanel11.setBackground(DENSITY_COLOR_10);
                togglePanel12.setBackground(DENSITY_COLOR_11);
                togglePanel1.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_0.brighter()));
                togglePanel2.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_1.brighter()));
                togglePanel3.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_2.brighter()));
                togglePanel4.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_3.brighter()));
                togglePanel5.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_4.brighter()));
                togglePanel6.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_5.brighter()));
                togglePanel7.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_6.brighter()));
                togglePanel8.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_7.brighter()));
                togglePanel9.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_8.brighter()));
                togglePanel10.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_9.brighter()));
                togglePanel11.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_10.brighter()));
                togglePanel12.setBorder(BorderFactory.createLineBorder(DENSITY_COLOR_11.brighter()));

                /*
        DENSITY:
        < 0.001     1-3    9-12    18-21
        0.001-0.1   3-6    12-15   21-24
        0.1-1.0     6-9    15-18   > 24
     */

                String[] labels = {"< 0.001", "0.001-0.1", "0.1-1.0",
                                   "1-3", "3-6", "6-9",
                                   "9-12", "12-15", "15-18",
                                   "18-21", "21-24", "> 24"};

                String constraintEnding = ", w 30!, h 30!, split 2";

                for (int i = 0; i < 12; i++) {
                    JLabel label = new JLabel(labels[i]);
                    label.setFont(defaultFont);
                    label.setForeground(Color.WHITE);

                    PanelButton togglePanel = toggles.get(i);
                    togglePanel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            highlightDensity(togglePanel);
                        }
                    });
                    String cellConstraint = "cell " + (i / 3) + " " + ((i % 3) + 1);
                    System.out.println(cellConstraint);
                    legendPanel.add(togglePanel, cellConstraint + constraintEnding);
                    legendPanel.add(label, cellConstraint);
                }
            }
        }
        table.updateUI();
    }

    private void enableCell(JPanel cell) {
        cell.setEnabled(true);
        for (Component c : cell.getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(Color.BLACK);
                continue;
            }
            c.setEnabled(true);
        }
        enabledMap.put(cell, true);
    }

    private Color disabledBackground = new Color(80, 80, 80);

    private void disableCell(JPanel cell) {
        cell.setEnabled(false);
        for (Component c : cell.getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(Color.WHITE);
                continue;
            }
            c.setEnabled(false);
        }
        cell.setBackground(disabledBackground);
        enabledMap.put(cell, false);
    }

    private final Border highlightBorder = BorderFactory.createLineBorder(new Color(255, 50, 50), 2);

    private void highlightCell(JPanel cell) {
        cell.setBorder(highlightBorder);
    }

    private void highlightFamily(PanelButton familyButton) {
        resetCells();
        clickedLabel = null;
        for (JLabel label : numberLabels) {
            label.setBackground(table.getBackground());
            label.setForeground(Color.WHITE);
        }
        if (clickedPanel == familyButton) {
            for (JPanel p : elementPanels) {
                enableCell(p);
            }
            enableCell(lanthanoidsPanel);
            lanthanoidsPanel.setBackground(lanthanideColor);
            enableCell(actinoidsPanel);
            actinoidsPanel.setBackground(actinideColor);
            clickedPanel = null;
            return;
        }
        clickedPanel = familyButton;
        disableCell(actinoidsPanel);
        disableCell(lanthanoidsPanel);
        for (JPanel p : elementPanels) {
            JLabel atomicNumberLabel = (JLabel) p.getComponent(1);
            Element element = Element.get(Integer.parseInt(atomicNumberLabel.getText()));
            if (familyButton == togglePanel1) {
                if (element.isNonMetal()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            } else if (familyButton == togglePanel2) {
                if (element.isMetalloid()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            } else if (familyButton == togglePanel3) {
                if (element.isPostTransitionMetal()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            else if (familyButton == togglePanel4) {
                if (element.isAlkalineMetal()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            else if (familyButton == togglePanel5) {
                if (element.isAlkalineEarthMetal()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            else if (familyButton == togglePanel6) {
                if (element.isTransitionMetal()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            else if (familyButton == togglePanel7) {
                if (element.isLanthanide()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
                enableCell(lanthanoidsPanel);
                lanthanoidsPanel.setBackground(lanthanideColor);
                highlightCell(lanthanoidsPanel);
            }
            else if (familyButton == togglePanel8) {
                if (element.isActinide()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
                enableCell(actinoidsPanel);
                actinoidsPanel.setBackground(actinideColor);
                highlightCell(actinoidsPanel);
            }
            else if (familyButton == togglePanel9) {
                if (element.isHalogen()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            else if (familyButton == togglePanel10) {
                if (element.isNobleGas()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
        }
    }

    private void highlightElectronegativity(PanelButton toggle) {
        resetCells();
        clickedLabel = null;
        for (JLabel label : numberLabels) {
            label.setBackground(table.getBackground());
            label.setForeground(Color.WHITE);
        }
        if (clickedPanel == toggle) {
            for (JPanel p : elementPanels) {
                enableCell(p);
            }
            clickedPanel = null;
            return;
        }
        clickedPanel = toggle;
        for (JPanel p : elementPanels) {
            JLabel atomicNumberLabel = (JLabel) p.getComponent(1);
            Element element = Element.get(Integer.parseInt(atomicNumberLabel.getText()));
            disableCell(actinoidsPanel);
            disableCell(lanthanoidsPanel);
            double en = element.getElectronegativity();
            if (toggle == togglePanel1) {
                if (en < 1.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel2) {
                if (en >= 1.0 && en < 1.5) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel3) {
                if (en >= 1.5 && en < 2.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel4) {
                if (en >= 2.0 && en < 2.5) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel5) {
                if (en >= 2.5 && en < 3.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel6) {
                if (en >= 3.0 && en < 3.5) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel7) {
                if (en >= 3.5 && en <= 3.98) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel8) {
                if (element.isNobleGas()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel9) {
                if (Double.isNaN(en) && !element.isNobleGas()) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
        }
        updateUI();
    }

    private void highlightMass(PanelButton toggle) {
        resetCells();
        clickedLabel = null;
        for (JLabel label : numberLabels) {
            label.setBackground(table.getBackground());
            label.setForeground(Color.WHITE);
        }
        if (clickedPanel == toggle) {
            for (JPanel p : elementPanels) {
                enableCell(p);
            }
            clickedPanel = null;
            return;
        }
        clickedPanel = toggle;
        for (JPanel p : elementPanels) {
            JLabel atomicNumberLabel = (JLabel) p.getComponent(1);
            Element element = Element.get(Integer.parseInt(atomicNumberLabel.getText()));
            disableCell(actinoidsPanel);
            disableCell(lanthanoidsPanel);
            double mass = element.getAtomicMass();
            if (toggle == togglePanel1) {
                if (mass > 1.0 && mass < 18.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel2) {
                if (mass >= 18.0 && mass < 36.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel3) {
                if (mass >= 36.0 && mass < 54.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel4) {
                if (mass >= 54.0 && mass < 72.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel5) {
                if (mass >= 72.0 && mass < 90.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel6) {
                if (mass >= 90.0 && mass < 108.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel7) {
                if (mass >= 108.0 && mass <= 126.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel8) {
                if (mass >= 126.0 && mass <= 144.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel9) {
                if (mass >= 144.0 && mass <= 162.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel10) {
                if (mass >= 162.0 && mass <= 180.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel11) {
                if (mass >= 180.0 && mass <= 198.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel12) {
                if (mass >= 198.0 && mass <= 216.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel13) {
                if (mass >= 216.0 && mass <= 234.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel14) {
                if (mass >= 234.0 && mass <= 252.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel15) {
                if (mass >= 252.0 && mass <= 270.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel16) {
                if (mass > 270.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
        }
        updateUI();
    }

    private void highlightDensity(PanelButton toggle) {
        resetCells();
        clickedLabel = null;
        for (JLabel label : numberLabels) {
            label.setBackground(table.getBackground());
            label.setForeground(Color.WHITE);
        }
        if (clickedPanel == toggle) {
            for (JPanel p : elementPanels) {
                enableCell(p);
            }
            clickedPanel = null;
            return;
        }
        clickedPanel = toggle;
        for (JPanel p : elementPanels) {
            JLabel atomicNumberLabel = (JLabel) p.getComponent(1);
            Element element = Element.get(Integer.parseInt(atomicNumberLabel.getText()));
            disableCell(actinoidsPanel);
            disableCell(lanthanoidsPanel);
            double density = element.getDensity();
            if (toggle == togglePanel1) {
                if (density < 0.001) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel2) {
                if (density >= 0.001 && density < 0.1) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel3) {
                if (density >= 0.1 && density < 1.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel4) {
                if (density >= 1.0 && density < 3.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel5) {
                if (density >= 3.0 && density < 6.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel6) {
                if (density >= 6.0 && density < 9.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel7) {
                if (density >= 9.0 && density < 12.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel8) {
                if (density >= 12.0 && density < 15.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel9) {
                if (density >= 15.0 && density <= 18.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel10) {
                if (density >= 18.0 && density < 21.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel11) {
                if (density >= 21.0 && density < 24.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
            if (toggle == togglePanel12) {
                if (density >= 24.0) {
                    enableCell(p);
                    highlightCell(p);
                } else {
                    disableCell(p);
                }
            }
        }
        updateUI();
    }

    private String superscript(String s) {
        if (s.equals("---")) return s;
        StringBuilder builder = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            switch (c) {
                case '0' -> builder.append("\u2070");
                case '1' -> builder.append("\u00B9");
                case '2' -> builder.append("\u00B2");
                case '3' -> builder.append("\u00B3");
                case '4' -> builder.append("\u2074");
                case '5' -> builder.append("\u2075");
                case '6' -> builder.append("\u2076");
                case '7' -> builder.append("\u2077");
                case '8' -> builder.append("\u2078");
                case '9' -> builder.append("\u2079");
                case '+' -> builder.append("\u207A");
                case '-' -> builder.append("\u207B");
                default -> builder.append(c);
            }
        }
        return builder.toString();
    }
}
