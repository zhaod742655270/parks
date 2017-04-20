package com.hbyd.parks.client.officesys.action;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.hbyd.parks.client.util.ComboHelper;
import com.hbyd.parks.client.util.ExportExcelHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.common.model.*;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.officesys.*;
import com.hbyd.parks.ws.managesys.UserWS;
import com.hbyd.parks.ws.officesys.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhao_d on 2016/12/26.
 */
@Controller
@Scope("prototype")
@Module(description = "入库管理记录")
public class WarehouseInputAction extends ActionSupport implements ModelDriven<WarehouseInputQuery>{

    @Resource
    private WarehouseInputWS warehouseInputWS;

    @Resource
    private WarehouseInputProWS warehouseInputProWS;

    @Resource
    private UserWS userWS;

    @Resource
    private WarehouseCompanyInWS warehouseCompanyInWS;

    @Resource
    private WarehouseApplicationWS warehouseApplicationWS;

    @Resource
    private WarehouseInfoWS warehouseInfoWS;

    @Resource
    private WarehouseWS warehouseWS;

    @Resource
    private WarehouseProductWS warehouseProductWS;

    private WarehouseInputQuery query = new WarehouseInputQuery();
    private WarehouseInputDTO warehouseInput = new WarehouseInputDTO();
    private WarehouseInputProDTO warehouseInputPro = new WarehouseInputProDTO();
    private String id;
    private String jsonAllRows;

    private Gson gson = new Gson();

    @Operation(type="查询入库信息")
    public void warehouseInputList(){
        PageBeanEasyUI list = warehouseInputWS.getPageBeanByQueryBean(query);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    @Operation(type="新增入库信息")
    public void addWarehouseInput(){
        AjaxMessage massage = new AjaxMessage();
        try{
            //没有申请单时，将申请单字段置为空
            if(Strings.isNullOrEmpty(warehouseInput.getApplicationID())){
                warehouseInput.setApplicationID(null);
            }
            warehouseInput = warehouseInputWS.save(warehouseInput);

            //根据入库单添加货品
            if(!Strings.isNullOrEmpty(warehouseInput.getApplicationID())) {
                addProductByApplication();
            }
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="修改入库信息")
    public void editWarehouseInput(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseInputWS.update(warehouseInput);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="删除入库信息")
    public void deleteWarehouseInput(){
        AjaxMessage massage = new AjaxMessage();
        try{
            //伪删除入库单信息
            warehouseInputWS.delFake(id);

            //同时更新库存信息
            WarehouseInputQuery query = new WarehouseInputQuery();
            query.setRows(1000);
            query.setSort("id");
            query.setOrder("asc");
            PageBeanEasyUI list = warehouseInputProWS.getPageBeanByQueryBean(query,id);
            if(list != null) {
                for (int i = 0; i < list.getTotal(); i++) {
                    WarehouseInputProDTO dto = (WarehouseInputProDTO) list.getRows().get(i);
                    //同时伪删除入库单下货品信息
                    warehouseInputProWS.delFake(dto.getId());
                    //更新库存信息
                    updateWarehouse(dto.getWarehouse().getId());
                }
            }
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="查询入库详细信息")
    public void warehouseInputProList(){
        PageBeanEasyUI list = warehouseInputProWS.getPageBeanByQueryBean(query,id);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    @Operation(type="编辑入库货品信息")
    public void editWarehouseInputPro(){
        AjaxMessage message = new AjaxMessage();
        try {
            String update = getJsonAllRows();
            List<LinkedTreeMap> listUpdated = new ArrayList();
            listUpdated = gson.fromJson(update,listUpdated.getClass());

            for(int i=0;i<listUpdated.size();i++){
                LinkedTreeMap map = listUpdated.get(i);
                //填充DTO
                WarehouseInputProDTO dto = fillInputProDTO(map);
                //保存
                if(map.get("id") == null){
                    dto = warehouseInputProWS.save(dto);
                    //更新库存信息
                    updateWarehouse(dto.getWarehouse().getId());
                }else{
                    warehouseInputProWS.update(dto);
                    //更新库存信息
                    updateWarehouse(dto.getWarehouse().getId());
                }
            }
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="删除入库货品信息")
    public void deleteWarehouseInputPro(){
        AjaxMessage massage = new AjaxMessage();
        try{
            WarehouseInputProDTO dto = warehouseInputProWS.getByID(id);
            warehouseInputProWS.delFake(id);

            //更新库存信息
            updateWarehouse(dto.getWarehouse().getId());
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="通过申请单添加入库信息")
    public void addProductByApplication(){
        String appli = getJsonAllRows();
        String productId = "";

        List<LinkedTreeMap> listAppli = new ArrayList();
        listAppli = gson.fromJson(appli,listAppli.getClass());
        for(int i=0;i<listAppli.size();i++){
            LinkedTreeMap map = listAppli.get(i);
            //判断货品是否已存在
            //如果相同货品不存在，则在货品库中添加该货品
            //返回货品ID
            productId = isProductExist(map,warehouseInput.getInputType());

            //填充DTO
            WarehouseInputProDTO dto = fillInputProDTO(map,productId);
            //保存货品信息
            dto = warehouseInputProWS.save(dto);

            //同时更新库存中的数据
            updateWarehouse(dto.getWarehouse().getId());

            //将申请单中该物品置为已完成
            dto = warehouseInputProWS.getByID(dto.getId());
            dto.getWarehouseApplicationPro().setFinished(true);
            warehouseInputProWS.update(dto);
        }
    }

    /**
     * 同时更改库存中的数据
     * 每次新增或修改一个入库货品时，都会重新统计该货品的当前数量，并更新到库存信息当中
     * @param warehouseId 关联的库存货品ID
     */
    public void updateWarehouse(String warehouseId){
        Double quantity = warehouseWS.getStatisticsForInputOutput(warehouseId);
        WarehouseDTO warehouseDTO = warehouseWS.getByID(warehouseId);
        warehouseDTO.setQuantity(quantity);
        warehouseDTO.setQuantityUse(quantity - warehouseDTO.getQuantityBorrow());
        warehouseWS.update(warehouseDTO);
    }

    /**
     * 判断货品是否已经存在(通过名称、型号、封装、版本),如果不存在则新增该货品,返回货品ID
     */
    public String isProductExist(LinkedTreeMap map,String productType){
        WarehouseProductQuery productQuery = new WarehouseProductQuery();
        productQuery.setRows(1);
        productQuery.setSort("id");
        productQuery.setOrder("asc");
        productQuery.setNameQuery(map.get("productName").toString());
        productQuery.setModelNumberQuery(map.get("productModelNumber").toString());
        productQuery.setSpecificationsQuery(map.get("productSpecifications").toString());
        productQuery.setBrandQuery(map.get("productBrand").toString());
        PageBeanEasyUI list = warehouseProductWS.getPageBeanByQueryBean(productQuery);

        if(list.getTotal() == 0){
            WarehouseProductDTO productDTO = new WarehouseProductDTO();
            productDTO.setName(map.get("productName").toString());
            productDTO.setProductType(productType);
            productDTO.setModelNumber(map.get("productModelNumber").toString());
            productDTO.setSpecifications(map.get("productSpecifications").toString());
            productDTO.setBrand(map.get("productBrand").toString());
            productDTO.setUnit(map.get("productUnit").toString());
            productDTO.setProductDesc("自动添加");
            productDTO = warehouseProductWS.save(productDTO);
            //同时更新库存信息
            warehouseWS.addProduct(productDTO.getId());
            return productDTO.getId();
        }else{
            WarehouseProductDTO productDTO = (WarehouseProductDTO)(list.getRows().get(0));
            return productDTO.getId();
        }
    }

    public WarehouseInputProDTO fillInputProDTO(LinkedTreeMap map){
        WarehouseInputProDTO dto = new WarehouseInputProDTO();
        if(map.get("id") != null) {
            dto=warehouseInputProWS.getByID(map.get("id").toString());
        }else{
            dto = new WarehouseInputProDTO();
        }
        dto.setParentIdFK(map.get("parentIdFK").toString());
        dto.setProductId(map.get("productId").toString());
        if(map.get("quantity") != null && !Strings.isNullOrEmpty(map.get("quantity").toString())) {
            dto.setQuantity(Double.valueOf(map.get("quantity").toString()));
        }else{
            dto.setQuantity(Double.valueOf(0));
        }
        if(map.get("price") != null && !Strings.isNullOrEmpty(map.get("price").toString())) {
            dto.setPrice(Double.valueOf(map.get("price").toString()));
        }else{
            dto.setPrice(Double.valueOf(0));
        }
        dto.setValence(Double.valueOf(0));
        if(map.get("productNum") != null) {
            dto.setProductNum(map.get("productNum").toString());
        }else{
            dto.setProductNum("");
        }
        if(map.get("note") != null) {
            dto.setNote(map.get("note").toString());
        }else{
            dto.setNote("");
        }
        //关联库存
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        warehouseDTO.setId(warehouseProductWS.getWarehouseByProdutId(dto.getProductId()));
        dto.setWarehouse(warehouseDTO);

        return dto;
    }

    public WarehouseInputProDTO fillInputProDTO(LinkedTreeMap map,String productId){
        WarehouseInputProDTO dto = new WarehouseInputProDTO();
        dto.setParentIdFK(warehouseInput.getId());
        dto.setProductId(productId);
        if(map.get("quantity") != null && !Strings.isNullOrEmpty(map.get("quantity").toString())) {
            dto.setQuantity(Double.valueOf(map.get("quantity").toString()));
        }else{
            dto.setQuantity(Double.valueOf(0));
        }
        if(map.get("price") != null && !Strings.isNullOrEmpty(map.get("price").toString())) {
            dto.setPrice(Double.valueOf(map.get("price").toString()));
        }else{
            dto.setPrice(Double.valueOf(0));
        }
        dto.setValence(Double.valueOf(0));
        if(map.get("productNum") != null) {
            dto.setProductNum(map.get("productNum").toString());
        }else{
            dto.setProductNum("");
        }
        if(map.get("note") != null) {
            dto.setNote(map.get("note").toString());
        }else{
            dto.setNote("");
        }
        //关联库存
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        String warehouseID = warehouseProductWS.getWarehouseByProdutId(dto.getProductId());
        warehouseDTO.setId(warehouseID);
        dto.setWarehouse(warehouseDTO);
        //关联入库单货品
        WarehouseApplicationProDTO applyPro = new WarehouseApplicationProDTO();
        applyPro.setId(map.get("id").toString());
        dto.setWarehouseApplicationPro(applyPro);
        return dto;
    }

    /**
     * 获得人员列表
     */
    public void getUserList(){
        List<UserDTO> lists = userWS.findAllValid();
        if(lists==null){
            lists=new ArrayList<>();
        }
        List<Combobox> project = ComboHelper.getNicknameCombobox(lists);   //根据数据填充下拉框
        String result = gson.toJson(project);
        JsonHelper.writeJson(result);
    }

    /**
     * 获得供应商列表
     */
    public void getCompanyList(){
        //查询数据
        WarehouseCompanyQuery companyQuery = new WarehouseCompanyQuery();
        companyQuery.setSort("name");
        companyQuery.setOrder("asc");
        companyQuery.setRows(10000);
        List<WarehouseCompanyInDTO> lists = warehouseCompanyInWS.getPageBeanByQueryBean(companyQuery).getRows();
        if(lists==null){
            lists=new ArrayList<>();
        }

        //根据数据填充下拉框
        ArrayList<Combobox> nodes = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            WarehouseCompanyInDTO dto = lists.get(i);
            Combobox node = new Combobox();
            node.setId(dto.getId());
            node.setText(dto.getName());
            nodes.add(node);
        }

        //Json格式传回
        String result = gson.toJson(nodes);
        JsonHelper.writeJson(result);
    }

    /**
     * 获得申请单列表
     */
    public void getApplicationList(){
        //查询数据
        WarehouseApplicationQuery applyQuery = new WarehouseApplicationQuery();
        applyQuery.setSort("name");
        applyQuery.setOrder("asc");
        applyQuery.setRows(10000);
        applyQuery.setTypeQuery("入库");
        List<WarehouseApplicationDTO> lists = warehouseApplicationWS.getPageBeanByQueryBean(applyQuery).getRows();
        if(lists==null){
            lists=new ArrayList<>();
        }

        //根据数据填充下拉框
        ArrayList<Combobox> nodes = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            WarehouseApplicationDTO dto = lists.get(i);
            Combobox node = new Combobox();
            node.setId(dto.getId());
            node.setText(dto.getName());
            nodes.add(node);
        }

        //Json格式传回
        String result = gson.toJson(nodes);
        JsonHelper.writeJson(result);
    }

    /**
     * 获得仓库列表
     */
    public void getWarehouseType(){
        //查询数据
        List<WarehouseInfoDTO> lists = warehouseInfoWS.getWarehouseType().getRows();
        if(lists==null){
            lists=new ArrayList<>();
        }

        //根据数据填充下拉框
        ArrayList<Combobox> nodes = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            WarehouseInfoDTO dto = lists.get(i);
            Combobox node = new Combobox();
            node.setId(dto.getId());
            node.setText(dto.getName());
            nodes.add(node);
        }

        //Json格式传回
        String result = gson.toJson(nodes);
        JsonHelper.writeJson(result);
    }

    /**
     * 自动获得新编号
     */
    public void getNewNumber(){
        query.setSort("id");
        query.setOrder("asc");
        query.setRows(1000);
        String number = warehouseInputWS.getNewNumber(query);
        if(number.length() == 10){
            JsonHelper.writeJson(number);
        }
    }

    public void exportExcel() throws Exception {
        AjaxMessage message = new AjaxMessage();
        try {
            query.setSort("number");
            query.setOrder("desc");
            query.setRows(10000);
            PageBeanEasyUI pageBean = warehouseInputWS.getPageBeanByQueryBean(query);
            ExportExcelHelper.exportWarehouseInput(pageBean.getRows());
        }catch(Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally{
            //由于导出文件时Response存在冲突，因此只在导出失败时返回信息
            if(!message.getSuccess()) {
                String result = gson.toJson(message);
                JsonHelper.writeJson(result);
            }
        }
    }

    @Override
    public WarehouseInputQuery getModel() {
        return query;
    }

    public WarehouseInputQuery getQuery() {
        return query;
    }

    public void setQuery(WarehouseInputQuery query) {
        this.query = query;
    }

    public WarehouseInputDTO getWarehouseInput() {
        return warehouseInput;
    }

    public void setWarehouseInput(WarehouseInputDTO warehouseInput) {
        this.warehouseInput = warehouseInput;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WarehouseInputProDTO getWarehouseInputPro() {
        return warehouseInputPro;
    }

    public void setWarehouseInputPro(WarehouseInputProDTO warehouseInputPro) {
        this.warehouseInputPro = warehouseInputPro;
    }

    public String getJsonAllRows() {
        return jsonAllRows;
    }

    public void setJsonAllRows(String jsonAllRows) {
        this.jsonAllRows = jsonAllRows;
    }
}
