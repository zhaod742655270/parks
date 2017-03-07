package com.hbyd.parks.managesys.restImpl;

import com.hbyd.parks.dto.managesys.UserDTO;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 使用 Jetty 发布 WebService, 需要引入 cxf-rt-transports-http-jetty 3.0.0
 * 
 * @author ren_xt
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class TestUserRestServerWithJetty {

	@Resource
	private UserRestWSImpl userService;

	@Test
	public void testRestful() {
		// Service instance
		JAXRSServerFactoryBean restServer = new JAXRSServerFactoryBean();
		restServer.setResourceClasses(UserDTO.class);
		restServer.setServiceBean(userService);
		restServer.setAddress("http://localhost:9999/");//http://localhost:9000/userService/user/2
		restServer.create();
		System.out.println("Server Started");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			br.readLine();//控制台随便输入什么内容，Jetty 就停下了
		} catch (IOException e) {
		}
		System.out.println("Server Stopped");
		System.exit(0);
	}
}
