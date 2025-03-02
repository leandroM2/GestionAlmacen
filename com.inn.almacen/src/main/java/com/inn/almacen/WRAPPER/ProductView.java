package com.inn.almacen.WRAPPER;

import lombok.Data;

@Data
public class ProductView {
    private String id;

    private String prodDesc;

    private String prodCode;

    private Integer prodStock;

    private Boolean prodState;

    private Integer catId;

    private Integer supplierDetailId;

    private Integer typeId;

    private Integer locationId;

    public ProductView(String id, String prodDesc, String prodCode, Integer prodStock, Boolean prodState,
                       Integer catId, Integer supplierDetailId, Integer typeId, Integer locationId) {
        this.id = id;
        this.prodDesc = prodDesc;
        this.prodCode = prodCode;
        this.prodStock = prodStock;
        this.prodState = prodState;
        this.catId = catId;
        this.supplierDetailId = supplierDetailId;
        this.typeId = typeId;
        this.locationId=locationId;
    }
}
