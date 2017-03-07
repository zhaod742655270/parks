package com.hbyd.parks.ws.attendancesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.attendancesys.GlobalDefDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * 全局定义服务
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({GlobalDefDTO.class})
public interface GlobalDefWS extends BaseWS<GlobalDefDTO> {
}