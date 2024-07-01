package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Date;

@Data
public class IncomeWrapper {
    private Integer id;

    private Date fecha;

    private String tipoPago;

    private Boolean estado;

    private Integer userId;

    private String userNombre;

    private Integer userAuthId;

    private String userAuthNombre;

    public IncomeWrapper(Integer id, java.util.Date fecha, String tipoPago, Boolean estado,
                         Integer receptorId, String userNombre,
                         Integer userAuthId, String userAuthNombre) {
        this.id = id;
        this.fecha = (Date) fecha;
        this.tipoPago=tipoPago;
        this.estado = estado;
        this.userId = receptorId;
        this.userNombre = userNombre;
        this.userAuthId = userAuthId;
        this.userAuthNombre = userAuthNombre;
    }
}