package com.inn.almacen.dao;

import com.inn.almacen.POJO.Income;
import com.inn.almacen.WRAPPER.IncomeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeDao extends JpaRepository<Income, Integer> {
    List<IncomeWrapper> getAllIncome();
}
