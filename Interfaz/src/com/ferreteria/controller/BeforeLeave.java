package com.ferreteria.controller;

/**
 * Interfaz para vistas que requieren confirmación antes de salir
 */
public interface BeforeLeave {
    /**
     * @return true para BLOQUEAR salida (primer clic), false para PERMITIR salida
     */
    boolean onBeforeLeave();
}