package com.hbyd.parks.client.officesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseCompanyQuery;
import com.hbyd.parks.dto.officesys.WarehouseCompanyInDTO;
import com.hbyd.parks.ws.officesys.WarehouseCompanyInWS;
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
@Module(description = "供应商信息")
public class WarehouseCompanyInAction extends ActionSupport implements ModelDriven<WarehouseCompanyQuery>{

    @Resource
    WarehouseCompanyInWS warehouseCompanyInWS;

    private WarehouseCompanyInDTO warehouseCompany = new WarehouseCompanyInDTO();
    private WarehouseCompanyQuery query = new WarehouseCompanyQuery();
    private Gson gson = new Gson();
    private String id;

    public void warehouseCompanyList(){
        PageBeanEasyUI list = warehouseCompanyInWS.getPageBeanByQueryBean(query);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public void addWarehouseCompany(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseCompanyInWS.save(warehouseCompany);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void editWarehouseCompany(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseCompanyInWS.update(warehouseCompany);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void deleteWarehouseCompany(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseCompanyInWS.delFake(id);
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

    public WarehouseCompanyInDTO getWarehouseCompany() {
        return warehouseCompany;
    }

    public void setWarehouseCompany(WarehouseCompanyInDTO warehouseCompany) {
        this.warehouseCompany = warehouseCompany;
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
}
