package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Income;
import com.inn.almacen.POJO.Outcome;
import com.inn.almacen.SERVICE.IncomeService;
import com.inn.almacen.SERVICE.KardexService;
import com.inn.almacen.SERVICE.OutcomeService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.*;
import com.inn.almacen.constens.AlmacenConstants;
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

import java.util.*;

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
    JwtFilter jwtFilter;

    @Autowired
    OutcomeService outcomeService;

    @Autowired
    IncomeService incomeService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResponseEntity<String> addNewKardexEntry(Map<String, String> requestMap) {
        log.info("Entrando a new kardex");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                if(validateKardexMap(requestMap)){
                    if(requestMap.containsKey("clientId")){
                        return AlmacenUtils.getResponseComplex(outcomeService.addNewOutcome(requestMap));
                    }
                    return AlmacenUtils.getResponseComplex(incomeService.addNewIncome(requestMap));
                }
                log.info("Data invalida en kardex service");
                return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
                }
            }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @Override
    public ResponseEntity<List<KardexWrapper>> getKardexByDate(){
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<KardexWrapper> kardexI=this.KardexIncome();
                List<KardexWrapper> kardexO=this.KardexOutcome();
                List<KardexWrapper> kardexFull=new ArrayList<>();
                kardexFull.addAll(kardexI);
                kardexFull.addAll(kardexO);
                Collections.sort(kardexFull, Comparator.comparing(KardexWrapper::getFecha).reversed().
                        thenComparing(KardexWrapper::getTime));

                return new ResponseEntity<>(kardexFull, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<KardexWrapper>> getAllKardexIncome() {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<KardexWrapper> kardexI=this.KardexIncome();
                return new ResponseEntity<>(kardexI, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<KardexWrapper>> getAllKardexOutcome() {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<KardexWrapper> kardexO=this.KardexOutcome();
                return new ResponseEntity<>(kardexO, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<KardexWrapper>> getById(String id) {
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                List<KardexWrapper> kardex=new ArrayList<>();
                Integer anyId=extractNumbers(id);
                Optional optional;
                if(id.charAt(0)=='S'){
                    optional=outcomeDao.findById(anyId);
                    if(!optional.isEmpty()) kardex=kardexOutcomeById(anyId);
                } else if (id.charAt(0)=='E') {
                    optional=incomeDao.findById(anyId);
                    if(!optional.isEmpty()) kardex=kardexIncomeById(anyId);
                }else{
                    return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
                }
                return new ResponseEntity<>(kardex, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
                }
            }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> generateFile(String kardexId){

        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()) {
                Integer id = extractNumbers(kardexId);
                String let=extractLetters(kardexId).toUpperCase();
                if (!validateDetails(id, let)) {
                    return AlmacenUtils.getResponseEntity("Debe existir al menos un producto para generar documento", HttpStatus.BAD_REQUEST);
                }
                switch (let) {
                    case "E":
                        return AlmacenUtils.getResponseComplex(incomeService.generateOrdenCompra(id));
                    case "S":
                        return AlmacenUtils.getResponseComplex(outcomeService.generateGuiaRemision(id));
                    default:
                        return AlmacenUtils.getResponseEntity("Id de kardex no corresponde a entrada o salida. ", HttpStatus.OK);
                }
            } else {
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Boolean validateDetails(Integer id, String let) {
        String sql;
        Integer det;
        Boolean val;
        switch (let) {
            case "E":
                sql = "SELECT count(*) FROM income_detail WHERE income_fk = ?";
                break;
            case "S":
                sql = "SELECT count(*) FROM outcome_detail WHERE outcome_fk = ?";
                break;
            default:
                return false;

        }
        det = jdbcTemplate.queryForObject(sql, new Integer[]{id}, Integer.class);
        val=det>0 ? true :  false;
        return val;
    }

    @Override
    public ResponseEntity<String> authorizeKardex(String kardexId) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Integer id=extractNumbers(kardexId);
                    if(extractLetters(kardexId).equalsIgnoreCase("E")){
                        return AlmacenUtils.getResponseComplex(incomeService.authorizeIncome(id));
                    }else if(extractLetters(kardexId).equalsIgnoreCase("S")){
                        return AlmacenUtils.getResponseComplex(outcomeService.authorizeOutcome(id));
                    }
                return AlmacenUtils.getResponseEntity("ID de kardex no se corresponde con entrada o salida.", HttpStatus.OK);
                }
            return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private List<KardexWrapper> KardexIncome(){
        List<IncomeWrapper> Inheader=incomeDao.getAllIncome();
        List<KardexWrapper> KW=new ArrayList<>();
        List<KardexDetailWrapper> InDetail;
        for (IncomeWrapper IW: Inheader) {
            Integer rawId=IW.getId();
            String ini="E";
            String KID=kardexId(ini, rawId);
            InDetail=incomeDetailDao.getAllByFk(IW.getId());
            KW.add(new KardexWrapper(KID, IW.getFecha() , IW.getFecha().getTime(), IW.getTipoPago(),"-" ,IW.getEstado(),"Entrada","-",KID,InDetail));
        }
        return KW;
    }

    private List<KardexWrapper> KardexOutcome(){
        List<OutcomeWrapper> Outheader=outcomeDao.getAllOutcome();
        List<KardexWrapper> KW=new ArrayList<>();
        List<KardexDetailWrapper> OutDetail;
        for (OutcomeWrapper OW: Outheader) {
            Integer rawId=OW.getId();
            String ini="S";
            String KID=kardexId(ini, rawId);
            OutDetail=outcomeDetailDao.getAllByFk(OW.getId());
            KW.add(new KardexWrapper(KID, OW.getFecha(), OW.getFecha().getTime(), OW.getTipoPago(), OW.getFactura() ,OW.getEstado(),"Salida",OW.getClientRazonSocial(),KID,OutDetail));
        }
        return KW;
    }

    private String kardexId(String ini, Integer rawId){
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

    private List<KardexWrapper> kardexOutcomeById(Integer id){
        Outcome outcome=outcomeDao.getById(id);
        List<KardexWrapper> KW=new ArrayList<>();
        List<KardexDetailWrapper> OutDetail;
        Integer rawId=outcome.getId();
        String ini="S";
        String KID=kardexId(ini, rawId);
        OutDetail=outcomeDetailDao.getAllByFk(outcome.getId());
        log.info("Dentro de kardexoutcomebyid "+OutDetail);
        KW.add(new KardexWrapper(KID, outcome.getFecha(), outcome.getFecha().getTime(), outcome.getTipoPago(), outcome.getFactura(), outcome.getEstado(),"Salida",outcome.getClient().getRazonSocial(),KID,OutDetail));
        return KW;
    }

    private List<KardexWrapper> kardexIncomeById(Integer id){
        Income income=incomeDao.getById(id);
        List<KardexWrapper> KW=new ArrayList<>();
        List<KardexDetailWrapper> InDetail;
        Integer rawId=income.getId();
        String ini="E";
        String KID=kardexId(ini, rawId);
        InDetail=incomeDetailDao.getAllByFk(income.getId());
        log.info("Dentro de kardexincomebyid "+InDetail);
        KW.add(new KardexWrapper(KID, income.getFecha(), income.getFecha().getTime(), income.getTipoPago(), "-", income.getEstado(),"Entrada","-",KID,InDetail));
        return KW;
    }

    private static Integer extractNumbers(String kardexId) {
        return Integer.parseInt(kardexId.replaceAll("[^0-9]", ""));
    }

    private static String extractLetters(String kardexId){
        return kardexId.replaceAll("[^a-zA-Z]", "");
    }

    private boolean validateKardexMap(Map<String, String> requestMap) {
        if (!requestMap.containsKey("fecha") || (!requestMap.containsKey("tipoPago")) || !requestMap.containsKey("tipo")) {
            return false;
        }
        if (Integer.parseInt(requestMap.get("tipo"))==1) {
            return true; // income
        }else{
            return requestMap.containsKey("clientId"); // outcome
        }
    }

}