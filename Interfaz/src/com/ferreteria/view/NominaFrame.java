package com.ferreteria.view;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.ferreteria.controller.BeforeLeave;
import com.ferreteria.controller.ControladorNavegacion;
import com.ferreteria.controller.NominaController;
import com.ferreteria.model.Pago;
import com.ferreteria.model.Trabajador;
import com.ferreteria.util.RoundedPanel;

public class NominaFrame extends JPanel implements BeforeLeave {
    private ControladorNavegacion navegacion;
    private NominaController controlador = new NominaController();

    // Campos editables
    private JTextField tfNombre;
    private JTextField tfHoras;
    private JTextField tfSalarioHora;

    // Campos calculados (solo lectura)
    private JLabel lblBruto;
    private JLabel lblSeguroSocial;
    private JLabel lblSeguroEducativo;
    private JLabel lblNeto;

    // Historial: 8 filas × 3 columnas
    private final List<JLabel> historialBruto = new ArrayList<>();
    private final List<JLabel> historialDeducciones = new ArrayList<>();
    private final List<JLabel> historialNeto = new ArrayList<>();

    // Paginación
    private JButton btnPrev;
    private JButton btnNext;

    // Timer para búsqueda con delay
    private Timer timerBusqueda;

    // Bandera para doble clic en salir
    private boolean salidaArmada = false;

    public NominaFrame(ControladorNavegacion navegacion) {
        this.navegacion = navegacion;
        setLayout(new BorderLayout());
        setOpaque(false);
        initComponents();
        configurarEventos();
    }

    @Override
    public boolean onBeforeLeave() {
        if (!salidaArmada && hayDatos()) {
            limpiarCampos();
            salidaArmada = true;
            return true;
        }
        return false;
    }

    private boolean hayDatos() {
        return !tfNombre.getText().trim().isEmpty()
                || !tfHoras.getText().trim().isEmpty()
                || !tfSalarioHora.getText().trim().isEmpty();
    }

    private void limpiarCampos() {
        tfHoras.setText("");
        tfSalarioHora.setText("");
        lblBruto.setText("");
        lblSeguroSocial.setText("");
        lblSeguroEducativo.setText("");
        lblNeto.setText("");
        // NO limpiar tfNombre para mantener el historial visible
    }

    private void limpiarTodo() {
        tfNombre.setText("");
        tfHoras.setText("");
        tfSalarioHora.setText("");
        lblBruto.setText("");
        lblSeguroSocial.setText("");
        lblSeguroEducativo.setText("");
        lblNeto.setText("");
        limpiarHistorial();
    }

    private void initComponents() {
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(container, BorderLayout.CENTER);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        container.add(center, BorderLayout.CENTER);

        JPanel hBox = new JPanel();
        hBox.setOpaque(false);
        hBox.setLayout(new BoxLayout(hBox, BoxLayout.X_AXIS));

        // Panel Nómina de Pago
        RoundedPanel panelNomina = new RoundedPanel(84, new Color(186, 32, 36));
        panelNomina.setOpaque(true);
        panelNomina.setLayout(new GridBagLayout());
        panelNomina.setBorder(new EmptyBorder(20, 10, 20, 10));
        hBox.add(panelNomina);

        hBox.add(Box.createHorizontalStrut(20));

        // Panel Historial de Pagos
        RoundedPanel panelHistorial = new RoundedPanel(84, new Color(186, 32, 36));
        panelHistorial.setOpaque(true);
        panelHistorial.setLayout(new GridBagLayout());
        panelHistorial.setBorder(new EmptyBorder(20, 10, 20, 10));
        hBox.add(panelHistorial);

        GridBagConstraints alignRight = new GridBagConstraints();
        alignRight.gridx = 0;
        alignRight.gridy = 0;
        alignRight.weightx = 1.0;
        alignRight.weighty = 1.0;
        alignRight.fill = GridBagConstraints.BOTH;
        alignRight.anchor = GridBagConstraints.EAST;
        center.add(hBox, alignRight);

        // ===== Panel Nómina de Pago =====
        GridBagConstraints g1 = new GridBagConstraints();
        g1.insets = new Insets(14, 16, 14, 16);
        g1.fill = GridBagConstraints.HORIZONTAL;
        g1.anchor = GridBagConstraints.CENTER;
        g1.weightx = 1.0;
        g1.weighty = 0.0;

        g1.gridx = 0;
        g1.gridy = 0;
        g1.gridwidth = 2;
        panelNomina.add(crearTituloPanel("Nómina de Pago", "icono03.png"), g1);

        g1.gridy = 1;
        panelNomina.add(Box.createVerticalStrut(20), g1);

        g1.gridwidth = 1;
        g1.anchor = GridBagConstraints.WEST;

        // Campos editables
        tfNombre = crearCampoEditable("", 280);
        agregarFilaCampo(panelNomina, g1, 2, "Nombre del Trabajador", tfNombre);

        tfHoras = crearCampoEditable("", 200);
        agregarFilaCampo(panelNomina, g1, 3, "Horas Trabajadas", tfHoras);

        tfSalarioHora = crearCampoEditable("", 240);
        agregarFilaCampo(panelNomina, g1, 4, "Salario por Hora", tfSalarioHora);

        // Campos calculados
        lblBruto = crearCampoDisplay("", 240);
        agregarFilaCampo(panelNomina, g1, 5, "Salario Bruto", lblBruto);

        lblSeguroSocial = crearCampoDisplay("", 240);
        agregarFilaCampo(panelNomina, g1, 6, "Seguro Social 9.75%", lblSeguroSocial);

        lblSeguroEducativo = crearCampoDisplay("", 240);
        agregarFilaCampo(panelNomina, g1, 7, "S. Educativo 1.25%", lblSeguroEducativo);

        lblNeto = crearCampoDisplay("", 240);
        agregarFilaCampo(panelNomina, g1, 8, "Salario Neto", lblNeto);

        g1.gridy = 9;
        g1.gridx = 0;
        g1.gridwidth = 2;
        g1.weighty = 1.0;
        g1.fill = GridBagConstraints.VERTICAL;
        panelNomina.add(Box.createVerticalGlue(), g1);

        g1.gridy = 10;
        g1.weighty = 0.0;
        g1.fill = GridBagConstraints.NONE;
        g1.anchor = GridBagConstraints.CENTER;
        panelNomina.add(crearBotonProcesar(), g1);

        // ===== Panel Historial de Pagos =====
        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(10, 10, 10, 10);
        g2.fill = GridBagConstraints.HORIZONTAL;
        g2.anchor = GridBagConstraints.CENTER;
        g2.weightx = 1.0;
        g2.weighty = 0.0;

        g2.gridx = 0;
        g2.gridy = 0;
        g2.gridwidth = 3;
        panelHistorial.add(crearTituloSoloTexto("Historial de Pagos"), g2);

        g2.gridy = 1;
        panelHistorial.add(Box.createVerticalStrut(20), g2);

        g2.gridy = 2;
        g2.gridwidth = 1;
        g2.anchor = GridBagConstraints.WEST;
        g2.gridx = 0;
        panelHistorial.add(etiquetaHeader("Bruto", Color.WHITE), g2);
        g2.gridx = 1;
        panelHistorial.add(etiquetaHeader("Deducciones", Color.WHITE), g2);
        g2.gridx = 2;
        panelHistorial.add(etiquetaHeader("Neto", Color.WHITE), g2);

        // 8 filas de historial
        int startRow = 3;
        for (int i = 0; i < 8; i++) {
            g2.gridy = startRow + i;

            JLabel chipBruto = crearChipValor("");
            JLabel chipDeduc = crearChipValor("");
            JLabel chipNeto = crearChipValor("");

            historialBruto.add(chipBruto);
            historialDeducciones.add(chipDeduc);
            historialNeto.add(chipNeto);

            g2.gridx = 0;
            panelHistorial.add(chipBruto, g2);
            g2.gridx = 1;
            panelHistorial.add(chipDeduc, g2);
            g2.gridx = 2;
            panelHistorial.add(chipNeto, g2);
        }

        g2.gridy = startRow + 8;
        g2.gridx = 0;
        g2.gridwidth = 3;
        g2.weighty = 1.0;
        g2.fill = GridBagConstraints.VERTICAL;
        panelHistorial.add(Box.createVerticalGlue(), g2);

        g2.gridy = startRow + 9;
        g2.weighty = 0.0;
        g2.fill = GridBagConstraints.NONE;
        g2.anchor = GridBagConstraints.CENTER;
        panelHistorial.add(crearPaginador(), g2);
    }

    private void configurarEventos() {
        // Timer para buscar trabajador 1 segundo después de escribir
        timerBusqueda = new Timer(1000, e -> {
            buscarYCargarHistorial();
        });
        timerBusqueda.setRepeats(false);

        // Listener del campo nombre
        tfNombre.getDocument().addDocumentListener(new DocumentListener() {
            private void reiniciarTimer() {
                salidaArmada = false;
                timerBusqueda.restart();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                reiniciarTimer();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                reiniciarTimer();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                reiniciarTimer();
            }
        });

        // Listener para horas y salario (desarmar salida)
        DocumentListener desarmarSalida = new DocumentListener() {
            private void desarmar() {
                salidaArmada = false;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                desarmar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                desarmar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                desarmar();
            }
        };
        tfHoras.getDocument().addDocumentListener(desarmarSalida);
        tfSalarioHora.getDocument().addDocumentListener(desarmarSalida);
    }

    private void buscarYCargarHistorial() {
        String nombre = tfNombre.getText().trim();
        if (nombre.isEmpty()) {
            limpiarHistorial();
            return;
        }

        Trabajador trabajador = controlador.buscarTrabajador(nombre);
        if (trabajador != null) {
            cargarHistorial();
        } else {
            limpiarHistorial();
        }
    }

    private void cargarHistorial() {
        List<Pago> pagos = controlador.obtenerHistorial();

        for (int i = 0; i < 8; i++) {
            if (i < pagos.size()) {
                Pago p = pagos.get(i);
                BigDecimal deducciones = p.getSeguroSocial().add(p.getSeguroEducativo());
                historialBruto.get(i).setText("$ " + p.getBruto().toPlainString());
                historialDeducciones.get(i).setText("$ " + deducciones.toPlainString());
                historialNeto.get(i).setText("$ " + p.getNeto().toPlainString());
            } else {
                historialBruto.get(i).setText("");
                historialDeducciones.get(i).setText("");
                historialNeto.get(i).setText("");
            }
        }

        // Actualizar estado de botones de paginación
        actualizarBotonesPaginacion();
    }

    private void limpiarHistorial() {
        for (int i = 0; i < 8; i++) {
            historialBruto.get(i).setText("");
            historialDeducciones.get(i).setText("");
            historialNeto.get(i).setText("");
        }
    }

    private void actualizarBotonesPaginacion() {
        btnPrev.setEnabled(controlador.getPaginaActual() > 0);
        btnNext.setEnabled(controlador.getPaginaActual() < controlador.getTotalPaginas() - 1);
    }

    // ===== Helpers de UI =====

    private JPanel crearTituloPanel(String texto, String iconoArchivo) {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        ImageIcon icon = com.ferreteria.util.IconLoader.cargarIcono(iconoArchivo);
        JLabel icono = new JLabel(icon);

        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 42));
        titulo.setForeground(Color.BLACK);

        header.add(Box.createHorizontalGlue());
        header.add(icono);
        header.add(Box.createHorizontalStrut(14));
        header.add(titulo);
        header.add(Box.createHorizontalGlue());
        return header;
    }

    private JPanel crearTituloSoloTexto(String texto) {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 42));
        titulo.setForeground(Color.BLACK);
        header.add(Box.createHorizontalGlue());
        header.add(titulo);
        header.add(Box.createHorizontalGlue());
        return header;
    }

    private void agregarFilaCampo(JPanel panel, GridBagConstraints g, int row, String etiqueta, JComponent campo) {
        g.gridy = row;
        g.gridx = 0;
        g.gridwidth = 1;
        g.anchor = GridBagConstraints.WEST;
        JLabel l = new JLabel(etiqueta);
        l.setFont(new Font("SansSerif", Font.BOLD, 28));
        l.setForeground(Color.WHITE);
        panel.add(l, g);

        g.gridx = 1;
        g.anchor = GridBagConstraints.EAST;
        panel.add(campo, g);
    }

    private JLabel etiquetaHeader(String txt, Color color) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("SansSerif", Font.BOLD, 28));
        l.setForeground(color);
        return l;
    }

    private JTextField crearCampoEditable(String texto, int width) {
        JTextField campo = new JTextField(texto) {
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
        campo.setOpaque(false);
        campo.setPreferredSize(new Dimension(width, 50));
        campo.setMinimumSize(new Dimension(width, 50));
        campo.setFont(new Font("SansSerif", Font.PLAIN, 24));
        campo.setForeground(new Color(30, 30, 30));
        campo.setBackground(new Color(230, 230, 230));
        campo.setBorder(new EmptyBorder(8, 14, 8, 14));
        campo.setHorizontalAlignment(JTextField.CENTER);
        return campo;
    }

    private JLabel crearCampoDisplay(String texto, int width) {
        JLabel campo = new JLabel(texto, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(200, 200, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        campo.setOpaque(false);
        campo.setPreferredSize(new Dimension(width, 50));
        campo.setMinimumSize(new Dimension(width, 50));
        campo.setFont(new Font("SansSerif", Font.BOLD, 24));
        campo.setForeground(new Color(30, 30, 30));
        campo.setBorder(new EmptyBorder(8, 14, 8, 14));
        return campo;
    }

    private JLabel crearChipValor(String texto) {
        JLabel chip = new JLabel(texto, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(230, 230, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        chip.setOpaque(false);
        chip.setPreferredSize(new Dimension(130, 44));
        chip.setMinimumSize(new Dimension(130, 44));
        chip.setFont(new Font("SansSerif", Font.PLAIN, 18));
        chip.setForeground(new Color(30, 30, 30));
        chip.setBorder(new EmptyBorder(6, 10, 6, 10));
        return chip;
    }

    private JPanel crearPaginador() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        btnPrev = crearBotonPaginacion("<");
        btnNext = crearBotonPaginacion(">");

        btnPrev.setEnabled(false);
        btnNext.setEnabled(false);

        btnPrev.addActionListener(e -> {
            if (controlador.paginaAnterior()) {
                cargarHistorial();
            }
        });

        btnNext.addActionListener(e -> {
            if (controlador.paginaSiguiente()) {
                cargarHistorial();
            }
        });

        p.add(Box.createHorizontalGlue());
        p.add(btnPrev);
        p.add(Box.createHorizontalStrut(16));
        p.add(btnNext);
        p.add(Box.createHorizontalGlue());
        return p;
    }

    private JButton crearBotonPaginacion(String texto) {
        JButton b = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isEnabled() ? getBackground() : new Color(180, 180, 180));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
            }
        };
        b.setFont(new Font("SansSerif", Font.BOLD, 22));
        b.setForeground(Color.BLACK);
        b.setBackground(new Color(230, 230, 230));
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
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

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(new Color(90, 120, 90));
                b.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(new Color(50, 50, 50));
                b.repaint();
            }
        });

        b.addActionListener(e -> procesarNomina());

        return b;
    }

    private void procesarNomina() {
        String nombre = tfNombre.getText().trim();
        String horasStr = tfHoras.getText().trim();
        String salarioStr = tfSalarioHora.getText().trim().replace("$", "").trim();

        // Validaciones
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del trabajador", "Error", JOptionPane.ERROR_MESSAGE);
            tfNombre.requestFocus();
            return;
        }

        if (horasStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese las horas trabajadas", "Error", JOptionPane.ERROR_MESSAGE);
            tfHoras.requestFocus();
            return;
        }

        if (salarioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el salario por hora", "Error", JOptionPane.ERROR_MESSAGE);
            tfSalarioHora.requestFocus();
            return;
        }

        try {
            BigDecimal horas = new BigDecimal(horasStr);
            BigDecimal salarioHora = new BigDecimal(salarioStr);

            if (horas.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Las horas deben ser mayor a 0", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (salarioHora.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "El salario debe ser mayor a 0", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calcular y mostrar
            Pago pago = controlador.calcular(horas, salarioHora);
            lblBruto.setText("$ " + pago.getBruto().toPlainString());
            lblSeguroSocial.setText("$ " + pago.getSeguroSocial().toPlainString());
            lblSeguroEducativo.setText("$ " + pago.getSeguroEducativo().toPlainString());
            lblNeto.setText("$ " + pago.getNeto().toPlainString());

            // Guardar en BD
            boolean guardado = controlador.procesar(nombre, horas, salarioHora);
            if (guardado) {
                // Limpiar campos editables (excepto nombre)
                tfHoras.setText("");
                tfSalarioHora.setText("");

                // Actualizar historial
                cargarHistorial();

                JOptionPane.showMessageDialog(this, "Nómina procesada correctamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar la nómina", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}