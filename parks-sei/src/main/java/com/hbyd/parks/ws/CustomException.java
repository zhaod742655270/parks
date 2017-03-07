package com.hbyd.parks.ws;

import javax.xml.ws.WebFault;

/**
 * The JAX-WS 2.0 specification demands that the exception
 * annotated with  @WebFault must have two constructors and
 * one method [getter to obtain the fault information]:
 *
 * http://stackoverflow.com/questions/2064447/jax-ws-map-exceptions-to-faults
 * http://willemjiang.blogspot.com/2011/01/how-to-map-soap-fault-message-with.html
 * http://www.luckyryan.com/2013/06/15/apache-cxf-exception-handler-for-jaxrs-rest/
 *
 * org.apache.cxf.interceptor.FaultOutInterceptor 打断点即可检测 out-going exception
 */
@WebFault(faultBean = "com.hbyd.parks.ws.CustomFault")
public class CustomException extends Exception {

    private FaultBean faultBean;

    public CustomException() {
        super();
    }

    public CustomException(String message, FaultBean faultBean, Throwable cause) {
        super(message, cause);
        this.faultBean = faultBean;
    }

    public CustomException(String message, FaultBean faultBean) {
        super(message);
        this.faultBean = faultBean;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}
