package com.hbyd.parks.common.cxf;

import org.apache.cxf.binding.soap.SoapFault;

import javax.xml.namespace.QName;

/**
 * 定义服务异常相关方法
 */
public class FaultBuilder {

    private static String faultNsURI = "http://www.ncft.net";

    /**
     * 不允许的操作，如：不允许删除有子部门的父部门
     * 此类 Fault 的 Message 允许直接展示给客户端
     *
     * 所有的 HTTP 状态码在 org.springframework.http.HttpStatus 中有定义
     */
    public static final int NOT_ALLOWED = 500;//貌似这个状态码不能随便写，否则异常

    /**获取服务异常
     * @param message 异常信息
     * @param statusCode 异常类型，使用状态码区分
     * @return {@link org.apache.cxf.binding.soap.SoapFault}
     */
    public static SoapFault getSoapFault(String message, int statusCode){
        QName faultCode = new QName(faultNsURI, "fault", "f");
        SoapFault soapFault = new SoapFault(message, faultCode);
        soapFault.setStatusCode(statusCode);

        return soapFault;
    }
}
