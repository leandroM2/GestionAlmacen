package com.inn.almacen.dao;

import com.inn.almacen.POJO.OutcomeDetail;
import com.inn.almacen.WRAPPER.KardexDetailWrapper;
import com.inn.almacen.WRAPPER.OutcomeDetailWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutcomeDetailDao extends JpaRepository<OutcomeDetail, Integer> {

    OutcomeDetail getById(@Param("id") Integer id);
    List<OutcomeDetailWrapper> getAllOutcomeDetail();
    List<KardexDetailWrapper> getAllByFk(@Param("outcome_fk") Integer outcome_fk);
}
