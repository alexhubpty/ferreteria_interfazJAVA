package com.ferreteria.controller;

import com.ferreteria.FrameBase;
import com.ferreteria.model.Usuario;
import com.ferreteria.service.AutenticacionService;
import com.ferreteria.view.LoginFrame;
import com.ferreteria.view.MenuFrame;
import javax.swing.JOptionPane;

/**
 * Controlador para la pantalla de Login
 * 
 * @author TÚ (Frontend)
 */
public class ControladorLogin {

    private LoginFrame vista;
    private AutenticacionService servicioAuth;
    private Usuario usuarioActual;
    private FrameBase frameBase; // ← NUEVO: referencia al frame principal

    public ControladorLogin(LoginFrame vista, FrameBase frameBase) {
        this.vista = vista;
        this.frameBase = frameBase; // ← NUEVO
        this.servicioAuth = new AutenticacionService();
        this.usuarioActual = null;
    }

    /**
     * Maneja el intento de login cuando el usuario presiona "Acceder"
     */
    public void intentarLogin() {
        // Obtener datos de la vista
        String username = vista.getUsername();
        String password = vista.getPassword();

        // Validar que no estén vacíos
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            vista.mostrarError("Por favor ingrese usuario y contraseña");
            return;
        }

        // Mostrar indicador de carga
        vista.setLoginEnProceso(true);

        // Llamar al servicio de autenticación
        Usuario usuario = servicioAuth.validarCredenciales(username, password);

        vista.setLoginEnProceso(false);

        if (usuario != null) {
            // Login exitoso
            this.usuarioActual = usuario;
            vista.mostrarMensaje("¡Bienvenido " + usuario.getNombreCompleto() + "!");

            System.out.println("✓ Usuario autenticado: " + usuario);

            // ← NUEVO: Navegar al menú principal
            navegarAlMenu();

        } else {
            // Login fallido
            vista.mostrarError("Usuario o contraseña incorrectos");
            vista.limpiarCampos();
        }
    }

    /**
     * Navega al menú principal después del login exitoso
     */
    private void navegarAlMenu() {
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setControlador(this); // Le pasamos el mismo controlador
        frameBase.setContenido(menuFrame);
    }

    /**
     * Maneja el evento de cerrar la aplicación
     */
    public void cerrarAplicacion() {
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de que desea salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Cerrando aplicación...");
            System.exit(0);
        }
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}