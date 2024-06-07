package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Date;

@Data
public class OutcomeWrapper {

    private Integer id;

    private Date fecha;

    private Integer clientId;

    private String clientRazonSocial;

    private Long clientRuc;

    private String clientCorreo;

    private Integer clientContacto;

    private String clientDireccion;

    private Integer userId;

    private String userNombre;

    private String userEmail;

    private Boolean userEstado;

    public OutcomeWrapper(Integer id, java.util.Date fecha, Integer clientId, String clientRazonSocial,
                          Long clientRuc, String clientCorreo, Integer clientContacto,
                          String clientDireccion, Integer userId, String userNombre,
                          String userEmail, Boolean userEstado) {
        this.id = id;
        this.fecha = (Date) fecha;
        this.clientId = clientId;
        this.clientRazonSocial = clientRazonSocial;
        this.clientRuc = clientRuc;
        this.clientCorreo = clientCorreo;
        this.clientContacto = clientContacto;
        this.clientDireccion = clientDireccion;
        this.userId = userId;
        this.userNombre = userNombre;
        this.userEmail = userEmail;
        this.userEstado = userEstado;
    }
}