package com.inn.almacen.RESTIMPL;

import com.inn.almacen.REST.KardexRest;
import com.inn.almacen.SERVICE.KardexService;
import com.inn.almacen.WRAPPER.KardexWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class KardexRestImpl implements KardexRest {

    @Autowired
    KardexService kardexService;

    @Override
    public ResponseEntity<List<KardexWrapper>> getAllKardex() {
        try {
            return kardexService.getAllKardex();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}