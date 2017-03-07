package com.hbyd.parks.client.attendancesys.action;

import com.google.gson.Gson;

import com.hbyd.parks.client.util.JsonHelper;

import com.hbyd.parks.common.model.AjaxMessage;

import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.attendancesys.RegularShiftDTO;
import com.hbyd.parks.dto.attendancesys.ShiftBindingDTO;
import com.hbyd.parks.dto.managesys.*;
import com.hbyd.parks.ws.attendancesys.RegularShiftWS;
import com.hbyd.parks.ws.managesys.PriviledgeWS;
import com.hbyd.parks.ws.managesys.ResAppWS;
import com.hbyd.parks.ws.managesys.ResBtnWS;
import com.hbyd.parks.ws.managesys.ResMenuWS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created by len on 14-7-14.
 */
@Controller
@Scope("prototype")
public class regularShiftAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private RegularShiftDTO regularShift;
    private List<ShiftBindingDTO> bindings;
    private String regularShiftId;

    @Resource
    private RegularShiftWS regularShiftWS;

    private Gson gson = new Gson();

    public String regularShiftList() {
        List<RegularShiftDTO> regularShifts = regularShiftWS.findAll();
        String result = gson.toJson(regularShifts);
        JsonHelper.writeJson(result);
        return null;
    }

    public String getBindingByRegularShift() {
        List<ShiftBindingDTO> sbs = regularShiftWS.getShiftBindings(regularShiftId);
        String result = gson.toJson(sbs);
        JsonHelper.writeJson(result);
        return null;
    }

    public String addRegularShift() {
        AjaxMessage message = new AjaxMessage();
        try {
            regularShiftWS.save(regularShift);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editRegularShift() {
        AjaxMessage message = new AjaxMessage();
        try {
            regularShiftWS.update(regularShift);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteRegularShift() {
        AjaxMessage message = new AjaxMessage();
        try {
            regularShiftWS.delByID(regularShiftId);

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

    public RegularShiftDTO getRegularShift() {
        return regularShift;
    }

    public void setRegularShift(RegularShiftDTO regularShift) {
        this.regularShift = regularShift;
    }

    public List<ShiftBindingDTO> getBindings() {
        return bindings;
    }

    public void setBindings(List<ShiftBindingDTO> bindings) {
        this.bindings = bindings;
    }

    public String getRegularShiftId() {
        return regularShiftId;
    }

    public void setRegularShiftId(String regularShiftId) {
        this.regularShiftId = regularShiftId;
    }
}
