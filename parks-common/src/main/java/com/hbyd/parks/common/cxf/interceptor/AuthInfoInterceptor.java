package com.hbyd.parks.common.cxf.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;

public class AuthInfoInterceptor extends AbstractPhaseInterceptor<SoapMessage>{

	public AuthInfoInterceptor(String phase) {
		super(phase);
	}
	
	// SoapMessage 只用来验证 WebService, 不包括 Restful
	public void handleMessage(SoapMessage message) throws Fault {
		Header header = message.getHeader(new QName("user"));
		if(header == null){
			throw new Fault(new RuntimeException("头信息没有用户信息: header is null"));
		}
		Element user = (Element) header.getObject();
		if(user == null){
			throw new Fault(new RuntimeException("头信息没有用户信息: user is null"));
		}
		String username = user.getElementsByTagName("username").item(0).getTextContent();
		String pwd = user.getElementsByTagName("pwd").item(0).getTextContent();
		
//		username 或 pwd 任何一个不为 admin， 不允许访问服务
		if(!"admin".equals(username)||!"admin".equals(pwd)){
			throw new Fault(new RuntimeException("用户名或密码不正确!"));
		}
		System.out.println("请求验证通过...");
	}
}
