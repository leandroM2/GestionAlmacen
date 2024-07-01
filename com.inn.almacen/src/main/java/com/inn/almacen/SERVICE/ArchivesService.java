package com.inn.almacen.SERVICE;

import com.inn.almacen.POJO.Archives;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import java.util.List;
import java.util.Map;

public interface ArchivesService {

    ResponseEntity<List<Archives>> getAllArchives(String filterValue);

    ResponseEntity<List<Archives>> getById(String id);

    ResponseEntity<String> addArchive(Map<String, String> requestMap);

    ResponseEntity<Resource> descargarPDF(String kardexId);
}