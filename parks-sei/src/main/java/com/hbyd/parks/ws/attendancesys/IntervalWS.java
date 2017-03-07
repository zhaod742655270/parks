package com.hbyd.parks.ws.attendancesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.attendancesys.IntervalDTO;

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
@XmlSeeAlso({IntervalDTO.class})
public interface IntervalWS extends BaseWS<IntervalDTO> {

    /**获取班次下的所有时段
     * @param shiftID 班次ID
     * @return 时段列表
     */
    List<IntervalDTO> findByShiftID(@WebParam(name = "shiftID") String shiftID);
}
