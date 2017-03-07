package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseQuery;
import com.hbyd.parks.dto.officesys.WarehouseDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by Zhao_d on 2017/2/9.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({WarehouseDTO.class})
public interface WarehouseWS extends BaseWS<WarehouseDTO>,RecoverableWS {
    public PageBeanEasyUI getPageBeanByQueryBean(WarehouseQuery query);

    public void addProduct(String productId);

    public void changeQuantity(String productId,Double quantity,Double newCost);

    public void changeQuantityByBorrow(String productId,Double quantity);
}
