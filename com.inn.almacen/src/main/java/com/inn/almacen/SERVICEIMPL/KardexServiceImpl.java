package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.SERVICE.KardexService;
import com.inn.almacen.WRAPPER.*;
import com.inn.almacen.dao.IncomeDao;
import com.inn.almacen.dao.IncomeDetailDao;
import com.inn.almacen.dao.OutcomeDao;
import com.inn.almacen.dao.OutcomeDetailDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class KardexServiceImpl implements KardexService {

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    IncomeDetailDao incomeDetailDao;

    @Autowired
    OutcomeDao outcomeDao;

    @Autowired
    OutcomeDetailDao outcomeDetailDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewKardexEntry(Map<String, String> requestMap) {
        return null;
    }

    @Override
    public ResponseEntity<List<KardexWrapper>> getAllKardex() {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<KardexWrapper> kardexI=this.KardexIncome();
                List<KardexWrapper> kardexO=this.KardexOutcome();
                List<KardexWrapper> kardexFull=new ArrayList<>();
                kardexFull.addAll(kardexI);
                kardexFull.addAll(kardexO);
                return new ResponseEntity<>(kardexFull, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public List<KardexWrapper> KardexIncome(){
        List<IncomeWrapper> Inheader=incomeDao.getAllIncome();
        List<KardexWrapper> KW=new ArrayList<>();
        List<KardexDetailWrapper> InDetail=new ArrayList<>();
        for (IncomeWrapper IW: Inheader) {
            Integer rawId=IW.getId();
            String ini="E";
            String KID=kardexId(ini, rawId);
            InDetail.clear();
            InDetail=incomeDetailDao.getAllByFk(IW.getId());
            KW.add(new KardexWrapper(KID, IW.getFecha(), IW.getEstado(),"Entrada","-",IW.getId(),InDetail));
        }
        return KW;
    }

    public List<KardexWrapper> KardexOutcome(){
        List<OutcomeWrapper> Outheader=outcomeDao.getAllOutcome();
        List<KardexWrapper> KW=new ArrayList<>();
        List<KardexDetailWrapper> OutDetail=new ArrayList<>();
        for (OutcomeWrapper OW: Outheader) {
            Integer rawId=OW.getId();
            String ini="S";
            String KID=kardexId(ini, rawId);
            OutDetail.clear();
            OutDetail=outcomeDetailDao.getAllByFk(OW.getId());
            KW.add(new KardexWrapper(KID, OW.getFecha(), OW.getEstado(),"Salida",OW.getClientRazonSocial(),OW.getId(),OutDetail));
        }
        return KW;
    }

    public String kardexId(String ini, Integer rawId){
        StringBuilder id = new StringBuilder(ini);
        Integer auxId=rawId;
        if(auxId==0) auxId++;
        double cont=Math.floor(Math.log10(Math.abs(auxId)) + 1);
        cont=5-cont;
        for (Integer i = 0; i<cont; i++){
            id.append("0");
        }
        id.append(rawId);
        return id.toString();
    }

    @Override
    public ResponseEntity<List<KardexWrapper>> getAllKardexIncome() {
        return null;
    }

    @Override
    public ResponseEntity<List<KardexWrapper>> getAllKardexOutcomwe() {
        return null;
    }

    @Override
    public ResponseEntity<List<KardexWrapper>> getById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteKardexHeader(String id) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteKardexDetail(Integer id) {
        return null;
    }
}