package com.inn.almacen.dao;

import com.inn.almacen.POJO.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierDao extends JpaRepository<Supplier, Integer> {

    List<Supplier> getAllSupplier();


}
