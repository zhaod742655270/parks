package com.hbyd.parks.ws.doorsys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.doorsys.CapturePhotoDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * 报警照片服务
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({CapturePhotoDTO.class})
public interface CapturePhotoWS extends BaseWS<CapturePhotoDTO> {
}