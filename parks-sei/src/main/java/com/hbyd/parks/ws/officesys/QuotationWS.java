package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QuotationQuery;
import com.hbyd.parks.dto.officesys.QuotationDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/6/17
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({QuotationDTO.class})
public interface QuotationWS extends BaseWS<QuotationDTO>, RecoverableWS {

   PageBeanEasyUI getQuotationPageBeanByQueryBean(String projectId,QuotationQuery queryBean);

}
