package com.inn.almacen.dao;

import com.inn.almacen.POJO.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierDao extends JpaRepository<Supplier, Integer> {

    Supplier getById(@Param("id") Integer id);
    List<Supplier> getAllSupplier();


}
