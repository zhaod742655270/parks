package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseApplicationQuery;
import com.hbyd.parks.dto.officesys.WarehouseApplicationDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2017/1/22.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({WarehouseApplicationDTO.class})
public interface WarehouseApplicationWS extends BaseWS<WarehouseApplicationDTO>,RecoverableWS {
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseApplicationQuery query);

    public String getNewNumber();
}
