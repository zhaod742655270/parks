package com.hbyd.parks.client.managesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.ws.managesys.PriviledgeWS;
import com.hbyd.parks.ws.managesys.UserWS;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Scope("prototype")
public class LoginAction extends ActionSupport {

    // 测试 Struts 2.X 和 Spring 的整合
    private static final long serialVersionUID = 1L;
    private UserDTO user = new UserDTO();

    @Resource
    private UserWS userWS;
    @Resource
    private PriviledgeWS priviledgeWS;
    private HttpServletRequest request;
    private HttpSession session;

    private Gson gson = new Gson();

    public String login() throws Exception {

        AjaxMessage message = new AjaxMessage();
        try {
            boolean chechked = priviledgeWS.checkUser(user.getUserName(), user.getPassword());
            if (chechked) {
                request = ServletActionContext.getRequest();
                session = request.getSession();
                user = userWS.getByName(user.getUserName());
                session.setAttribute("user", user);

            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            message.setMessage("");
            message.setSuccess(false);

        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }


    public String logout() {
        AjaxMessage message = new AjaxMessage();
        try {
            request = ServletActionContext.getRequest();
            session = request.getSession();
            session.removeAttribute("user");
        } catch (Exception e) {

        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}
