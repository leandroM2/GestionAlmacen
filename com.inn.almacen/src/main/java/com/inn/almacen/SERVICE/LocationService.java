package com.inn.almacen.SERVICE;

import com.inn.almacen.POJO.Location;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LocationService {
    ResponseEntity<String> addNewLocation(Map<String,String> requestMap);

    ResponseEntity<List<Location>> getAllLocation(String filterValue);

    ResponseEntity<String> updateLocation(Map<String, String> requestMap);

    ResponseEntity<String> deleteLocation(Integer locationId);

    ResponseEntity<List<Location>> getById(Integer locationId);
}
