package com.inn.almacen.WRAPPER;

import lombok.Data;


@Data
public class ProductWrapper {

    private Integer id;

    private String nombre;

    private String color;

    private Float precio;

    private Integer stock;

    private Boolean estado;

    private Integer categoryId;

    private String categoryNombre;

    private Integer supplierId;

    private String supplierRazonSocial;

    private Long supplierRuc;

    private Integer supplierContacto;

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
