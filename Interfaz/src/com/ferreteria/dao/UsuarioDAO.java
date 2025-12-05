package com.ferreteria.dao;

import com.ferreteria.database.DatabaseConnection;
import com.ferreteria.model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public Usuario buscarPorUsername(String username) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.err.println("No hay conexión a la base de datos");
            return null;
        }

        String sql = "SELECT * FROM usuarios WHERE username = ? AND activo = 1";
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
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    public boolean guardar(Usuario usuario) {
        return false;
    }
}