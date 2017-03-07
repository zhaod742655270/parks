package com.hbyd.parks.client.officesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.PaymentLogQueryBean;
import com.hbyd.parks.ws.officesys.PaymentLogWS;
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
@Module(description = "付款合同记录")
public class PaymentLogAction extends ActionSupport implements ModelDriven<PaymentLogQueryBean> {
    private static final long serialVersionUID = 1L;
    private PaymentLogQueryBean queryBean = new PaymentLogQueryBean();
    private Gson gson = new Gson();

//
    private String id;

    @Resource
    private PaymentLogWS paymentLogWS;


    public String paymentLogList() throws Exception{
        PageBeanEasyUI list = paymentLogWS.getPageBeanByLogQueryBean(getQueryBean());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    @Operation(type="操作记录查询")
    public String operationQueryList() throws Exception{
        PageBeanEasyUI list = paymentLogWS.getPageBeanByDate(getQueryBean());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }


    public PaymentLogQueryBean getModel() {
        return getQueryBean();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentLogQueryBean getQueryBean() {
        return queryBean;
    }

    public void setQueryBean(PaymentLogQueryBean queryBean) {
        this.queryBean = queryBean;
    }
}
