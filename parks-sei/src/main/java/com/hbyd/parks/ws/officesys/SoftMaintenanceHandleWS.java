package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.SoftMaintenanceQuery;
import com.hbyd.parks.dto.officesys.SoftMaintenanceHandleDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Administrator on 2016/12/21.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso(SoftMaintenanceHandleDTO.class)
public interface SoftMaintenanceHandleWS extends BaseWS<SoftMaintenanceHandleDTO>,RecoverableWS {

    public PageBeanEasyUI getPageBeanByQueryBean(SoftMaintenanceQuery query, String parentIdFK);
}
