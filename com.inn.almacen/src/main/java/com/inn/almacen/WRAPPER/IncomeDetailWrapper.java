package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Date;

@Data
public class IncomeDetailWrapper {

    private Integer id;

    private Integer cantidad;

    private Integer incomeId;

    private Date incomeFecha;

    private Integer incomeSupplierId;

    private String incomeSupplierRazonSocial;

    private Long incomeSupplierRuc;

    private Integer incomeSupplierContacto;

    private Integer productId;

    private String productNombre;

    private String productColor;

    private Float productPrecio;

    private Integer productStock;

    private Boolean productEstado;

    private Integer productCategoryId;

    private String productCategoryNombre;

    private Integer productSupplierId;

    private String productSupplierRazonSocial;

    private Long productSupplierRuc;

    private Integer productSupplierContacto;

    public IncomeDetailWrapper(Integer id, Integer cantidad, Integer incomeId, java.util.Date incomeFecha,
                               Integer incomeSupplierId, String incomeSupplierRazonSocial,
                               Long incomeSupplierRuc, Integer incomeSupplierContacto, Integer productId,
                               String productNombre, String productColor, Float productPrecio,
                               Integer productStock, Boolean productEstado, Integer productCategoryId,
                               String productCategoryNombre, Integer productSupplierId,
                               String productSupplierRazonSocial, Long productSupplierRuc, Integer productSupplierContacto) {
        this.id = id;
        this.cantidad = cantidad;
        this.incomeId = incomeId;
        this.incomeFecha = (Date) incomeFecha;
        this.incomeSupplierId = incomeSupplierId;
        this.incomeSupplierRazonSocial = incomeSupplierRazonSocial;
        this.incomeSupplierRuc = incomeSupplierRuc;
        this.incomeSupplierContacto = incomeSupplierContacto;
        this.productId = productId;
        this.productNombre = productNombre;
        this.productColor = productColor;
        this.productPrecio = productPrecio;
        this.productStock = productStock;
        this.productEstado = productEstado;
        this.productCategoryId = productCategoryId;
        this.productCategoryNombre = productCategoryNombre;
        this.productSupplierId = productSupplierId;
        this.productSupplierRazonSocial = productSupplierRazonSocial;
        this.productSupplierRuc = productSupplierRuc;
        this.productSupplierContacto = productSupplierContacto;
    }
}