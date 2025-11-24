package com.ferreteria.controller;

import com.ferreteria.model.Usuario;
import com.ferreteria.service.AutenticacionService;
import com.ferreteria.view.LoginFrame;
import javax.swing.JOptionPane;

/**
 * Controlador para la pantalla de Login
 * 
 * @author TÚ (Frontend)
 * 
 *         Este controlador conecta la vista (LoginFrame) con la lógica de
 *         negocio (AutenticacionService).
 *         NO toca la base de datos directamente, eso lo hace Alex en su capa.
 */
public class ControladorLogin {

    private LoginFrame vista;
    private AutenticacionService servicioAuth;
    private Usuario usuarioActual;

    public ControladorLogin(LoginFrame vista) {
        this.vista = vista;
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

        // Mostrar indicador de carga (opcional)
        vista.setLoginEnProceso(true);

        // Llamar al servicio de autenticación (esto lo hace Alex)
        Usuario usuario = servicioAuth.validarCredenciales(username, password);

        vista.setLoginEnProceso(false);

        if (usuario != null) {
            // Login exitoso
            this.usuarioActual = usuario;
            vista.mostrarMensaje("¡Bienvenido " + usuario.getNombreCompleto() + "!");

            // TODO FELIX: Aquí abrirías la siguiente ventana (MenuPrincipal o Dashboard)
            // Por ejemplo:
            // new MenuPrincipalFrame(usuario).setVisible(true);
            // vista.cerrarVentana();

            System.out.println("✓ Usuario autenticado: " + usuario);

        } else {
            // Login fallido
            vista.mostrarError("Usuario o contraseña incorrectos");
            vista.limpiarCampos();
        }
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