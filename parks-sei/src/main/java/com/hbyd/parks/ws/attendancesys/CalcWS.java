package com.hbyd.parks.ws.attendancesys;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * 考勤计算服务
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public interface CalcWS {
    /**重新计算考勤
     * @param deptId 部门ID
     * @param begin 起始日期
     * @param end   结束日期
     */
    void recalcDept(
            @WebParam(name = "deptId") String deptId,
            @WebParam(name = "begin") Date begin,
            @WebParam(name = "end") Date end) ;

    /**重新计算考勤
     * @param empIds 人员ID列表
     * @param begin 起始日期
     * @param end   结束日期
     */
    void recalcEmps(
            @WebParam(name = "empIds") List<String> empIds,
            @WebParam(name = "begin") Date begin,
            @WebParam(name = "end") Date end);
}
