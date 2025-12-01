package com.ferreteria.controller;

import javax.swing.JPanel;
import com.ferreteria.view.MenuFrame;

/**
 * Controlador de navegación entre vistas dentro de MenuFrame.
 */
public class ControladorNavegacion {
    private final MenuFrame menuFrame;

    public ControladorNavegacion(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;
    }

    // Navega a una vista en el panel derecho
    public void navegarA(JPanel nuevaVista) {
        if (menuFrame != null && nuevaVista != null) {
            menuFrame.cambiarContenidoDerecho(nuevaVista);
        }
    }

    // Vuelve al menú principal (casco)
    public void volverAlMenu() {
        if (menuFrame != null) {
            menuFrame.volverAlMenuPrincipal();
        }
    }

    public MenuFrame getMenuFrame() {
        return menuFrame;
    }
}