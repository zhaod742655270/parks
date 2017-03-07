package com.hbyd.parks.client.managesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.DOMHelper;
import com.hbyd.parks.client.util.ExcludeResource;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.client.util.TreeHelper;
import com.hbyd.parks.dto.managesys.*;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.EasyUITree;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
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
public class PrivilegeAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private List<PriviledgeDTO> pris;
    private String appId;
    private String roleId;
    private String menuId;
    //  private String userId;

    @Resource
    private PriviledgeWS priWS;
    @Resource
    private ResAppWS appWS;
    @Resource
    private ResMenuWS menuWS;
    @Resource
    private ResBtnWS btnWS;

    private Gson gson = new Gson();

    private UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");

    private QueryBeanEasyUI page = new QueryBeanEasyUI();

    public String getApps() {
        Collection<ResAppDTO> appDTOs;
        if (user.getUserName().equals("super")) {
            appDTOs = appWS.findAll();
        } else {
            appDTOs = ExcludeResource.excludeApp(priWS.getApps(this.getUserId()));
        }

        if (appDTOs != null) {
            List<HashMap<String, String>> list = DOMHelper.getApps(appDTOs);
            String result = gson.toJson(list);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String getMenus() {
        Collection<ResMenuDTO> menuDTOs;
        if (user.getUserName().equals("super")) {
            page.setRows(1000);
            page.setSorts( new String[]{"menuOrder"});
            page.setOrders(new String[]{"asc"});
            List params = new ArrayList();
            String hql = "where resApp.id = ? ";
            params.add(appId);
            PageBeanEasyUI list = menuWS.getPageBean(page, hql, params.toArray());
            menuDTOs = list.getRows();
        } else {

            menuDTOs = ExcludeResource.excludeMenu(priWS.getMenus(this.getUserId(), appId));
        }
        ResAppDTO app = appWS.getByID(appId);
        HashMap<String, String> according = DOMHelper.getMenus(app, menuDTOs);
        String result = gson.toJson(according);
        JsonHelper.writeJson(result);
        return null;
    }

    public String getBtns() {
        Collection<ResBtnDTO> btnDTOs;
        if (user.getUserName().equals("super")) {
            page.setRows(1000);
            page.setSorts( new String[]{"btnOrder"});
            page.setOrders(new String[]{"asc"});
            List params = new ArrayList();

            String hql = "where resMenu.id= ? ";
            params.add(menuId);
            PageBeanEasyUI list = btnWS.getPageBean(page, hql, params.toArray());
            btnDTOs = list.getRows();
        } else {
            btnDTOs = ExcludeResource.excludeBtn(priWS.getButtons(this.getUserId(), menuId));
        }

        String result = "";
        if (btnDTOs != null) {
            List<HashMap<String, String>> list = DOMHelper.getBtns(btnDTOs);
            result = gson.toJson(list);
        }
        JsonHelper.writeJson(result);
        return null;
    }

    public String getTree() {
        ResAppDTO app = appWS.getByID(appId);
        List<EasyUITree> trees = TreeHelper.getPriTree(app);
        String result = gson.toJson(trees);
        JsonHelper.writeJson(result);
        return null;
    }

    public String priList() throws IOException {

        List<PriviledgeDTO> list = priWS.getResPriDTOs(roleId, appId);
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    public String savePris() {

        AjaxMessage message = new AjaxMessage();
        try {
            priWS.updateBatch(roleId, appId, pris);
            message.setMessage("保存成功");

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("保存失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<PriviledgeDTO> getPris() {
        return pris;
    }

    public void setPris(List<PriviledgeDTO> pris) {
        this.pris = pris;
    }

    private String getUserId() {
        ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
        UserDTO user = (UserDTO) session.get("user");
        return user.getId();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }


}
