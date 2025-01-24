package com.inn.almacen.RESTIMPL;

import com.inn.almacen.POJO.Location;
import com.inn.almacen.REST.LocationRest;
import com.inn.almacen.SERVICE.LocationService;
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
public class LocationRestImpl implements LocationRest {

    @Autowired
    LocationService locationService;

    @Override
    public ResponseEntity<String> addNewLocation(Map<String, String> requestMap) {
        try {
            return locationService.addNewLocation(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Location>> getAllLocation(String filterValue) {
        try {
            return locationService.getAllLocation(filterValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateLocation(Map<String, String> requestMap) {
        try {
            return locationService.updateLocation(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteLocation(Integer locationId) {
        try {
            return locationService.deleteLocation(locationId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Location>> getById(Integer locationId) {
        try {
            return locationService.getById(locationId);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Location>>( new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
