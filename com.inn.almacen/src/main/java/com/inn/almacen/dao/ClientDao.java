package com.inn.almacen.dao;

import com.inn.almacen.POJO.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Integer> {
    List<Client> getAllClient();
}
