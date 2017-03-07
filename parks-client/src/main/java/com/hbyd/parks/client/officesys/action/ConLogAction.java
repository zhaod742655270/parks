package com.hbyd.parks.client.officesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.common.model.ConLogQueryBean;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.ws.officesys.ContractGatheringLogWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/6
 */
@Controller
@Scope("prototype")
@Module(description = "收款合同记录")
public class ConLogAction extends ActionSupport implements ModelDriven<ConLogQueryBean> {
    private static final long serialVersionUID = 1L;
    private ConLogQueryBean page = new ConLogQueryBean();
    private Gson gson = new Gson();

//
    private String id;

    @Resource
    private ContractGatheringLogWS contractGatheringLogWS;


    public String conLogList() throws Exception{
        PageBeanEasyUI list = contractGatheringLogWS.getPageBeanByLogQueryBean(page);
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    @Operation(type="操作记录查询")
    public String operationQueryList() throws Exception{
        PageBeanEasyUI list = contractGatheringLogWS.getPageBeanByDate(page);
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }


    public ConLogQueryBean getModel() {
        return page;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
