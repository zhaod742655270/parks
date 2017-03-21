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

    @Resource
    private WarehouseApplicationProWS warehouseApplicationProWS;

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

            //同时更新库存信息
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
                    Double quantity = dto.getQuantity() * -1;
                    warehouseWS.changeQuantity(dto.getProductId(), quantity, dto.getPrice());

                    //同时伪删除入库单下货品信息
                    warehouseInputProWS.delFake(dto.getId());
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

    @Operation(type="删除入库货品信息")
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
            //如果相同货品不存在，则在货品库中添加该货品
            //返回货品ID
            productId = warehouseProductWS.isProductExist(map);

            //填充DTO
            WarehouseInputProDTO dto = fillInputProDTO(map,productId);
            //保存货品信息
            dto = warehouseInputProWS.save(dto);

            //同时更新库存中的数据
            warehouseWS.changeQuantity(dto.getProductId(),dto.getQuantity(),dto.getPrice());

            //将申请单中该物品置为已完成
            dto = warehouseInputProWS.getByID(dto.getId());
            dto.getWarehouseApplicationPro().setFinished(true);
            warehouseInputProWS.update(dto);
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
        if(map.get("valence") != null && !Strings.isNullOrEmpty(map.get("valence").toString())) {
            dto.setValence(Double.valueOf(map.get("valence").toString()));
        }else{
            dto.setValence(Double.valueOf(0));
        }
        dto.setProductNum(map.get("productNum").toString());
        if(map.get("note") != null) {
            dto.setNote(map.get("note").toString());
        }else{
            dto.setNote("");
        }
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
        dto.setValence(dto.getPrice() * dto.getQuantity());
        dto.setProductNum(map.get("productNum").toString());
        if(map.get("note") != null) {
            dto.setNote(map.get("note").toString());
        }else{
            dto.setNote("");
        }
        WarehouseApplicationProDTO applyPro = new WarehouseApplicationProDTO();
        applyPro.setId(map.get("id").toString());
        dto.setWarehouseApplicationPro(applyPro);
        return dto;
    }

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
