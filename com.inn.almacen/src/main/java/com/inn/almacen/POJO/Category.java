package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="Category.getById",query = "select c from Category c where c.id in (select p.category from Product p where p.estado=1) and c.id=:id")

@NamedQuery(name="Category.getAllCategory",query = "select c from Category c")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="category")
public class Category implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;



}