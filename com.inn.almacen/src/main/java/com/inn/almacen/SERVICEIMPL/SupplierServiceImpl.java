package com.inn.almacen.SERVICEIMPL;

import com.google.common.base.Strings;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Supplier;
import com.inn.almacen.SERVICE.SupplierService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.SupplierDao;
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
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierDao supplierDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewSupplier(Map<String, String> requestMap) {
        log.info("Dentro de add new supplier");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isSuperAdmin()){
                if(validateSupplierMap(requestMap, false)){
                    supplierDao.save(getSupplierFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Nuevo proveedor agregado con exito.", HttpStatus.OK);
                }
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Supplier>> getAllSupplier(String filterValue) {
        log.info("Dentro de get all supplier");
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Supplier>>(supplierDao.getAllSupplier(), HttpStatus.OK);
            }
            return new ResponseEntity<>(supplierDao.findAll(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Supplier>>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateSupplier(Map<String, String> requestMap) {
        log.info("Dentro de update supplier");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isSuperAdmin()){
                if(validateSupplierMap(requestMap, true)){
                    Optional optional= supplierDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        supplierDao.save(getSupplierFromMap(requestMap, true));
                        return AlmacenUtils.getResponseEntity("Proveedor actualizado correctamente.", HttpStatus.OK);
                    }else {
                        return AlmacenUtils.getResponseEntity("Id de proveedor no existe.", HttpStatus.OK);
                    }
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
    public ResponseEntity<String> deleteSupplier(Integer id) {
        log.info("Dentro de delete supplier");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=supplierDao.findById(id);
                if (!optional.isEmpty()){
                    supplierDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Proveedor eliminado correctamente.", HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity("Id de proveedor no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Supplier>> getById(Integer id) {
        log.info("Dentro de get supplier by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=supplierDao.findById(id);
                if(!optional.isEmpty()){
                    List<Supplier> myList = new ArrayList<>();
                    myList.add(supplierDao.getById(id));
                    return new ResponseEntity<>(myList,HttpStatus.OK);
                }
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateSupplierMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("razonSocial") && requestMap.containsKey("ruc") && requestMap.containsKey("contacto")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Supplier getSupplierFromMap(Map<String, String> requestMap, boolean esAdd) {
        Supplier supplier=new Supplier();
        if(esAdd){
            supplier.setId(Integer.parseInt(requestMap.get("id")));
        }
        supplier.setRazonSocial(requestMap.get("razonSocial"));
        supplier.setRuc(Long.parseLong(requestMap.get("ruc")));
        supplier.setContacto(Integer.parseInt(requestMap.get("contacto")));
        return supplier;
    }
}
