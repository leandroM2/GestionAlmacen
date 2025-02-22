package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Supplier;
import com.inn.almacen.POJO.SupplierDetail;
import com.inn.almacen.SERVICE.SupplierDetailService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.SupplierDetailWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.SupplierDetailDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SupplierDetailServiceImpl implements SupplierDetailService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    SupplierDetailDao supplierDetailDao;
    
    @Override
    public ResponseEntity<String> addNewSupplierDetail(Map<String, String> requestMap) {
        log.info("Entra a add new supplierDetail");
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateSupplierDetailMap(requestMap, false)){
                    supplierDetailDao.save(getSupplierDetailFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Nueva fábrica de proveedor registrada exitosamente", HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SupplierDetailWrapper>> getAllSupplierDetail() {
        log.info("Dentro de Get All SupplierDetail");
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                return new ResponseEntity<>(supplierDetailDao.getAllSupplierDetail(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateSupplierDetail(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateSupplierDetailMap(requestMap, true)){
                    Optional optional=supplierDetailDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        supplierDetailDao.save(getSupplierDetailFromMap(requestMap,true));
                        return AlmacenUtils.getResponseEntity("Fábrica de proveedor actualizada correctamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de fábrica no existe.", HttpStatus.OK);
                    }
                }
                return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
            }
            return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteSupplierDetail(Integer id) {
        log.info("Dentro de delete SupplierDetail");
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=supplierDetailDao.findById(id);
                if(!optional.isEmpty()){
                    supplierDetailDao.save(stateById(id));
                    return AlmacenUtils.getResponseEntity("Estado de fábrica de proveedor actualizado correctamente.", HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity("Id de fábrica no existe.", HttpStatus.OK);

            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SupplierDetailWrapper>> getViewById(Integer id) {
        log.info("Dentro de get supplierDetail by id");
        try{
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=supplierDetailDao.findById(id);
                if(!optional.isEmpty()){
                    List<SupplierDetailWrapper> cdw=new ArrayList<>();
                    cdw.add(supplierDetailDao.getViewById(id));
                    return new ResponseEntity<>(cdw,HttpStatus.OK);
                }
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SupplierDetailWrapper>> getViewBySupDetId(Integer supplier_fk, Integer supDetId) {
        log.info("Dentro de get supplierDetail by FK");
        try{
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<SupplierDetailWrapper> cdw=supplierDetailDao.getViewBySupDetId(supplier_fk,supDetId);
                if(cdw.size()==1) return new ResponseEntity<>(cdw, HttpStatus.OK);
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SupplierDetailWrapper>> getAllBySupplier(Integer supplier_fk) {
        log.info("Dentro de get all supplierDetail by FK");
        try{
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<SupplierDetailWrapper> cdw=supplierDetailDao.getAllBySupplier(supplier_fk);
                if(cdw.size()>0) return new ResponseEntity<>(cdw, HttpStatus.OK);
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private boolean validateSupplierDetailMap(Map<String, String> requestMap, boolean isUpd) {
        if(requestMap.containsKey("supDetFactory") && requestMap.containsKey("supDetAddress")
                && requestMap.containsKey("supDetNumber") && requestMap.containsKey("supplier_fk")){
            if(requestMap.containsKey("id") && isUpd){
                return true;
            }else if(!isUpd){
                return true;
            }
        }
        return false;
    }
    private SupplierDetail getSupplierDetailFromMap(Map<String, String> requestMap, boolean isUpd) {
        SupplierDetail supplierDetail=new SupplierDetail();

        Supplier supplier=new Supplier();
        supplier.setId(Integer.parseInt(requestMap.get("supplier_fk")));
        supplierDetail.setSupplier(supplier);

        if(isUpd) supplierDetail=supplierDetailDao.getById(Integer.parseInt(requestMap.get("id")));

        supplierDetail.setSupDetFactory(requestMap.get("supDetFactory"));
        supplierDetail.setSupDetAddress(requestMap.get("supDetAddress"));
        supplierDetail.setSupDetNumber(requestMap.get("supDetNumber"));
        if(!isUpd){
            supplierDetail.setSupDetState(true);
            supplierDetail.setSupDetId(getTotalDetails(Integer.parseInt(requestMap.get("supplier_fk"))));
        }
        return supplierDetail;
    }

    private Integer getTotalDetails(Integer supplier_fk) {
        List<SupplierDetailWrapper> sdw=supplierDetailDao.getAllBySupplier(supplier_fk);
        return sdw.size()+1;

    }
    private SupplierDetail stateById(Integer id) {
        SupplierDetail supplierDetail=supplierDetailDao.getById(id);
        supplierDetail.setSupDetState(!supplierDetail.getSupDetState());
        return supplierDetail;
    }
}
