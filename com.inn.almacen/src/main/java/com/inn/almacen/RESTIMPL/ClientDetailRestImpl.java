package com.inn.almacen.RESTIMPL;

import com.inn.almacen.REST.ClientDetailRest;
import com.inn.almacen.SERVICE.ClientDetailService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.ClientDetailWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ClientDetailRestImpl implements ClientDetailRest {

    @Autowired
    ClientDetailService clientDetailService;

    @Override
    public ResponseEntity<String> addNewClientDetail(Map<String, String> requestMap) {
        try{
            return clientDetailService.addNewClientDetail(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ClientDetailWrapper>> getAllClientDetail() {
        try{
            return clientDetailService.getAllClientDetail();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateClientDetail(Map<String, String> requestMap) {
        try{
            return clientDetailService.updateClientDetail(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteClientDetail(Integer id) {
        try{
            return clientDetailService.deleteClientDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ClientDetailWrapper>> getById(Integer id) {
        try{
            return clientDetailService.getById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ClientDetailWrapper>> getByFk(Integer client_fk, Integer cliDetId) {
        try{
            return clientDetailService.getByFk(client_fk, cliDetId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ClientDetailWrapper>> getAllByFk(Integer client_fk) {
        try{
            return clientDetailService.getAllByFk(client_fk);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
