package com.callumrodgers.tabela.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PanelButton extends JPanel {

    private Color originalColor, hoverColor, pressedColor;

    private final MouseAdapter interactionListener;

    private boolean isPressed, isHovered;

    public PanelButton() {
        super();
        interactionListener = new MouseAdapter() {

            Point clickedPoint;

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                PanelButton.super.setBackground(pressedColor);
                isPressed = true;
                clickedPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (isHovered) {
                    PanelButton.super.setBackground(hoverColor);
                } else {
                    PanelButton.super.setBackground(originalColor);
                }
                isPressed = false;

                if (!e.getPoint().equals(clickedPoint)) {
                    if (isCursorWithinBounds(e)) {
                        for (MouseListener ml : PanelButton.this.getMouseListeners()) {
                            if (ml != this) {
                                ml.mouseClicked(e);
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                PanelButton.super.setBackground(hoverColor);
                isHovered = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (!isPressed) {
                    PanelButton.super.setBackground(originalColor);
                }
                isHovered = false;
            }
        };
        setInteractionListenerEnabled(true);
    }

    public PanelButton(LayoutManager layoutManager) {
        this();
        this.setLayout(layoutManager);
    }

    public void resetInteractions() {
        isHovered = false;
        isPressed = false;
        super.setBackground(originalColor);
    }

    private boolean isCursorWithinBounds(MouseEvent e) {
        if (e.getX() >= 0 && e.getX() <= this.getWidth()) {
            if (e.getY() >= 0 && e.getY() <= this.getHeight()) {
                return true;
            }
        }
        return false;
    }

    public void setInteractionListenerEnabled(boolean flag) {
        if (flag) {
            this.addMouseListener(interactionListener);
        } else {
            this.removeMouseListener(interactionListener);
        }
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        originalColor = bg;
        hoverColor = bg.darker();
        pressedColor = hoverColor.darker();
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public Color getPressedColor() {
        return pressedColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

}
