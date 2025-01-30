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

    private Integer supplierId;

    private Integer typeId;

    private Integer locationId;

    public ProductView(String id, String prodDesc, String prodCode, Integer prodStock, Boolean prodState,
                       Integer catId, Integer supplierId, Integer typeId, Integer locationId) {
        this.id = id;
        this.prodDesc = prodDesc;
        this.prodCode = prodCode;
        this.prodStock = prodStock;
        this.prodState = prodState;
        this.catId = catId;
        this.supplierId = supplierId;
        this.typeId = typeId;
        this.locationId=locationId;
    }
}
