package com.inn.almacen.dao;

import com.inn.almacen.POJO.SupplierDetail;
import com.inn.almacen.WRAPPER.SupplierDetailWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierDetailDao extends JpaRepository<SupplierDetail, Integer> {

    SupplierDetail getById(@Param("id") Integer id);

    SupplierDetailWrapper getViewById(@Param("id") Integer id);

    List<SupplierDetailWrapper> getAllSupplierDetail();

    List<SupplierDetailWrapper> getViewBySupDetId(@Param("supplier_fk") Integer supplier_fk,
                                                  @Param("supDetId") Integer supDetId);

    List<SupplierDetailWrapper> getAllBySupplier(@Param("supplier_fk") Integer supplier_fk);

}
