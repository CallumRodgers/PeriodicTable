package com.callumrodgers.tabela.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HyperlinkButton extends JButton {

    private final String link;

    private Color linkForeground = Color.decode("#3355FF");

    public HyperlinkButton(String link) {
        this(link, link);
    }

    public HyperlinkButton(String link, String text) {
        super();
        this.link = link;
        this.addActionListener(getButtonActionListener());
        this.setText("<html><u>" + text + "</u></html>");
        this.setHorizontalTextPosition(JButton.LEFT);
        this.setHorizontalAlignment(JButton.LEFT);


        // UI calls
        this.setBorderPainted(false);
        this.setOpaque(false);
        this.setFocusPainted(false);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setForeground(linkForeground);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                HyperlinkButton.super.setForeground(getForeground().brighter().brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                HyperlinkButton.super.setForeground(linkForeground);
            }
        });
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        this.linkForeground = fg;
    }

    private ActionListener getButtonActionListener() {
        return e -> {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.browse(new URI(link));
                }
            } catch (URISyntaxException | IOException ex) {
                ex.printStackTrace();
            }
        };
    }
}
