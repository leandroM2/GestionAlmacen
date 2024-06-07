package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "OutcomeDetail.getAllOutcomeDetail", query = "select new com.inn.almacen.WRAPPER.OutcomeDetailWrapper(i.id, i.cantidad, i.outcome.id, i.outcome.fecha, i.outcome.client.id, i.outcome.client.razonSocial, i.outcome.client.ruc, i.outcome.client.correo, i.outcome.client.contacto, i.outcome.client.direccion, i.outcome.user.id, i.outcome.user.nombre, i.outcome.user.email, i.outcome.user.estado, i.product.id, i.product.nombre, i.product.color, i.product.precio, i.product.stock, i.product.estado, i.product.category.id, i.product.category.nombre, i.product.supplier.id, i.product.supplier.razonSocial, i.product.supplier.ruc, i.product.supplier.contacto) from OutcomeDetail i")

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outcome_fk", nullable = false)
    private Outcome outcome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk", nullable = false)
    private Product product;
}