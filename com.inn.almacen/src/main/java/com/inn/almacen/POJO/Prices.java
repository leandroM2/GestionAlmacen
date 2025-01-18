package com.inn.almacen.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Blob;

@NamedQuery(name="Prices.getById",query = "select p from Prices p where p.prodId=:prodId")
@NamedQuery(name="Prices.getAllPrices",query = "select p from Prices p")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="prices")
public class Prices {
    public static final Long serialVersionUid= 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="prodId")
    private Integer prodId;

    @Column(name = "prodPrice")
    private Blob prodPrice;

    @Column(name = "pricesState")
    private Boolean pricesState;

}
