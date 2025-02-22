package com.inn.almacen.SERVICE;

import com.inn.almacen.WRAPPER.SupplierDetailWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SupplierDetailService {

    ResponseEntity<String> addNewSupplierDetail(Map<String, String> requestMap);
    ResponseEntity<List<SupplierDetailWrapper>> getAllSupplierDetail();
    ResponseEntity<String> updateSupplierDetail(Map<String, String> requestMap);
    ResponseEntity<String> deleteSupplierDetail(Integer id);
    ResponseEntity<List<SupplierDetailWrapper>> getViewById(Integer id);
    ResponseEntity<List<SupplierDetailWrapper>> getViewBySupDetId(Integer supplier_fk, Integer supDetId);
    ResponseEntity<List<SupplierDetailWrapper>> getAllBySupplier(Integer supplier_fk);

}
