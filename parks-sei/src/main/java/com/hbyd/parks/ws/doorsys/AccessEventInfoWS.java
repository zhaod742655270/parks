package com.hbyd.parks.ws.doorsys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.doorsys.AccessEventInfoDTO;
import com.hbyd.parks.dto.doorsys.AccessEventStatusDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * 读卡数据检索服务
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({AccessEventInfoDTO.class})
public interface AccessEventInfoWS extends BaseWS<AccessEventInfoDTO> {
    /**
     * 查询园区状态信息
     * @return 包含园区状态信息的对象
     */
    AccessEventStatusDTO getStatus();
}
