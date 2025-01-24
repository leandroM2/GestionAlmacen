package com.inn.almacen.POJO;



import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="Type.getById",query = "select t from Type t where t.typeId=:typeId")

@NamedQuery(name="Type.getAllType",query = "select t from Type t")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "type")
public class Type implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="typeId")
    private Integer typeId;

    @Column(name = "typeName")
    private String typeName;

    @Column(name = "typeState")
    private Boolean typeState;

}
