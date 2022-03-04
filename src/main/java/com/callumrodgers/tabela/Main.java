package com.callumrodgers.tabela;

import com.callumrodgers.tabela.ui.UIDefaults;
import com.callumrodgers.tabela.util.ResourceLoader;

import javax.swing.*;
import java.awt.*;

public class Main {

    static JFrame mainFrame;
    static JPanel contentPane = new JPanel(new BorderLayout());

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException |
                    IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        });

        Element.loadElements();

        SwingUtilities.invokeLater(() -> {
            mainFrame = new JFrame("Tabela Periódica");
            mainFrame.setIconImage(UIDefaults.icon);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setContentPane(contentPane);
            PeriodicTable table = new PeriodicTable();
            OptionsPanel panel = new OptionsPanel(mainFrame, table);

            DisplayMode displayMode = mainFrame.getGraphicsConfiguration().getDevice().getDisplayMode();
            int w = displayMode.getWidth();
            int h = displayMode.getHeight();

            mainFrame.setSize((int) (w * 0.80), (int) (h * 0.80));
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setEnabled(true);
            mainFrame.setVisible(true);
            mainFrame.requestFocus();
        });

        Thread updateCheckerThread = new Thread(new UpdateChecker(), "Update Checker");
        updateCheckerThread.start();
    }
}
