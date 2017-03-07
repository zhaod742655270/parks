package com.hbyd.parks.ws.attendancesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.attendancesys.RegularShiftDTO;
import com.hbyd.parks.dto.attendancesys.ShiftBindingDTO;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.List;

@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({RegularShiftDTO.class})
public interface RegularShiftWS extends BaseWS<RegularShiftDTO> {
    /**获取规律班次中的班次绑定
     * @param id 规律班次的 ID
     * @return 班次绑定列表，按照 (idx asc, dayToBound asc) 升序排列
     */
    List<ShiftBindingDTO> getShiftBindings(@WebParam(name = "regularShiftID") String id);
}
