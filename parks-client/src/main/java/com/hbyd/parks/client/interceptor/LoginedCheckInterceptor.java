package com.hbyd.parks.client.interceptor;

import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.managesys.UserDTO;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginedCheckInterceptor extends AbstractInterceptor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 拦截请求并进行登录有效性验证
     */
    public String intercept(ActionInvocation ai) throws Exception {
        //取得请求的URL
        String url = ServletActionContext.getRequest().getRequestURL().toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        UserDTO user = null;

        //对登录与注销请求直接放行,不予拦截
        if (url.indexOf("login") != -1 || url.indexOf("logout") != -1 || url.indexOf("authentication") != -1) {
            return ai.invoke();
        } else {
            //验证Session是否过期
            if (!ServletActionContext.getRequest().isRequestedSessionIdValid()) {

                response.setHeader("sessionstatus", "timeout");
                JsonHelper.writeJson("{\"total\":0,\"rows\":[],\"message\":\"会话已经过期，请重新登录\",\"success\":false}");
                return null;
            } else {
                user = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
                //验证是否已经登录
                if (user == null) {
                    //尚未登录。
                    response.setHeader("sessionstatus", "timeout");
                    JsonHelper.writeJson("{\"total\":0,\"rows\":[],\"message\":\"会话已经过期，请重新登录\",\"success\":false}");
                    return null;
                } else {
                    return ai.invoke();

                }
            }
        }
    }

}
