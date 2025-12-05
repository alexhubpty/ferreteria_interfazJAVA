package com.ferreteria.util;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int radius;
    private Color fill;

    public RoundedPanel(int radius, Color fill) {
        this.radius = radius;
        this.fill = fill;
        setOpaque(false); // importante: no dejar que Swing pinte el fondo opaco
    }

    public void setFill(Color fill) {
        this.fill = fill;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Relleno redondeado
        g2.setColor(fill != null ? fill : getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        // No llamamos a super para evitar que repinte fondo con color del parent
        // super.paintComponent(g);
    }
}