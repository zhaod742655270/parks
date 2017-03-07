package com.hbyd.parks.client.officesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseQuery;
import com.hbyd.parks.ws.officesys.WarehouseWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Zhao_d on 2017/2/9.
 */
@Controller
@Scope("prototype")
@Module(description = "库存信息")
public class WarehouseAction extends ActionSupport implements ModelDriven<WarehouseQuery>{

    @Resource
    private WarehouseWS warehouseWS;

    private WarehouseQuery query = new WarehouseQuery();
    private Gson gson = new Gson();

    public void warehouseList(){
        PageBeanEasyUI list = warehouseWS.getPageBeanByQueryBean(query);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public WarehouseQuery getModel(){
        return query;
    }

    public WarehouseQuery getQuery() {
        return query;
    }

    public void setQuery(WarehouseQuery query) {
        this.query = query;
    }
}
