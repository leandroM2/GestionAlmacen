package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@NamedQuery(name = "Income.getById", query = "select new com.inn.almacen.WRAPPER.IncomeWrapper(i.id, i.fecha, i.tipoPago, i.estado, i.user.id, i.user.nombre,  i.userAuth.id, i.userAuth.nombre) from Income i where i.id=:id")
@NamedQuery(name = "Income.getAllIncome", query = "select new com.inn.almacen.WRAPPER.IncomeWrapper(i.id, i.fecha, i.tipoPago, i.estado, i.user.id, i.user.nombre,  i.userAuth.id, i.userAuth.nombre) from Income i")

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

    @Column(name="tipoPago")
    private String tipoPago;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_fk", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autorizador_fk", nullable = true)
    private User userAuth;

}