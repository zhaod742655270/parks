package com.hbyd.parks.common.cxf.invoker;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.common.util.ClassHelper;
import org.apache.cxf.headers.Header;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.AbstractInvoker;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * 自定义 Invoker 实现权限验证， Invoker 其实就是为服务 Bean 创建了动态代理 
 * Spring 中配置 Invoker 失败，不知道为什么
 * http://cxf.apache.org/docs/invokers.html
 */
public class AuthenticationInvoker extends AbstractInvoker {
	private String userName;
	private String password;

	private Object bean;

	public void setBean(Object bean) {
		System.out.println("AuthenticationInvoker.setBean()");
		this.bean = bean;
	}

	@Override
	public Object invoke(Exchange exchange, Object o) {
		System.out.println("AuthenticationInvoker.invoke()");

		// Get method and class details from the request
		// OperationInfo opInfo = exchange.get(OperationInfo.class);
		// String methodName = opInfo.getInputName();

		// BindingOperationInfo opInfo = exchange.getBindingOperationInfo();
		// String methodName = opInfo.getOperationInfo().getInputName();

		Class<?> realClass = ClassHelper.getRealClass(bean);

		QName qName = new QName("user");

		// Perform security check only if the service class is OrderProcessImpl
		// and method name is processOrder
		// if (realClass == UserRestServiceImpl.class &&
		// "processOrder".equals(methodName)) {
//		if (realClass == DeptWSImpl.class) {
        if(true){
			List list = (List) exchange.getInMessage().get(Header.HEADER_LIST);
			if(list == null){
				throw new RuntimeException("没有头信息！");
			}
			
			boolean flag = false;//是否找到验证信息
			
			for (int i = 0; i < list.size(); i++) {
				// Get the SOAP header
				SoapHeader header = (SoapHeader) list.get(i);
				if (header.getName().equals(qName)) {//匹配 QName
					Element user = (Element) header.getObject();
					Node username = user.getFirstChild();
					Node pwd = user.getLastChild();
					
					if (username != null) {
						userName = username.getTextContent();
					}
					if (pwd != null) {
						password = pwd.getTextContent();
					}
					
					System.out.println("userName reterived from SOAP Header is "
							+ userName);
					System.out.println("password reterived from SOAP Header is "
							+ password);
					
					flag = true;
					break;
				}
			}
			
			if(!flag){
				throw new RuntimeException(
						"Request doesn't contain OrderCredentials namespace");
			}
			
			// Perform dummy validation for John
			if ("admin".equalsIgnoreCase(userName)
					&& "admin".equalsIgnoreCase(password)) {
				System.out.println("Authentication successful!");
			} else {
				throw new RuntimeException("Invalid user or password");
			}
		}
		
		return super.invoke(exchange, o);//调用目标服务方法
	}

	@Override
	public Object getServiceObject(Exchange arg0) {
		return bean;
	}
}