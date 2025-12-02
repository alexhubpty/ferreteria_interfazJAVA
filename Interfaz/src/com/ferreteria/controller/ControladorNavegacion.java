package com.ferreteria.controller;

import com.ferreteria.view.MenuFrame;
import javax.swing.JPanel;

public class ControladorNavegacion {
    private final MenuFrame menu;
    private JPanel vistaActual;

    public ControladorNavegacion(MenuFrame menu) {
        this.menu = menu;
    }

    public void navegarA(JPanel nuevaVista) {
        this.vistaActual = nuevaVista;
        menu.cambiarContenidoDerecho(nuevaVista);
    }

    public JPanel getVistaActual() {
        return vistaActual;
    }

    public MenuFrame getMenuFrame() {
        return menu;
    }

    public void volverAlMenu() {
        this.vistaActual = null;
        menu.volverAlMenuPrincipal();
    }
}