package com.ferreteria.service;

import com.ferreteria.dao.UsuarioDAO;
import com.ferreteria.model.Usuario;

/**
 * Servicio de autenticación - Lógica de negocio para login
 * 
 * @author Alex (Backend)
 * 
 * INSTRUCCIONES PARA ALEX:
 * Esta clase contiene la lógica de validación de usuarios.
 * Tiene un FALLBACK hardcodeado para que funcione aunque la BD no esté lista.
 * 
 * Tu trabajo:
 * 1. Asegurarte de que UsuarioDAO.buscarPorUsername() funcione
 * 2. El método validarCredenciales() ya está completo, solo verifica que funcione
 * 3. Más adelante puedes añadir hash de contraseñas (BCrypt)
 */
public class AutenticacionService {
    
    private UsuarioDAO usuarioDAO;
    
    public AutenticacionService() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    /**
     * Valida las credenciales de un usuario
     * 
     * @param username nombre de usuario
     * @param password contraseña en texto plano
     * @return Usuario si las credenciales son válidas, null si no
     */
    public Usuario validarCredenciales(String username, String password) {
        // Validaciones básicas
        if (username == null || username.trim().isEmpty()) {
            System.err.println("✗ Username vacío");
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            System.err.println("✗ Password vacío");
            return null;
        }
        
        try {
            // Intentar buscar en la base de datos
            Usuario usuario = usuarioDAO.buscarPorUsername(username.trim());
            
            if (usuario != null) {
                // Comparar contraseña (TODO ALEX: implementar hash más adelante)
                if (usuario.getPassword().equals(password)) {
                    System.out.println("✓ Login exitoso: " + usuario.getNombreCompleto());
                    return usuario;
                } else {
                    System.err.println("✗ Contraseña incorrecta");
                    return null;
                }
            }
            
        } catch (Exception e) {
            System.err.println("⚠ Error al validar en BD, usando fallback hardcodeado");
            e.printStackTrace();
        }
        
        // FALLBACK: Usuario hardcodeado para desarrollo
        // IMPORTANTE: Este código permite trabajar SIN base de datos
        if (username.equals("admin") && password.equals("1234")) {
            System.out.println("✓ Login exitoso (modo desarrollo)");
            Usuario usuarioDemo = new Usuario();
            usuarioDemo.setId(1);
            usuarioDemo.setUsername("admin");
            usuarioDemo.setPassword("1234");
            usuarioDemo.setNombreCompleto("Administrador Demo");
            usuarioDemo.setRol("ADMIN");
            usuarioDemo.setActivo(true);
            return usuarioDemo;
        }
        
        System.err.println("✗ Credenciales inválidas");
        return null;
    }
    
    /**
     * Verifica si un usuario tiene un rol específico
     */
    public boolean tieneRol(Usuario usuario, String rol) {
        return usuario != null && rol.equals(usuario.getRol());
    }
}