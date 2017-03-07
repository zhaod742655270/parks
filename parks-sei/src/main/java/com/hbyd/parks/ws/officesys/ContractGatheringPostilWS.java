package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.officesys.ContractGatheringPostilDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;


/**
 * @author ren_xt
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({ContractGatheringPostilDTO.class})
public interface ContractGatheringPostilWS extends BaseWS<ContractGatheringPostilDTO> {

}
