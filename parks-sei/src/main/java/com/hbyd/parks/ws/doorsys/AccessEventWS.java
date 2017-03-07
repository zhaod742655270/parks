package com.hbyd.parks.ws.doorsys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.doorsys.AccessEventDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * 读卡数据检索服务
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({AccessEventDTO.class})
public interface AccessEventWS extends BaseWS<AccessEventDTO> {
}
