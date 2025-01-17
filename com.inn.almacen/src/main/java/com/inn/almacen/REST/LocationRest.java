package com.inn.almacen.REST;

import com.inn.almacen.POJO.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/location")
public interface LocationRest {
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewLocation(@RequestBody(required = true)
                                       Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<Location>> getAllLocation(@RequestParam(required = false)
                                            String filterValue);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateLocation(@RequestBody(required = true)
                                       Map<String, String> requestMap);

    @PostMapping(path = "/delete/{locationId}")
    ResponseEntity<String> deleteLocation(@PathVariable Integer locationId);

    @PostMapping(path = "/get/{locationId}")
    ResponseEntity<List<Location>> getById(@PathVariable Integer locationId);
}
