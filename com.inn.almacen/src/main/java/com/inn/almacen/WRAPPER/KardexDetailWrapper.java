package com.inn.almacen.WRAPPER;

import lombok.Data;

@Data
public class KardexDetailWrapper {

    private Integer id;

    private Integer productId;

    private String producto;

    private Integer cantidad;

    private Float precioVenta;

    private Float oldPrecioVenta;

    private Integer saldo;

    private Float total;

    public KardexDetailWrapper(Integer id, String producto, Integer cantidad, Float precioVenta, Float oldPrecioVenta, Integer saldo, Float total) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.oldPrecioVenta=oldPrecioVenta;
        this.saldo=saldo;
        this.total=total;
    }

    public KardexDetailWrapper(Integer productId, String producto, Integer cantidad, Float precioVenta, Float total){
        this.productId=productId;
        this.producto=producto;
        this.cantidad=cantidad;
        this.precioVenta=precioVenta;
        this.total=total;
    }
}