package com.inn.almacen.WRAPPER;

import lombok.Data;


@Data
public class ProductWrapper {

    Integer id;

    String nombre;

    String descripcion;

    Float precio;

    Integer stock;

    Boolean estado;

    Integer categoryId;

    String categoryNombre;

    public ProductWrapper(Integer id, String nombre, String descripcion, Float precio,
                          Integer stock, Boolean estado, Integer categoryId, String categoryNombre) {
        this.id=id;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.precio=precio;
        this.stock=stock;
        this.estado=estado;
        this.categoryId=categoryId;
        this.categoryNombre=categoryNombre;

    }

}
