package com.ferreteria.util;

import javax.swing.ImageIcon;
import java.net.URL;

/**
 * Utilidad para cargar recursos gráficos
 */
public class IconLoader {

    /**
     * Carga un icono desde resources/imagenes/iconos/
     * 
     * @param nombreArchivo nombre del archivo (ej: "venta.png")
     * @return ImageIcon o null si no se encuentra
     */
    public static ImageIcon cargarIcono(String nombreArchivo) {
        try {
            URL url = IconLoader.class.getResource("/resources/iconos/" + nombreArchivo);
            if (url != null) {
                return new ImageIcon(url);
            } else {
                System.err.println("⚠ No se encontró el icono: " + nombreArchivo);
                return null;
            }
        } catch (Exception e) {
            System.err.println("✗ Error al cargar icono " + nombreArchivo + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Carga un icono de flecha genérica (por si no tienes iconos personalizados
     * aún)
     */
    public static ImageIcon crearIconoFlecha() {
        // Icono temporal: triángulo/flecha simple
        int size = 24;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(
                size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new java.awt.Color(70, 70, 70));

        // Dibujar flecha derecha
        int[] xPoints = { 6, 18, 6 };
        int[] yPoints = { 4, 12, 20 };
        g2.fillPolygon(xPoints, yPoints, 3);
        g2.dispose();

        return new ImageIcon(img);
    }
}