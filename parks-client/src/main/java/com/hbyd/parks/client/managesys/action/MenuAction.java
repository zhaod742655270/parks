package com.hbyd.parks.client.managesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.managesys.ResMenuDTO;
import com.hbyd.parks.ws.managesys.ResMenuWS;
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
public class MenuAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {

    private static final long serialVersionUID = 1L;
    private QueryBeanEasyUI page = new QueryBeanEasyUI();

    //add、edit 提交的参数
    private ResMenuDTO menu;
    private ResMenuDTO subMenu;
    //delete使用的Id
    private String menuId;
    private String appId;


    public QueryBeanEasyUI getModel() {
        return page;
    }

    @Resource
    private ResMenuWS menuWS;

    private Gson gson = new Gson();


    public String menuList() throws IOException {
        page.setRows(1000);
        page.setSorts(new String[]{"menuName"});
        page.setOrders(new String[]{"asc"});
        List params = new ArrayList();

        if (appId == null && menuId == null) {

            JsonHelper.writeJson("{\"total\":0,\"rows\":[]}");
            return null;
        }
        String hql;
        if (appId == null) {
            hql = "where parent.id= ? ";
            params.add(menuId);
        } else {
            hql = "where parent=null and resApp.id = ? ";
            params.add(appId);
        }


        PageBeanEasyUI list = menuWS.getPageBean(page, hql, params.toArray());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }


    public String getAllMenu() {

        return null;
    }

    public String addMenu() {
        AjaxMessage message = new AjaxMessage();
        ResMenuDTO m = menu == null ? subMenu : menu;
        try {
            menuWS.save(m);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建菜单失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editMenu() {
        AjaxMessage message = new AjaxMessage();
        ResMenuDTO m = menu == null ? subMenu : menu;
        try {
            menuWS.update(m);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑菜单失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteMenu() {
        AjaxMessage message = new AjaxMessage();
        try {
            menuWS.delByID(menuId);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("删除失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }


        return null;
    }


    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public ResMenuDTO getMenu() {
        return menu;
    }

    public void setMenu(ResMenuDTO menu) {
        this.menu = menu;
    }

    public ResMenuDTO getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(ResMenuDTO subMenu) {
        this.subMenu = subMenu;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
