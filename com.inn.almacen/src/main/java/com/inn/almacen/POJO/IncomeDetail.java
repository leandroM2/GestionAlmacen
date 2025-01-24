package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "IncomeDetail.getById", query = "select new com.inn.almacen.WRAPPER.IncomeDetailWrapper(i.id, i.cantidad, i.precioVentaUnit, i.oldPrecioVenta, i.saldo, i.income.id, i.income.fecha, i.income.estado, i.income.user.id, i.income.user.nombre, i.income.userAuth.id, i.income.userAuth.nombre, i.product.prodId, i.product.prodDesc, i.product.prodCode,  i.product.prodStock, i.product.prodState, i.product.category.catId, i.product.category.catName, i.product.supplier.id, i.product.supplier.razonSocial, i.product.supplier.ruc, i.product.supplier.contacto) from IncomeDetail i where i.id=:id")
@NamedQuery(name = "IncomeDetail.getAllIncomeDetail", query = "select new com.inn.almacen.WRAPPER.IncomeDetailWrapper(i.id, i.cantidad, i.precioVentaUnit, i.oldPrecioVenta, i.saldo, i.income.id, i.income.fecha, i.income.estado, i.income.user.id, i.income.user.nombre, i.income.userAuth.id, i.income.userAuth.nombre, i.product.prodId, i.product.prodDesc, i.product.prodCode,  i.product.prodStock, i.product.prodState, i.product.category.catId, i.product.category.catName, i.product.supplier.id, i.product.supplier.razonSocial, i.product.supplier.ruc, i.product.supplier.contacto) from IncomeDetail i")
@NamedQuery(name = "IncomeDetail.getAllByFk", query = "select new com.inn.almacen.WRAPPER.KardexDetailWrapper(i.id, i.product.prodDesc, i.cantidad, i.precioVentaUnit," /*i.oldPrecioVenta,*/ +"i.saldo, i.cantidad*i.precioVentaUnit) from IncomeDetail i where i.income.id=:income_fk")
@NamedQuery(name = "IncomeDetail.getByFk", query = "select i from IncomeDetail i where i.id=:id and i.income.id=:income_fk")
@NamedQuery(name = "IncomeDetail.getAllOrderByFk", query = "select new com.inn.almacen.WRAPPER.KardexDetailWrapper(i.product.prodId, i.product.prodDesc, i.cantidad, i.precioVentaUnit, i.cantidad*i.precioVentaUnit) from IncomeDetail i where i.income.id=:income_fk")

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

    @Column(name="precioVentaUnit")
    private Float precioVentaUnit;

    @Column(name = "oldPrecioVenta")
    private Float oldPrecioVenta;

    @Column(name = "saldo")
    private Integer saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_fk", nullable = false)
    private Income income;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk", nullable = false)
    private Product product;
}