package com.inn.almacen.dao;

import com.inn.almacen.POJO.OutcomeDetail;
import com.inn.almacen.WRAPPER.OutcomeDetailWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutcomeDetailDao extends JpaRepository<OutcomeDetail, Integer> {
    List<OutcomeDetailWrapper> getAllOutcomeDetail();
}
