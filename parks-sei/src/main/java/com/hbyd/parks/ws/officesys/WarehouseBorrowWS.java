package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseBorrowQuery;
import com.hbyd.parks.dto.officesys.WarehouseBorrowDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2017/3/3.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({WarehouseBorrowDTO.class})
public interface WarehouseBorrowWS extends BaseWS<WarehouseBorrowDTO>,RecoverableWS {
    public PageBeanEasyUI getPageBeanByQuery(WarehouseBorrowQuery query);
    public String getNewNumber(WarehouseBorrowQuery query);
}
