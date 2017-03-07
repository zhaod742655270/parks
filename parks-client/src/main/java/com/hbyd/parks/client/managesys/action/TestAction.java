package com.hbyd.parks.client.managesys.action;

import com.hbyd.parks.dto.managesys.RoleDTO;
import com.hbyd.parks.ws.managesys.RoleWS;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by allbutone on 14-7-10.
 */
@Controller
@Scope("prototype")
public class TestAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    @Resource
    private RoleWS roleWS;//注入角色服务客户端

    public String test_struts_into_spring() throws Exception {

        List<RoleDTO> dlist = roleWS.findAll();
        for(RoleDTO role : dlist){
            System.out.println(role.getRoleName());
        }

        return SUCCESS;
    }
}
