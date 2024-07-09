package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "OutcomeDetail.getById", query = "select new com.inn.almacen.WRAPPER.OutcomeDetailWrapper(i.id, i.cantidad, i.precioDeVenta, i.saldo, i.outcome.id, i.outcome.fecha, i.outcome.estado, i.outcome.client.id, i.outcome.client.razonSocial, i.outcome.client.ruc, i.outcome.client.correo, i.outcome.client.contacto, i.outcome.client.direccion, i.outcome.user.id, i.outcome.user.nombre, i.outcome.userAuth.id, i.outcome.userAuth.nombre, i.product.id, i.product.nombre, i.product.color, i.product.precio, i.product.stock, i.product.estado, i.product.category.id, i.product.category.nombre, i.product.supplier.id, i.product.supplier.razonSocial, i.product.supplier.ruc, i.product.supplier.contacto) from OutcomeDetail i where i.id=:id")
@NamedQuery(name = "OutcomeDetail.getAllOutcomeDetail", query = "select new com.inn.almacen.WRAPPER.OutcomeDetailWrapper(i.id, i.cantidad, i.precioDeVenta, i.saldo, i.outcome.id, i.outcome.fecha, i.outcome.estado, i.outcome.client.id, i.outcome.client.razonSocial, i.outcome.client.ruc, i.outcome.client.correo, i.outcome.client.contacto, i.outcome.client.direccion, i.outcome.user.id, i.outcome.user.nombre, i.outcome.userAuth.id, i.outcome.userAuth.nombre, i.product.id, i.product.nombre, i.product.color, i.product.precio, i.product.stock, i.product.estado, i.product.category.id, i.product.category.nombre, i.product.supplier.id, i.product.supplier.razonSocial, i.product.supplier.ruc, i.product.supplier.contacto) from OutcomeDetail i")
@NamedQuery(name = "OutcomeDetail.getAllByFk", query = "select new com.inn.almacen.WRAPPER.KardexDetailWrapper(i.id, i.product.nombre, i.cantidad, i.precioDeVenta, i.product.precio, i.saldo, i.cantidad*i.precioDeVenta) from OutcomeDetail i where i.outcome.id=:outcome_fk")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="outcomeDetail")
public class OutcomeDetail implements Serializable {

    public static final Long serialVersionUid= 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precioDeVenta")
    private Float precioDeVenta;

    @Column(name="saldo")
    private Integer saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outcome_fk", nullable = false)
    private Outcome outcome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk", nullable = false)
    private Product product;
}