package com.ferreteria.model;

/**
 * POJO Producto
 * 
 * @author Alex (Backend)
 * INSTRUCCIONES: Similar a Usuario, crear todos los getters/setters
 */
public class Producto {
    private int id;
    private String codigo;
    private String nombre;
    private String categoria;
    private double precioUnitario;
    private int stockDisponible;
    private boolean activo;

    public Producto() {
    }

    public Producto(int id, String codigo, String nombre, String categoria, 
                    double precioUnitario, int stockDisponible, boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioUnitario = precioUnitario;
        this.stockDisponible = stockDisponible;
        this.activo = activo;
    }

    // TODO ALEX: Generar getters y setters para todos los atributos
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    
    public int getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(int stockDisponible) { this.stockDisponible = stockDisponible; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}