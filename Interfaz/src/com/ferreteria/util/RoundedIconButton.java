package com.ferreteria.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Botón redondeado con icono y texto
 * Estilo: 148px x 42px, fondo #d9d9d9, radio 32px
 */
public class RoundedIconButton extends JButton {
    private final int radius = 32;
    private Color bgNormal = new Color(217, 217, 217); // #d9d9d9
    private Color bgHover = new Color(180, 180, 180);
    private Color fgNormal = new Color(30, 30, 30);
    private Color fgHover = Color.WHITE;

    public RoundedIconButton(String texto, ImageIcon icono) {
        super(texto);

        if (icono != null) {
            // Escalar icono a 24x24
            Image scaledImage = icono.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));
            setHorizontalTextPosition(SwingConstants.RIGHT);
            setIconTextGap(10);
        }

        setFont(new Font("SansSerif", Font.BOLD, 16));
        setForeground(fgNormal);
        setBackground(bgNormal);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(320, 60));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(bgHover);
                setForeground(fgHover);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(bgNormal);
                setForeground(fgNormal);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Sin borde
    }
}