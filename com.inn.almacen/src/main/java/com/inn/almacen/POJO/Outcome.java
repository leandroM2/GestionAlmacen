package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@NamedQuery(name = "Outcome.getById", query = "select new com.inn.almacen.WRAPPER.OutcomeWrapper(o.id, o.fecha, o.tipoPago, o.factura, o.estado, o.client.id, o.client.razonSocial, o.client.ruc, o.client.correo, o.client.contacto, o.client.direccion, o.user.id, o.user.nombre, o.userAuth.id, o.userAuth.nombre) from Outcome o where o.id=:id")
@NamedQuery(name = "Outcome.getAllOutcome", query = "select new com.inn.almacen.WRAPPER.OutcomeWrapper(o.id, o.fecha, o.tipoPago, o.factura, o.estado, o.client.id, o.client.razonSocial, o.client.ruc, o.client.correo, o.client.contacto, o.client.direccion, o.user.id, o.user.nombre, o.userAuth.id, o.userAuth.nombre) from Outcome o")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="outcome")
public class Outcome implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="fecha")
    private Date fecha;

    @Column(name = "tipoPago")
    private String tipoPago;

    @Column(name="factura")
    private String factura;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_fk", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_fk", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autorizador_fk", nullable = false)
    private User userAuth;
}