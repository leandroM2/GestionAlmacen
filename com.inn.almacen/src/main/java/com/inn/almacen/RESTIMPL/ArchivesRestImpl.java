package com.inn.almacen.RESTIMPL;

import com.inn.almacen.POJO.Archives;
import com.inn.almacen.REST.ArchivesRest;
import com.inn.almacen.SERVICE.ArchivesService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.constens.AlmacenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ArchivesRestImpl implements ArchivesRest {

    @Autowired
    ArchivesService archivesService;

    @Override
    public ResponseEntity<String> addArchives(Map<String, String> requestMap) {
        try {
            return archivesService.addArchive(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Archives>> getAllArchives(String filterValue) {
        try {
            return archivesService.getAllArchives(filterValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Archives>> getById(String id) {
        try {
            return archivesService.getById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Resource> descargarPDF(String kardexId) {
        try {
            return archivesService.descargarPDF(kardexId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}