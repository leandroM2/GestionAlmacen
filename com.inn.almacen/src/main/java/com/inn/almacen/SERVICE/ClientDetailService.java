package com.inn.almacen.SERVICE;

import com.inn.almacen.WRAPPER.ClientDetailWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ClientDetailService {

    ResponseEntity<String> addNewClientDetail(Map<String, String> requestMap);
    ResponseEntity<List<ClientDetailWrapper>> getAllClientDetail();
    ResponseEntity<String> updateClientDetail(Map<String, String> requestMap);
    ResponseEntity<String> deleteClientDetail(Integer id);
    ResponseEntity<List<ClientDetailWrapper>> getById(Integer id);
    ResponseEntity<List<ClientDetailWrapper>> getByFk(Integer client_fk, Integer cliDetId);
    ResponseEntity<List<ClientDetailWrapper>> getAllByFk(Integer client_fk);
}
