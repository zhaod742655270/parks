package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.PaymentLogQueryBean;
import com.hbyd.parks.dto.officesys.PaymentLogDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;


/**
 * @author zhang_f
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({PaymentLogDTO.class})
public interface PaymentLogWS extends BaseWS<PaymentLogDTO> {
    public PageBeanEasyUI getPageBeanByLogQueryBean(PaymentLogQueryBean queryBean);

    public PageBeanEasyUI getPageBeanByDate(PaymentLogQueryBean queryBean);



}
