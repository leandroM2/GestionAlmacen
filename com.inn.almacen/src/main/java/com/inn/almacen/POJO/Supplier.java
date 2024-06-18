package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="Supplier.getById",query = "select s from Supplier s where s.id in (select p.supplier from Product p where p.estado=1) and s.id=:id")

@NamedQuery(name="Supplier.getAllSupplier",query = "select s from Supplier s where s.id in (select p.supplier from Product p where p.estado=1)")


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="supplier")
public class Supplier implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "razonSocial")
    private String razonSocial;

    @Column(name = "ruc")
    private Long ruc;

    @Column(name = "contacto")
    private Integer contacto;
}
