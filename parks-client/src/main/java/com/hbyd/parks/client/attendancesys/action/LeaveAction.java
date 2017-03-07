package com.hbyd.parks.client.attendancesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.attendancesys.LeaveRecordDTO;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.attendancesys.LeaveRecordWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 14-8-4.
 */
@Controller
@Scope("prototype")
public class LeaveAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {
    private QueryBeanEasyUI page = new QueryBeanEasyUI();
    //搜索提交的参数
    private String key;

    private String name;

    private LeaveRecordDTO leave;

    private String leaveId;
    @Resource
    private LeaveRecordWS leaveRecordWS;

    @Override
    public QueryBeanEasyUI getModel() {
        return page;
    }

    private Gson gson = new Gson();

    public String leaveList() {
        String hql = "where employee.empName like ?";
        List params = new ArrayList();
        params.add(key + "%");

        PageBeanEasyUI list = key == null ? leaveRecordWS.getPageBean(page, "", null) : leaveRecordWS.getPageBean(page, hql, params.toArray());
        // 如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;
    }

    public String addLeave() {
        AjaxMessage message = new AjaxMessage();

        try {

            leaveRecordWS.save(leave);

        } catch (Exception e) {

            message.setSuccess(false);

            message.setMessage(e.getMessage());

        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editLeave() {
        AjaxMessage message = new AjaxMessage();
        try {
            leaveRecordWS.update(leave);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteLeave() {
        AjaxMessage message = new AjaxMessage();
        try {
            leaveRecordWS.delByID(leaveId);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("删除失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }


        return null;
    }

    public String validateNameExist() {

        return null;
    }

    public LeaveRecordDTO getLeave() {
        return leave;
    }

    public void setLeave(LeaveRecordDTO leave) {
        this.leave = leave;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }
}


