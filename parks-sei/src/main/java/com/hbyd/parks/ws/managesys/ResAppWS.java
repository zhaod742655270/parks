package com.hbyd.parks.ws.managesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.managesys.ResAppDTO;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;

/**
 * Created by allbutone on 14-7-15.
 */
@WebService
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({ResAppDTO.class})
public interface ResAppWS extends BaseWS<ResAppDTO> {
    ResAppDTO getByName(@WebParam(name = "name") String name);
}
