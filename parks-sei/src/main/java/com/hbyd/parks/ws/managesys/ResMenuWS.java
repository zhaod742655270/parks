package com.hbyd.parks.ws.managesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.managesys.ResMenuDTO;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;

/**
 * Created by allbutone on 14-7-21.
 */
@WebService
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({ResMenuDTO.class})
public interface ResMenuWS extends BaseWS<ResMenuDTO> {
    ResMenuDTO getByName(@WebParam(name = "name") String name);
}
