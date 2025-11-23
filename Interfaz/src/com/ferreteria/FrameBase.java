package com.ferreteria;

/**
 * Clase FrameBase
 * Panel interno celeste con esquinas redondeadas y separación 40px.
 * Fondo gris reemplazado por imagen escalada.
 */
import java.awt.*;
import javax.swing.*;

import com.ferreteria.view.LoginFrame;

// importaciones de utilidades reutilizables
import com.ferreteria.util.RoundedPanel;
import com.ferreteria.util.ImagePanel;

// import java.io.IOException;
// import javax.imageio.ImageIO;

public class FrameBase extends JFrame {

  private ImagePanel contenedorPrincipal;
  private RoundedPanel panelFondo;

  public FrameBase(String titulo) {
    super(titulo);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    initComponents();
    ajustarATamanoMonitor(0);

    addComponentListener(new java.awt.event.ComponentAdapter() {
      @Override
      public void componentMoved(java.awt.event.ComponentEvent e) {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        Rectangle b = gc.getBounds();
        setBounds(b);
      }
    });

    setVisible(true);
  }

  private void initComponents() {
    // Panel con imagen de fondo en lugar de color gris
    contenedorPrincipal = new ImagePanel("/resources/imagenes/fondo.jpg");
    contenedorPrincipal.setLayout(null);

    panelFondo = new RoundedPanel(60, new Color(229, 240, 250));
    panelFondo.setLayout(new BorderLayout());

    contenedorPrincipal.add(panelFondo);

    contenedorPrincipal.addComponentListener(new java.awt.event.ComponentAdapter() {
      @Override
      public void componentResized(java.awt.event.ComponentEvent e) {
        recalcularBoundsPanelFondo();
      }
    });

    add(contenedorPrincipal, BorderLayout.CENTER);
    cargarVistaInicial();
  }

  private void recalcularBoundsPanelFondo() {
    int margen = 40;
    int w = contenedorPrincipal.getWidth() - margen * 2;
    int h = contenedorPrincipal.getHeight() - margen * 2;
    if (w < 0)
      w = 0;
    if (h < 0)
      h = 0;
    panelFondo.setBounds(margen, margen, w, h);
    panelFondo.revalidate();
    panelFondo.repaint();
  }

  private void ajustarATamanoMonitor(int monitorIndex) {
    GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    if (monitorIndex < 0 || monitorIndex >= devices.length)
      monitorIndex = 0;
    GraphicsDevice gd = devices[monitorIndex];
    Rectangle bounds = gd.getDefaultConfiguration().getBounds();
    setBounds(bounds);
  }

  public void setContenido(JPanel nuevaVista) {
    panelFondo.removeAll();
    panelFondo.add(nuevaVista, BorderLayout.CENTER);
    panelFondo.revalidate();
    panelFondo.repaint();
  }

  private void cargarVistaInicial() {
    // Cambiar según la vista que quieres probar:
    setContenido(new LoginFrame());
    // setContenido(new VentaFrame());
    // setContenido(new ResumenFacturaFrame());
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new FrameBase("Ferretería El Polaco - Base"));
  }

}
