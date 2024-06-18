package com.inn.almacen.dao;

import com.inn.almacen.POJO.Income;
import com.inn.almacen.WRAPPER.IncomeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncomeDao extends JpaRepository<Income, Integer> {
    Income getById(@Param("id") Integer id);
    List<IncomeWrapper> getAllIncome();
}
