package com.inn.almacen.WRAPPER;

import lombok.Data;

@Data
public class IncomeDetailView {

    private Integer id;

    private Integer cantidad;

    private Float precioVentaUnit;

    private Float oldPrecioVenta;

    private Integer saldo;

    private Integer incomeId;

    private String prodId;

    public IncomeDetailView(Integer id, Integer cantidad, Float precioVentaUnit, Float oldPrecioVenta, Integer saldo,
                            Integer incomeId, String prodId) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioVentaUnit = precioVentaUnit;
        this.oldPrecioVenta = oldPrecioVenta;
        this.saldo = saldo;
        this.incomeId = incomeId;
        this.prodId = prodId;
    }
}