package com.inn.almacen.RESTIMPL;

import com.inn.almacen.SERVICE.SupplierDetailService;
import com.inn.almacen.REST.SupplierDetailRest;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.SupplierDetailWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SupplierDetailRestImpl implements SupplierDetailRest {

    @Autowired
    SupplierDetailService supplierDetailService;

    @Override
    public ResponseEntity<String> addNewSupplierDetail(Map<String, String> requestMap) {
        try{
            return supplierDetailService.addNewSupplierDetail(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SupplierDetailWrapper>> getAllSupplierDetail() {
        try{
            return supplierDetailService.getAllSupplierDetail();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateSupplierDetail(Map<String, String> requestMap) {
        try{
            return supplierDetailService.updateSupplierDetail(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteSupplierDetail(Integer id) {
        try{
            return supplierDetailService.deleteSupplierDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SupplierDetailWrapper>> getViewById(Integer id) {
        try{
            return supplierDetailService.getViewById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SupplierDetailWrapper>> getViewBySupDetId(Integer supplier_fk, Integer supDetId) {
        try{
            return supplierDetailService.getViewBySupDetId(supplier_fk, supDetId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SupplierDetailWrapper>> getAllBySupplier(Integer supplier_fk) {
        try{
            return supplierDetailService.getAllBySupplier(supplier_fk);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
