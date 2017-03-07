package com.hbyd.parks.client.managesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.managesys.ResBtnDTO;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.managesys.ResBtnWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 14-7-14.
 */
@Controller
@Scope("prototype")
public class ButtonAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {

    private static final long serialVersionUID = 1L;
    private QueryBeanEasyUI page = new QueryBeanEasyUI();

    //add、edit 提交的参数
    private ResBtnDTO btn;

    //delete使用的Id
    private String btnId;

    private String menuId;

    public QueryBeanEasyUI getModel() {
        return page;
    }

    @Resource
    private ResBtnWS btnWS;

    private Gson gson = new Gson();


    public String btnList() throws IOException {
        page.setRows(1000);
        page.setSorts( new String[]{"btnName"});
        page.setOrders(new String[]{"asc"});
        List params = new ArrayList();
        if (menuId == null) {
            JsonHelper.writeJson("{}");
            return null;
        }
        String hql = "where resMenu.id= ? ";
        params.add(menuId);
        PageBeanEasyUI list = btnWS.getPageBean(page, hql, params.toArray());

        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    public String addBtn() {
        AjaxMessage message = new AjaxMessage();
        try {
            btnWS.save(btn);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建按钮失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editBtn() {
        AjaxMessage message = new AjaxMessage();
        try {
            btnWS.update(btn);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑按钮失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteBtn() {
        AjaxMessage message = new AjaxMessage();
        try {
            btnWS.delByID(btnId);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("删除按钮失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }


        return null;
    }

    public ResBtnDTO getBtn() {
        return btn;
    }

    public void setBtn(ResBtnDTO btn) {
        this.btn = btn;
    }

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
