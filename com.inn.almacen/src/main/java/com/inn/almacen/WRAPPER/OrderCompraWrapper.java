package com.inn.almacen.WRAPPER;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCompraWrapper {

    private Integer productId;

    private String producto;

    private Integer cantidad;

    private Float precioVenta;

    private Float total;
}
