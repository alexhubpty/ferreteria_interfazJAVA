package com.ferreteria.view;

/**
 * ResumenFacturaFrame - Resumen de factura antes de confirmar
 * Panel rojo con subtotales y totales
 */
import java.awt.*;
import javax.swing.*;

import com.ferreteria.view.RoundedPanel;

public class ResumenFacturaFrame extends JPanel {
  private RoundedPanel panelRojo;
  private JLabel lblSubtotal;
  private JLabel lblITBMS;
  private JLabel lblTotal;
  private JLabel lblCambio;
  private JButton botonGuardar;

  public ResumenFacturaFrame() {
    setLayout(null);
    setOpaque(false);
    initComponents();
  }

  private void initComponents() {
    // Panel rojo similar al de Venta (450x520)
    panelRojo = new RoundedPanel(40, new Color(186, 30, 30));
    panelRojo.setLayout(null);
    panelRojo.setBounds(600, 100, 450, 520);

    // Título
    JLabel lblTitulo = new JLabel("Resumen de Factura", SwingConstants.CENTER);
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
    lblTitulo.setForeground(Color.WHITE);
    lblTitulo.setBounds(0, 30, 450, 35);
    panelRojo.add(lblTitulo);

    // Subtotal
    crearCampoResumen("Subtotal", 100, 1);
    lblSubtotal = crearValorResumen("$ 0.00", 100);

    // ITBMS 7%
    crearCampoResumen("ITBMS 7%", 160, 1);
    lblITBMS = crearValorResumen("$ 0.00", 160);

    // Total a Pagar
    JLabel lblTotalLabel = new JLabel("Total a Pagar", SwingConstants.CENTER);
    lblTotalLabel.setFont(new Font("Arial", Font.BOLD, 20));
    lblTotalLabel.setForeground(Color.WHITE);
    lblTotalLabel.setBounds(0, 240, 450, 30);
    panelRojo.add(lblTotalLabel);

    lblTotal = new JLabel("$ 0.00", SwingConstants.CENTER);
    lblTotal.setFont(new Font("Arial", Font.BOLD, 28));
    lblTotal.setForeground(Color.WHITE);
    lblTotal.setBounds(0, 275, 450, 40);
    panelRojo.add(lblTotal);

    // Pago
    crearCampoResumen("Pago", 340, 2);

    // Cambio
    JLabel lblCambioLabel = new JLabel("Cambio:", SwingConstants.LEFT);
    lblCambioLabel.setForeground(Color.WHITE);
    lblCambioLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    lblCambioLabel.setBounds(100, 390, 100, 25);
    panelRojo.add(lblCambioLabel);

    lblCambio = new JLabel("$ 0.00", SwingConstants.LEFT);
    lblCambio.setForeground(Color.WHITE);
    lblCambio.setFont(new Font("Arial", Font.BOLD, 18));
    lblCambio.setBounds(250, 390, 150, 25);
    panelRojo.add(lblCambio);

    // Botón Guardar
    botonGuardar = new JButton("Guardar");
    botonGuardar.setBounds(150, 440, 150, 40);
    botonGuardar.setBackground(new Color(40, 40, 40));
    botonGuardar.setForeground(Color.WHITE);
    botonGuardar.setFocusPainted(false);
    botonGuardar.setBorderPainted(false);
    panelRojo.add(botonGuardar);

    add(panelRojo);
  }

  private void crearCampoResumen(String texto, int y, int tipo) {
    JLabel lbl = new JLabel(texto + ":");
    lbl.setForeground(Color.WHITE);
    lbl.setFont(new Font("Arial", Font.PLAIN, 16));
    lbl.setBounds(100, y, 100, 25);
    panelRojo.add(lbl);

    if (tipo == 2) { // Campo editable para Pago
      JTextField campo = new JTextField("$ 0.00");
      campo.setBounds(250, y, 100, 30);
      panelRojo.add(campo);
    }
  }

  private JLabel crearValorResumen(String valor, int y) {
    JLabel lbl = new JLabel(valor, SwingConstants.LEFT);
    lbl.setForeground(Color.WHITE);
    lbl.setFont(new Font("Arial", Font.BOLD, 18));
    lbl.setBounds(250, y, 150, 25);
    panelRojo.add(lbl);
    return lbl;
  }
}