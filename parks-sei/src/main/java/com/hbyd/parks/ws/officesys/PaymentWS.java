package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.PaymentQuery;
import com.hbyd.parks.dto.officesys.PaymentDTO;
import com.hbyd.parks.dto.officesys.PaymentSumDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.List;


/**
 * @author zhang_f
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({PaymentDTO.class})
public interface PaymentWS extends BaseWS<PaymentDTO>,RecoverableWS {

    public PageBeanEasyUI getPageBeanByPaymentQuery(PaymentQuery queryBean);

    public PaymentSumDTO getPaymentSum(PaymentQuery queryBean);

    public List<PaymentDTO> getPaymentListByGathering(String id);

}
