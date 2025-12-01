package com.ferreteria.view;

import java.awt.*;
import javax.swing.*;
import com.ferreteria.controller.ControladorNavegacion;

public class InventarioFrame extends JPanel {
    private ControladorNavegacion navegacion;

    public InventarioFrame(ControladorNavegacion navegacion) {
        this.navegacion = navegacion;
        setLayout(new BorderLayout());
        setOpaque(false);
        initComponents();
    }

    private void initComponents() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        JLabel titulo = new JLabel("Nuevo Inventario");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 48));
        titulo.setForeground(new Color(30, 30, 30));

        contentPanel.add(titulo);
        add(contentPanel, BorderLayout.CENTER);
    }
}