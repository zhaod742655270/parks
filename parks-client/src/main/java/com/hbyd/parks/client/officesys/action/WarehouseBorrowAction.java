package com.hbyd.parks.client.officesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.ComboHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.model.*;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.officesys.WarehouseBorrowDTO;
import com.hbyd.parks.dto.officesys.WarehouseDTO;
import com.hbyd.parks.ws.managesys.UserWS;
import com.hbyd.parks.ws.officesys.WarehouseBorrowWS;
import com.hbyd.parks.ws.officesys.WarehouseWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhao_d on 2017/3/3.
 */
@Controller
@Scope("prototype")
@Module(description = "借用信息")
public class WarehouseBorrowAction extends ActionSupport implements ModelDriven<WarehouseBorrowQuery>{
    @Resource
    private WarehouseBorrowWS warehouseBorrowWS;

    @Resource
    private UserWS userWS;

    @Resource
    private WarehouseWS warehouseWS;

    private WarehouseBorrowQuery query = new WarehouseBorrowQuery();
    private WarehouseBorrowDTO warehouseBorrow = new WarehouseBorrowDTO();
    private Gson gson = new Gson();
    private String id;
    private String productId;

    public void warehouseBorrowList(){
        PageBeanEasyUI list = warehouseBorrowWS.getPageBeanByQuery(query);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public void addWarehouseBorrow(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseBorrow.setState("未归还");
            warehouseBorrow = warehouseBorrowWS.save(warehouseBorrow);
            //同时更新库存信息
            warehouseWS.changeQuantityByBorrow(warehouseBorrow.getProductId(),warehouseBorrow.getQuantity());
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void editWarehouseBorrow(){
        AjaxMessage massage = new AjaxMessage();
        try{
            WarehouseBorrowDTO dto = warehouseBorrowWS.getByID(warehouseBorrow.getId());
            Double oldQuantity = dto.getQuantity();
            warehouseBorrowWS.update(warehouseBorrow);
            //同时更新库存信息
            if(dto.getState().equals("未归还")){
                Double quantity = warehouseBorrow.getQuantity() - oldQuantity;
                warehouseWS.changeQuantityByBorrow(warehouseBorrow.getProductId(),quantity);
            }
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void deleteWarehouseBorrow(){
        AjaxMessage massage = new AjaxMessage();
        try{
            WarehouseBorrowDTO dto = warehouseBorrowWS.getByID(id);
            warehouseBorrowWS.delFake(id);
            //同时更新库存数据
            if(dto.getState().equals("未归还")){
                warehouseWS.changeQuantityByBorrow(dto.getProductId(),dto.getQuantity() * -1);
            }
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void backProduct(){
        AjaxMessage massage = new AjaxMessage();
        try {
            WarehouseBorrowDTO dto = warehouseBorrowWS.getByID(id);
            if (dto.getState().equals("未归还")){
                //更新状态
                dto.setState("已归还");
                dto.setBackDate(DateTime.now().toString("yyyy-MM-dd"));
                warehouseBorrowWS.update(dto);
                //同时更新库存信息
                warehouseWS.changeQuantityByBorrow(dto.getProductId(),dto.getQuantity() * -1);
            }
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
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

    public void getProductList(){
        List<WarehouseDTO> lists = warehouseWS.findAllValid();
        if(lists==null){
            lists=new ArrayList<>();
        }
        List<Combobox> project = ComboHelper.getProductNameCombobox(lists);   //根据数据填充下拉框
        String result = gson.toJson(project);
        JsonHelper.writeJson(result);
    }

    public void getProductInfo(){
        WarehouseQuery query = new WarehouseQuery();
        query.setSort("id");
        query.setOrder("asc");
        query.setRows(1000);
        query.setProductIdQuery(productId);
        PageBeanEasyUI page = warehouseWS.getPageBeanByQueryBean(query);
        WarehouseDTO list = new WarehouseDTO();
        if(page.getRows().size() != 0){
            list=(WarehouseDTO) page.getRows().get(0);
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public void getNewNumber(){
        query.setSort("id");
        query.setOrder("asc");
        query.setRows(1000);
        String number = warehouseBorrowWS.getNewNumber(query);
        if(number.length() == 10){
            JsonHelper.writeJson(number);
        }
    }

    @Override
    public WarehouseBorrowQuery getModel() {
        return query;
    }

    public WarehouseBorrowQuery getQuery() {
        return query;
    }

    public void setQuery(WarehouseBorrowQuery query) {
        this.query = query;
    }

    public WarehouseBorrowDTO getWarehouseBorrow() {
        return warehouseBorrow;
    }

    public void setWarehouseBorrow(WarehouseBorrowDTO warehouseBorrow) {
        this.warehouseBorrow = warehouseBorrow;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
