package com.inn.almacen.dao;

import com.inn.almacen.POJO.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationDao extends JpaRepository<Location, Integer> {

    Location getById(@Param("brandId") Integer locationId);

    List<Location> getAllLocation();
}
