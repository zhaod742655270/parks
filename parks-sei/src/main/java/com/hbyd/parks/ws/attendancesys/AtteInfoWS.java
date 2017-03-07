package com.hbyd.parks.ws.attendancesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.attendancesys.AtteInfoDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * 考勤记录服务
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({AtteInfoDTO.class})
public interface AtteInfoWS extends BaseWS<AtteInfoDTO> {

}
