package com.ferreteria.model;

import java.math.BigDecimal;

public class Pago {
    private int id;
    private int trabajadorId;
    private String fecha;
    private BigDecimal horas;
    private BigDecimal salarioHora;
    private BigDecimal bruto;
    private BigDecimal seguroSocial;
    private BigDecimal seguroEducativo;
    private BigDecimal neto;

    public Pago() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(int trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getHoras() {
        return horas;
    }

    public void setHoras(BigDecimal horas) {
        this.horas = horas;
    }

    public BigDecimal getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(BigDecimal salarioHora) {
        this.salarioHora = salarioHora;
    }

    public BigDecimal getBruto() {
        return bruto;
    }

    public void setBruto(BigDecimal bruto) {
        this.bruto = bruto;
    }

    public BigDecimal getSeguroSocial() {
        return seguroSocial;
    }

    public void setSeguroSocial(BigDecimal seguroSocial) {
        this.seguroSocial = seguroSocial;
    }

    public BigDecimal getSeguroEducativo() {
        return seguroEducativo;
    }

    public void setSeguroEducativo(BigDecimal seguroEducativo) {
        this.seguroEducativo = seguroEducativo;
    }

    public BigDecimal getNeto() {
        return neto;
    }

    public void setNeto(BigDecimal neto) {
        this.neto = neto;
    }
}