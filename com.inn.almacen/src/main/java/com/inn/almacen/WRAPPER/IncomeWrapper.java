package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.sql.Date;

@Data
public class IncomeWrapper {
    Integer id;

    Date fecha;

    Integer supplierId;

    String supplierRazonSocial;

    Long supplierRuc;

    Integer supplierContacto;

    public IncomeWrapper(Integer id, java.util.Date fecha, Integer supplierId,
                         String supplierRazonSocial, Long supplierRuc,
                         Integer supplierContacto) {
        this.id = id;
        this.fecha = (Date) fecha;
        this.supplierId = supplierId;
        this.supplierRazonSocial = supplierRazonSocial;
        this.supplierRuc = supplierRuc;
        this.supplierContacto = supplierContacto;
    }
}
