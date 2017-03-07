package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.ExpenditureQuery;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.dto.officesys.ExpenditureDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2017/2/23.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso(ExpenditureDTO.class)
public interface ExpenditureWS extends BaseWS<ExpenditureDTO>,RecoverableWS {
    public PageBeanEasyUI getPageBeanByQueryBean(ExpenditureQuery query);
}
