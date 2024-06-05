package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@NamedQuery(name = "Income.getAllIncome", query = "select new com.inn.almacen.WRAPPER.IncomeWrapper(i.id, i.fecha, i.supplier.id, i.supplier.razonSocial, i.supplier.ruc, i.supplier.contacto) from Income i")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="income")
public class Income implements Serializable {

    private static final long serialVersionUID=123456L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="fecha")
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_fk", nullable = false)
    private Supplier supplier;

}
