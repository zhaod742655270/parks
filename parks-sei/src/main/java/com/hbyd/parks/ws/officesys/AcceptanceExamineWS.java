package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.dto.officesys.AcceptanceExamineDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2017/2/14.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({AcceptanceExamineDTO.class})
public interface AcceptanceExamineWS extends BaseWS<AcceptanceExamineDTO> {
    public PageBeanEasyUI getPageBeanByAcceptanceId(String acceptanceId);
}
