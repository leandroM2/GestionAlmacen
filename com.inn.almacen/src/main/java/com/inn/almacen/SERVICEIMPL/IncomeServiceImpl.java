package com.inn.almacen.SERVICEIMPL;

import com.inn.almacen.JWT.JwtFilter;
import com.inn.almacen.POJO.Income;
import com.inn.almacen.POJO.IncomeDetail;
import com.inn.almacen.POJO.Product;
import com.inn.almacen.POJO.User;
import com.inn.almacen.SERVICE.ArchivesService;
import com.inn.almacen.SERVICE.IncomeService;
import com.inn.almacen.UTILS.AlmacenUtils;
import com.inn.almacen.WRAPPER.IncomeWrapper;
import com.inn.almacen.WRAPPER.KardexDetailWrapper;
import com.inn.almacen.WRAPPER.OrderCompraWrapper;
import com.inn.almacen.constens.AlmacenConstants;
import com.inn.almacen.dao.IncomeDao;
import com.inn.almacen.dao.IncomeDetailDao;
import com.inn.almacen.dao.ProductDao;
import com.inn.almacen.dao.UserDao;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;

@Slf4j
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    IncomeDetailDao incomeDetailDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    ArchivesService archivesService;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ResponseEntity<String> addNewIncome(Map<String, String> requestMap) {
        log.info("Entra a add new income");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                if(validateIncomeMap(requestMap, false)){
                    incomeDao.save(getIncomeFromMap(requestMap, false));
                    return AlmacenUtils.getResponseEntity("Solicitud de entrada iniciada. Inserte los productos.", HttpStatus.OK);
                }
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
    public ResponseEntity<List<IncomeWrapper>> getAllIncome() {
        log.info("Dentro de Get All Income");
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                return new ResponseEntity<>(incomeDao.getAllIncome(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateIncome(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                if(validateIncomeMap(requestMap, true)){
                    Optional<Income> optional=incomeDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Income income= getIncomeFromMap(requestMap,true);
                        incomeDao.save(income);
                        return AlmacenUtils.getResponseEntity("Entrada actualizada exitosamente.", HttpStatus.OK);
                    }else{
                        return AlmacenUtils.getResponseEntity("Id de entrada no existe.", HttpStatus.OK);
                    }
                }else{
                    return AlmacenUtils.getResponseEntity(AlmacenConstants.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteIncome(Integer id) {
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=incomeDao.findById(id);
                if(!optional.isEmpty()){
                    incomeDao.deleteById(id);
                    return AlmacenUtils.getResponseEntity("Entrada eliminada correctamente.", HttpStatus.OK );
                }
                return AlmacenUtils.getResponseEntity("Id de entrada no existe.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<IncomeWrapper>> getById(Integer id) {
        log.info("Dentro de get Income by id");
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=incomeDao.findById(id);
                if(!optional.isEmpty()){
                    Income income=incomeDao.getById(id);
                    List<IncomeWrapper> myList = new ArrayList<>();
                    myList.add(new IncomeWrapper(income.getId(), income.getFecha(), income.getTipoPago(), income.getEstado(),
                            income.getUser().getId(), income.getUser().getNombre(),
                            income.getUserAuth().getId(), income.getUserAuth().getNombre()));
                    return new ResponseEntity<>(myList,HttpStatus.OK);
                }
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> authorizeIncome(Integer id) {
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin()){
                Optional optional=incomeDao.findById(id);
                if(!optional.isEmpty() && validateDetails(id)){
                    String user=jwtFilter.getCurrentUser();
                    User u=userDao.findByEmailId(user);
                    if(validateSign(u.getNombre())){
                        return AlmacenUtils.getResponseEntity("USUARIO "+jwtFilter.getCurrentUser()+" NO CUENTA CON FIRMA DENTRO DEL SISTEMA.", HttpStatus.CONFLICT);
                    }
                    updateState(user, id);
                    updateInstance(user, id);
                    return AlmacenUtils.getResponseEntity("Entradas autorizadas por el supervisor "+user, HttpStatus.OK );
                }
                return AlmacenUtils.getResponseEntity("Id de entrada no existe o no cuenta con productos registrados.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Boolean validateSign(String fileName){
        String ruta=Paths.get("src", "main", "resources", "templates","auths") + File.separator;
        fileName=fileName+".png";
        Path filePath = Paths.get(ruta, fileName);
        if (!Files.exists(filePath)){
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<String> generateOrdenCompra(Integer id){
        try {
            if(jwtFilter.isAdmin() || jwtFilter.isSuperAdmin() || jwtFilter.isUser()){
                Optional optional=incomeDao.findById(id);
                if(!optional.isEmpty()){
                    Income income=incomeDao.getById(id);;
                    String msg=createJOrden(income);
                    return AlmacenUtils.getResponseEntity(msg, HttpStatus.OK);
                }
                return AlmacenUtils.getResponseEntity("ID DE ENTRADA NO EXISTE.", HttpStatus.OK);
            }else{
                return AlmacenUtils.getResponseEntity(AlmacenConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
            }catch (Exception e){
            e.printStackTrace();
        }
        return AlmacenUtils.getResponseEntity(AlmacenConstants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Boolean validateDetails(Integer id) {
        String sql="SELECT count(*) FROM income_detail WHERE income_fk = ?";
        Integer det= jdbcTemplate.queryForObject(sql, new Integer[]{id}, Integer.class);
        Boolean val=det>0 ? true :  false;
        return val;
    }

    private void updateInstance(String user, Integer id){
        Income income=incomeDao.getById(id);
        String sql = "SELECT id FROM user WHERE email = ?";
        Integer userId = jdbcTemplate.queryForObject(sql, new String[]{user}, Integer.class);
        User u=userDao.findByRol(userId);
        income.setEstado(true);
        income.setUserAuth(u);
        createJOrden(income);
    }

    private String createJOrden(Income income){
        String sql = "SELECT id FROM income_detail WHERE income_fk = ? ORDER BY id LIMIT 1";
        Integer incomeDetailId = jdbcTemplate.queryForObject(sql, new Integer[]{income.getId()}, Integer.class);
        IncomeDetail incomeDetail=incomeDetailDao.getByFk(incomeDetailId, income.getId());
        List<KardexDetailWrapper> incomeDetailWrappers=incomeDetailDao.getAllOrderByFk(income.getId());
        List<OrderCompraWrapper> incomeOrder=new ArrayList<>();
        Float subtotal= 0.0f;

        for (KardexDetailWrapper KDW: incomeDetailWrappers){
            subtotal=subtotal+KDW.getTotal();
            incomeOrder.add(new OrderCompraWrapper(String.valueOf(KDW.getProductId()),
                    String.valueOf(KDW.getProducto()),
                    String.valueOf(KDW.getCantidad()),
                    String.valueOf(Float.parseFloat(String.format(Locale.US,"%.2f", KDW.getPrecioVenta()))),
                    String.valueOf(Float.parseFloat(String.format(Locale.US,"%.2f", KDW.getTotal())))));
        }
        String st=String.format(Locale.US,"%.2f", subtotal);
        Float igv=Float.parseFloat(String.format(Locale.US,"%.2f", subtotal*0.18f));
        Float total=Float.parseFloat(String.format(Locale.US,"%.2f", subtotal+igv));

        Map<String, Object> parameters=new HashMap<>();
        parameters.put("REPORT_DIR", Paths.get("src", "main", "resources", "templates") + File.separator);
        parameters.put("incomeId", kardexId("", income.getId()));
        parameters.put("incomeFecha",String.valueOf(income.getFecha()));
        parameters.put("supplierRazonSocial",String.valueOf(incomeDetail.getProduct().getSupplier().getRazonSocial()));
        parameters.put("supplierRuc",String.valueOf(incomeDetail.getProduct().getSupplier().getRuc()));
        parameters.put("supplierContacto",String.valueOf(incomeDetail.getProduct().getSupplier().getContacto()));
        parameters.put("tipoPago",income.getTipoPago());
        parameters.put("userNombre",String.valueOf(income.getUser().getNombre()));
        parameters.put("userAuth", String.valueOf(income.getUserAuth().getNombre()));
        parameters.put("incomeSubtotal", st);
        parameters.put("incomeIGV", String.valueOf(igv));
        parameters.put("incomeTotal", String.valueOf(total));

        JRBeanCollectionDataSource OrderDataSource=new JRBeanCollectionDataSource(incomeOrder);
        parameters.put("OrderCompraWrapper",OrderDataSource);

        try{
            String incomeId=kardexId("E",income.getId());
            String doc="orden.jrxml";
            log.info("AQUI ESTAMOS "+income.getEstado());
            if(income.getEstado()){
                parameters.put("REPORT_AUTH",Paths.get("src","main","resources","templates","auths",income.getUserAuth().getNombre()+".png") + File.separator);
                doc="ordenAuth.jrxml";
            }

            String ruta = Paths.get(Paths.get("data", "orders").toString(), incomeId + ".pdf").toString();
            createEmptyPDF(ruta);
            JasperReport report= JasperCompileManager.compileReport(Paths.get(Paths.get("src", "main", "resources", "templates", "report").toString(),doc).toString());
            JasperPrint print= JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(print,ruta);
            Map<String, String> arch=new HashMap<>();
            arch.put("id",incomeId);
            arch.put("ruta",ruta);
            archivesService.addArchive(arch);
            return "ORDEN "+incomeId+" GENERADA CON ÉXITO";
        }catch (Exception e){
            e.printStackTrace();
            return "ERROR DURANTE LA GENERACIÓN DE ORDEN DE COMPRA: "+e.getMessage();
        }
    }

    private void createEmptyPDF(String ruta){
        File file = new File(ruta);
        try {
            if (file.getParentFile().mkdirs()) log.info("Directorios existen");
            if (file.createNewFile()) log.info("Archivo creado: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph(" "));
            document.close();
            log.info("PDF limpio creado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateIncomeMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("fecha") && requestMap.containsKey("tipoPago")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Income getIncomeFromMap(Map<String, String> requestMap, boolean esAdd) {
        Income income=new Income();
        User user=new User();
        User userAuth=new User();
        userAuth.setId(0);
        String name=jwtFilter.getCurrentUser();
        log.info("PRUEBA: "+name);
        String sql = "SELECT id FROM user WHERE email = ?";
        Integer userId = jdbcTemplate.queryForObject(sql, new String[]{name}, Integer.class);
        user.setId(userId);
        if(esAdd) income.setId(Integer.parseInt(requestMap.get("id")));
        income.setFecha(Date.valueOf(requestMap.get("fecha")));
        income.setTipoPago(requestMap.get("tipoPago"));
        income.setEstado(requestMap.containsKey("estado") ? Boolean.parseBoolean(requestMap.get("estado")) : false);
        income.setUser(user);
        income.setUserAuth(userAuth);
        return income;
    }

    private void updateState(String user, Integer incomeId){
        log.info("Hemos llegado hasta actualizacion de estado de entrada");
        String sql;
        sql = "SELECT id FROM user WHERE email = ?";
        Integer userId = jdbcTemplate.queryForObject(sql, new String[]{user}, Integer.class);

        sql = "UPDATE income SET estado = true, autorizador_fk=? WHERE id = ?";
        jdbcTemplate.update(sql, userId, incomeId);
        sql = "SELECT id FROM income_detail WHERE income_fk=?";

        // Obtener una lista de Strings
        List<Integer> ids = jdbcTemplate.queryForList(sql, new Integer[]{incomeId}, Integer.class);

        log.info("Vamos a efectuar las actualizaciones de stock y precio en los productos");
        Integer i=0;
        while(i<ids.size()){;
            IncomeDetail incomeDetail=incomeDetailDao.getById(ids.get(i));
            updateProduct(incomeDetail.getProduct().getId(), incomeDetail.getId(), incomeDetail.getCantidad(), incomeDetail.getPrecioVentaUnit());
            i++;
        }
    }

    private void updateProduct(Integer productId, Integer incomeDetailId, Integer cant, Float precioVentaUnit){
        log.info("Hemos llegado hasta actualizacion de stock y precio producto.");
        String sql;
        Product product=productDao.getById(productId);
            log.info("Estamos insertando y precios a Income detail.");
            Integer stock=product.getStock();
            stock=stock+cant;
            Float precio=product.getPrecio();
            precio=Math.round(((precio+precioVentaUnit)/2) * 100.0f) / 100.0f;
            sql = "UPDATE product SET stock = ?, precio=? WHERE id = ?";
            jdbcTemplate.update(sql, stock, precio, productId);
            sql="UPDATE income_detail SET saldo = ? WHERE id = ?";
            jdbcTemplate.update(sql, stock, incomeDetailId);
        log.info("Valores de precio y stock de producto y detalle income actualizado");
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

}