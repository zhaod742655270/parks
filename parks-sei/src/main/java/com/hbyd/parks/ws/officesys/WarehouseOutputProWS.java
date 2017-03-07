package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseOutputQuery;
import com.hbyd.parks.dto.officesys.WarehouseOutputProDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2017/2/20.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({WarehouseOutputProDTO.class})
public interface WarehouseOutputProWS extends BaseWS<WarehouseOutputProDTO>,RecoverableWS {
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseOutputQuery query, String parentId);

    public Double getQuantityById(String id);
}
