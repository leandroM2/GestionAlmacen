package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Income;
import com.inn.almacen.POJO.Supplier;
import com.inn.almacen.SERVICE.IncomeService;

import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.IncomeWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.IncomeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewIncome(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateIncomeMap(requestMap, false)){
                    incomeDao.save(getIncomeFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Ingreso correctamente registrado.", HttpStatus.OK);
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
    public ResponseEntity<List<IncomeWrapper>> getAllIncome() {
        try {
            return new ResponseEntity<>(incomeDao.getAllIncome(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateIncome(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateIncomeMap(requestMap, true)){
                    Optional<Income> optional=incomeDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Income income= getIncomeFromMap(requestMap,true);
                        incomeDao.save(income);
                        return AlmacenUtils.getResponseEntity("Ingreso actualizado exitosamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de ingreso no existe.", HttpStatus.OK);
                    }
                }else{
                    return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> deleteIncome(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional optional=incomeDao.findById(id);
                if(!optional.isEmpty()){
                    incomeDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Ingreso eliminado correctamente.", HttpStatus.OK );
                }
                return AlmacenUtils.getResponseEntity("Id de ingreso no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<IncomeWrapper>> getById(Integer id) {
        log.info("Dentro de get Income by id");
        try {
            if (jwtFilter.isAdmin()){
                Optional optional=incomeDao.findById(id);
                if(!optional.isEmpty()){
                    Income income=incomeDao.getById(id);
                    List<IncomeWrapper> myList = new ArrayList<>();
                    myList.add(new IncomeWrapper(income.getId(), income.getFecha(), income.getSupplier().getId(),
                            income.getSupplier().getRazonSocial(), income.getSupplier().getRuc(), income.getSupplier().getContacto()));
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

    private boolean validateIncomeMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("fecha")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Income getIncomeFromMap(Map<String, String> requestMap, boolean esAdd) {
        Supplier supplier=new Supplier();
        supplier.setId(Integer.parseInt(requestMap.get("supplierId")));

        Income income=new Income();
        if(esAdd){
            income.setId(Integer.parseInt(requestMap.get("id")));
        }

        income.setSupplier(supplier);
        income.setFecha(Date.valueOf(requestMap.get("fecha")));
        return income;
    }

}
