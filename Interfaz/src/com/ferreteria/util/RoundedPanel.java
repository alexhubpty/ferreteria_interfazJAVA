package com.ferreteria.util;

import java.awt.*;
import javax.swing.*;

/**
 * Panel redondeado que pinta su fondo con antialias.
 * Componente reutilizable en toda la aplicación.
 */
public class RoundedPanel extends JPanel {
    private final int arc;
    private final Color fill;

    public RoundedPanel(int arc, Color fill) {
        this.arc = arc;
        this.fill = fill;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fill);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2.dispose();
        super.paintComponent(g);
    }

    // Métodos útiles adicionales
    public int getArc() {
        return arc;
    }

    public Color getFillColor() {
        return fill;
    }
}