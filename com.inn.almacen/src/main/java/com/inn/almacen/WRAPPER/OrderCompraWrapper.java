package com.inn.almacen.WRAPPER;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCompraWrapper {

    private String productId;

    private String producto;

    private String cantidad;

    private String precioVenta;

    private String total;
}
