package com.inn.almacen.WRAPPER;

import lombok.Data;

import java.util.List;
@Data
public class SupplierWrapper {

    private Integer id;
    private String razSocial;
    private Long ruc;
    private Integer contactNumber;
    private List<SupplierDetailWrapper> details;

    private boolean expanded;

    public SupplierWrapper(Integer id, String razSocial, Long ruc,
                           Integer contactNumber, List<SupplierDetailWrapper> details, Boolean expanded) {
        this.id = id;
        this.razSocial = razSocial;
        this.ruc = ruc;
        this.contactNumber = contactNumber;
        this.details = details;
        this.expanded = expanded;
    }
}
