package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@NamedQuery(name = "Outcome.getAllOutcome", query = "select new com.inn.almacen.WRAPPER.OutcomeWrapper(o.id, o.fecha, o.client.id, o.client.razonSocial, o.client.ruc, o.client.correo, o.client.contacto, o.client.direccion, o.user.id, o.user.nombre, o.user.email, o.user.estado) from Outcome o")

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_fk", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;
}