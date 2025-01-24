package com.inn.almacen.SERVICE;

import com.inn.almacen.POJO.Prices;
import com.inn.almacen.WRAPPER.PricesWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PricesService {
    ResponseEntity<String> addNewPrices(Map<String,String> requestMap);

    ResponseEntity<List<Prices>> getAllPrices(String filterValue);

    ResponseEntity<String> updatePrices(Map<String, String> requestMap);

    ResponseEntity<String> deletePrices(String prodId);

    ResponseEntity<List<PricesWrapper>> getById(String prodId);
}
