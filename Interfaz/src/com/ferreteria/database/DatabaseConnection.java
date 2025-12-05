package com.ferreteria.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:ferreteria.db";
    private static Connection connection;

    // Cargar el driver al iniciar la clase
    static {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("✓ Driver SQLite cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error al cargar driver SQLite: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("⚠ ADVERTENCIA: Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    public static void inicializarDB() {
        String sqlUsuarios = """
                    CREATE TABLE IF NOT EXISTS usuarios (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT UNIQUE NOT NULL,
                        password TEXT NOT NULL,
                        nombre_completo TEXT NOT NULL,
                        rol TEXT NOT NULL,
                        activo INTEGER DEFAULT 1
                    )
                """;

        String sqlUsuarioAdmin = """
                    INSERT OR IGNORE INTO usuarios (username, password, nombre_completo, rol, activo)
                    VALUES ('admin', '1234', 'Administrador', 'ADMIN', 1)
                """;

        String sqlTrabajadores = """
                    CREATE TABLE IF NOT EXISTS trabajadores (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre TEXT UNIQUE NOT NULL
                    )
                """;

        String sqlPagos = """
                    CREATE TABLE IF NOT EXISTS pagos (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        trabajador_id INTEGER NOT NULL,
                        fecha TEXT DEFAULT (datetime('now','localtime')),
                        horas REAL NOT NULL,
                        salario_hora REAL NOT NULL,
                        bruto REAL NOT NULL,
                        seguro_social REAL NOT NULL,
                        seguro_educativo REAL NOT NULL,
                        neto REAL NOT NULL,
                        FOREIGN KEY (trabajador_id) REFERENCES trabajadores(id)
                    )
                """;

        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("Error al inicializar la base de datos: No hay conexión");
            return;
        }

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlUsuarioAdmin);
            stmt.execute(sqlTrabajadores);
            stmt.execute(sqlPagos);
            System.out.println("✓ Base de datos inicializada correctamente");
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    public static void cerrar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}