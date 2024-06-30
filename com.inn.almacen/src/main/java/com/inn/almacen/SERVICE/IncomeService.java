package com.inn.almacen.SERVICE;

import com.inn.almacen.WRAPPER.IncomeWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IncomeService {
    ResponseEntity<String> addNewIncome(Map<String, String> requestMap);

    ResponseEntity<List<IncomeWrapper>> getAllIncome();

    ResponseEntity<String> updateIncome(Map<String, String> requestMap);

    ResponseEntity<String> deleteIncome(Integer id);

    ResponseEntity<List<IncomeWrapper>> getById(Integer id);

    ResponseEntity<String> authorizeIncome(Integer id);

    ResponseEntity<String> generateOrdenCompra(Integer id);
}
