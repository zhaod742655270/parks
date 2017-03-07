package com.hbyd.parks.client.attendancesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.attendancesys.GlobalDefDTO;
import com.hbyd.parks.common.enums.AssertType;
import com.hbyd.parks.common.enums.RestDay;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.ws.attendancesys.GlobalDefWS;
import com.opensymphony.xwork2.ActionSupport;

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
public class GlobalShiftAction extends ActionSupport {


    private GlobalDefDTO definition;

    private String[] days;
    private String invalidAssert;

    private Gson gson = new Gson();

    @Resource
    private GlobalDefWS globalDefWS;


    public String saveShift() {
        AjaxMessage message = new AjaxMessage();
        try {

            globalDefWS.update(getDefinitionDTO());

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("保存设置失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String loadShift() {
        AjaxMessage message = new AjaxMessage();
        try {

            List<GlobalDefDTO> list = globalDefWS.findAll();
            if (list != null) {
                message.setData(list);

            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            message.setSuccess(false);

            message.setMessage("加载失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String validateNameExist() {

        return null;
    }

    private GlobalDefDTO getDefinitionDTO() {
        GlobalDefDTO result = definition;
        List<RestDay> ds = new ArrayList<>();
        if (days != null) {
            for (String d : days) {
                switch (d) {
                    case "星期一":
                        ds.add(RestDay.星期一);
                        break;
                    case "星期二":
                        ds.add(RestDay.星期二);
                        break;

                    case "星期三":
                        ds.add(RestDay.星期三);
                        break;

                    case "星期四":
                        ds.add(RestDay.星期四);
                        break;

                    case "星期五":
                        ds.add(RestDay.星期五);
                        break;

                    case "星期六":
                        ds.add(RestDay.星期六);
                        break;

                    case "星期日":
                        ds.add(RestDay.星期日);
                        break;
                }
            }


        }
        result.setRestDays(ds);
        if (invalidAssert != null) {
            switch (invalidAssert) {
                case "旷工":
                    result.setInvalidAssert(AssertType.旷工);
                    break;
                case "休息":
                    result.setInvalidAssert(AssertType.休息);
            }
        }

        return result;
    }


    public GlobalDefDTO getDefinition() {
        return definition;
    }

    public void setDefinition(GlobalDefDTO definition) {
        this.definition = definition;
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public String getInvalidAssert() {
        return invalidAssert;
    }

    public void setInvalidAssert(String invalidAssert) {
        this.invalidAssert = invalidAssert;
    }
}
