package com.ferreteria.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ferreteria.database.DatabaseConnection;
import com.ferreteria.model.Pago;
import com.ferreteria.model.Trabajador;

public class NominaRepository {

    /**
     * Busca un trabajador por nombre (sin crear si no existe).
     */
    public Trabajador buscarTrabajador(String nombre) {
        String sql = "SELECT id, nombre FROM trabajadores WHERE nombre = ?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre.toUpperCase().trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Trabajador(rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar trabajador: " + e.getMessage());
        }
        return null;
    }

    public Trabajador obtenerOCrearTrabajador(String nombre) {
        String sqlBuscar = "SELECT id, nombre FROM trabajadores WHERE nombre = ?";
        String sqlInsertar = "INSERT INTO trabajadores (nombre) VALUES (?)";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return null;

        try {
            // Buscar si existe
            try (PreparedStatement ps = conn.prepareStatement(sqlBuscar)) {
                ps.setString(1, nombre.toUpperCase().trim());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Trabajador(rs.getInt("id"), rs.getString("nombre"));
                }
            }

            // Si no existe, crear
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertar, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nombre.toUpperCase().trim());
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    return new Trabajador(keys.getInt(1), nombre.toUpperCase().trim());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener/crear trabajador: " + e.getMessage());
        }
        return null;
    }

    public boolean guardarPago(Pago pago) {
        String sql = """
                    INSERT INTO pagos (trabajador_id, horas, salario_hora, bruto, seguro_social, seguro_educativo, neto)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pago.getTrabajadorId());
            ps.setBigDecimal(2, pago.getHoras());
            ps.setBigDecimal(3, pago.getSalarioHora());
            ps.setBigDecimal(4, pago.getBruto());
            ps.setBigDecimal(5, pago.getSeguroSocial());
            ps.setBigDecimal(6, pago.getSeguroEducativo());
            ps.setBigDecimal(7, pago.getNeto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar pago: " + e.getMessage());
            return false;
        }
    }

    public List<Pago> obtenerHistorial(int trabajadorId, int pagina, int porPagina) {
        List<Pago> pagos = new ArrayList<>();
        String sql = """
                    SELECT * FROM pagos
                    WHERE trabajador_id = ?
                    ORDER BY fecha DESC
                    LIMIT ? OFFSET ?
                """;

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return pagos;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trabajadorId);
            ps.setInt(2, porPagina);
            ps.setInt(3, pagina * porPagina);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pago p = new Pago();
                p.setId(rs.getInt("id"));
                p.setTrabajadorId(rs.getInt("trabajador_id"));
                p.setFecha(rs.getString("fecha"));
                p.setHoras(rs.getBigDecimal("horas"));
                p.setSalarioHora(rs.getBigDecimal("salario_hora"));
                p.setBruto(rs.getBigDecimal("bruto"));
                p.setSeguroSocial(rs.getBigDecimal("seguro_social"));
                p.setSeguroEducativo(rs.getBigDecimal("seguro_educativo"));
                p.setNeto(rs.getBigDecimal("neto"));
                pagos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial: " + e.getMessage());
        }
        return pagos;
    }

    public int contarPagos(int trabajadorId) {
        String sql = "SELECT COUNT(*) FROM pagos WHERE trabajador_id = ?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return 0;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trabajadorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar pagos: " + e.getMessage());
        }
        return 0;
    }
}