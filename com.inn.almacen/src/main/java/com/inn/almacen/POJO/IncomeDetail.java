package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "IncomeDetail.getAllIncomeDetail", query = "select new com.inn.almacen.WRAPPER.IncomeDetailWrapper(i.id, i.cantidad, i.income.id, i.income.fecha, i.income.supplier.id, i.income.supplier.razonSocial, i.income.supplier.ruc, i.income.supplier.contacto,i.product.id, i.product.nombre, i.product.color, i.product.precio, i.product.stock, i.product.estado, i.product.category.id, i.product.category.nombre, i.product.supplier.id, i.product.supplier.razonSocial, i.product.supplier.ruc, i.product.supplier.contacto) from IncomeDetail i")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="incomeDetail")
public class IncomeDetail implements Serializable {

    public static final Long serialVersionUid= 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_fk", nullable = false)
    private Income income;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk", nullable = false)
    private Product product;
}