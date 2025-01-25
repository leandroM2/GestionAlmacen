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

    private String locationName;

    public OutcomeDetailWrapper(Integer id, Integer cantidad, Float precioDeVenta, Integer saldo, Integer outcomeId,
                                Date outcomeFecha, Boolean outcomeEstado, Integer outcomeClientId,
                                String outcomeClientRazonSocial, Long outcomeClientRuc, String outcomeClientCorreo,
                                Integer outcomeClientContacto, String outcomeClientDireccion, Integer outcomeUserId,
                                String outcomeUserNombre, Integer outcomeUserAuthId, String outcomeUserAuthNombre,
                                String productId, String productNombre, String productColor, Integer productStock,
                                Boolean productEstado, Integer productCategoryId, String productCategoryNombre,
                                Integer productSupplierId, String productSupplierRazonSocial, Long productSupplierRuc,
                                Integer productSupplierContacto, Integer typeId, String typeName, Integer locationId,
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
        this.locationName = locationName;
    }
}