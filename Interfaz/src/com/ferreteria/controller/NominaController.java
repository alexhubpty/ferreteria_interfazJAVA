package com.ferreteria.controller;

import java.math.BigDecimal;
import java.util.List;

import com.ferreteria.model.Pago;
import com.ferreteria.model.Trabajador;
import com.ferreteria.repository.NominaRepository;
import com.ferreteria.service.NominaService;

public class NominaController {
    private final NominaService servicio = new NominaService();
    private final NominaRepository repositorio = new NominaRepository();

    private Trabajador trabajadorActual;
    private int paginaActual = 0;
    private static final int PAGOS_POR_PAGINA = 8;

    public Pago calcular(BigDecimal horas, BigDecimal salarioHora) {
        return servicio.calcularNomina(horas, salarioHora);
    }

    /**
     * Busca un trabajador por nombre. Retorna el trabajador si existe, null si no.
     */
    public Trabajador buscarTrabajador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            trabajadorActual = null;
            return null;
        }
        trabajadorActual = repositorio.buscarTrabajador(nombre.trim());
        paginaActual = 0;
        return trabajadorActual;
    }

    /**
     * Procesa la nómina: crea trabajador si no existe, guarda el pago.
     */
    public boolean procesar(String nombre, BigDecimal horas, BigDecimal salarioHora) {
        if (nombre == null || nombre.trim().isEmpty())
            return false;
        if (horas == null || horas.compareTo(BigDecimal.ZERO) <= 0)
            return false;
        if (salarioHora == null || salarioHora.compareTo(BigDecimal.ZERO) <= 0)
            return false;

        // Obtener o crear trabajador
        trabajadorActual = repositorio.obtenerOCrearTrabajador(nombre.trim());
        if (trabajadorActual == null)
            return false;

        // Calcular nómina
        Pago pago = servicio.calcularNomina(horas, salarioHora);
        pago.setTrabajadorId(trabajadorActual.getId());

        // Guardar en BD
        boolean guardado = repositorio.guardarPago(pago);
        if (guardado) {
            paginaActual = 0; // Volver a primera página para ver el nuevo pago
        }
        return guardado;
    }

    /**
     * Obtiene el historial de pagos del trabajador actual.
     */
    public List<Pago> obtenerHistorial() {
        if (trabajadorActual == null)
            return List.of();
        return repositorio.obtenerHistorial(trabajadorActual.getId(), paginaActual, PAGOS_POR_PAGINA);
    }

    /**
     * Avanza a la siguiente página si hay más registros.
     */
    public boolean paginaSiguiente() {
        if (trabajadorActual == null)
            return false;
        int total = repositorio.contarPagos(trabajadorActual.getId());
        if ((paginaActual + 1) * PAGOS_POR_PAGINA < total) {
            paginaActual++;
            return true;
        }
        return false;
    }

    /**
     * Retrocede a la página anterior si es posible.
     */
    public boolean paginaAnterior() {
        if (paginaActual > 0) {
            paginaActual--;
            return true;
        }
        return false;
    }

    public Trabajador getTrabajadorActual() {
        return trabajadorActual;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public int getTotalPaginas() {
        if (trabajadorActual == null)
            return 0;
        int total = repositorio.contarPagos(trabajadorActual.getId());
        return (int) Math.ceil((double) total / PAGOS_POR_PAGINA);
    }
}