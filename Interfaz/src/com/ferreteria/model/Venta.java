package com.ferreteria.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO Venta
 * 
 * @author Alex (Backend)
 */
public class Venta {
    private int id;
    private int idUsuario;
    private LocalDateTime fecha;
    private double subtotal;
    private double itbms; // 7%
    private double total;
    private double montoPagado;
    private double cambio;
    private List<DetalleVenta> detalles;

    public Venta() {
        this.detalles = new ArrayList<>();
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
    public double getItbms() { return itbms; }
    public void setItbms(double itbms) { this.itbms = itbms; }
    
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    
    public double getMontoPagado() { return montoPagado; }
    public void setMontoPagado(double montoPagado) { this.montoPagado = montoPagado; }
    
    public double getCambio() { return cambio; }
    public void setCambio(double cambio) { this.cambio = cambio; }
    
    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
    
    public void agregarDetalle(DetalleVenta detalle) {
        this.detalles.add(detalle);
    }
}