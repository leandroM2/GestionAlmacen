package com.inn.almacen.POJO;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name="User.findByEmailId",query="select U from User U where U.email=:email")

@NamedQuery(name="User.findByRol",query="select U from User U where U.id=:id")

@NamedQuery(name="User.getAllUser", query = "select new com.inn.almacen.WRAPPER.UserWrapper(u.id, u.nombre, u.email, u.estado) from User u where u.rol='user' ")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="email")
    private String email;

    @Column(name="contrasena")
    private String contrasena;

    @Column(name="estado")
    private Boolean estado;

    @Column(name="rol")
    private String rol;



}
