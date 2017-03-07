package com.hbyd.parks.client.managesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.managesys.RoleDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.managesys.UserWS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by len on 14-7-17.
 */
@Controller
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {

    private static final long serialVersionUID = 1L;
    private QueryBeanEasyUI page = new QueryBeanEasyUI();

    //add、edit 提交的参数
    private UserDTO user;
    private String[] roleIds;
    //delete使用的Id
    private String userId;
    private String oldPwd;
    private String newPwd;
    //搜索提交的参数
    private String key;
    private String userName;


    //验证重名需要的参数
    private String validateValue;
    private String validateId;

    public QueryBeanEasyUI getModel() {
        return page;
    }

    @Resource
    private UserWS userWS;

    private Gson gson = new Gson();

    public String userList() throws IOException {
        UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
        String hql;
        List params = new ArrayList();
        if (key != null) {
            params.add(key + "%");
        } else {
            params.add("%%");
        }
        if (user.getUserName().equals("super")) {
            hql = "where  userName like ? ";
        } else {
            hql = "where  userName like ? and  userName <> ?";
            params.add("super");
        }


        PageBeanEasyUI list = userWS.getPageBean(page, hql, params.toArray());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    public String getCurrentUser() {
        UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
        String result = gson.toJson(user);
        JsonHelper.writeJson(result);
        return null;
    }

    public String editUserPwd() {
        AjaxMessage message = new AjaxMessage();
        try {
            UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
            if (user.getPassword().equals(oldPwd)) {
                userWS.updatePwd(user.getId(), newPwd);
                user.setPassword(newPwd);
            }
            else
            {
                message.setSuccess(false);
                message.setMessage("旧密码不正确");

            }
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("服务器内部错误，修改失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String addUser() {
        AjaxMessage message = new AjaxMessage();

        try {
            UserDTO u = getFullUser();
            userWS.save(u);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    private UserDTO getFullUser() {
        UserDTO result = user;
        if (result.getDepartment().getId().equals("")) {
            result.setDepartment(null);
        }
        Set<RoleDTO> roles = new HashSet<>();
        if (roleIds != null) {
            for (int i = 0; i < roleIds.length; i++) {

                RoleDTO role = new RoleDTO();
                role.setId(roleIds[i]);
                roles.add(role);
            }
            result.setRoles(roles);
        }
        return result;
    }


    public String editUser() {
        AjaxMessage message = new AjaxMessage();
        try {
            UserDTO u = getFullUser();
            userWS.update(u);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteUser() {
        AjaxMessage message = new AjaxMessage();
        try {
            userWS.delByID(userId);

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
        AjaxMessage message = new AjaxMessage();
        try {
            boolean check = userWS.check(validateId, validateValue);
            if (check) {
                message.setSuccess(false);
                message.setMessage("用户名已存在");
            }

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("验证失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getValidateValue() {
        return validateValue;
    }

    public void setValidateValue(String validateValue) {
        this.validateValue = validateValue;
    }

    public String getValidateId() {
        return validateId;
    }

    public void setValidateId(String validateId) {
        this.validateId = validateId;
    }
}
