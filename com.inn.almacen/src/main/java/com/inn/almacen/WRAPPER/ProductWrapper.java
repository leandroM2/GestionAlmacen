package com.inn.almacen.WRAPPER;

import lombok.Data;


@Data
public class ProductWrapper {

    Integer id;

    String nombre;

    String color;

    Float precio;

    Integer stock;

    Boolean estado;

    Integer categoryId;

    String categoryNombre;

    Integer supplierId;

    String supplierRazonSocial;

    Long supplierRuc;

    Integer supplierContacto;

    public ProductWrapper(Integer id, String nombre, String color, Float precio,
                          Integer stock, Boolean estado, Integer categoryId, String categoryNombre,
                          Integer supplierId, String supplierRazonSocial, Long supplierRuc, Integer supplierContacto) {
        this.id=id;
        this.nombre=nombre;
        this.color=color;
        this.precio=precio;
        this.stock=stock;
        this.estado=estado;
        this.categoryId=categoryId;
        this.categoryNombre=categoryNombre;
        this.supplierId=supplierId;
        this.supplierRazonSocial=supplierRazonSocial;
        this.supplierRuc=supplierRuc;
        this.supplierContacto=supplierContacto;

    }

}
