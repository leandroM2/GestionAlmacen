package com.inn.almacen.SERVICE;

import com.inn.almacen.WRAPPER.KardexWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface KardexService {

    ResponseEntity<String> addNewKardexEntry(Map<String, String> requestMap);

    ResponseEntity<List<KardexWrapper>> getAllKardex();

    ResponseEntity<List<KardexWrapper>> getKardexByDate();

    ResponseEntity<List<KardexWrapper>> getAllKardexIncome();

    ResponseEntity<List<KardexWrapper>> getAllKardexOutcome();

    ResponseEntity<List<KardexWrapper>> getById(String id);

    ResponseEntity<String> generateFile(String kardexId);

    ResponseEntity<String> authorizeKardex(String kardexId);

}