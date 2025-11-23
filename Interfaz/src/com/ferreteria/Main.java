package com.ferreteria;

import com.ferreteria.controller.ControladorLogin;
import com.ferreteria.database.DatabaseConnection;
// import com.ferreteria.model.Venta;
import com.ferreteria.view.LoginFrame;
import com.ferreteria.view.ResumenFacturaFrame;
import com.ferreteria.view.VentaFrame;
import javax.swing.*;
// import java.awt.*;

/**
 * Clase principal - Arranca la aplicación
 * 
 * @author TÚ (Frontend) y Alex (Backend)
 * 
 *         Este es el punto de entrada. Crea las vistas, controladores y los
 *         conecta.
 */
public class Main {

    public static void main(String[] args) {
        // Configurar Look and Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo configurar Look and Feel: " + e.getMessage());
        }

        // Verificar conexión a BD (opcional, solo para debug)
        SwingUtilities.invokeLater(() -> {
            verificarConexionBD();
            iniciarAplicacion();
        });
    }

    /**
     * Verifica si hay conexión a la base de datos
     * Si no hay, muestra advertencia pero la app sigue funcionando con datos
     * hardcodeados
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
        // Crear ventana principal (tu FrameBase existente)
        FrameBase frameBase = new FrameBase("Ferretería El Polaco - Sistema de Ventas");

        // Crear vistas de la aplicación
        // login, venta, resumen factura
        LoginFrame vistaLogin = new LoginFrame();
        // VentaFrame vistaVenta = new VentaFrame();
        // ResumenFacturaFrame vistaResumen = new ResumenFacturaFrame();

        // Crear controlador y conectarlo con la vista
        ControladorLogin controladorLogin = new ControladorLogin(vistaLogin);
        vistaLogin.setControlador(controladorLogin);

        // Mostrar vistas en el FrameBase
        // frameBase.setContenido(vistaVenta);
        frameBase.setContenido(vistaLogin);
        // frameBase.setContenido(vistaResumen);

        System.out.println("✓ Aplicación iniciada correctamente");
        System.out.println("  Esperando autenticación...");
    }
}

// NOTA: Necesitas mover tu FrameBase.java a este paquete o importarlo
// correctamente
// Si está en la raíz de src/, muévelo a com/ferreteria/view/FrameBase.java