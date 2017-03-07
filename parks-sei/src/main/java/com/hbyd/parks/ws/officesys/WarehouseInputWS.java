package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseInputQuery;
import com.hbyd.parks.dto.officesys.WarehouseInputDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2016/12/26.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({WarehouseInputDTO.class})
public interface WarehouseInputWS extends BaseWS<WarehouseInputDTO>,RecoverableWS {

    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseInputQuery query);

    public String getNewNumber(WarehouseInputQuery query);
}
