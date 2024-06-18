package com.inn.almacen.dao;

import com.inn.almacen.POJO.Outcome;
import com.inn.almacen.WRAPPER.OutcomeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutcomeDao extends JpaRepository<Outcome, Integer> {
    Outcome getById(@Param("id") Integer id);
    List<OutcomeWrapper> getAllOutcome();
}
