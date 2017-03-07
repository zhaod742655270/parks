package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QuotationQuery;
import com.hbyd.parks.dto.officesys.QuotationProjectDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Administrator on 2016/10/31.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({QuotationProjectDTO.class})
public interface QuotationProjectWS extends BaseWS<QuotationProjectDTO>, RecoverableWS {
    PageBeanEasyUI getProjectPageBeanByQueryBean(QuotationQuery queryBean);
}
