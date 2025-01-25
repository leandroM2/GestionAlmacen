package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Date;

@Data
public class IncomeDetailWrapper {

    private Integer id;

    private Integer cantidad;

    private Float precioVentaUnit;

    private Float oldPrecioVenta;

    private Integer saldo;

    private Integer incomeId;

    private Date incomeFecha;

    private Boolean incomeEstado;

    private Integer incomeUserId;

    private String incomeUserNombre;

    private Integer incomeUserAuthId;

    private String incomeUserAuthNombre;

    private String productId;

    private String productNombre;

    private String productColor;

    private Integer productStock;

    private Boolean productEstado;

    private Integer productCategoryId;

    private String productCategoryNombre;

    private Integer productSupplierId;

    private String productSupplierRazonSocial;

    private Long productSupplierRuc;

    private Integer productSupplierContacto;

    private Integer typeId;

    private String typeName;

    private Integer locationId;

    private String locationFloor;


    public IncomeDetailWrapper(Integer id, Integer cantidad, Float precioVentaUnit, Float oldPrecioVenta, Integer saldo, Integer incomeId,
                               java.util.Date incomeFecha, Boolean incomeEstado, Integer incomeUserId,
                               String incomeUserNombre, Integer incomeUserAuthId,
                               String incomeUserAuthNombre, String productId, String productNombre,
                               String productColor, Integer productStock,
                               Boolean productEstado, Integer productCategoryId, String productCategoryNombre,
                               Integer productSupplierId, String productSupplierRazonSocial,
                               Long productSupplierRuc, Integer productSupplierContacto,
                               Integer typeId, String typeName, Integer locationId, String locationFloor) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioVentaUnit = precioVentaUnit;
        this.oldPrecioVenta=oldPrecioVenta;
        this.saldo=saldo;
        this.incomeId = incomeId;
        this.incomeFecha = (Date) incomeFecha;
        this.incomeEstado = incomeEstado;
        this.incomeUserId = incomeUserId;
        this.incomeUserNombre = incomeUserNombre;
        this.incomeUserAuthId = incomeUserAuthId;
        this.incomeUserAuthNombre = incomeUserAuthNombre;
        this.productId = productId;
        this.productNombre = productNombre;
        this.productColor = productColor;
        this.productStock = productStock;
        this.productEstado = productEstado;
        this.productCategoryId = productCategoryId;
        this.productCategoryNombre = productCategoryNombre;
        this.productSupplierId = productSupplierId;
        this.productSupplierRazonSocial = productSupplierRazonSocial;
        this.productSupplierRuc = productSupplierRuc;
        this.productSupplierContacto = productSupplierContacto;
        this.typeId = typeId;
        this.typeName = typeName;
        this.locationId = locationId;
        this.locationFloor = locationFloor;
    }
}