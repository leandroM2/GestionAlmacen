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
@Table(name = "brand")
public class Brand implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="brandId")
    private Integer brandId;

    @Column(name = "brandName")
    private String brandName;

    @Column(name = "brandState")
    private Boolean brandState;

}
