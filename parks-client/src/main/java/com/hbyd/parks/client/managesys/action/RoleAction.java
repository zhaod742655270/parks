package com.hbyd.parks.client.managesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.managesys.RoleDTO;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.managesys.RoleWS;
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
public class RoleAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {

    private static final long serialVersionUID = 1L;
    private QueryBeanEasyUI page = new QueryBeanEasyUI();

    //add、edit 提交的参数
    private RoleDTO role;


    //delete使用的roleId
    private String roleId;

    //搜索提交的参数
    private String key;

    private String roleName;

    public QueryBeanEasyUI getModel() {
        return page;
    }

    @Resource
    private RoleWS roleWS;

    private Gson gson = new Gson();

    public String roleList() throws IOException {

        String hql = "where roleName like ?";
        List params = new ArrayList();
        params.add(key + "%");

        PageBeanEasyUI list = key == null ? roleWS.getPageBean(page, "", null) : roleWS.getPageBean(page, hql, params.toArray());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    public String getAllRoles() {
        List<RoleDTO> list = roleWS.findAll();
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;
    }

    public String addRole() {
        AjaxMessage message = new AjaxMessage();
        try {
            roleWS.save(role);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editRole() {
        AjaxMessage message = new AjaxMessage();
        try {
            roleWS.update(role);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteRole() {
        AjaxMessage message = new AjaxMessage();
        try {
            roleWS.delByID(roleId);

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

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public RoleDTO getRole() {
        return this.role;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


}
