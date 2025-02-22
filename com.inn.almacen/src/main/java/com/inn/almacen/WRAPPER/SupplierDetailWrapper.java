package com.inn.almacen.WRAPPER;

import lombok.Data;

@Data
public class SupplierDetailWrapper {

    private Integer id;

    private Integer supDetId;

    private String supDetFactory;

    private String supDetAddress;

    private String supDetNumber;

    private Boolean supDetState;

    private Integer supplierId;

    public SupplierDetailWrapper(Integer id, Integer supDetId, String supDetFactory, String supDetAddress,
                                 String supDetNumber, Boolean supDetState, Integer supplierId) {
        this.id = id;
        this.supDetId = supDetId;
        this.supDetFactory = supDetFactory;
        this.supDetAddress = supDetAddress;
        this.supDetNumber = supDetNumber;
        this.supDetState = supDetState;
        this.supplierId = supplierId;
    }
}
