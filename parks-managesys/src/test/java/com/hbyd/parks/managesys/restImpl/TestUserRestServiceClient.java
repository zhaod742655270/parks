package com.hbyd.parks.managesys.restImpl;

import com.google.gson.Gson;
import com.hbyd.parks.dto.managesys.UserDTO;
import junit.framework.Assert;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**Http-centric web client
 * @author ren_xt
 *
 */
public class TestUserRestServiceClient {
	/*
		get() delete() post(obj) put(obj) 的返回值都是 Response 对象
		get(dClass) post(obj, dClass) put(obj, dClass) 返回的都是 dClass 指定的泛型
	 */
	@Test
	public void testJettyClient(){
		WebClient client = WebClient.create("http://localhost:9999/");
		UserDTO user = client.path("userService/user/12")//
			.accept("application/xml")//
			.get(UserDTO.class);//GET 方式调用, get() 的参数：expected type of response object
		
		System.out.println(new Gson().toJson(user));
	}
	
	@Test
	public void testTomcatClient(){
		WebClient client = WebClient.create("http://localhost:8080/");
		String userID = "24c686bf470e69a201470e6ffb1c0001";
		UserDTO user = client.path("common/ws/userService/user/"+userID)//
			.accept(MediaType.APPLICATION_XML)
			.get(UserDTO.class);//GET 方式调用, get() 的参数：expected type of response object
		System.out.println(new Gson().toJson(user));
	}
	
	/**
	 * 测试 UserRestServiceImpl 中对 UserDTO 的 crud 操作
	 * @author ren_xt
	 */
	@Test
	public void testCrud(){
		WebClient client = WebClient.create("http://localhost:8080/");
		client
//			.type(MediaType.APPLICATION_XML)//HTTP content-type
			.accept(MediaType.APPLICATION_XML);//HTTP accept
		
		
		UserDTO user = new UserDTO();
		user.setUserName("test_username");
		user.setNickname("test_nickname");
		user.setPassword("test_password");
		
		client.path("common/ws/userService/user");
		UserDTO userAdded = client.post(user, UserDTO.class);
		Assert.assertEquals("test_username", userAdded.getUserName());
		
		System.out.println(new Gson().toJson(userAdded));
		
		userAdded.setPassword("new_password");//userAdded 具有 id, user 没有
//		client.path("common/ws/rest/userService/user");//更新和添加使用相同的服务地址
		Response resp3 = client.put(userAdded);
		Assert.assertEquals(200, resp3.getStatus());//Status.OK 是字符串常量，不适用
		
//		path 会在原来的地址上追加地址，而不是更新，因此，不要重复使用 WebClient，除非地址相同
		WebClient client2 = WebClient.create("http://localhost:8080/");
		client2.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML);
		
		client2.path("common/ws/userService/user/" + userAdded.getId());
		Response resp = client2.delete();
		Assert.assertEquals(200, resp.getStatus());
		
		Response resp2 = client2.get();
		Assert.assertEquals(400, resp2.getStatus());
	}
}
