package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Blob;


@Data
public class ProductWrapper {

    private String id;

    private String prodDesc;

    private String prodCode;

    private Integer prodStock;

    private Boolean prodState;

    private Integer categoryId;

    private String categoryNombre;

    private Integer supplierId;

    private String supplierRazonSocial;

    private Long supplierRuc;

    private Integer supplierContacto;

    private Integer brandId;

    private String brandName;

    private Integer locationId;

    private String locationFloor;

    private String prodId;

    private Blob prodPrices;

    private String prodPrice;

    public ProductWrapper(String id, String prodDesc, String prodCode, Integer prodStock, Boolean prodState,
                          Integer categoryId, String categoryNombre, Integer supplierId, String supplierRazonSocial,
                          Long supplierRuc, Integer supplierContacto, Integer brandId, String brandName,
                          Integer locationId, String locationFloor, String prodId, String prodPrice) {
        this.id = id;
        this.prodDesc = prodDesc;
        this.prodCode = prodCode;
        this.prodStock = prodStock;
        this.prodState = prodState;
        this.categoryId = categoryId;
        this.categoryNombre = categoryNombre;
        this.supplierId = supplierId;
        this.supplierRazonSocial = supplierRazonSocial;
        this.supplierRuc = supplierRuc;
        this.supplierContacto = supplierContacto;
        this.brandId = brandId;
        this.brandName = brandName;
        this.locationId = locationId;
        this.locationFloor = locationFloor;
        this.prodId = prodId;
        this.prodPrice = prodPrice;
    }


    public ProductWrapper(String id, String prodDesc, String prodCode, Integer prodStock, Boolean prodState,
                          Integer categoryId, String categoryNombre, Integer supplierId, String supplierRazonSocial,
                          Long supplierRuc, Integer supplierContacto, Integer brandId, String brandName,
                          Integer locationId, String locationFloor, String prodId, Blob prodPrices) {
        this.id = id;
        this.prodDesc = prodDesc;
        this.prodCode = prodCode;
        this.prodStock = prodStock;
        this.prodState = prodState;
        this.categoryId = categoryId;
        this.categoryNombre = categoryNombre;
        this.supplierId = supplierId;
        this.supplierRazonSocial = supplierRazonSocial;
        this.supplierRuc = supplierRuc;
        this.supplierContacto = supplierContacto;
        this.brandId = brandId;
        this.brandName = brandName;
        this.locationId = locationId;
        this.locationFloor = locationFloor;
        this.prodId = prodId;
        this.prodPrices = prodPrices;
    }

}
