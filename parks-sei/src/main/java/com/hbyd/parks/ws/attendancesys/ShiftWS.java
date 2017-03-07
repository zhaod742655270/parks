package com.hbyd.parks.ws.attendancesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.attendancesys.ShiftDTO;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.List;

/**
 * 班次 Web Service
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({ShiftDTO.class})
public interface ShiftWS extends BaseWS<ShiftDTO> {
    ShiftDTO getByName(@WebParam(name = "name") String name);

    /**查询有效班次：有时段的班次 + 休息班次（没有时段）
     * @return 班次列表
     */
    List<ShiftDTO> findValidShift();

    /**查询类型为 NORMAL 的班次
     * @return 班次列表
     */
    List<ShiftDTO> findAllNormal();

    /**查询类型为NORMAL的且有时段的班次
     * @return 班次列表
     */
    List<ShiftDTO> findValidNormal() ;
}
