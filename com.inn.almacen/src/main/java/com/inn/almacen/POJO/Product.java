package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Product.getById", query = "select new com.inn.almacen.WRAPPER.ProductWrapper(p.id, p.prodDesc, p.prodCode, p.precio, p.stock, p.estado, p.category.catId, p.category.catName, p.supplier.id, p.supplier.razonSocial, p.supplier.ruc, p.supplier.contacto) from Product p where p.id=:id")
@NamedQuery(name = "Product.getAllProduct", query = "select new com.inn.almacen.WRAPPER.ProductWrapper(p.id, p.prodDesc, p.prodCode, p.precio, p.stock, p.estado, p.category.catId, p.category.catName, p.supplier.id, p.supplier.razonSocial, p.supplier.ruc, p.supplier.contacto) from Product p")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="product")
public class Product implements Serializable {

    public static final Long serialVersionUid= 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="prodId")
    private Integer id;

    @Column(name="prodDesc")
    private String prodDesc;

    @Column(name = "prodCode")
    private String prodCode;

    @Column(name = "precio")
    private Float precio;

    @Column(name = "prodStock")
    private Integer stock;

    @Column(name="prodState")
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_fk", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_fk", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_fk", nullable = false)
    private Location location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prices_fk", nullable = false)
    private Prices prices;

}