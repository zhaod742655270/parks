package com.hbyd.parks.ws.attendancesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.attendancesys.ShiftAssignDTO;
import com.hbyd.parks.dto.attendancesys.ShiftDTO;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.Date;

/**
 * 排班服务
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({ShiftAssignDTO.class})
public interface ShiftAssignWS extends BaseWS<ShiftAssignDTO> {

//    /**
//     * 查询排班结果
//     *
//     * @param beginYear  起始年
//     * @param beginMonth 起始月
//     * @param endYear    结束年
//     * @param endMonth   结束月
//     * @param id         人员ID 或 部门ID, 由 idType 确定
//     * @param idType     true 表示查询部门的排班，false 表示查询人员的排班
//     * @return 排班列表
//     */
//    List<ShiftAssignDTO> getShiftAssignResult(
//            @WebParam(name = "beginYear") int beginYear,
//            @WebParam(name = "beginMonth") int beginMonth,
//            @WebParam(name = "endYear") int endYear,
//            @WebParam(name = "endMonth") int endMonth,
//            @WebParam(name = "id") String id,
//            @WebParam(name = "idType") boolean idType);

    /**
     * 在某个时段内，为部门排班
     *
     * @param begin          起始日期(包含当天，时间部分无效)
     * @param end            结束日期(包含当天，时间部分无效)
     * @param deptId         部门 ID
     * @param regularShiftId 规律班次的 ID
     */
    void assignShiftForDept(
            @WebParam(name = "begin") Date begin,
            @WebParam(name = "end") Date end,
            @WebParam(name = "deptId") String deptId,
            @WebParam(name = "regularShiftId") String regularShiftId);

    /**
     * 在某个时段内，为多个人员排班
     *
     * @param begin          起始日期(包含当天，时间部分无效)
     * @param end            结束日期(包含当天，时间部分无效)
     * @param empIds         人员ID 数组
     * @param regularShiftId 规律班次的 ID
     */
    void assignShiftForEmps(Date begin, Date end, String[] empIds, String regularShiftId);

    /**
     * 在某个时段内，为人员排班
     *
     * @param begin          起始日期(包含当天，时间部分无效)
     * @param end            结束日期(包含当天，时间部分无效)
     * @param empId          人员ID
     * @param regularShiftId 规律班次的 ID
     */
    void assignShiftForEmp(
            @WebParam(name = "begin") Date begin,
            @WebParam(name = "end") Date end,
            @WebParam(name = "empId") String empId,
            @WebParam(name = "regularShiftId") String regularShiftId);

    /**
     * 更新某天的班次
     *
     * @param shiftAssignId    排班记录的 ID
     * @param shiftId 要绑定的班次，如果是删除，传 NULL 即可
     */
    void updateDayShiftForEmp(
            @WebParam(name = "shiftAssignId") String shiftAssignId,
            @WebParam(name = "day") int day,
            @WebParam(name = "shiftId") String shiftId);

    /**
     * 获取某人某天的班次
     * @param date 日期，时间部分无效
     * @param empId 人员ID
     * @return 当天绑定的班次，如果没有，返回 null
     */
    ShiftDTO getDayShift(
            @WebParam(name = "date") Date date,
            @WebParam(name = "empId") String empId);
}
