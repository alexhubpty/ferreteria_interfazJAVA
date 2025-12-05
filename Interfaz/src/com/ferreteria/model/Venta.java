package com.ferreteria.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Venta {
    private Map<String, Integer> productos = new HashMap<>();
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal itbms = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    public void setCantidad(String producto, int cantidad) {
        if (cantidad > 0) {
            productos.put(producto, cantidad);
        } else {
            productos.remove(producto);
        }
    }

    public Map<String, Integer> getProductos() {
        return productos;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getItbms() {
        return itbms;
    }

    public void setItbms(BigDecimal itbms) {
        this.itbms = itbms;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean tieneProductos() {
        return productos.values().stream().anyMatch(c -> c > 0);
    }
}