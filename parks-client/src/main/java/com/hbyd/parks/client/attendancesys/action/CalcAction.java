package com.hbyd.parks.client.attendancesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.attendancesys.CalcWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 14-8-4.
 */
@Controller
@Scope("prototype")
public class CalcAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {

    private QueryBeanEasyUI page = new QueryBeanEasyUI();

    //排班用到的参数

    private String deptId;
    private List<String> empIds;
    private Date date;

    @Override

    public QueryBeanEasyUI getModel() {
        return page;
    }

    private Gson gson = new Gson();

    @Resource
    private CalcWS calcWS;


    public String calcForDept() {
        AjaxMessage message = new AjaxMessage();
        try {
            calcWS.recalcDept(deptId, date, date);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("计算失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }


    public String calcForEmps() {
        AjaxMessage message = new AjaxMessage();
        try {
            int size = empIds.size();
            String[] emps = empIds.toArray(new String[size]);
             calcWS.recalcEmps(empIds,date,date);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("计算失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }


    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<String> getEmpIds() {
        return empIds;
    }

    public void setEmpIds(List<String> empIds) {
        this.empIds = empIds;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}


