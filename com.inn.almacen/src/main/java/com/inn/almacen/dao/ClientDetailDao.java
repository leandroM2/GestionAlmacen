package com.inn.almacen.dao;

import com.inn.almacen.POJO.ClientDetail;
import com.inn.almacen.WRAPPER.ClientDetailWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientDetailDao extends JpaRepository<ClientDetail, Integer> {

    ClientDetail getById(@Param("id") Integer id);

    ClientDetailWrapper getViewById(@Param("id") Integer id);
    List<ClientDetailWrapper> getAllClientDetail();
    List<ClientDetailWrapper> getViewByCliDetId(@Param("client_fk") Integer client_fk, @Param("cliDetId") Integer cliDetId);
    List<ClientDetailWrapper> getAllByClient(@Param("client_fk") Integer client_fk);

}
