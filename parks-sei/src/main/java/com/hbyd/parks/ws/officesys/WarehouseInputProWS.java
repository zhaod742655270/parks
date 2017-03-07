package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseInputQuery;
import com.hbyd.parks.dto.officesys.WarehouseInputProDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2016/12/27.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({WarehouseInputProDTO.class})
public interface WarehouseInputProWS extends BaseWS<WarehouseInputProDTO>,RecoverableWS {

    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseInputQuery query,String parentId);

    public Double getQuantityById(String id);
}
