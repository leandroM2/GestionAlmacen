package com.inn.almacen.RESTIMPL;

import com.inn.almacen.POJO.Client;
import com.inn.almacen.REST.ClientRest;
import com.inn.almacen.SERVICE.ClientService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ClientRestImpl implements ClientRest {

    @Autowired
    ClientService clientService;

    @Override
    public ResponseEntity<String> addNewClient(Map<String, String> requestMap) {
        try {
            return clientService.addNewClient(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }

        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<Client>> getAllClient(String filterValue) {
        try {
            return clientService.getAllClient(filterValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateClient(Map<String, String> requestMap) {
        try {
            return clientService.updateClient(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteClient(Integer id) {
        try {
            return clientService.deleteClient(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Client>> getById(Integer id) {
        try {
            return clientService.getById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Client>>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
