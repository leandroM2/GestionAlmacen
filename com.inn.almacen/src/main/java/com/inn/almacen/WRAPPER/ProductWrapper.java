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

    private Integer catId;

    private String catName;

    private Integer supplierId;

    private String supplierRazonSocial;

    private Long supplierRuc;

    private Integer supplierContacto;

    private Integer typeId;

    private String typeName;

    private Integer locationId;

    private String locationFloor;

    private String prodId;

    private Blob prodPrices;

    private String prodPrice;

    public ProductWrapper(String id, String prodDesc, String prodCode, Integer prodStock, Boolean prodState,
                          Integer catId, String catName, Integer supplierId, String supplierRazonSocial,
                          Long supplierRuc, Integer supplierContacto, Integer typeId, String typeName,
                          Integer locationId, String locationFloor, String prodId, String prodPrice) {
        this.id = id;
        this.prodDesc = prodDesc;
        this.prodCode = prodCode;
        this.prodStock = prodStock;
        this.prodState = prodState;
        this.catId = catId;
        this.catName = catName;
        this.supplierId = supplierId;
        this.supplierRazonSocial = supplierRazonSocial;
        this.supplierRuc = supplierRuc;
        this.supplierContacto = supplierContacto;
        this.typeId = typeId;
        this.typeName = typeName;
        this.locationId = locationId;
        this.locationFloor = locationFloor;
        this.prodId = prodId;
        this.prodPrice = prodPrice;
    }


    public ProductWrapper(String id, String prodDesc, String prodCode, Integer prodStock, Boolean prodState,
                          Integer catId, String catName, Integer supplierId, String supplierRazonSocial,
                          Long supplierRuc, Integer supplierContacto, Integer typeId, String typeName,
                          Integer locationId, String locationFloor, String prodId, Blob prodPrices) {
        this.id = id;
        this.prodDesc = prodDesc;
        this.prodCode = prodCode;
        this.prodStock = prodStock;
        this.prodState = prodState;
        this.catId = catId;
        this.catName = catName;
        this.supplierId = supplierId;
        this.supplierRazonSocial = supplierRazonSocial;
        this.supplierRuc = supplierRuc;
        this.supplierContacto = supplierContacto;
        this.typeId = typeId;
        this.typeName = typeName;
        this.locationId = locationId;
        this.locationFloor = locationFloor;
        this.prodId = prodId;
        this.prodPrices = prodPrices;
    }

}
