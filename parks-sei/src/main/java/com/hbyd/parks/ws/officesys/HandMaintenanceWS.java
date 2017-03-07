package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.HandMaintenanceQuery;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.dto.officesys.HandMaintenanceDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2016/12/14.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso(HandMaintenanceDTO.class)
public interface HandMaintenanceWS extends BaseWS<HandMaintenanceDTO>,RecoverableWS {

    public PageBeanEasyUI getPageBeanByQueryBean(HandMaintenanceQuery query);

    public String getNewNumber(HandMaintenanceQuery query);
}
