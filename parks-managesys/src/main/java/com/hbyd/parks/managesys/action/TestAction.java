package com.hbyd.parks.managesys.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

/**
 * 该类用于测试 Struts 2
 */
@Controller
@Scope("prototype")
public class TestAction extends ActionSupport implements Serializable{
	private static final long serialVersionUID = 1L;

    public TestAction() {
        super();
        System.out.println("construct TestAction !");
    }

    //测试 Struts 2.X 和 Spring 的整合
	public String test_struts_into_spring() throws Exception {

        return SUCCESS;
	}
	
	/**该方法仅为了测试
	 * 
	 * @author ren_xt
	 */
	public void fooTest(){
		System.out.println("print something from TestAction");
	}
}

