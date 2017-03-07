package com.hbyd.parks.ws.managesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.managesys.OperLogDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({OperLogDTO.class})
public interface OperLogWS extends BaseWS<OperLogDTO> {
}
