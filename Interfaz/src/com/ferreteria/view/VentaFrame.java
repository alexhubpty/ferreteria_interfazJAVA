package com.ferreteria.view;

/**
 * VentaFrame - Pantalla de nueva venta
 * Panel rojo más ancho a la derecha con campos de venta
 */
import java.awt.*;
import javax.swing.*;

// import com.ferreteria.util.ImagePanel;
import com.ferreteria.util.RoundedPanel;

public class VentaFrame extends JPanel {
  private RoundedPanel panelRojo;
  // private JTextField campoCodigoProducto;
  private JComboBox<String> comboBloques;
  private JTextField campoNumeroBloques;
  private JTextField campoTamano;
  private JTextField campoPrecio;
  private JButton botonAgregar;

  public VentaFrame() {
    setLayout(null);
    setOpaque(false);
    initComponents();
  }

  private void initComponents() {
    // Panel rojo más ancho y desplazado a la derecha (450x520)
    panelRojo = new RoundedPanel(40, new Color(186, 30, 30));
    panelRojo.setLayout(null);
    panelRojo.setBounds(600, 100, 450, 520);

    // Título
    JLabel lblTitulo = new JLabel("Nueva Venta", SwingConstants.CENTER);
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
    lblTitulo.setForeground(Color.WHITE);
    lblTitulo.setBounds(0, 30, 450, 35);
    panelRojo.add(lblTitulo);

    // Usuario Central - Precio
    JLabel lblUsuario = new JLabel("Usuario Central", SwingConstants.CENTER);
    lblUsuario.setForeground(Color.WHITE);
    lblUsuario.setBounds(150, 75, 150, 20);
    panelRojo.add(lblUsuario);

    // Bloques o Pago (ComboBox)
    JLabel lblBloques = new JLabel("Bloques o Pago #:");
    lblBloques.setForeground(Color.WHITE);
    lblBloques.setBounds(50, 110, 120, 25);
    panelRojo.add(lblBloques);

    comboBloques = new JComboBox<>(new String[] { "Precio", "Bloques" });
    comboBloques.setBounds(170, 110, 230, 30);
    panelRojo.add(comboBloques);

    // Número de Bloques
    JLabel lblNumBloques = new JLabel("Bloques #:");
    lblNumBloques.setForeground(Color.WHITE);
    lblNumBloques.setBounds(50, 160, 100, 25);
    panelRojo.add(lblNumBloques);

    campoNumeroBloques = new JTextField();
    campoNumeroBloques.setBounds(170, 160, 230, 30);
    panelRojo.add(campoNumeroBloques);

    // Tamaño
    JLabel lblTamano = new JLabel("Tamaño:");
    lblTamano.setForeground(Color.WHITE);
    lblTamano.setBounds(50, 210, 100, 25);
    panelRojo.add(lblTamano);

    campoTamano = new JTextField();
    campoTamano.setBounds(170, 210, 230, 30);
    panelRojo.add(campoTamano);

    // Total a Pagar
    JLabel lblTotal = new JLabel("Total a Pagar", SwingConstants.CENTER);
    lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
    lblTotal.setForeground(Color.WHITE);
    lblTotal.setBounds(0, 280, 450, 30);
    panelRojo.add(lblTotal);

    // Precio
    JLabel lblPrecio = new JLabel("Precio:");
    lblPrecio.setForeground(Color.WHITE);
    lblPrecio.setBounds(50, 330, 100, 25);
    panelRojo.add(lblPrecio);

    campoPrecio = new JTextField("$ 0.00");
    campoPrecio.setBounds(170, 330, 230, 30);
    campoPrecio.setEditable(false);
    campoPrecio.setBackground(Color.WHITE);
    panelRojo.add(campoPrecio);

    // Botón Agregar
    botonAgregar = new JButton("Agregar");
    botonAgregar.setBounds(150, 410, 150, 40);
    botonAgregar.setBackground(new Color(40, 40, 40));
    botonAgregar.setForeground(Color.WHITE);
    botonAgregar.setFocusPainted(false);
    botonAgregar.setBorderPainted(false);
    panelRojo.add(botonAgregar);

    add(panelRojo);
  }
}