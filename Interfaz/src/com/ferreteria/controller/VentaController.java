package com.ferreteria.controller;

import java.math.BigDecimal;
import com.ferreteria.model.Venta;
import com.ferreteria.service.VentaService;

public class VentaController {
    private final VentaService servicio = new VentaService();
    private final Venta venta = new Venta();

    public BigDecimal getPrecio(String producto) {
        return servicio.getPrecio(producto);
    }

    public BigDecimal calcularTotal(String producto, int cantidad) {
        venta.setCantidad(producto, cantidad);
        return servicio.calcularLinea(producto, cantidad);
    }

    public boolean tieneProductos() {
        return venta.tieneProductos();
    }

    public Venta procesarVenta() {
        servicio.calcularTotales(venta);
        return venta;
    }

    public void limpiar() {
        venta.getProductos().clear();
    }
}