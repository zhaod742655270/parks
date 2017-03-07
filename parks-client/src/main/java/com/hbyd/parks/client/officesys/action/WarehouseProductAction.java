package com.hbyd.parks.client.officesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseProductQuery;
import com.hbyd.parks.dto.officesys.WarehouseProductDTO;
import com.hbyd.parks.ws.officesys.WarehouseProductWS;
import com.hbyd.parks.ws.officesys.WarehouseWS;
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
@Module(description = "货品信息管理")
public class WarehouseProductAction extends ActionSupport implements ModelDriven<WarehouseProductQuery> {

    @Resource
    private WarehouseProductWS warehouseProductWS;

    @Resource
    private WarehouseWS warehouseWS;

    private WarehouseProductDTO warehouseProduct = new WarehouseProductDTO();
    private WarehouseProductQuery query = new WarehouseProductQuery();
    private Gson gson = new Gson();
    private String id;

    public void warehouseProductList(){
        PageBeanEasyUI list = warehouseProductWS.getPageBeanByQueryBean(query);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }


    public void addWarehouseProduct(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseProduct = warehouseProductWS.save(warehouseProduct);
            //同时更新库存数据
            warehouseWS.addProduct(warehouseProduct.getId());
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void editWarehouseProduct(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseProductWS.update(warehouseProduct);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void deleteWarehouseProduct(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseProductWS.delFake(id);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public WarehouseProductDTO getWarehouseProduct() {
        return warehouseProduct;
    }

    public void setWarehouseProduct(WarehouseProductDTO warehouseProduct) {
        this.warehouseProduct = warehouseProduct;
    }

    public WarehouseProductQuery getQuery() {
        return query;
    }

    public void setQuery(WarehouseProductQuery query) {
        this.query = query;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public WarehouseProductQuery getModel() {
        return query;
    }
}
