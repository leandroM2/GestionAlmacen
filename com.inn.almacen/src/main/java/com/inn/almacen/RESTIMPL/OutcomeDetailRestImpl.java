package com.inn.almacen.RESTIMPL;

import com.inn.almacen.REST.OutcomeDetailRest;
import com.inn.almacen.SERVICE.OutcomeDetailService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.OutcomeDetailWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OutcomeDetailRestImpl implements OutcomeDetailRest {

    @Autowired
    OutcomeDetailService outcomeDetailService;

    @Override
    public ResponseEntity<String> addNewOutcomeDetail(Map<String, String> requestMap) {
        try {
            return outcomeDetailService.addNewOutcomeDetail(requestMap);

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<OutcomeDetailWrapper>> getAllOutcomeDetail() {
        try {
            return outcomeDetailService.getAllOutcomeDetail();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateOutcomeDetail(Map<String, String> requestMap) {
        try {
            return outcomeDetailService.updateOutcomeDetail(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteOutcomeDetail(Integer id) {
        try {
            return outcomeDetailService.deleteOutcomeDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<OutcomeDetailWrapper>> getById(Integer id) {
        try {
            return outcomeDetailService.getById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}