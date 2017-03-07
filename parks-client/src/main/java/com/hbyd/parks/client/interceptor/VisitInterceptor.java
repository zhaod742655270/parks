package com.hbyd.parks.client.interceptor;

import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.ws.managesys.PriviledgeWS;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    @Resource
    private PriviledgeWS priviledgeWS;

    @Override
    public String intercept(ActionInvocation ai) throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        //取得请求的URL
        String url = ServletActionContext.getRequest().getRequestURL().toString();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        //对登录与注销请求直接放行,不予拦截
        if (url.indexOf("login") != -1 || url.indexOf("logout") != -1 || url.indexOf("authentication") != -1) {
            return ai.invoke();
        }
        //其他请求
        else {
            //验证Session是否过期 如果过期返回登录页
            if (!ServletActionContext.getRequest().isRequestedSessionIdValid()) {
                //返回登录页
                response.setHeader("sessionStatus", "timeout");
                JsonHelper.writeJson("{\"total\":0,\"rows\":[],\"message\":\"会话已经过期，请重新登录\",\"success\":false}");
                return null;

            } else {
                UserDTO user = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
                //验证是否已经登录
                if (user == null) {
                    response.setHeader("sessionstatus", "timeout");
                    JsonHelper.writeJson("{\"total\":0,\"rows\":[],\"message\":\"会话已经过期，请重新登录\",\"success\":false}");
                    return null;
                } else if (user.getUserName().equals("super")) {
                    return ai.invoke();
                } else if (url.indexOf("add") != -1 || url.indexOf("edit") != -1 || url.indexOf("delete") != -1 || url.indexOf("sum") != -1) {
                        //验证权限
                        boolean hasPri = priviledgeWS.validatePriviledge(user.getId(), request.getRequestURI().substring(8));
                        //如果有权限，继续调用
                        if (hasPri)
                            return ai.invoke();
                            //如果没有权限，返回无权限页面
                        else {
                            response.setHeader("sessionStatus", "unauthorized");
                            JsonHelper.writeJson("{\"total\":0,\"rows\":[],\"message\":\"您没有权限\",\"success\":false}");
                        }
                    }else {
                    return ai.invoke();
                }


            }
        }
        return null;
    }

}
