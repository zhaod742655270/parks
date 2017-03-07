package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.model.ConLogQueryBean;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.dto.officesys.ContractGatheringLogDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;


/**
 * @author ren_xt
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({ContractGatheringLogDTO.class})
public interface ContractGatheringLogWS extends BaseWS<ContractGatheringLogDTO> {
    public PageBeanEasyUI getPageBeanByLogQueryBean(ConLogQueryBean queryBean);

    public PageBeanEasyUI getPageBeanByDate(ConLogQueryBean queryBean);

}
