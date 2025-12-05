package com.ferreteria;

import com.ferreteria.controller.ControladorLogin;
import com.ferreteria.database.DatabaseConnection;
import com.ferreteria.view.LoginFrame;
import javax.swing.*;

/**
 * Clase principal - Arranca la aplicación
 */
public class Main {

    public static void main(String[] args) {
        // Configurar Look and Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo configurar Look and Feel: " + e.getMessage());
        }

        // Inicializar base de datos
        DatabaseConnection.inicializarDB();

        // Verificar conexión a BD (opcional, solo para debug)
        SwingUtilities.invokeLater(() -> {
            verificarConexionBD();
            iniciarAplicacion();
        });
    }

    /**
     * Verifica si hay conexión a la base de datos
     */
    private static void verificarConexionBD() {
        System.out.println("========================================");
        System.out.println("  FERRETERÍA EL POLACO - SISTEMA POS  ");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Verificando conexión a base de datos...");

        if (DatabaseConnection.getConnection() != null) {
            System.out.println("✓ Base de datos conectada correctamente");
        } else {
            System.out.println("⚠ ADVERTENCIA: No se pudo conectar a la base de datos");
            System.out.println("  La aplicación funcionará en MODO DESARROLLO");
            System.out.println("  Usuario de prueba: admin / 1234");
        }
        System.out.println();
    }

    /**
     * Inicializa la aplicación creando el FrameBase con LoginFrame
     */
    private static void iniciarAplicacion() {
        // Crear ventana principal
        FrameBase frameBase = new FrameBase("Ferretería El Polaco - Sistema de Ventas");

        // Crear vista de login
        LoginFrame vistaLogin = new LoginFrame();

        // Crear controlador y conectarlo con la vista Y el frameBase
        ControladorLogin controladorLogin = new ControladorLogin(vistaLogin, frameBase);
        vistaLogin.setControlador(controladorLogin);

        // Mostrar login en el FrameBase
        frameBase.setContenido(vistaLogin);

        System.out.println("✓ Aplicación iniciada correctamente");
        System.out.println("  Esperando autenticación...");
    }
}