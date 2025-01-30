package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Date;

@Data
public class OutcomeDetailWrapper {

    private Integer id;

    private Integer cantidad;

    private Float precioDeVenta;

    private Integer saldo;

    private Integer outcomeId;

    private Date outcomeFecha;

    private Boolean outcomeEstado;

    private Integer outcomeClientId;

    private String outcomeClientRazonSocial;

    private Long outcomeClientRuc;

    private String outcomeClientCorreo;

    private Integer outcomeClientContacto;

    private String outcomeClientDireccion;

    private Integer outcomeUserId;

    private String outcomeUserNombre;

    private Integer outcomeUserAuthId;

    private String outcomeUserAuthNombre;

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

    private String locationName;

    public OutcomeDetailWrapper(Integer id, Integer cantidad, Float precioDeVenta, Integer saldo, Integer outcomeId,
                                Date outcomeFecha, Boolean outcomeEstado, Integer outcomeClientId,
                                String outcomeClientRazonSocial, Long outcomeClientRuc, String outcomeClientCorreo,
                                Integer outcomeClientContacto, String outcomeClientDireccion, Integer outcomeUserId,
                                String outcomeUserNombre, Integer outcomeUserAuthId, String outcomeUserAuthNombre,
                                String prodId, String prodDesc, String prodCode, Integer prodStock,
                                Boolean prodState, Integer catId, String catName,
                                Integer supplierId, String supplierRazonSocial, Long supplierRuc,
                                Integer supplierContacto, Integer typeId, String typeName, Integer locationId,
                                String locationName) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioDeVenta = precioDeVenta;
        this.saldo = saldo;
        this.outcomeId = outcomeId;
        this.outcomeFecha = outcomeFecha;
        this.outcomeEstado = outcomeEstado;
        this.outcomeClientId = outcomeClientId;
        this.outcomeClientRazonSocial = outcomeClientRazonSocial;
        this.outcomeClientRuc = outcomeClientRuc;
        this.outcomeClientCorreo = outcomeClientCorreo;
        this.outcomeClientContacto = outcomeClientContacto;
        this.outcomeClientDireccion = outcomeClientDireccion;
        this.outcomeUserId = outcomeUserId;
        this.outcomeUserNombre = outcomeUserNombre;
        this.outcomeUserAuthId = outcomeUserAuthId;
        this.outcomeUserAuthNombre = outcomeUserAuthNombre;
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
        this.locationName = locationName;
    }
}