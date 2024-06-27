package com.inn.almacen.RESTIMPL;

import com.inn.almacen.REST.IncomeDetailRest;
import com.inn.almacen.SERVICE.IncomeDetailService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.IncomeDetailWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class IncomeDetailRestImpl implements IncomeDetailRest {

    @Autowired
    IncomeDetailService incomeDetailService;

    @Override
    public ResponseEntity<String> addNewIncomeDetail(Map<String, String> requestMap) {
        try {
            return incomeDetailService.addNewIncomeDetail(requestMap);

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<IncomeDetailWrapper>> getAllIncomeDetail() {
        try {
            return incomeDetailService.getAllIncomeDetail();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateIncomeDetail(Map<String, String> requestMap) {
        try {
            return incomeDetailService.updateIncomeDetail(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteIncomeDetail(Integer id) {
        try {
            return incomeDetailService.deleteIncomeDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<IncomeDetailWrapper>> getById(Integer id) {
        try {
            return incomeDetailService.getById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}