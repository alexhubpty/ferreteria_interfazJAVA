package com.ferreteria.model;

/**
 * POJO Usuario - Representa un usuario del sistema
 * 
 * @author Alex (Backend)
 * 
 *         TODO INSTRUCCIONES PARA ALEX:
 *         Completa esta clase según las especificaciones:
 *         - Esta clase ya está completa, solo úsala como referencia
 *         - Cada atributo debe tener getter y setter
 *         - Incluye constructor vacío y constructor con parámetros
 */
public class Usuario {
    private int id;
    private String username;
    private String password;
    private String nombreCompleto;
    private String rol; // "ADMIN", "VENDEDOR", "CAJERO"
    private boolean activo;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor completo
    public Usuario(int id, String username, String password, String nombreCompleto, String rol, boolean activo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.activo = activo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}