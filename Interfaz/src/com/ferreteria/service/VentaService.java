package com.ferreteria.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import com.ferreteria.model.Venta;

public class VentaService {
    private static final BigDecimal ITBMS_RATE = new BigDecimal("0.07");

    private static final Map<String, BigDecimal> PRECIOS = Map.of(
            "Bloque 6", new BigDecimal("0.75"),
            "Bloque 4", new BigDecimal("0.60"),
            "Cemento", new BigDecimal("10.00"),
            "Arena", new BigDecimal("15.00"));

    public BigDecimal getPrecio(String producto) {
        return PRECIOS.getOrDefault(producto, BigDecimal.ZERO);
    }

    public BigDecimal calcularLinea(String producto, int cantidad) {
        return getPrecio(producto).multiply(BigDecimal.valueOf(cantidad));
    }

    public void calcularTotales(Venta venta) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> e : venta.getProductos().entrySet()) {
            subtotal = subtotal.add(calcularLinea(e.getKey(), e.getValue()));
        }

        BigDecimal itbms = subtotal.multiply(ITBMS_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(itbms);

        venta.setSubtotal(subtotal);
        venta.setItbms(itbms);
        venta.setTotal(total);
    }

    public BigDecimal calcularCambio(BigDecimal total, BigDecimal pago) {
        if (pago.compareTo(total) >= 0) {
            return pago.subtract(total);
        }
        return BigDecimal.ZERO;
    }
}