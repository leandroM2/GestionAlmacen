package com.inn.almacen.SERVICEIMPL;

import com.google.common.base.Strings;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Brand;
import com.inn.almacen.SERVICE.BrandService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.BrandDao;
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
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandDao brandDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewBrand(Map<String, String> requestMap) {
        log.info("Dentro de Add New Brand");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateBrandMap(requestMap,false)){
                    brandDao.save(getBrandFromMap(requestMap,false));
                    return AlmacenUtils.getResponseEntity
                            ("Nueva marca agregada con exito.", HttpStatus.OK);

                }
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Brand>> getAllBrand(String filterValue) {
        log.info("Dentro de Get All Brand");
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Brand>>(brandDao.getAllBrand(),HttpStatus.OK);
            }
            return new ResponseEntity<>(brandDao.findAll(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Brand>>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateBrand(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateBrandMap(requestMap, true)){
                    Optional optional = brandDao.findById(Integer.parseInt(requestMap.get("brandId")));
                    if(!optional.isEmpty()){
                        brandDao.save(getBrandFromMap(requestMap, true));
                        return AlmacenUtils.getResponseEntity("Marca actualizada correctamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de Marca no existe.", HttpStatus.OK);
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
    public ResponseEntity<String> deleteBrand(Integer brandId) {
        log.info("Dentro de delete Brand");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=brandDao.findById(brandId);
                if(!optional.isEmpty()){
                    brandDao.save(stateById(brandId));
                    return AlmacenUtils.getResponseEntity("Estado de marca actualizado correctamente", HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity("Id de Marca no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<Brand>> getById(Integer brandId) {
        log.info("Dentro de get Brand by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=brandDao.findById(brandId);
                if(!optional.isEmpty()){
                    List<Brand> myList = new ArrayList<>();
                    myList.add(brandDao.getById(brandId));
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

    private boolean validateBrandMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("brandName")){
            if(requestMap.containsKey("brandId") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Brand getBrandFromMap(Map<String, String> requestMap, boolean isUpd) {
        Brand brand=new Brand();
        if(isUpd){
            brand.setBrandId(Integer.parseInt(requestMap.get("brandId")));
        }
        brand.setBrandName(requestMap.get("brandName"));
        brand.setBrandState(true);
        return brand;
    }


    private Brand stateById(Integer brandId) {
        Brand brand;
        brand=brandDao.getById(brandId);
        brand.setBrandState(!brand.getBrandState());
        return brand;
    }
}
