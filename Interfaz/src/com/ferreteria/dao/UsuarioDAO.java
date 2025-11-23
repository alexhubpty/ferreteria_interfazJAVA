package com.ferreteria.dao;

import com.ferreteria.database.DatabaseConnection;
import com.ferreteria.model.Usuario;
import java.sql.*;

/**
 * DAO para operaciones de Usuario en la base de datos
 * 
 * @author Alex (Backend)
 * 
 * INSTRUCCIONES PARA ALEX:
 * Este archivo tiene métodos que debes completar. Por ahora retornan null
 * para que el proyecto compile. Tu trabajo es:
 * 
 * 1. Crear la tabla en MySQL:
 * 
 * CREATE TABLE usuarios (
 *     id INT PRIMARY KEY AUTO_INCREMENT,
 *     username VARCHAR(50) UNIQUE NOT NULL,
 *     password VARCHAR(255) NOT NULL,
 *     nombre_completo VARCHAR(100) NOT NULL,
 *     rol VARCHAR(20) NOT NULL,
 *     activo BOOLEAN DEFAULT TRUE
 * );
 * 
 * INSERT INTO usuarios (username, password, nombre_completo, rol, activo)
 * VALUES ('admin', '1234', 'Administrador', 'ADMIN', TRUE);
 * 
 * 2. Completar el método buscarPorUsername() con una consulta SQL SELECT
 * 3. Probar que funcione desde Main
 */
public class UsuarioDAO {
    
    /**
     * Busca un usuario por su nombre de usuario
     * 
     * @param username nombre de usuario a buscar
     * @return Usuario encontrado o null si no existe
     * 
     * TODO ALEX: Implementar consulta SELECT a la tabla usuarios
     * Ejemplo de código que debes escribir aquí:
     * 
     * Connection conn = DatabaseConnection.getConnection();
     * if (conn == null) return null;
     * 
     * String sql = "SELECT * FROM usuarios WHERE username = ? AND activo = TRUE";
     * try (PreparedStatement stmt = conn.prepareStatement(sql)) {
     *     stmt.setString(1, username);
     *     ResultSet rs = stmt.executeQuery();
     *     
     *     if (rs.next()) {
     *         Usuario usuario = new Usuario();
     *         usuario.setId(rs.getInt("id"));
     *         usuario.setUsername(rs.getString("username"));
     *         usuario.setPassword(rs.getString("password"));
     *         usuario.setNombreCompleto(rs.getString("nombre_completo"));
     *         usuario.setRol(rs.getString("rol"));
     *         usuario.setActivo(rs.getBoolean("activo"));
     *         return usuario;
     *     }
     * } catch (SQLException e) {
     *     System.err.println("Error al buscar usuario: " + e.getMessage());
     * }
     * return null;
     */
    public Usuario buscarPorUsername(String username) {
        // ALEX: Aquí va tu código de consulta SQL
        // Por ahora retorna null para que compile
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return null;
        
        String sql = "SELECT * FROM usuarios WHERE username = ? AND activo = TRUE";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setRol(rs.getString("rol"));
                usuario.setActivo(rs.getBoolean("activo"));
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Guarda un nuevo usuario en la BD
     * TODO ALEX: Implementar INSERT cuando sea necesario
     */
    public boolean guardar(Usuario usuario) {
        // ALEX: Por ahora no es necesario, enfócate primero en buscarPorUsername
        return false;
    }
}