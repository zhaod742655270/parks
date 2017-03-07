package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.SoftMaintenanceQuery;
import com.hbyd.parks.dto.officesys.SoftMaintenanceDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2016/12/9.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso(SoftMaintenanceDTO.class)
public interface SoftMaintenanceWS extends BaseWS<SoftMaintenanceDTO> ,RecoverableWS{

    public PageBeanEasyUI getPageBeanByQueryBean(SoftMaintenanceQuery query);

    public String getNewNumber(SoftMaintenanceQuery query);
}
