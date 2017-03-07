package com.hbyd.parks.client.officesys.action;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.hbyd.parks.client.util.ComboHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
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

    public void warehouseInputList(){
        PageBeanEasyUI list = warehouseInputWS.getPageBeanByQueryBean(query);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public void addWarehouseInput(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseInput = warehouseInputWS.save(warehouseInput);
            addProductByApplication();
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

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

    public void deleteWarehouseInput(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseInputWS.delFake(id);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void warehouseInputProList(){
        PageBeanEasyUI list = warehouseInputProWS.getPageBeanByQueryBean(query,id);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public void editWarehouseInputPro(){
        AjaxMessage message = new AjaxMessage();
        try {
            Boolean saveRow = false;
            String update = getJsonAllRows();
            List<LinkedTreeMap> listUpdated = new ArrayList();
            listUpdated = gson.fromJson(update,listUpdated.getClass());

            for(int i=0;i<listUpdated.size();i++){
                LinkedTreeMap map = listUpdated.get(i);
                //填充DTO
                WarehouseInputProDTO dto;
                if(map.get("id") != null) {
                    dto=warehouseInputProWS.getByID(map.get("id").toString());
                    saveRow = false;
                }else{
                    dto = new WarehouseInputProDTO();
                    saveRow = true;
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
                if(map.get("valence") != null && !Strings.isNullOrEmpty(map.get("valence").toString())) {
                    dto.setValence(Double.valueOf(map.get("valence").toString()));
                }else{
                    dto.setValence(Double.valueOf(0));
                }
                if(map.get("note") != null) {
                    dto.setNote(map.get("note").toString());
                }else{
                    dto.setNote("");
                }

                //保存
                if(saveRow){
                    warehouseInputProWS.save(dto);
                    //同时更新库存中的数据
                    warehouseWS.changeQuantity(dto.getProductId(),dto.getQuantity(),dto.getPrice());
                }else{
                    Double oldQuantity = warehouseInputProWS.getQuantityById(dto.getId());
                    warehouseInputProWS.update(dto);
                    //同时更新库存中的数据
                    Double quantity = dto.getQuantity() - oldQuantity;
                    warehouseWS.changeQuantity(dto.getProductId(),quantity,dto.getPrice());
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

    public void deleteWarehouseInputPro(){
        AjaxMessage massage = new AjaxMessage();
        try{
            WarehouseInputProDTO dto=warehouseInputProWS.getByID(id);
            warehouseInputProWS.delFake(id);

            //同时更新库存中的数据
            Double quantity = dto.getQuantity() * -1;
            warehouseWS.changeQuantity(dto.getProductId(),quantity,dto.getPrice());
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void addProductByApplication(){
        String appli = getJsonAllRows();
        String productId = "";

        List<LinkedTreeMap> listAppli = new ArrayList();
        listAppli = gson.fromJson(appli,listAppli.getClass());
        for(int i=0;i<listAppli.size();i++){
            LinkedTreeMap map = listAppli.get(i);
            //判断货品是否已存在
            WarehouseProductQuery productQuery = new WarehouseProductQuery();
            productQuery.setRows(1);
            productQuery.setSort("id");
            productQuery.setOrder("asc");
            productQuery.setNameQuery(map.get("productName").toString());
            productQuery.setModelNumberQuery(map.get("productModelNumber").toString());
            productQuery.setSpecificationsQuery(map.get("productSpecifications").toString());
            productQuery.setBrandQuery(map.get("productBrand").toString());
            PageBeanEasyUI list = warehouseProductWS.getPageBeanByQueryBean(productQuery);

            //如果相同货品不存在，则在货品库中添加该货品
            if(list.getRows() == null){
                WarehouseProductDTO productDTO = new WarehouseProductDTO();
                productDTO.setName(map.get("productName").toString());
                productDTO.setProductType("原材料");
                productDTO.setModelNumber(map.get("productModelNumber").toString());
                productDTO.setSpecifications(map.get("productSpecifications").toString());
                productDTO.setBrand(map.get("productBrand").toString());
                productDTO.setUnit(map.get("productUnit").toString());
                productDTO.setProductDesc("自动添加");
                productDTO = warehouseProductWS.save(productDTO);
                productId = productDTO.getId();
                //同时更新库存数据
                warehouseWS.addProduct(productId);
            }else{
                WarehouseProductDTO inputProDTO = (WarehouseProductDTO)(list.getRows().get(0));
                productId = inputProDTO.getId();
            }
            //保存货品信息
            WarehouseInputProDTO dto = new WarehouseInputProDTO();
            dto.setParentIdFK(warehouseInput.getId());
            dto.setProductId(productId);
            if(map.get("quantityInput") != null && !Strings.isNullOrEmpty(map.get("quantityInput").toString())) {
                dto.setQuantity(Double.valueOf(map.get("quantityInput").toString()));
            }else{
                dto.setQuantity(Double.valueOf(0));
            }
            if(map.get("price") != null && !Strings.isNullOrEmpty(map.get("price").toString())) {
                dto.setPrice(Double.valueOf(map.get("price").toString()));
            }else{
                dto.setPrice(Double.valueOf(0));
            }
            dto.setValence(dto.getPrice() * dto.getQuantity());
            if(map.get("note") != null) {
                dto.setNote(map.get("note").toString());
            }else{
                dto.setNote("");
            }
            warehouseInputProWS.save(dto);
            //同时更新库存中的数据
            warehouseWS.changeQuantity(dto.getProductId(),dto.getQuantity(),dto.getPrice());
        }
    }

    public void getUserList(){
        List<UserDTO> lists = userWS.getUserByDeptName("采购部");
        if(lists==null){
            lists=new ArrayList<>();
        }
        List<Combobox> project = ComboHelper.getPurchaserNameCombobox(lists);   //根据数据填充下拉框
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
        List<WarehouseApplicationDTO> lists = warehouseApplicationWS.getPageBeanByQueryBean(applyQuery).getRows();
        if(lists==null){
            lists=new ArrayList<>();
        }

        //根据数据填充下拉框
        ArrayList<Combobox> nodes = new ArrayList<>();
        Combobox nodeNull = new Combobox();
        nodeNull.setId(null);
        nodeNull.setText("");
        nodes.add(nodeNull);
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

    public void getNewNumber(){
        query.setSort("id");
        query.setOrder("asc");
        query.setRows(1000);
        String number = warehouseInputWS.getNewNumber(query);
        if(number.length() == 10){
            JsonHelper.writeJson(number);
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
