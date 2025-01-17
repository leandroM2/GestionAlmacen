package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="Location.getById",query = "select l from Location l where l.locationId=:locationId")

@NamedQuery(name="Location.getAllLocation",query = "select l from Location l")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="locationId")
    private Integer locationId;

    @Column(name="locationFloor")
    private String locationFloor;

    @Column(name = "locationState")
    private Boolean locationState;
}
