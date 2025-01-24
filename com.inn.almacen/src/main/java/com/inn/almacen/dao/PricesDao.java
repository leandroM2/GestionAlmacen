package com.inn.almacen.dao;

import com.inn.almacen.POJO.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PricesDao extends JpaRepository<Prices, Integer> {

    Prices getById(@Param("prodId") String prodId);

    List<Prices> getAllPrices();

}
