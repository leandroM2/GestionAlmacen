package com.inn.almacen.WRAPPER;

import lombok.Data;

@Data
public class KardexDetailWrapper {

    private Integer id;

    private String producto;

    private Integer cantidad;

    private Float precioVenta;

    public KardexDetailWrapper(Integer id, String producto, Integer cantidad, Float precioVenta) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }
}