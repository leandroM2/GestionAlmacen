package com.inn.almacen.RESTIMPL;

import com.inn.almacen.REST.OutcomeRest;
import com.inn.almacen.SERVICE.OutcomeService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.OutcomeWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OutcomeRestImpl implements OutcomeRest {

    @Autowired
    OutcomeService outcomeService;

    @Override
    public ResponseEntity<String> addNewOutcome(Map<String, String> requestMap) {
        try {
            return outcomeService.addNewOutcome(requestMap);

        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<OutcomeWrapper>> getAllOutcome() {
        try {
            return outcomeService.getAllOutcome();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateOutcome(Map<String, String> requestMap) {
        try {
            return outcomeService.updateOutcome(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteOutcome(Integer id) {
        try {
            return outcomeService.deleteOutcome(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<OutcomeWrapper>> getById(Integer id) {
        try {
            return outcomeService.getById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> authorizeOutcome(Integer id) {
        try {
            return outcomeService.authorizeOutcome(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}