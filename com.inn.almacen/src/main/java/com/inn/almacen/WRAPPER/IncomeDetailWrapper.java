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

    private String prodId;

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


    public IncomeDetailWrapper(Integer id, Integer cantidad, Float precioVentaUnit, Float oldPrecioVenta, Integer saldo, Integer incomeId,
                               java.util.Date incomeFecha, Boolean incomeEstado, Integer incomeUserId,
                               String incomeUserNombre, Integer incomeUserAuthId,
                               String incomeUserAuthNombre, String prodId, String prodDesc,
                               String prodCode, Integer prodStock,
                               Boolean prodState, Integer catId, String catName,
                               Integer supplierId, String supplierRazonSocial,
                               Long supplierRuc, Integer supplierContacto,
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
        this.prodId = prodId;
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
    }
}