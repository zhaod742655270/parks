package com.hbyd.parks.client.attendancesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.attendancesys.ShiftDTO;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.ws.attendancesys.IntervalWS;
import com.hbyd.parks.ws.attendancesys.ShiftWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;


@Controller
@Scope("prototype")
public class ShiftAction extends ActionSupport implements ModelDriven<ShiftDTO> {

    private static final long serialVersionUID = 1L;
    private ShiftDTO shift = new ShiftDTO();


    //搜索提交的参数


    public ShiftDTO getModel() {
        return shift;
    }

    @Resource
    private ShiftWS shiftWS;

    @Resource
    private IntervalWS intervalWS;
    private Gson gson = new Gson();

    /**
     * 返回类型为NORMAL的,有时段的班次
     * @return
     */
    public String getValidNormal() {
        List<ShiftDTO> list = shiftWS.findValidNormal();
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;
    }

    /**查询有效班次：有时段的班次 + 休息班次（没有时段）
     * 排班管理，右键菜单中使用。
     * @return 班次列表
     */
    public String getValidShift() {
        List<ShiftDTO> list = shiftWS.findValidShift();
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;
    }

    /**查询所有类型为NORMAL的班次
     * 班次管理中使用
     * @return 班次列表
     */
    public String getAllNormal()
    {
        List<ShiftDTO> list = shiftWS.findAllNormal();
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;
    }

    public String addShift() {
        AjaxMessage message = new AjaxMessage();
        try {
            shiftWS.save(shift);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editShift() {
        AjaxMessage message = new AjaxMessage();
        try {
            shiftWS.update(shift);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteShift() {
        AjaxMessage message = new AjaxMessage();
        try {
            shiftWS.delByID(shift.getId());
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String validateNameExist() {

        return null;
    }


}
