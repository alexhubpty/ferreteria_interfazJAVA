package com.ferreteria.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.ferreteria.model.Pago;

public class NominaService {
    private static final BigDecimal TASA_SS = new BigDecimal("0.0975"); // 9.75%
    private static final BigDecimal TASA_SE = new BigDecimal("0.0125"); // 1.25%

    public Pago calcularNomina(BigDecimal horas, BigDecimal salarioHora) {
        Pago pago = new Pago();
        pago.setHoras(horas);
        pago.setSalarioHora(salarioHora);

        // Salario Bruto = Horas × Salario por Hora
        BigDecimal bruto = horas.multiply(salarioHora).setScale(2, RoundingMode.HALF_UP);
        pago.setBruto(bruto);

        // Seguro Social = Bruto × 9.75%
        BigDecimal ss = bruto.multiply(TASA_SS).setScale(2, RoundingMode.HALF_UP);
        pago.setSeguroSocial(ss);

        // Seguro Educativo = Bruto × 1.25%
        BigDecimal se = bruto.multiply(TASA_SE).setScale(2, RoundingMode.HALF_UP);
        pago.setSeguroEducativo(se);

        // Salario Neto = Bruto - SS - SE
        BigDecimal neto = bruto.subtract(ss).subtract(se);
        pago.setNeto(neto);

        return pago;
    }
}