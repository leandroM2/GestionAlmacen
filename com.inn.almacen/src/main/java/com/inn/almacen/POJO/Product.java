package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Product.getById", query = "select new com.inn.almacen.WRAPPER.ProductWrapper(p.id, p.nombre, p.color, p.precio, p.stock, p.estado, p.category.id, p.category.nombre, p.supplier.id, p.supplier.razonSocial, p.supplier.ruc, p.supplier.contacto) from Product p where p.id=:id")
@NamedQuery(name = "Product.getAllProduct", query = "select new com.inn.almacen.WRAPPER.ProductWrapper(p.id, p.nombre, p.color, p.precio, p.stock, p.estado, p.category.id, p.category.nombre, p.supplier.id, p.supplier.razonSocial, p.supplier.ruc, p.supplier.contacto) from Product p")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="product")
public class Product implements Serializable {

    public static final Long serialVersionUid= 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @Column(name = "color")
    private String color;

    @Column(name = "precio")
    private Float precio;

    @Column(name = "stock")
    private Integer stock;

    @Column(name="estado")
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_fk", nullable = false)
    private Supplier supplier;

}