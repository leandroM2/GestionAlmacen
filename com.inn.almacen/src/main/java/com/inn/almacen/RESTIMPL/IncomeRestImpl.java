package com.inn.almacen.RESTIMPL;

import com.inn.almacen.REST.IncomeRest;
import com.inn.almacen.SERVICE.IncomeService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.IncomeWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class IncomeRestImpl implements IncomeRest {

    @Autowired
    IncomeService incomeService;

    @Override
    public ResponseEntity<String> addNewIncome(Map<String, String> requestMap) {
        try {
            return incomeService.addNewIncome(requestMap);

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<IncomeWrapper>> getAllIncome() {
        try {
            return incomeService.getAllIncome();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateIncome(Map<String, String> requestMap) {
        try {
            return incomeService.updateIncome(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteIncome(Integer id) {
        try {
            return incomeService.deleteIncome(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<IncomeWrapper>> getById(Integer id) {
        try {
            return incomeService.getById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> authorizeIncome(Integer id) {
        try {
            return incomeService.authorizeIncome(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}