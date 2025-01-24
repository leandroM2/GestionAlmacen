package com.inn.almacen.SERVICEIMPL;

import com.google.common.base.Strings;
import com.inn.almacen.JWT.Jasypt;
import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Prices;
import com.inn.almacen.SERVICE.PricesService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.PricesWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.PricesDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PricesServiceImpl implements PricesService {

    @Autowired
    PricesDao pricesDao;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    Jasypt jasypt;

    @Override
    public ResponseEntity<String> addNewPrices(Map<String, String> requestMap) {
        log.info("Dentro de Add New Prices");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validatePricesMap(requestMap)){
                    pricesDao.save(getPricesFromMap(requestMap));
                    return AlmacenUtils.getResponseEntity
                            ("Nueva precio agregado con exito.", HttpStatus.OK);
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
    public ResponseEntity<List<Prices>> getAllPrices(String filterValue) {
        log.info("Dentro de Get All Prices");
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Prices>>(pricesDao.getAllPrices(),HttpStatus.OK);
            }
            return new ResponseEntity<>(pricesDao.findAll(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Prices>>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updatePrices(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validatePricesMap(requestMap)){
                    Prices pp = pricesDao.getById(requestMap.get("prodId"));
                    if(!pp.getProdId().isEmpty()){
                        pricesDao.save(getPricesFromMap(requestMap));
                        return AlmacenUtils.getResponseEntity("Precio actualizado correctamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de Producto no existe.", HttpStatus.OK);
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
    public ResponseEntity<String> deletePrices(String prodId) {
        log.info("Dentro de delete Type");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Prices pp=pricesDao.getById(prodId);
                if(!pp.getProdId().isEmpty()){
                    pricesDao.save(stateById(prodId));
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
    public ResponseEntity<List<PricesWrapper>> getById(String prodId) {
        log.info("Dentro de get price by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Prices pp=pricesDao.getById(prodId);
                if(!pp.getProdId().isEmpty()){
                    Prices prices=pricesDao.getById(prodId);
                    List<PricesWrapper> myList = new ArrayList<>();
                    myList.add(new PricesWrapper(prices.getProdId(),Float.valueOf(jasypt.decrypting(prices.getProdPrice())), prices.getPricesState()));
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

    private boolean validatePricesMap(Map<String, String> requestMap) {
        if(requestMap.containsKey("prodPrice") && requestMap.containsKey("prodId")){
            return true;
        }else{
            return false;
        }
    }

    private Prices getPricesFromMap(Map<String, String> requestMap) {
        Prices prices=new Prices();
        String unitPrice;

        prices.setProdId(requestMap.get("prodId"));
        prices.setPricesState(true);

        unitPrice=requestMap.get("prodPrice");
        try{
            unitPrice=jasypt.encrypting(unitPrice);
            byte[] byteData = unitPrice.getBytes("UTF-8");
            Blob blob=new SerialBlob(byteData);
            prices.setProdPrice(blob);
        }catch (Exception e){
            e.printStackTrace();
        }
        return prices;
    }

    private Prices stateById(String prodId) {
        Prices prices;
        prices=pricesDao.getById(prodId);
        prices.setPricesState(!prices.getPricesState());
        return prices;
    }

}
