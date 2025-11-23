package com.ferreteria.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Conexión a base de datos MySQL
 * 
 * @author TÚ o Alex (según acuerden)
 * 
 * INSTRUCCIONES PARA ALEX (si toca esta parte):
 * 1. Instalar MySQL/MariaDB
 * 2. Crear base de datos: CREATE DATABASE ferreteria_polaco;
 * 3. Ajustar URL, USER y PASSWORD según tu configuración local
 * 4. Añadir mysql-connector-java.jar al classpath del proyecto
 */
public class DatabaseConnection {
    
    // Configuración de conexión - ALEX: Modifica estos valores según tu BD local
    private static final String URL = "jdbc:mysql://localhost:3306/ferreteria_polaco";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static Connection conexion = null;
    
    /**
     * Obtiene una conexión a la base de datos
     * @return Connection o null si falla
     */
    public static Connection getConnection() {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Cargar driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establecer conexión
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✓ Conexión exitosa a la base de datos");
            }
            return conexion;
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error: Driver MySQL no encontrado");
            System.err.println("  Añade mysql-connector-java.jar al proyecto");
            return null;
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar a la base de datos:");
            System.err.println("  " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Cierra la conexión activa
     */
    public static void closeConnection() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✓ Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al cerrar conexión: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si hay conexión activa
     */
    public static boolean isConnected() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}