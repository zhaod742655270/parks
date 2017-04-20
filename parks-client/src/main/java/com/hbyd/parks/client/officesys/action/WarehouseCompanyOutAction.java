package com.hbyd.parks.client.officesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseCompanyQuery;
import com.hbyd.parks.dto.officesys.WarehouseCompanyOutDTO;
import com.hbyd.parks.ws.officesys.WarehouseCompanyOutWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Zhao_d on 2017/1/20.
 */
@Controller
@Scope("prototype")
@Module(description = "客户信息")
public class WarehouseCompanyOutAction extends ActionSupport implements ModelDriven<WarehouseCompanyQuery>{

    @Resource
    WarehouseCompanyOutWS warehouseCompanyOutWS;

    private WarehouseCompanyOutDTO warehouseCompany = new WarehouseCompanyOutDTO();
    private WarehouseCompanyQuery query = new WarehouseCompanyQuery();
    private Gson gson = new Gson();
    private String id;

    public void warehouseCompanyList(){
        PageBeanEasyUI list = warehouseCompanyOutWS.getPageBeanByQueryBean(query);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    @Operation(type="新增客户信息")
    public void addWarehouseCompany(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseCompanyOutWS.save(warehouseCompany);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="修改客户信息")
    public void editWarehouseCompany(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseCompanyOutWS.update(warehouseCompany);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="删除客户信息")
    public void deleteWarehouseCompany(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseCompanyOutWS.delFake(id);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    @Override
    public WarehouseCompanyQuery getModel() {
        return query;
    }

    public WarehouseCompanyQuery getQuery() {
        return query;
    }

    public void setQuery(WarehouseCompanyQuery query) {
        this.query = query;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WarehouseCompanyOutDTO getWarehouseCompany() {
        return warehouseCompany;
    }

    public void setWarehouseCompany(WarehouseCompanyOutDTO warehouseCompany) {
        this.warehouseCompany = warehouseCompany;
    }
}
