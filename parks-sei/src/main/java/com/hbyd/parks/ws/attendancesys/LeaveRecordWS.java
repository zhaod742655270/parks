package com.hbyd.parks.ws.attendancesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.attendancesys.LeaveRecordDTO;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.text.ParseException;

/**
 * Created by allbutone on 2014/8/18.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({LeaveRecordDTO.class})
public interface LeaveRecordWS extends BaseWS<LeaveRecordDTO> {
    /**
     * 查询某段时间内员工的请假记录，
     * @param queryBean 分页对象
     * @param empId 员工ID
     * @param fromDate 起始日期字符串
     * @param toDate 结束日期字符串
     * @return 分页对象
     */
    PageBeanEasyUI getRecords(@WebParam(name = "queryBean") QueryBeanEasyUI queryBean,
                              @WebParam(name = "empId") String empId,
                              @WebParam(name = "fromDate") String fromDate,
                              @WebParam(name = "toDate") String toDate) throws ParseException;
}
