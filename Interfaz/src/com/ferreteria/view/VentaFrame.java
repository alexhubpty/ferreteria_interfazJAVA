package com.ferreteria.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.ferreteria.controller.BeforeLeave;
import com.ferreteria.controller.ControladorNavegacion;
import com.ferreteria.controller.VentaController;
import com.ferreteria.util.RoundedPanel;

public class VentaFrame extends JPanel implements BeforeLeave {
  private final ControladorNavegacion navegacion;
  private final VentaController controlador = new VentaController();

  // Campos para limpiar
  private final List<JTextField> camposCantidad = new ArrayList<>();
  private final List<JTextField> camposTotales = new ArrayList<>();

  // Bandera para doble clic
  private boolean salidaArmada = false;

  public VentaFrame(ControladorNavegacion navegacion) {
    this.navegacion = navegacion;
    setLayout(new BorderLayout());
    setOpaque(false);
    initComponents();
  }

  @Override
  public boolean onBeforeLeave() {
    // Si hay datos y aún no se armó la salida
    if (!salidaArmada && hayDatos()) {
      limpiarCampos();
      salidaArmada = true;
      return true; // Bloquear salida (primer clic)
    }
    return false; // Permitir salida (segundo clic)
  }

  private boolean hayDatos() {
    for (JTextField tf : camposCantidad) {
      String texto = tf.getText().trim();
      if (!texto.isEmpty() && !texto.equals("0")) {
        return true;
      }
    }
    return false;
  }

  private void limpiarCampos() {
    for (int i = 0; i < camposCantidad.size(); i++) {
      camposCantidad.get(i).setText("");
      camposTotales.get(i).setText("$ 0.00");
    }
  }

  private void initComponents() {
    JPanel container = new JPanel(new BorderLayout());
    container.setOpaque(false);
    container.setBorder(new EmptyBorder(20, 20, 20, 20));
    add(container, BorderLayout.CENTER);

    RoundedPanel panel = new RoundedPanel(84, new Color(186, 32, 36));
    panel.setLayout(new GridBagLayout());
    panel.setOpaque(true);
    panel.setBorder(new EmptyBorder(40, 50, 40, 50));
    container.add(panel, BorderLayout.CENTER);

    GridBagConstraints g = new GridBagConstraints();
    g.insets = new Insets(16, 16, 16, 16);
    g.fill = GridBagConstraints.HORIZONTAL;

    // Título
    g.gridx = 0;
    g.gridy = 0;
    g.gridwidth = 4;
    g.anchor = GridBagConstraints.CENTER;
    panel.add(crearTitulo("Nueva Venta", "icono02.png"), g);

    g.gridy = 1;
    panel.add(Box.createVerticalStrut(20), g);

    // Encabezados
    g.gridy = 2;
    g.gridwidth = 1;
    g.anchor = GridBagConstraints.WEST;
    g.gridx = 0;
    panel.add(crearEtiqueta("Item"), g);
    g.gridx = 1;
    panel.add(crearEtiqueta("Unitario"), g);
    g.gridx = 2;
    panel.add(crearEtiqueta("Cantidad"), g);
    g.gridx = 3;
    panel.add(crearEtiqueta("Precio"), g);

    // Productos
    agregarFila(panel, 3, "Bloque 6");
    agregarFila(panel, 4, "Bloque 4");
    agregarFila(panel, 5, "Cemento");
    agregarFila(panel, 6, "Arena");

    // Botón procesar
    g.gridy = 7;
    g.gridx = 0;
    g.gridwidth = 4;
    g.anchor = GridBagConstraints.CENTER;
    g.fill = GridBagConstraints.NONE;
    panel.add(Box.createVerticalStrut(20), g);

    g.gridy = 8;
    panel.add(crearBotonProcesar(), g);
  }

  private void agregarFila(JPanel panel, int row, String producto) {
    GridBagConstraints g = new GridBagConstraints();
    g.insets = new Insets(12, 16, 12, 16);
    g.fill = GridBagConstraints.HORIZONTAL;
    g.anchor = GridBagConstraints.WEST;
    g.gridy = row;

    // Nombre producto
    g.gridx = 0;
    JLabel nombre = new JLabel(producto);
    nombre.setFont(new Font("SansSerif", Font.BOLD, 32));
    nombre.setForeground(Color.WHITE);
    panel.add(nombre, g);

    // Precio unitario
    g.gridx = 1;
    String precio = controlador.getPrecio(producto).toPlainString();
    panel.add(crearCampoFijo("$ " + precio, 140), g);

    // Cantidad
    g.gridx = 2;
    JTextField tfCant = crearCampoEditable("", 120);
    panel.add(tfCant, g);
    camposCantidad.add(tfCant);

    // Total
    g.gridx = 3;
    JTextField tfTotal = crearCampoFijo("$ 0.00", 160);
    panel.add(tfTotal, g);
    camposTotales.add(tfTotal);

    // Listener para recalcular y desarmar salida
    tfCant.getDocument().addDocumentListener(new DocumentListener() {
      private void actualizar() {
        salidaArmada = false; // Desarmar si escribe de nuevo
        try {
          int cant = tfCant.getText().trim().isEmpty() ? 0
              : Integer.parseInt(tfCant.getText().trim());
          String total = controlador.calcularTotal(producto, cant).toPlainString();
          tfTotal.setText("$ " + total);
        } catch (NumberFormatException ex) {
          tfTotal.setText("$ 0.00");
        }
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        actualizar();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        actualizar();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        actualizar();
      }
    });
  }

  // ========== Componentes UI ==========

  private JLabel crearEtiqueta(String texto) {
    JLabel lbl = new JLabel(texto);
    lbl.setFont(new Font("SansSerif", Font.BOLD, 28));
    lbl.setForeground(Color.WHITE);
    return lbl;
  }

  private JTextField crearCampoFijo(String texto, int ancho) {
    JTextField tf = crearCampoBase(texto, ancho);
    tf.setEditable(false);
    return tf;
  }

  private JTextField crearCampoEditable(String texto, int ancho) {
    return crearCampoBase(texto, ancho);
  }

  private JTextField crearCampoBase(String texto, int ancho) {
    JTextField tf = new JTextField(texto) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
        super.paintComponent(g);
      }

      @Override
      protected void paintBorder(Graphics g) {
      }
    };
    tf.setPreferredSize(new Dimension(ancho, 50));
    tf.setFont(new Font("SansSerif", Font.PLAIN, 26));
    tf.setForeground(new Color(30, 30, 30));
    tf.setBackground(new Color(230, 230, 230));
    tf.setBorder(new EmptyBorder(8, 12, 8, 12));
    tf.setOpaque(false);
    tf.setHorizontalAlignment(JTextField.CENTER);
    return tf;
  }

  private JPanel crearTitulo(String texto, String icono) {
    JPanel header = new JPanel();
    header.setOpaque(false);
    header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

    ImageIcon icon = com.ferreteria.util.IconLoader.cargarIcono(icono);
    if (icon != null) {
      header.add(new JLabel(icon));
      header.add(Box.createHorizontalStrut(12));
    }

    JLabel titulo = new JLabel(texto);
    titulo.setFont(new Font("SansSerif", Font.BOLD, 42));
    titulo.setForeground(Color.BLACK);
    header.add(titulo);

    return header;
  }

  private JButton crearBotonProcesar() {
    JButton btn = new JButton("Procesar") {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();
        super.paintComponent(g);
      }

      @Override
      protected void paintBorder(Graphics g) {
      }
    };
    btn.setFont(new Font("SansSerif", Font.BOLD, 28));
    btn.setForeground(Color.WHITE);
    btn.setBackground(new Color(50, 50, 50));
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false);
    btn.setBorder(BorderFactory.createEmptyBorder(14, 50, 14, 50));
    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    btn.addActionListener(e -> {
      if (!controlador.tieneProductos()) {
        JOptionPane.showMessageDialog(this, "Ingrese al menos un producto", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
      }
      // Procesar y navegar a resumen
      com.ferreteria.model.Venta venta = controlador.procesarVenta();
      ResumenFacturaFrame resumen = new ResumenFacturaFrame(navegacion, venta);
      navegacion.navegarA(resumen);
    });

    return btn;
  }
}