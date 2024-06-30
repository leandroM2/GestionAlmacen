package com.inn.almacen.dao;
import com.inn.almacen.POJO.IncomeDetail;
import com.inn.almacen.WRAPPER.IncomeDetailWrapper;
import com.inn.almacen.WRAPPER.KardexDetailWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface IncomeDetailDao extends JpaRepository<IncomeDetail, Integer>{
    IncomeDetail getById(@Param("id") Integer id);
    List<IncomeDetailWrapper> getAllIncomeDetail();
    List<KardexDetailWrapper> getAllByFk(@Param("income_fk") Integer income_fk);
    List<KardexDetailWrapper> getAllOrderByFk(@Param("income_fk") Integer income_fk);
    IncomeDetail getByFk(@Param("id") Integer id, @Param("income_fk") Integer income_fk);
}