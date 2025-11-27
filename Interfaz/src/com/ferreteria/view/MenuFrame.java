package com.ferreteria.view;

/**
 * VentaFrame - Pantalla de nueva venta
 * Panel rojo más ancho a la derecha con campos de venta
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.ferreteria.controller.ControladorLogin;

// import org.w3c.dom.events.MouseEvent;

// import com.ferreteria.util.ImagePanel;
import com.ferreteria.util.RoundedPanel;

public class MenuFrame extends JPanel {
    private RoundedPanel panelRojo;

    private JPanel panelFormulario;
    private JButton botonCerrar;
    // private JTextField campoCodigoProducto;
    private JComboBox<String> comboBloques;
    private JTextField campoNumeroBloques;
    private JTextField campoTamano;
    private JTextField campoPrecio;
    private JButton botonAgregar;
    private ControladorLogin controlador;

    public MenuFrame() {
        setLayout(new BorderLayout());
        setOpaque(false);
        initComponents();
    }

    public void setControlador(ControladorLogin controlador) {
        this.controlador = controlador;
        configurarEventos();
    }

    // NUEVO: Configurar eventos de botones
    private void configurarEventos() {
        // botonAcceder.addActionListener(e -> {
        // if (controlador != null) {
        // controlador.intentarLogin();
        // }
        // });

        botonCerrar.addActionListener(e -> {
            if (controlador != null) {
                controlador.cerrarAplicacion();
            }
        });

        botonNVenta.addActionListener(e -> {
            if (controlador != null) {
                controlador.cerrarAplicacion();
            }
        });

        botonNPago.addActionListener(e -> {
            if (controlador != null) {
                controlador.cerrarAplicacion();
            }
        });

        botonIVentas.addActionListener(e -> {
            if (controlador != null) {
                controlador.cerrarAplicacion();
            }
        });

        // Enter en password ejecuta login
        // campoContrasena.addActionListener(e -> {
        // if (controlador != null) {
        // controlador.intentarLogin();
        // }
        // });
    }

    private void initComponents() {

        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(50, 70, 50, 70));
        add(inner, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // PANEL FORMULARIO (IZQUIERDA) - 35% del ancho
        panelFormulario = crearPanelFormulario();
        gbc.gridx = 0;
        gbc.weightx = 0.35;
        gbc.insets = new Insets(0, 0, 0, 50);
        inner.add(panelFormulario, gbc);

        // PANEL ROJO (DERECHA) - 65% del ancho
        panelRojo = new RoundedPanel(40, new Color(245, 240, 250));
        panelRojo.setLayout(null);
        gbc.gridx = 1;
        gbc.weightx = 0.65;
        gbc.insets = new Insets(0, 50, 0, 0);
        inner.add(panelRojo, gbc);

    }

    private JPanel crearPanelFormulario() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BorderLayout());

        // Encabezado
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        // Botón cerrar
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        closePanel.setOpaque(false);
        closePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        botonCerrar = crearBotonCircularCerrar();
        closePanel.add(botonCerrar);
        top.add(closePanel);
        top.add(Box.createVerticalStrut(100));

        // Botones para cambiar de vistas # falta crear los botones
        JLabel lblVenta = new JLabel("Nueva Venta", SwingConstants.LEFT);
        lblVenta.setFont(new Font("SansSerif", Font.BOLD, 35));
        lblVenta.setForeground(new Color(20, 20, 20));
        lblVenta.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblVenta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        top.add(lblVenta);
        top.add(Box.createVerticalStrut(50));

        JLabel lblNomina = new JLabel("Nomina de Pago", SwingConstants.LEFT);
        lblNomina.setFont(new Font("SansSerif", Font.BOLD, 35));
        lblNomina.setForeground(new Color(20, 20, 20));
        lblNomina.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNomina.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        top.add(lblNomina);
        top.add(Box.createVerticalStrut(50));

        JLabel lblInforme = new JLabel("Informe de Ventas", SwingConstants.LEFT);
        lblInforme.setFont(new Font("SansSerif", Font.BOLD, 35));
        lblInforme.setForeground(new Color(20, 20, 20));
        lblInforme.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblInforme.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        top.add(lblInforme);
        top.add(Box.createVerticalStrut(50));

        // JLabel lblCrearCuenta = new JLabel("Crear Cuenta", SwingConstants.LEFT);
        // lblCrearCuenta.setFont(new Font("SansSerif", Font.BOLD, 35));
        // lblCrearCuenta.setForeground(new Color(20, 20, 20));
        // lblCrearCuenta.setAlignmentX(Component.LEFT_ALIGNMENT);
        // lblCrearCuenta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        // top.add(lblCrearCuenta);
        // top.add(Box.createVerticalStrut(50));

        // JLabel lblInventario = new JLabel("Inventario", SwingConstants.LEFT);
        // lblInventario.setFont(new Font("SansSerif", Font.BOLD, 35));
        // lblInventario.setForeground(new Color(20, 20, 20));
        // lblInventario.setAlignmentX(Component.LEFT_ALIGNMENT);
        // lblInventario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        // top.add(lblInventario);
        // top.add(Box.createVerticalStrut(50));

        p.add(top, BorderLayout.NORTH);

        // Formulario - alineado a la izquierda
        JPanel formWrapper = new JPanel();
        formWrapper.setOpaque(false);
        formWrapper.setLayout(new GridBagLayout());

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(40, 0, 0, 0);

        for (Component c : form.getComponents()) {
            if (c instanceof JComponent jc) {
                jc.setAlignmentX(Component.LEFT_ALIGNMENT);

                Dimension d = jc.getPreferredSize();
                jc.setMaximumSize(new Dimension(Integer.MAX_VALUE, d.height));
            }
        }

        formWrapper.add(form, gbc);
        p.add(formWrapper, BorderLayout.CENTER);
        return p;
    }

    private JButton crearBotonCircularCerrar() {
        JButton b = new JButton("'Icono<----' Salir") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("SansSerif", Font.BOLD, 40));
        b.setForeground(new Color(70, 70, 70));
        b.setBackground(new Color(210, 210, 210));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setContentAreaFilled(false);
        b.setPreferredSize(new Dimension(240, 48));
        b.setMaximumSize(new Dimension(240, 48));

        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setForeground(Color.WHITE);
                b.setBackground(new Color(60, 60, 60));
                b.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setForeground(new Color(70, 70, 70));
                b.setBackground(new Color(210, 210, 210));
                b.repaint();
            }
        });

        b.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(b.getBackground());
                g2.fillOval(0, 0, c.getWidth(), c.getHeight());
                g2.setColor(b.getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (c.getWidth() - fm.stringWidth(b.getText())) / 2;
                int y = (c.getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(b.getText(), x, y);
                g2.dispose();
            }
        });
        return b;
    }

}