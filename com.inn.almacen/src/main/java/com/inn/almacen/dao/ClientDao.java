package com.inn.almacen.dao;

import com.inn.almacen.POJO.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Integer> {

    Client getById(@Param("id") Integer id);
    List<Client> getAllClient();
}
