package com.ferreteria.view;

import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.ferreteria.controller.ControladorNavegacion;
import com.ferreteria.model.Venta;
import com.ferreteria.service.VentaService;
import com.ferreteria.util.RoundedPanel;

public class ResumenFacturaFrame extends JPanel {
  private ControladorNavegacion navegacion;
  private Venta venta;
  private VentaService servicio = new VentaService();

  private JLabel lblSubtotal;
  private JLabel lblITBMS;
  private JLabel lblTotal;
  private JTextField tfPago;
  private JLabel lblCambio;

  // Estado del botón
  private boolean ventaProcesada = false;

  public ResumenFacturaFrame(ControladorNavegacion navegacion, Venta venta) {
    this.navegacion = navegacion;
    this.venta = venta;
    setLayout(new BorderLayout());
    setOpaque(false);
    initComponents();
    mostrarDatos();
  }

  private void mostrarDatos() {
    lblSubtotal.setText("$ " + venta.getSubtotal().toPlainString());
    lblITBMS.setText("$ " + venta.getItbms().toPlainString());
    lblTotal.setText("$ " + venta.getTotal().toPlainString());
    tfPago.setText("");
    lblCambio.setText("$ 0.00");
  }

  private void initComponents() {
    JPanel container = new JPanel(new BorderLayout());
    container.setOpaque(false);
    container.setBorder(new EmptyBorder(20, 20, 20, 20));
    add(container, BorderLayout.CENTER);

    RoundedPanel panelRojo = new RoundedPanel(84, new Color(186, 32, 36));
    panelRojo.setLayout(new GridBagLayout());
    panelRojo.setOpaque(true);
    panelRojo.setBorder(new EmptyBorder(40, 50, 40, 50));
    container.add(panelRojo, BorderLayout.CENTER);

    GridBagConstraints rg = new GridBagConstraints();
    rg.insets = new Insets(10, 16, 10, 16);
    rg.fill = GridBagConstraints.HORIZONTAL;
    rg.anchor = GridBagConstraints.CENTER;

    // Título
    rg.gridx = 0;
    rg.gridy = 0;
    rg.gridwidth = 2;
    panelRojo.add(crearTituloPanel("Resumen de Factura", "icono02.png"), rg);

    rg.gridy = 1;
    panelRojo.add(Box.createVerticalStrut(40), rg);

    // Subtotal
    rg.gridy = 2;
    rg.gridwidth = 1;
    rg.gridx = 0;
    rg.anchor = GridBagConstraints.EAST;
    panelRojo.add(etiquetaLabel("Subtotal"), rg);
    rg.gridx = 1;
    rg.anchor = GridBagConstraints.WEST;
    lblSubtotal = crearCampoValor("$ 0.00");
    panelRojo.add(lblSubtotal, rg);

    // ITBMS
    rg.gridy = 3;
    rg.gridx = 0;
    rg.anchor = GridBagConstraints.EAST;
    panelRojo.add(etiquetaLabel("ITBMS 7%"), rg);
    rg.gridx = 1;
    rg.anchor = GridBagConstraints.WEST;
    lblITBMS = crearCampoValor("$ 0.00");
    panelRojo.add(lblITBMS, rg);

    rg.gridy = 4;
    rg.gridx = 0;
    rg.gridwidth = 2;
    panelRojo.add(Box.createVerticalStrut(20), rg);

    // Total
    rg.gridy = 5;
    rg.gridwidth = 1;
    rg.gridx = 0;
    rg.anchor = GridBagConstraints.EAST;
    panelRojo.add(etiquetaLabelGrande("Total a Pagar"), rg);
    rg.gridx = 1;
    rg.anchor = GridBagConstraints.WEST;
    lblTotal = new JLabel("$ 0.00");
    lblTotal.setFont(new Font("SansSerif", Font.BOLD, 48));
    lblTotal.setForeground(Color.BLACK);
    panelRojo.add(lblTotal, rg);

    rg.gridy = 6;
    rg.gridx = 0;
    rg.gridwidth = 2;
    panelRojo.add(Box.createVerticalStrut(20), rg);

    // Pago
    rg.gridy = 7;
    rg.gridwidth = 1;
    rg.gridx = 0;
    rg.anchor = GridBagConstraints.EAST;
    panelRojo.add(etiquetaLabel("Pago"), rg);
    rg.gridx = 1;
    rg.anchor = GridBagConstraints.WEST;
    tfPago = crearCampoEditable("");
    panelRojo.add(tfPago, rg);

    // Cambio (sin DocumentListener, se calcula solo al presionar Procesar)
    rg.gridy = 8;
    rg.gridwidth = 1;
    rg.gridx = 0;
    rg.anchor = GridBagConstraints.EAST;
    panelRojo.add(etiquetaLabelGrande("Cambio"), rg);
    rg.gridx = 1;
    rg.anchor = GridBagConstraints.WEST;
    lblCambio = new JLabel("$ 0.00");
    lblCambio.setFont(new Font("SansSerif", Font.BOLD, 48));
    lblCambio.setForeground(Color.BLACK);
    panelRojo.add(lblCambio, rg);

    rg.gridy = 9;
    rg.gridx = 0;
    rg.gridwidth = 2;
    panelRojo.add(Box.createVerticalStrut(30), rg);

    // Botón Cerrar
    rg.gridy = 10;
    rg.fill = GridBagConstraints.NONE;
    rg.anchor = GridBagConstraints.CENTER;
    panelRojo.add(crearBotonCerrar(), rg);
  }

  private JLabel etiquetaLabel(String texto) {
    JLabel l = new JLabel(texto);
    l.setFont(new Font("SansSerif", Font.BOLD, 32));
    l.setForeground(Color.WHITE);
    return l;
  }

  private JLabel etiquetaLabelGrande(String texto) {
    JLabel l = new JLabel(texto);
    l.setFont(new Font("SansSerif", Font.BOLD, 38));
    l.setForeground(Color.BLACK);
    return l;
  }

  private JLabel crearCampoValor(String valor) {
    JLabel campo = new JLabel(valor) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(230, 230, 230));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.setColor(new Color(160, 160, 160));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        g2.dispose();
        super.paintComponent(g);
      }
    };
    campo.setPreferredSize(new Dimension(280, 60));
    campo.setMinimumSize(new Dimension(280, 60));
    campo.setFont(new Font("SansSerif", Font.PLAIN, 32));
    campo.setForeground(new Color(30, 30, 30));
    campo.setBorder(new EmptyBorder(10, 14, 10, 14));
    campo.setOpaque(false);
    campo.setHorizontalAlignment(SwingConstants.CENTER);
    return campo;
  }

  private JTextField crearCampoEditable(String valor) {
    JTextField tf = new JTextField(valor) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.setColor(new Color(160, 160, 160));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        g2.dispose();
        super.paintComponent(g);
      }

      @Override
      protected void paintBorder(Graphics g) {
      }
    };
    tf.setPreferredSize(new Dimension(280, 60));
    tf.setMinimumSize(new Dimension(280, 60));
    tf.setFont(new Font("SansSerif", Font.PLAIN, 32));
    tf.setForeground(new Color(30, 30, 30));
    tf.setBackground(new Color(230, 230, 230));
    tf.setBorder(new EmptyBorder(10, 14, 10, 14));
    tf.setOpaque(false);
    tf.setHorizontalAlignment(JTextField.CENTER);
    return tf;
  }

  private JButton crearBotonCerrar() {
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

    b.addActionListener(e -> {
      // Si ya se procesó, solo cerrar
      if (ventaProcesada) {
        navegacion.volverAlMenu();
        return;
      }

      // Validar que se ingresó un pago
      String textoPago = tfPago.getText().trim().replace("$", "").trim();
      if (textoPago.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Ingrese el monto del pago",
            "Pago requerido",
            JOptionPane.WARNING_MESSAGE);
        tfPago.requestFocus();
        return;
      }

      try {
        BigDecimal pago = new BigDecimal(textoPago);
        BigDecimal total = venta.getTotal();

        // Validar que el pago sea suficiente
        if (pago.compareTo(total) < 0) {
          BigDecimal faltante = total.subtract(pago);
          JOptionPane.showMessageDialog(this,
              "Pago insuficiente. Faltan $" + faltante.toPlainString(),
              "Pago insuficiente",
              JOptionPane.ERROR_MESSAGE);
          tfPago.requestFocus();
          tfPago.selectAll();
          return;
        }

        // Pago válido: calcular cambio y mostrar
        BigDecimal cambio = servicio.calcularCambio(total, pago);
        lblCambio.setText("$ " + cambio.toPlainString());

        // Deshabilitar campo de pago
        tfPago.setEditable(false);
        tfPago.setBackground(new Color(200, 200, 200));

        // Cambiar botón a "Cerrar"
        b.setText("Cerrar");
        ventaProcesada = true;

        // TODO: Guardar venta en BD aquí

      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
            "Ingrese un monto válido",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        tfPago.requestFocus();
        tfPago.selectAll();
      }
    });

    return b;
  }

  private JPanel crearTituloPanel(String texto, String iconoArchivo) {
    JPanel header = new JPanel();
    header.setOpaque(false);
    header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

    ImageIcon icon = com.ferreteria.util.IconLoader.cargarIcono(iconoArchivo);
    JLabel icono = new JLabel(icon);

    JLabel titulo = new JLabel(texto);
    titulo.setFont(new Font("SansSerif", Font.BOLD, 48));
    titulo.setForeground(Color.BLACK);

    header.add(Box.createHorizontalGlue());
    header.add(icono);
    header.add(Box.createHorizontalStrut(16));
    header.add(titulo);
    header.add(Box.createHorizontalGlue());

    return header;
  }
}