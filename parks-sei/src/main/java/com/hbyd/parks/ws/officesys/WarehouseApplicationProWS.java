package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseApplicationQuery;
import com.hbyd.parks.dto.officesys.WarehouseApplicationProDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2017/1/23.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({WarehouseApplicationProDTO.class})
public interface WarehouseApplicationProWS extends BaseWS<WarehouseApplicationProDTO>,RecoverableWS {
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseApplicationQuery query, String parentId);
}
