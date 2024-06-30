package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="Archives.getById",query = "select a from Archives a where a.id=:id")

@NamedQuery(name="Archives.getAllArchives",query = "select a from Archives a")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "archives")
public class Archives implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name="ruta")
    private String ruta;
}