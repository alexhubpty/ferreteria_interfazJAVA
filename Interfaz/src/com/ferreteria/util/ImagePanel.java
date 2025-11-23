package com.ferreteria.util;

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Panel con imagen de fondo escalada.
 * Componente reutilizable para fondos con imágenes.
 */
public class ImagePanel extends JPanel {
    private Image imagen;

    public ImagePanel(String rutaImagen) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream(rutaImagen));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("No se pudo cargar la imagen: " + rutaImagen);
            setBackground(new Color(230, 230, 230));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            g2.dispose();
        }
    }

    public Image getImagen() {
        return imagen;
    }
}