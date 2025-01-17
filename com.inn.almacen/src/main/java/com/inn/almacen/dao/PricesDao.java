package com.inn.almacen.dao;

import com.inn.almacen.POJO.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PricesDao extends JpaRepository<Prices, Integer> {

    //Prices getById(@Param("prodKey") String prodKey, @Param("prodId") Integer prodId);
    Prices getById(@Param("prodId") Integer prodId);

    //List<Prices> getAllPrices(@Param("prodKey") String prodKey);
    List<Prices> getAllPrices();

}
