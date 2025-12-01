package com.ferreteria.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.ferreteria.controller.ControladorNavegacion;
import com.ferreteria.util.RoundedPanel;

public class NominaFrame extends JPanel {
    private ControladorNavegacion navegacion;

    public NominaFrame(ControladorNavegacion navegacion) {
        this.navegacion = navegacion;
        setLayout(new BorderLayout());
        setOpaque(false);
        initComponents();
    }

    private void initComponents() {
        // Margen celeste visible alrededor: 20px igual que VentaFrame
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(container, BorderLayout.CENTER);

        // Centro con GridBagLayout para anclar el conjunto a la derecha (EAST)
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        container.add(center, BorderLayout.CENTER);

        // Holder horizontal para los dos paneles rojos
        JPanel hBox = new JPanel();
        hBox.setOpaque(false);
        hBox.setLayout(new BoxLayout(hBox, BoxLayout.X_AXIS));

        // Panel rojo 1: Nómina de Pago (izquierda)
        RoundedPanel panelNomina = new RoundedPanel(84, new Color(186, 32, 36));
        panelNomina.setOpaque(true);
        panelNomina.setLayout(new GridBagLayout());
        panelNomina.setBorder(new EmptyBorder(20, 10, 20, 10));

        hBox.add(panelNomina);

        // Separación entre paneles
        hBox.add(Box.createHorizontalStrut(20));

        // Panel rojo 2: Historial de Pagos (derecha)
        RoundedPanel panelHistorial = new RoundedPanel(84, new Color(186, 32, 36));
        panelHistorial.setOpaque(true);
        panelHistorial.setLayout(new GridBagLayout());
        panelHistorial.setBorder(new EmptyBorder(20, 10, 20, 10));

        hBox.add(panelHistorial);

        // Anclar todo el bloque hBox al borde derecho con GridBagLayout
        GridBagConstraints alignRight = new GridBagConstraints();
        alignRight.gridx = 0;
        alignRight.gridy = 0;
        alignRight.weightx = 1.0;
        alignRight.weighty = 1.0;
        alignRight.fill = GridBagConstraints.BOTH; // ← CAMBIO: de NONE a BOTH para llenar vertical
        alignRight.anchor = GridBagConstraints.EAST; // ancla a la derecha
        center.add(hBox, alignRight);

        // ===== Panel Nómina de Pago =====
        GridBagConstraints g1 = new GridBagConstraints();
        g1.insets = new Insets(14, 16, 14, 16);
        g1.fill = GridBagConstraints.HORIZONTAL;
        g1.anchor = GridBagConstraints.CENTER;
        g1.weightx = 1.0; // ← AÑADIR: permite expansión horizontal
        g1.weighty = 0.0; // ← contenido no se expande verticalmente

        g1.gridx = 0;
        g1.gridy = 0;
        g1.gridwidth = 2;
        panelNomina.add(crearTituloPanel("Nómina de Pago", "icono03.png"), g1);

        g1.gridy = 1;
        panelNomina.add(Box.createVerticalStrut(20), g1);

        g1.gridwidth = 1;
        g1.anchor = GridBagConstraints.WEST;
        agregarFilaCampo(panelNomina, g1, 2, "Nombre del Trabajador", crearCampoDisplay("JOHN HARVARD", 280));
        agregarFilaCampo(panelNomina, g1, 3, "Horas Trabajadas", crearCampoMoneda("123", 200));
        agregarFilaCampo(panelNomina, g1, 4, "Salario por Hora", crearCampoMoneda("$ 12.30", 240));
        agregarFilaCampo(panelNomina, g1, 5, "Salario Bruto", crearCampoMoneda("$ 12.30", 240));
        agregarFilaCampo(panelNomina, g1, 6, "Seguro Social", crearCampoMoneda("$ 12.30", 240));
        agregarFilaCampo(panelNomina, g1, 7, "Seguro Educativo", crearCampoMoneda("$ 12.30", 240));
        agregarFilaCampo(panelNomina, g1, 8, "Salario Neto", crearCampoMoneda("$ 12.30", 240));

        g1.gridy = 9;
        g1.gridx = 0;
        g1.gridwidth = 2;
        g1.weighty = 1.0; // ← AÑADIR: espacio vacío absorbe el resto
        g1.fill = GridBagConstraints.VERTICAL;
        panelNomina.add(Box.createVerticalGlue(), g1); // ← CAMBIO: glue en lugar de strut

        g1.gridy = 10;
        g1.weighty = 0.0; // ← volver a 0 para el botón
        g1.fill = GridBagConstraints.NONE;
        g1.anchor = GridBagConstraints.CENTER;
        panelNomina.add(crearBotonProcesar(), g1);

        // ===== Panel Historial de Pagos =====
        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(10, 10, 10, 10);
        g2.fill = GridBagConstraints.HORIZONTAL;
        g2.anchor = GridBagConstraints.CENTER;
        g2.weightx = 1.0; // ← AÑADIR: permite expansión horizontal
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
        panelHistorial.add(etiquetaHeader("Bruto", Color.WHITE), g2);
        g2.gridx = 1;
        panelHistorial.add(etiquetaHeader("Deducciones", Color.WHITE), g2);
        g2.gridx = 2;
        panelHistorial.add(etiquetaHeader("Neto", Color.WHITE), g2);

        int startRow = 3;
        for (int i = 0; i < 8; i++) {
            g2.gridy = startRow + i;
            g2.gridx = 0;
            panelHistorial.add(crearChipValor("12343.90"), g2);
            g2.gridx = 1;
            panelHistorial.add(crearChipValor("12343.90"), g2);
            g2.gridx = 2;
            panelHistorial.add(crearChipValor("12343.90"), g2);
        }

        // Espacio flexible antes de la paginación
        g2.gridy = startRow + 8;
        g2.gridx = 0;
        g2.gridwidth = 3;
        g2.weighty = 1.0; // ← AÑADIR: espacio vacío absorbe el resto
        g2.fill = GridBagConstraints.VERTICAL;
        panelHistorial.add(Box.createVerticalGlue(), g2); // ← AÑADIR: glue para empujar hacia arriba

        // Navegación de páginas (visual)
        g2.gridy = startRow + 9; // ← CAMBIO: nueva fila para paginador
        g2.weighty = 0.0; // ← volver a 0
        g2.fill = GridBagConstraints.NONE;
        g2.anchor = GridBagConstraints.CENTER;
        panelHistorial.add(crearPaginador(), g2);
    }

    // ===== helpers de estilo =====

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

    private void agregarFilaCampo(JPanel panelNomina, GridBagConstraints g1, int row, String etiqueta,
            JComponent campo) {
        g1.gridy = row;
        g1.gridx = 0;
        g1.gridwidth = 1;
        g1.anchor = GridBagConstraints.WEST;
        JLabel l = new JLabel(etiqueta);
        l.setFont(new Font("SansSerif", Font.BOLD, 32));
        l.setForeground(Color.WHITE);
        panelNomina.add(l, g1);

        g1.gridx = 1;
        g1.anchor = GridBagConstraints.EAST;
        panelNomina.add(campo, g1);
    }

    private JLabel etiquetaHeader(String txt, Color color) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("SansSerif", Font.BOLD, 28));
        l.setForeground(color);
        return l;
    }

    // Campos visuales tipo “pill”
    private JComponent crearCampoDisplay(String texto, int width) {
        JLabel campo = new JLabel(texto, SwingConstants.CENTER) {
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
        campo.setOpaque(false);
        campo.setPreferredSize(new Dimension(width, 56));
        campo.setMinimumSize(new Dimension(width, 56));
        campo.setFont(new Font("SansSerif", Font.BOLD, 26));
        campo.setForeground(new Color(30, 30, 30));
        campo.setBorder(new EmptyBorder(8, 14, 8, 14));
        return campo;
    }

    private JComponent crearCampoMoneda(String texto, int width) {
        JLabel campo = new JLabel(texto, SwingConstants.CENTER) {
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
        campo.setOpaque(false);
        campo.setPreferredSize(new Dimension(width, 56));
        campo.setMinimumSize(new Dimension(width, 56));
        campo.setFont(new Font("SansSerif", Font.PLAIN, 26));
        campo.setForeground(new Color(30, 30, 30));
        campo.setBorder(new EmptyBorder(8, 14, 8, 14));
        return campo;
    }

    private JComponent crearChipValor(String texto) {
        JLabel chip = new JLabel(texto, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(230, 230, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(new Color(160, 160, 160));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        chip.setOpaque(false);
        chip.setPreferredSize(new Dimension(130, 44)); // ← de 150 a 130
        chip.setMinimumSize(new Dimension(130, 44)); // ← de 150 a 130
        chip.setFont(new Font("SansSerif", Font.PLAIN, 20)); // ← de 22 a 20
        chip.setForeground(new Color(30, 30, 30));
        chip.setBorder(new EmptyBorder(6, 10, 6, 10)); // ← de 12 a 10
        return chip;
    }

    private JPanel crearPaginador() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        JButton prev = crearBotonPaginacion("<");
        JButton next = crearBotonPaginacion(">");
        p.add(Box.createHorizontalGlue());
        p.add(prev);
        p.add(Box.createHorizontalStrut(16));
        p.add(next);
        p.add(Box.createHorizontalGlue());
        return p;
    }

    private JButton crearBotonPaginacion(String texto) {
        JButton b = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
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

        return b;
    }
}