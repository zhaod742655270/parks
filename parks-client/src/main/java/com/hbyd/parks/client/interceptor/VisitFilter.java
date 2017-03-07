package com.hbyd.parks.client.interceptor;

import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.ws.managesys.PriviledgeWS;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* Created by IntelliJ IDEA
* Author:Zhang_F
* Data:2015/9/24
*/
public class VisitFilter extends HttpServlet implements Filter {

    private static final long serialVersionUID=1L;

    @Resource
    private PriviledgeWS priviledgeWS;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        if((req.getRequestURI().contains("/managesys/")||(req.getRequestURI().contains("/officesys/")))
                &&(req.getRequestURI().endsWith(".jsp"))) {
            if (!req.isRequestedSessionIdValid()) {
                res.setHeader("status", "jspTimeout");
                res.sendRedirect(req.getContextPath() + "/timeout.jsp");
            } else{
                UserDTO user = (UserDTO) req.getSession().getAttribute("user");
                if (user == null) {
//                    res.setHeader("status", "jspTimeout");
//                    res.sendRedirect(req.getContextPath() + "/timeout.jsp");
                    chain.doFilter(request, response);
                } else if (user.getUserName().equals("super")) {
                    chain.doFilter(request, response);
                }
                //如果有权限，继续调用
                else if (priviledgeWS.validatePriviledge(user.getId(), req.getRequestURI().substring(8))) {
                    chain.doFilter(request, response);
                }
                //如果没有权限，返回无权限页面
                else {
                    res.sendRedirect(((HttpServletRequest) request).getContextPath() + "/unauthorized.jsp");
                }
            }

        }else {

            chain.doFilter(request,response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
