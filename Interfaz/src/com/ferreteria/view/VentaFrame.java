package com.ferreteria.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.ferreteria.controller.ControladorNavegacion;
import com.ferreteria.util.RoundedPanel;

public class VentaFrame extends JPanel {
  private ControladorNavegacion navegacion;

  public VentaFrame(ControladorNavegacion navegacion) {
    this.navegacion = navegacion;
    setLayout(new BorderLayout());
    setOpaque(false);
    initComponents();
  }

  private void initComponents() {
    // Margen de 20px en todos los lados
    JPanel container = new JPanel(new BorderLayout());
    container.setOpaque(false);
    container.setBorder(new EmptyBorder(20, 20, 20, 20));
    add(container, BorderLayout.CENTER);

    // Panel rojo que ocupa todo el espacio disponible después del margen
    RoundedPanel panelRojo = new RoundedPanel(84, new Color(186, 32, 36));
    panelRojo.setLayout(new GridBagLayout());
    panelRojo.setOpaque(true);
    panelRojo.setBorder(new EmptyBorder(40, 50, 40, 50)); // Padding interno generoso

    container.add(panelRojo, BorderLayout.CENTER);

    GridBagConstraints rg = new GridBagConstraints();
    rg.insets = new Insets(16, 16, 16, 16); // Más espacio entre elementos
    rg.fill = GridBagConstraints.HORIZONTAL;
    rg.anchor = GridBagConstraints.EAST;

    // Título con icono
    rg.gridx = 0;
    rg.gridy = 0;
    rg.gridwidth = 4;
    JPanel titulo = crearTituloPanel("Nueva Venta", "icono02.png");
    panelRojo.add(titulo, rg);

    // Espacio entre título y encabezados
    rg.gridy = 1;
    panelRojo.add(Box.createVerticalStrut(30), rg);

    // Encabezados (más grandes)
    rg.gridy = 2;
    rg.gridwidth = 1;
    rg.anchor = GridBagConstraints.WEST;

    rg.gridx = 0;
    panelRojo.add(etiquetaHeader("Item", Color.WHITE), rg);
    rg.gridx = 1;
    panelRojo.add(etiquetaHeader("Unitario", Color.WHITE), rg);
    rg.gridx = 2;
    panelRojo.add(etiquetaHeader("Cantidad", Color.WHITE), rg);
    rg.gridx = 3;
    panelRojo.add(etiquetaHeader("Precio", Color.WHITE), rg);

    // Filas de productos (campos más grandes)
    agregarFila(panelRojo, 3, "Bloque 6", "$", "0.75", "18280", "$ 13332.90");
    agregarFila(panelRojo, 4, "Bloque 4", "$", "0.75", "18280", "$ 13332.90");
    agregarFila(panelRojo, 5, "Cemento", "$", "10.00", "18280", "$ 13332.90");
    agregarFila(panelRojo, 6, "Arena", "$", "15.00", "18280", "$ 13332.90");

    // Espacio antes del botón
    rg.gridy = 7;
    rg.gridx = 0;
    rg.gridwidth = 4;
    panelRojo.add(Box.createVerticalStrut(20), rg);

    // Botón procesar (más grande)
    rg.gridy = 8;
    rg.fill = GridBagConstraints.NONE;
    rg.anchor = GridBagConstraints.CENTER;
    JButton btnProcesar = crearBotonProcesar();
    panelRojo.add(btnProcesar, rg);
  }

  private JLabel etiquetaHeader(String txt, Color color) {
    JLabel l = new JLabel(txt);
    l.setFont(new Font("SansSerif", Font.BOLD, 32)); // ← Más grande (antes 24)
    l.setForeground(color);
    return l;
  }

  private void agregarFila(JPanel panel, int row, String item, String currency, String unitPrice,
      String qty, String total) {
    GridBagConstraints rg = new GridBagConstraints();
    rg.insets = new Insets(18, 16, 18, 16); // ← Más espacio vertical entre filas
    rg.fill = GridBagConstraints.HORIZONTAL;
    rg.anchor = GridBagConstraints.WEST;
    rg.gridy = row;

    // Item (más grande)
    rg.gridx = 0;
    JLabel lItem = new JLabel(item);
    lItem.setFont(new Font("SansSerif", Font.BOLD, 38)); // ← Más grande (antes 30)
    lItem.setForeground(Color.WHITE);
    panel.add(lItem, rg);

    // Unitario (campo más grande)
    rg.gridx = 1;
    panel.add(campoMonedaFijo(unitPrice, 160), rg); // ← Más ancho (antes 110)

    // Cantidad (campo más grande)
    rg.gridx = 2;
    JTextField tfCant = crearCampo(qty, 160); // ← Más ancho (antes 110)
    panel.add(tfCant, rg);

    // Precio total (campo más grande)
    rg.gridx = 3;
    JTextField tfTotal = crearCampo(total, 200); // ← Más ancho (antes 150)
    tfTotal.setEditable(false);
    panel.add(tfTotal, rg);
  }

  private JTextField campoMonedaFijo(String valor, int width) {
    JTextField tf = new JTextField(valor) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pintar fondo redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // ← Bordes redondeados

        // Pintar borde redondeado
        g2.setColor(new Color(160, 160, 160));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        g2.dispose();
        super.paintComponent(g);

        // Pintar símbolo $ después del texto
        Graphics2D g3 = (Graphics2D) g.create();
        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g3.setColor(new Color(30, 30, 30));
        g3.setFont(getFont().deriveFont(Font.BOLD, 32f)); // ← Símbolo $ más grande
        FontMetrics fm = g3.getFontMetrics();
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g3.drawString("$", 14, y);
        g3.dispose();
      }

      @Override
      protected void paintBorder(Graphics g) {
        // No pintar borde por defecto
      }
    };

    tf.setPreferredSize(new Dimension(width, 60));
    tf.setMinimumSize(new Dimension(width, 60));
    tf.setFont(new Font("SansSerif", Font.PLAIN, 32)); // ← Números más grandes (antes 26)
    tf.setForeground(new Color(30, 30, 30));
    tf.setBackground(new Color(230, 230, 230));
    tf.setBorder(new EmptyBorder(10, 32, 10, 14)); // ← Sin borde estándar, solo padding
    tf.setOpaque(false); // ← Importante: dejar que paintComponent maneje el fondo
    tf.setEditable(false);
    tf.setText(valor);
    return tf;
  }

  private JTextField crearCampo(String valor, int width) {
    JTextField tf = new JTextField(valor) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pintar fondo redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // ← Bordes redondeados

        // Pintar borde redondeado
        g2.setColor(new Color(160, 160, 160));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        g2.dispose();
        super.paintComponent(g);
      }

      @Override
      protected void paintBorder(Graphics g) {
        // No pintar borde por defecto
      }
    };

    tf.setPreferredSize(new Dimension(width, 60));
    tf.setMinimumSize(new Dimension(width, 60));
    tf.setFont(new Font("SansSerif", Font.PLAIN, 32)); // ← Números más grandes (antes 26)
    tf.setForeground(new Color(30, 30, 30));
    tf.setBackground(new Color(230, 230, 230));
    tf.setBorder(new EmptyBorder(10, 14, 10, 14)); // ← Sin borde estándar, solo padding
    tf.setOpaque(false); // ← Importante: dejar que paintComponent maneje el fondo
    tf.setHorizontalAlignment(JTextField.CENTER);
    return tf;
  }

  private JButton crearBotonProcesar() {
    JButton b = new JButton("Procesar") {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
        g2.dispose();
        super.paintComponent(g);
      }

      @Override
      protected void paintBorder(Graphics g) {
      }
    };
    b.setFont(new Font("SansSerif", Font.BOLD, 32));
    b.setForeground(Color.WHITE);
    b.setBackground(new Color(50, 50, 50));
    b.setFocusPainted(false);
    b.setContentAreaFilled(false);
    b.setBorder(BorderFactory.createEmptyBorder(18, 60, 18, 60));
    b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    // Hover effect (igual que el botón Acceder)
    b.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        b.setBackground(new Color(90, 120, 90)); // Verde al pasar el mouse
        b.repaint();
      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent e) {
        b.setBackground(new Color(50, 50, 50)); // Vuelve a gris oscuro
        b.repaint();
      }
    });

    // Acción: navegar a ResumenFacturaFrame
    b.addActionListener(e -> {
      if (navegacion != null) {
        ResumenFacturaFrame resumen = new ResumenFacturaFrame(navegacion);
        navegacion.navegarA(resumen);

        // Cambiar botón a "Atrás" para volver a VentaFrame (no al menú principal)
        MenuFrame menuFrame = navegacion.getMenuFrame();
        if (menuFrame != null) {
          menuFrame.actualizarBotonAccion("Atrás", "icono12.png", "icono11.png", ev -> {
            VentaFrame nuevaVenta = new VentaFrame(navegacion);
            navegacion.navegarA(nuevaVenta);
            menuFrame.actualizarBotonAccion("Nuevo", "icono12.png", "icono11.png", evt -> {
              navegacion.volverAlMenu();
            });
          });
        }
      }
    });

    return b;
  }

  private JPanel crearTituloPanel(String texto, String iconoArchivo) {
    JPanel header = new JPanel();
    header.setOpaque(false);
    header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

    ImageIcon icon = com.ferreteria.util.IconLoader.cargarIcono(iconoArchivo); // ← Icono más grande
    JLabel icono = new JLabel(icon);

    JLabel titulo = new JLabel(texto);
    titulo.setFont(new Font("SansSerif", Font.BOLD, 48)); // ← Más grande (antes 36)
    titulo.setForeground(Color.BLACK);

    header.add(Box.createHorizontalGlue());
    header.add(icono);
    header.add(Box.createHorizontalStrut(16));
    header.add(titulo);
    header.add(Box.createHorizontalGlue());

    return header;
  }
}