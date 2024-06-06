package com.inn.almacen.dao;
import com.inn.almacen.POJO.IncomeDetail;
import com.inn.almacen.WRAPPER.IncomeDetailWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeDetailDao extends JpaRepository<IncomeDetail, Integer>{
    List<IncomeDetailWrapper> getAllIncomeDetail();
}
