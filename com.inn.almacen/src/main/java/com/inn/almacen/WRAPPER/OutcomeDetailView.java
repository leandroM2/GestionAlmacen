package com.inn.almacen.WRAPPER;

import lombok.Data;

@Data
public class OutcomeDetailView {

    private Integer id;

    private Integer cantidad;

    private Float precioDeVenta;

    private Integer saldo;

    private Integer outcomeId;

    private String prodId;

    public OutcomeDetailView(Integer id, Integer cantidad, Float precioDeVenta,
                             Integer saldo, Integer outcomeId, String prodId) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioDeVenta = precioDeVenta;
        this.saldo = saldo;
        this.outcomeId = outcomeId;
        this.prodId = prodId;
    }
}
