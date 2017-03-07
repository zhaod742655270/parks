package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseCompanyQuery;
import com.hbyd.parks.dto.officesys.WarehouseCompanyInDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2017/1/20.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({WarehouseCompanyInDTO.class})
public interface WarehouseCompanyInWS extends BaseWS<WarehouseCompanyInDTO>,RecoverableWS{
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseCompanyQuery query);
}
