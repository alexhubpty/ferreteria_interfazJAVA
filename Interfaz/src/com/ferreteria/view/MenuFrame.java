package com.ferreteria.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.ferreteria.controller.ControladorLogin;
import com.ferreteria.util.RoundedPanel;
import com.ferreteria.util.IconLoader;

public class MenuFrame extends JPanel {
    private RoundedPanel panelDerecho;
    private JPanel panelFormulario;
    private JButton botonCerrar;
    private JButton botonNVenta;
    private JButton botonNPago;
    private JButton botonIVentas;
    private JButton botonCrearCuenta;
    private JButton botonInventario;
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

    private void configurarEventos() {
        botonCerrar.addActionListener(e -> {
            if (controlador != null)
                controlador.cerrarAplicacion();
        });
        // Aquí conectas navegación a otras vistas cuando estén listas
    }

    private void initComponents() {
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(40, 60, 40, 60));
        add(inner, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // Panel izquierdo (35%): menú plano
        panelFormulario = crearPanelFormulario();
        gbc.gridx = 0;
        gbc.weightx = 0.20;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 40);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        inner.add(panelFormulario, gbc);

        // Panel derecho (65%): casco centrado
        panelDerecho = new RoundedPanel(40, new Color(229, 240, 250)); // fondo suave
        panelDerecho.setLayout(new BorderLayout());
        gbc.gridx = 1;
        gbc.weightx = 0.80;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        inner.add(panelDerecho, gbc);

        // Casco grande centrado + título
        JPanel rightCenter = new JPanel();
        rightCenter.setOpaque(false);
        rightCenter.setLayout(new BoxLayout(rightCenter, BoxLayout.Y_AXIS));

        JLabel casco = new JLabel();
        casco.setAlignmentX(Component.CENTER_ALIGNMENT);
        casco.setHorizontalAlignment(SwingConstants.CENTER);
        // casco.setIcon(IconLoader.cargarIcono("icono01.png")); // casco grande

        ImageIcon cascoBase = IconLoader.cargarIcono("icono01.png");
        if (cascoBase != null) {
            Image img = cascoBase.getImage().getScaledInstance(680, 680, Image.SCALE_SMOOTH);
            casco.setIcon(new ImageIcon(img));
        } else {
            casco.setIcon(null);
        }

        JLabel titulo = new JLabel("Ferreteria El Polaco", SwingConstants.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 42));
        titulo.setForeground(new Color(30, 30, 30));

        rightCenter.add(Box.createVerticalGlue());
        rightCenter.add(casco);
        rightCenter.add(Box.createVerticalStrut(30));
        rightCenter.add(titulo);
        rightCenter.add(Box.createVerticalGlue());

        panelDerecho.add(rightCenter, BorderLayout.CENTER);
    }

    private JPanel crearPanelFormulario() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BorderLayout());

        // Encabezado con botón Salir (redondeado y con icono05.png)
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        closePanel.setOpaque(false);
        closePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        botonCerrar = crearBotonSalir(); // único botón con fondo redondeado
        closePanel.add(botonCerrar);
        top.add(closePanel);
        top.add(Box.createVerticalStrut(50)); // ← AJUSTAR: espacio razonable entre Salir y los botones

        p.add(top, BorderLayout.NORTH);

        // Botones planos con iconos a la izquierda
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        // Iconos menú
        ImageIcon iconoVenta = IconLoader.cargarIcono("icono02.png");
        ImageIcon iconoNomina = IconLoader.cargarIcono("icono03.png");
        ImageIcon iconoInforme = IconLoader.cargarIcono("icono04.png");
        ImageIcon iconoCuenta = IconLoader.cargarIcono("icono06.png");
        ImageIcon iconoStock = IconLoader.cargarIcono("icono09.png");

        botonNVenta = crearBotonPlano("Nueva Venta", iconoVenta);
        botonNPago = crearBotonPlano("Nomina de Pago", iconoNomina);
        botonIVentas = crearBotonPlano("Informe de ventas", iconoInforme);
        botonCrearCuenta = crearBotonPlano("Crear Cuenta", iconoCuenta);
        botonInventario = crearBotonPlano("Inventario", iconoStock);

        form.add(botonNVenta);
        form.add(Box.createVerticalStrut(26));
        form.add(botonNPago);
        form.add(Box.createVerticalStrut(26));
        form.add(botonIVentas);
        form.add(Box.createVerticalStrut(26));
        form.add(botonCrearCuenta);
        form.add(Box.createVerticalStrut(26));
        form.add(botonInventario);

        // Añadir directamente sin GridBagLayout wrapper
        p.add(form, BorderLayout.CENTER); // ← CAMBIAR de NORTH a CENTER

        return p;
    }

    // Botón plano: sin fondo gris, solo texto + icono
    private JButton crearBotonPlano(String texto, ImageIcon icono) {
        JButton b = new JButton(texto, icono);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 6));
        b.setContentAreaFilled(false); // sin fondo rectángulo
        b.setOpaque(false); // transparente
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setIconTextGap(12);
        b.setFont(new Font("SansSerif", Font.PLAIN, 30));
        b.setForeground(new Color(30, 30, 30));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        // sutil hover: oscurece texto
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setForeground(new Color(0, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setForeground(new Color(30, 30, 30));
            }
        });
        return b;
    }

    // Único con fondo redondeado y flecha (normal: negro, hover/pressed: blanco)
    private JButton crearBotonSalir() {
        // final int iconSize = 22;
        ImageIcon iconNormal = IconLoader.cargarIcono("icono12.png"); // negro
        ImageIcon iconHover = IconLoader.cargarIcono("icono11.png"); // blanco

        JButton b = new JButton("Salir", iconNormal) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 48, 48);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
            }
        };

        // estilo
        b.setFont(new Font("SansSerif", Font.BOLD, 30));
        b.setForeground(new Color(50, 50, 50));
        b.setBackground(new Color(210, 210, 210));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setIconTextGap(8);
        b.setPreferredSize(new Dimension(180, 48));
        b.setMaximumSize(new Dimension(180, 48));

        // rollover/pressed
        b.setRolloverEnabled(true);
        if (iconHover != null) {
            b.setRolloverIcon(iconHover);
            b.setPressedIcon(iconHover);
            b.setSelectedIcon(iconHover);
        }

        // cambio de colores del botón (opcional)
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setForeground(Color.WHITE);
                b.setBackground(new Color(60, 60, 60));
                b.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setForeground(new Color(50, 50, 50));
                b.setBackground(new Color(210, 210, 210));
                b.repaint();
            }
        });
        return b;
    }
}