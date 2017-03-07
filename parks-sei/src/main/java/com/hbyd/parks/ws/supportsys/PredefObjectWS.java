package com.hbyd.parks.ws.supportsys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.supportsys.PredefObjectDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * 对象类型服务
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso(PredefObjectDTO.class)
public interface PredefObjectWS extends BaseWS<PredefObjectDTO> {
}
