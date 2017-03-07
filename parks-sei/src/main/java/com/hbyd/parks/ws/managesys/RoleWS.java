package com.hbyd.parks.ws.managesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.managesys.RoleDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;


/**
 * @author ren_xt
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({RoleDTO.class})
public interface RoleWS extends BaseWS<RoleDTO> {

}
