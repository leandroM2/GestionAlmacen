package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Date;

@Data
public class OutcomeWrapper {

    private Integer id;

    private Date fecha;

    private String tipoPago;

    private String factura;

    private Boolean estado;

    private Integer clientId;

    private String clientRazonSocial;

    private Long clientRuc;

    private String clientCorreo;

    private Integer clientContacto;

    private String clientDireccion;

    private Integer userId;

    private String userNombre;

    private Integer userAuthId;

    private String userAuthNombre;

    public OutcomeWrapper(Integer id, java.util.Date fecha, String tipoPago, String factura, Boolean estado, Integer clientId, String clientRazonSocial, Long clientRuc, String clientCorreo, Integer clientContacto, String clientDireccion, Integer userId, String userNombre, Integer userAuthId, String userAuthNombre) {
        this.id = id;
        this.fecha =(Date) fecha;
        this.tipoPago = tipoPago;
        this.factura=factura;
        this.estado = estado;
        this.clientId = clientId;
        this.clientRazonSocial = clientRazonSocial;
        this.clientRuc = clientRuc;
        this.clientCorreo = clientCorreo;
        this.clientContacto = clientContacto;
        this.clientDireccion = clientDireccion;
        this.userId = userId;
        this.userNombre = userNombre;
        this.userAuthId = userAuthId;
        this.userAuthNombre = userAuthNombre;
    }
}