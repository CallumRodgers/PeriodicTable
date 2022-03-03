package com.callumrodgers.tabela.ui;

import com.callumrodgers.tabela.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import java.util.List;

import static java.lang.Math.*;

public class AtomVisualization extends JPanel {

    private static final MathContext CONTEXT = new MathContext(5, RoundingMode.HALF_UP);

    private static final Color TRANSPARENT = new Color(0x00000000, true),
            ELECTRON_COLOR = Color.WHITE,
            SHELL_COLOR = new Color(220, 220, 220),
            PROTON_COLOR = new Color(100, 100, 250),
            NEUTRON_COLOR = new Color(250, 100, 100);

    private int protons, neutrons, electrons;
    private String shells;

    private int usedShells;

    private int K_AMOUNT, L_AMOUNT, M_AMOUNT, N_AMOUNT, O_AMOUNT, P_AMOUNT, Q_AMOUNT;

    private float distanceFactor = 15.0f;
    private int minDistance = 40;

    private int electronSize = 8,
            protonSize = 10,
            neutronSize = 10;

    private long dt;

    private List<Ellipse2D> electronDrawings;

    private final List<Point> randomPositions = new LinkedList<>();

    public AtomVisualization(Element element) {
        this.protons = element.getProtons();
        this.neutrons = element.getNeutrons();
        this.electrons = element.getElectrons();
        this.shells = element.getElectronShells();
        this.electronDrawings = new LinkedList<>();
        this.setOpaque(false);
        parseShellConfiguration(shells);

        for (int i = 0; i < electrons; i++) {
            electronDrawings.add(new Ellipse2D.Double(0, 0, electronSize, electronSize));
        }

        Timer updateTimer = new Timer(16, new ActionListener() {

            final long lastTime = System.currentTimeMillis();

            @Override
            public void actionPerformed(ActionEvent e) {
                dt = System.currentTimeMillis() - lastTime;
                AtomVisualization.this.repaint();
                if (!AtomVisualization.this.isShowing()) {
                    ((Timer) (e.getSource())).stop();
                }
            }
        });
        updateTimer.start();
    }

    private void parseShellConfiguration(String configuration) {
        String[] shells = configuration.split("\s");
        for (int i = 0; i < shells.length; i++) {
            String shell = shells[i];
            int num = Integer.parseInt(shell.substring(1));
            if (num == 0) {
                usedShells = i;
                return;
            }
            if (i == 0) K_AMOUNT = num;
            if (i == 1) L_AMOUNT = num;
            if (i == 2) M_AMOUNT = num;
            if (i == 3) N_AMOUNT = num;
            if (i == 4) O_AMOUNT = num;
            if (i == 5) P_AMOUNT = num;
            if (i == 6) Q_AMOUNT = num;
        }
        usedShells = 7;
    }

    int t = 0;
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int w = this.getWidth();
        int h = this.getHeight();
        Point center = new Point(w / 2, h / 2);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        // Filling empty background.
        g2d.setColor(TRANSPARENT);
        g2d.fillRect(0, 0, w, h);

        // Drawing shells.
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.setColor(SHELL_COLOR);

        int electronsDone = 0;
        for (int i = 0; i < usedShells; i++) {
            int x = (int) (center.x - (i * distanceFactor) - minDistance);
            int y = (int) (center.y - (i * distanceFactor) - minDistance);
            int s = w < h ? ((center.x - x) * 2) : ((center.y - y) * 2);
            g2d.drawOval(x, y, s, s);

            // Drawing electrons.
            g2d.setColor(ELECTRON_COLOR);
            switch (i) {
                case 0 -> {
                    double d = 360.0 / K_AMOUNT;
                    for (int e = 0; e < K_AMOUNT; e++) {
                        drawElectron(g2d, electronsDone, d, e, s / 2.0, center);
                    }
                }
                case 1 -> {
                    double d = 360.0 / L_AMOUNT;
                    for (int e = 0; e < L_AMOUNT; e++) {
                        drawElectron(g2d, electronsDone, d, e, s / 2.0, center);
                    }
                }
                case 2 -> {
                    double d = 360.0 / M_AMOUNT;
                    for (int e = 0; e < M_AMOUNT; e++) {
                        drawElectron(g2d, electronsDone, d, e, s / 2.0, center);
                    }
                }
                case 3 -> {
                    double d = 360.0 / N_AMOUNT;
                    for (int e = 0; e < N_AMOUNT; e++) {
                        drawElectron(g2d, electronsDone, d, e, s / 2.0, center);
                    }
                }
                case 4 -> {
                    double d = 360.0 / O_AMOUNT;
                    for (int e = 0; e < O_AMOUNT; e++) {
                        drawElectron(g2d, electronsDone, d, e, s / 2.0, center);
                    }
                }
                case 5 -> {
                    double d = 360.0 / P_AMOUNT;
                    for (int e = 0; e < P_AMOUNT; e++) {
                        drawElectron(g2d, electronsDone, d, e, s / 2.0, center);
                    }
                }
                case 6 -> {
                    double d = 360.0 / Q_AMOUNT;
                    for (int e = 0; e < Q_AMOUNT; e++) {
                        drawElectron(g2d, electronsDone, d, e, s / 2.0, center);
                    }
                }
            }
            electronsDone++;
        }

        // Preparing to draw nucleus.
        int maxRadius = minDistance / 3;
        Rectangle usableArea = new Rectangle(center.x - maxRadius,
                center.y - maxRadius,
                maxRadius * 2,
                maxRadius * 2);

        // Drawing nucleus.
        int protonsDone = 0;
        int neutronsDone = 0;
        if (t % 5 == 0) {
            randomPositions.clear();
            for (int i = 0; i < protons + neutrons; i++) {
                randomPositions.add(generateRandomPos(center, i, usableArea));
            }
        }
        for (int i = 0; i < protons + neutrons; i++) {
            int x = randomPositions.get(i).x;
            int y = randomPositions.get(i).y;
            if (protonsDone == protons) {
                g2d.setColor(NEUTRON_COLOR);
                g2d.fillOval(x, y, neutronSize, neutronSize);
                g2d.setColor(NEUTRON_COLOR.darker());
                g2d.drawOval(x, y, neutronSize, neutronSize);
                neutronsDone++;
                continue;
            }
            if (neutronsDone == neutrons) {
                g2d.setColor(PROTON_COLOR);
                g2d.fillOval(x, y, protonSize, protonSize);
                g2d.setColor(PROTON_COLOR.darker());
                g2d.drawOval(x, y, protonSize, protonSize);
                protonsDone++;
                continue;
            }
            if ((i % 3 == 0)) {
                g2d.setColor(PROTON_COLOR);
                g2d.fillOval(x, y, protonSize, protonSize);
                g2d.setColor(PROTON_COLOR.darker());
                g2d.drawOval(x, y, protonSize, protonSize);
                protonsDone++;
            } else {
                g2d.setColor(NEUTRON_COLOR);
                g2d.fillOval(x, y, neutronSize, neutronSize);
                g2d.setColor(NEUTRON_COLOR.darker());
                g2d.drawOval(x, y, neutronSize, neutronSize);
                neutronsDone++;
            }
        }

        t++;
    }

    private void drawElectron(Graphics2D g2d, int electronsDone, double d, int e, double radius, Point center) {
        Ellipse2D electron = electronDrawings.get(electronsDone);
        double rad = toRadians(d * e + (dt / (radius / 4.0)));
        double xPos = center.x - (cos(rad) * (radius)) - (electronSize / 2.0);
        double yPos = center.y - (sin(rad) * (radius)) - (electronSize / 2.0);
        electron.setFrame(xPos, yPos, electronSize, electronSize);
        g2d.fill(electron);
    }

    private Point generateRandomPos(Point center, int index, Rectangle area) {
        // Algorithm for random positions. The fewer protons and neutrons the atom has,
        // lower the distance between them will be.
        int randomXOffset = ThreadLocalRandom.current().nextInt(- ((area.width / 2) * index), (area.width / 2) * index + 1);
        int randomYOffset = ThreadLocalRandom.current().nextInt(- ((area.width / 2) * index), (area.width / 2) * index + 1);
        int x = (int) (Math.round(center.x + (randomXOffset * (0.1 * pow(1.04, - (index))))) - (protonSize / 2.0));
        int y = (int) (Math.round(center.y + (randomYOffset * (0.1 * pow(1.04, - (index))))) - (protonSize / 2.0));
        return new Point(x, y);
    }
}
