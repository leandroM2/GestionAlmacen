package com.inn.almacen.dao;

import com.inn.almacen.POJO.Outcome;
import com.inn.almacen.WRAPPER.OutcomeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutcomeDao extends JpaRepository<Outcome, Integer> {
    List<OutcomeWrapper> getAllOutcome();
}
