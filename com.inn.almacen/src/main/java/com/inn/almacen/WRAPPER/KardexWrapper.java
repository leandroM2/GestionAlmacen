package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class KardexWrapper {

    private String id;

    private Date fecha;

    private Boolean estado;

    private String tipoMov;

    private String persona;

    private String archivesId;

    private List<KardexDetailWrapper> detalles;

    public KardexWrapper(String id, java.util.Date fecha, Boolean estado, String tipoMov,
                         String persona, String archivesId, List<KardexDetailWrapper> detalles) {
        this.id = id;
        this.fecha = (Date) fecha;
        this.estado = estado;
        this.tipoMov = tipoMov;
        this.persona=persona;
        this.archivesId = archivesId;
        this.detalles = detalles;
    }
}