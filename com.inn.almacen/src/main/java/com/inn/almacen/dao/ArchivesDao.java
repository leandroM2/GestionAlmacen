package com.inn.almacen.dao;

import com.inn.almacen.POJO.Archives;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArchivesDao extends JpaRepository<Archives, Integer> {

    Archives getById(@Param("id") String id);

    List<Archives> getAllArchives();
}