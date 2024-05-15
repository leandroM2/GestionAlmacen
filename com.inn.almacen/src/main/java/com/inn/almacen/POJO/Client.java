package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="client")
public class Client implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "razonSocial")
    private String razonSocial;

    @Column(name = "ruc")
    private Long ruc;

    @Column(name = "correo")
    private Integer correo;

    @Column(name = "contacto")
    private Integer contacto;

    @Column(name = "direccion")
    private Integer direccion;

}
