package com.hbyd.parks.common.cxf.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

/**为发出的 WebService 请求添加认证头信息
 * 拦截器的实现参照 CXF 内置的 LoggingInInterceptor
 * 
 * 注意：泛型使用的 SoapMessage 是 org.apache.cxf.binding.soap.SoapMessage
 */
public class AddAuthInfoInterceptor extends AbstractPhaseInterceptor<SoapMessage>{

	private String username;
	private String pwd;
	
	
	public String getUsername() {
		return username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**在哪个阶段拦截
	 * @param phase org.apache.cxf.phase.Phase 下定义了阶段常量
	 */
	public AddAuthInfoInterceptor(String phase, String username, String pwd) {
		super(phase);
		this.username = username;
		this.pwd = pwd;
	}
	
	public AddAuthInfoInterceptor(String phase) {
		super(phase);
	}

	public void handleMessage(SoapMessage message) throws Fault {
		/*
			想要添加的消息头如下：
			<soapenv:Head>
				<user>
					<username>jack</username>
					<pwd>123</pwd>
				</user>
			</soapenv:Head>
		 */
		
//		获取头信息列表
		List<Header> headerList = message.getHeaders();
		
//		为自定义的头信息创建 DOM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			doc = factory.newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		Element userEle = doc.createElement("user");//服务端查询 Header 时所用的 QName 的名称需要和这里根元素名称一致
			Element usernameEle = doc.createElement("username");
				usernameEle.setTextContent(this.getUsername());
			Element pwdEle = doc.createElement("pwd");
				pwdEle.setTextContent(this.getPwd());
		
		userEle.appendChild(usernameEle);
		userEle.appendChild(pwdEle);
		Header header = new Header(new QName("whatever"), userEle);//这个 QName 随便写，服务端查询 Header 时所用的 QName 和这个 QName 无关
		
		headerList.add(header);
	}
	
}
