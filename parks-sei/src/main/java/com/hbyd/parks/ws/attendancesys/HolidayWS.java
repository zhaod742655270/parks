package com.hbyd.parks.ws.attendancesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.attendancesys.HolidayDTO;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * 节假日
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({HolidayDTO.class})
public interface HolidayWS extends BaseWS<HolidayDTO> {
    HolidayDTO getByName(@WebParam(name = "name") String name);
}
