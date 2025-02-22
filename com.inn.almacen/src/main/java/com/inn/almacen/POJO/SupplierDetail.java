package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="SupplierDetail.getById",
query = "select sd from SupplierDetail sd where sd.id=:id")

@NamedQuery(name = "SupplierDetail.getViewById",
query="select new com.inn.almacen.WRAPPER.SupplierDetailWrapper " +
        "(sd.id, sd.supDetId, sd.supDetFactory, sd.supDetAddress, sd.supDetNumber, sd.supDetState, sd.supplier.id) " +
        "from SupplierDetail sd where sd.id=:id")

@NamedQuery(name = "SupplierDetail.getAllSupplierDetail",
query = "select new com.inn.almacen.WRAPPER.SupplierDetailWrapper " +
        "(sd.id, sd.supDetId, sd.supDetFactory, sd.supDetAddress, sd.supDetNumber, sd.supDetState, sd.supplier.id) " +
        "from SupplierDetail sd")

@NamedQuery(name = "SupplierDetail.getViewBySupDetId",
query = "select new com.inn.almacen.WRAPPER.SupplierDetailWrapper " +
        "(sd.id, sd.supDetId, sd.supDetFactory, sd.supDetAddress, sd.supDetNumber, sd.supDetState, sd.supplier.id) " +
        "from SupplierDetail sd where sd.supplier.id=:supplier_fk and sd.supDetId=:supDetId")

@NamedQuery(name = "SupplierDetail.getAllBySupplier",
        query = "select new com.inn.almacen.WRAPPER.SupplierDetailWrapper " +
                "(sd.id, sd.supDetId, sd.supDetFactory, sd.supDetAddress, sd.supDetNumber, sd.supDetState, sd.supplier.id) " +
                "from SupplierDetail sd where sd.supplier.id=:supplier_fk")


//new com.inn.almacen.WRAPPER.SupplierDetailWrapper (sd.id, sd.supDetId, sd.supDetFactory, sd.supDetAddress, sd.supDetNumber, sd.supDetState, sd.supplier.id)

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="supplierDetail")
public class SupplierDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="supDetId")
    private Integer supDetId;

    @Column(name="supDetFactory")
    private String supDetFactory;

    @Column(name="supDetAddress")
    private String supDetAddress;

    @Column(name="supDetNumber")
    private String supDetNumber;

    @Column(name = "supDetState")
    private Boolean supDetState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supplier_fk", nullable = false)
    private Supplier supplier;

}
