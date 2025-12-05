package com.ferreteria.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.ferreteria.controller.ControladorLogin;
import com.ferreteria.controller.ControladorNavegacion;
import com.ferreteria.controller.BeforeLeave;
import com.ferreteria.util.RoundedPanel;
import com.ferreteria.util.IconLoader;

public class MenuFrame extends JPanel {
    private RoundedPanel panelDerecho; // Este panel cambia de contenido
    private JPanel panelFormulario; // Este panel se mantiene fijo (menú izquierdo)
    private JButton botonAccion; // Botón flexible (Salir/Atrás/Nuevo)
    private JButton botonNVenta;
    private JButton botonNPago;
    private JButton botonIVentas;
    private JButton botonCrearCuenta;
    private JButton botonInventario;

    private ControladorLogin controlador;
    private ControladorNavegacion controladorNav;

    private JPanel panelCascoInicial; // Guardamos el panel inicial del casco

    public MenuFrame() {
        setLayout(new BorderLayout());
        setOpaque(false);
        this.controladorNav = new ControladorNavegacion(this);
        initComponents();
    }

    public void setControlador(ControladorLogin controlador) {
        this.controlador = controlador;
        configurarEventos();
    }

    public ControladorNavegacion getControladorNavegacion() {
        return controladorNav;
    }

    private void configurarEventos() {
        // Por defecto, el botón Salir cierra la app (cuando estamos en menú principal)
        botonAccion.addActionListener(e -> {
            if (controlador != null) {
                controlador.cerrarAplicacion();
            }
        });

        // Botones de navegación
        botonNVenta.addActionListener(e -> abrirVentaFrame());
        botonNPago.addActionListener(e -> abrirNominaFrame());
        botonIVentas.addActionListener(e -> abrirInformeFrame());
        botonCrearCuenta.addActionListener(e -> abrirCrearCuentaFrame());
        botonInventario.addActionListener(e -> abrirInventarioFrame());

        botonAccion.addActionListener(e -> {
            var vista = controladorNav.getVistaActual();
            if (vista instanceof com.ferreteria.controller.BeforeLeave b && b.onBeforeLeave()) {
                return; // bloquea navegación en el primer clic si hay datos
            }
            controladorNav.volverAlMenu();
        });
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

        // Panel izquierdo (FIJO): menú con botones
        panelFormulario = crearPanelFormulario();
        gbc.gridx = 0;
        gbc.weightx = 0.20;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 40);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        inner.add(panelFormulario, gbc);

        // Panel derecho (DINÁMICO): cambia de contenido
        panelDerecho = new RoundedPanel(40, new Color(229, 240, 250));
        panelDerecho.setLayout(new GridBagLayout()); // ← CAMBIO: permite posicionamiento flexible
        gbc.gridx = 1;
        gbc.weightx = 0.80;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        inner.add(panelDerecho, gbc);

        // Inicialmente muestra el casco
        panelCascoInicial = crearPanelCasco();
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.anchor = GridBagConstraints.CENTER;
        innerGbc.fill = GridBagConstraints.NONE; // ← No expandir
        panelDerecho.add(panelCascoInicial, innerGbc);
    }

    /**
     * Panel del menú izquierdo (FIJO)
     */
    private JPanel crearPanelFormulario() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BorderLayout());

        // Botón de acción arriba (Salir/Atrás/Nuevo)
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        closePanel.setOpaque(false);
        closePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        botonAccion = crearBotonAccion("Salir", "icono12.png", "icono11.png");
        closePanel.add(botonAccion);
        top.add(closePanel);
        top.add(Box.createVerticalStrut(50));

        p.add(top, BorderLayout.NORTH);

        // Botones de navegación
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

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

        p.add(form, BorderLayout.CENTER);

        return p;
    }

    /**
     * Panel del casco (vista inicial del panel derecho)
     */
    private JPanel crearPanelCasco() {
        JPanel rightCenter = new JPanel();
        rightCenter.setOpaque(false);
        rightCenter.setLayout(new BoxLayout(rightCenter, BoxLayout.Y_AXIS));

        JLabel casco = new JLabel();
        casco.setAlignmentX(Component.CENTER_ALIGNMENT);
        casco.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon cascoBase = IconLoader.cargarIcono("icono01.png");
        if (cascoBase != null) {
            Image img = cascoBase.getImage().getScaledInstance(680, 680, Image.SCALE_SMOOTH);
            casco.setIcon(new ImageIcon(img));
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

        return rightCenter;
    }

    /**
     * Botón plano (para el menú)
     */
    private JButton crearBotonPlano(String texto, ImageIcon icono) {
        JButton b = new JButton(texto, icono);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 6));
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setIconTextGap(12);
        b.setFont(new Font("SansSerif", Font.PLAIN, 30));
        b.setForeground(new Color(30, 30, 30));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);

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

    /**
     * Botón de acción (Salir/Atrás/Nuevo) - texto e íconos cambian dinámicamente
     */
    private JButton crearBotonAccion(String texto, String iconoNormalPath, String iconoHoverPath) {
        ImageIcon iconNormal = IconLoader.cargarIcono(iconoNormalPath);
        ImageIcon iconHover = IconLoader.cargarIcono(iconoHoverPath);

        JButton b = new JButton(texto, iconNormal) {
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

        b.setFont(new Font("SansSerif", Font.BOLD, 30));
        b.setForeground(new Color(50, 50, 50));
        b.setBackground(new Color(210, 210, 210));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setIconTextGap(8);
        b.setPreferredSize(new Dimension(200, 48));
        b.setMaximumSize(new Dimension(200, 48));

        b.setRolloverEnabled(true);
        if (iconHover != null) {
            b.setRolloverIcon(iconHover);
            b.setPressedIcon(iconHover);
            b.setSelectedIcon(iconHover);
        }

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

    // ========== NAVEGACIÓN ==========

    /**
     * Cambia el contenido del panel derecho
     */
    public void cambiarContenidoDerecho(JPanel nuevaVista) {
        panelDerecho.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // ocupa el ancho disponible
        gbc.weighty = 1.0; // ocupa el alto disponible
        gbc.fill = GridBagConstraints.NONE; // la vista mantiene su tamaño preferido
        gbc.anchor = GridBagConstraints.EAST; // ← ancla al borde derecho
        gbc.insets = new Insets(0, 0, 0, 20); // ← reduce margen derecho (antes 50)

        panelDerecho.add(nuevaVista, gbc);
        panelDerecho.revalidate();
        panelDerecho.repaint();
    }

    /**
     * Vuelve a mostrar el casco (menú principal)
     */
    public void volverAlMenuPrincipal() {
        panelDerecho.removeAll();

        // Constraints específicos para el casco (CENTRADO, sin margen)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE; // No expandir
        gbc.anchor = GridBagConstraints.CENTER; // CENTRADO (≠ EAST)
        gbc.insets = new Insets(0, 0, 0, 0); // SIN margen derecho

        panelDerecho.add(panelCascoInicial, gbc);
        panelDerecho.revalidate();
        panelDerecho.repaint();

        cambiarBotonAccion("Salir", "icono12.png", "icono11.png", e -> {
            if (controlador != null) {
                controlador.cerrarAplicacion();
            }
        });
    }

    /**
     * Cambia el texto, íconos y acción del botón de acción
     */
    public void cambiarBotonAccion(String texto, String iconoNormal, String iconoHover, ActionListener accion) {
        botonAccion.setText(texto);
        botonAccion.setIcon(IconLoader.cargarIcono(iconoNormal));
        botonAccion.setRolloverIcon(IconLoader.cargarIcono(iconoHover));
        botonAccion.setPressedIcon(IconLoader.cargarIcono(iconoHover));

        // Remover listeners anteriores
        for (ActionListener al : botonAccion.getActionListeners()) {
            botonAccion.removeActionListener(al);
        }

        // Añadir nuevo listener
        if (accion != null) {
            botonAccion.addActionListener(accion);
        }
    }

    /**
     * Permite cambiar el botón de acción desde vistas hijas
     */
    public void actualizarBotonAccion(String texto, String iconoNormal, String iconoHover, ActionListener accion) {
        cambiarBotonAccion(texto, iconoNormal, iconoHover, accion);
    }

    // ========== MÉTODOS DE NAVEGACIÓN A CADA VISTA ==========

    private void abrirVentaFrame() {
        VentaFrame venta = new VentaFrame(controladorNav);
        controladorNav.navegarA(venta);

        // Configurar botón con lógica de doble clic
        cambiarBotonAccion("Atrás", "icono12.png", "icono11.png", e -> {
            JPanel vista = controladorNav.getVistaActual();
            if (vista instanceof BeforeLeave bl && bl.onBeforeLeave()) {
                return; // Primer clic: bloqueado, solo limpia
            }
            controladorNav.volverAlMenu(); // Segundo clic: salir
        });
    }

    private void abrirNominaFrame() {
        NominaFrame nomina = new NominaFrame(controladorNav);
        cambiarContenidoDerecho(nomina);
        cambiarBotonAccion("Atrás", "icono12.png", "icono11.png", e -> volverAlMenuPrincipal());
    }

    private void abrirInformeFrame() {
        InformeFrame informe = new InformeFrame(controladorNav);
        cambiarContenidoDerecho(informe);
        cambiarBotonAccion("Atrás", "icono12.png", "icono11.png", e -> volverAlMenuPrincipal());
    }

    private void abrirCrearCuentaFrame() {
        CrearCuentaFrame cuenta = new CrearCuentaFrame(controladorNav);
        cambiarContenidoDerecho(cuenta);
        cambiarBotonAccion("Atrás", "icono12.png", "icono11.png", e -> volverAlMenuPrincipal());
    }

    private void abrirInventarioFrame() {
        InventarioFrame inventario = new InventarioFrame(controladorNav);
        cambiarContenidoDerecho(inventario);
        cambiarBotonAccion("Atrás", "icono12.png", "icono11.png", e -> volverAlMenuPrincipal());
    }

    // Getter para el botón de acción
    public JButton getBotonAccion() {
        return botonAccion;
    }
}