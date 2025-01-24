package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Product.getById",
        query = "select p from Product p where p.prodId=:prodId")

@NamedQuery(name = "Product.getByIdView",
        query = "select new com.inn.almacen.WRAPPER.ProductView" +
                "(p.prodId, p.prodDesc, p.prodCode, p.prodStock, " +
                "p.prodState, p.category.catId, p.supplier.id," +
                " p.type.typeId, p.location.locationId)  " +
                " from Product p where p.prodId=:prodId")

@NamedQuery(name = "Product.getAllProduct",
        query = "select new com.inn.almacen.WRAPPER.ProductView" +
                "(p.prodId, p.prodDesc, p.prodCode, p.prodStock, " +
                "p.prodState, p.category.catId, p.supplier.id," +
                " p.type.typeId, p.location.locationId)  " +
                "from Product p")

@NamedQuery(name="Product.getCountCorr",
        query = "select COUNT(p) from Product p where " +
                "p.prodId like CONCAT(:prodId,'%')")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="product")
public class Product implements Serializable {

    public static final Long serialVersionUid= 123456L;

    @Id
    @Column(name="prodId")
    private String prodId;

    @Column(name="prodDesc")
    private String prodDesc;

    @Column(name = "prodCode")
    private String prodCode;

    @Column(name = "prodStock")
    private Integer prodStock;

    @Column(name="prodState")
    private Boolean prodState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_fk", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_fk", nullable = false)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_fk", nullable = false)
    private Location location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prices_fk", nullable = false)
    private Prices prices;

}