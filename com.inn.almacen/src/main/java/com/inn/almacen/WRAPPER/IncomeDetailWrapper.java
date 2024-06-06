package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Date;

@Data
public class IncomeDetailWrapper {

    Integer id;

    Integer cantidad;

    Integer incomeId;

    Date incomeFecha;

    Integer incomeSupplierId;

    String incomeSupplierRazonSocial;

    Long incomeSupplierRuc;

    Integer incomeSupplierContacto;

    Integer productId;

    String productNombre;

    String productColor;

    Float productPrecio;

    Integer productStock;

    Boolean productEstado;

    Integer productCategoryId;

    String productCategoryNombre;

    Integer productSupplierId;

    String productSupplierRazonSocial;

    Long productSupplierRuc;

    Integer productSupplierContacto;

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