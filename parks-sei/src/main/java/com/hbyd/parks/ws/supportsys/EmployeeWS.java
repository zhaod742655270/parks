package com.hbyd.parks.ws.supportsys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.dto.supportsys.EmployeeDTO;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.List;

/**
 * 员工服务
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({EmployeeDTO.class})
public interface EmployeeWS extends BaseWS<EmployeeDTO>, RecoverableWS {
    /**查询名称匹配的人员/员工记录，前后模糊匹配
     * @param name 名称，关键字
     * @return 员工列表
     */
    List<EmployeeDTO> getBySimilarName(@WebParam(name = "name") String name);

    /**
     * 设置考勤开关，即：更新是否参加考勤的状态
     * @param empIDs 人员/员工的 ID 列表
     * @param isInvolve 是否参与考勤，如果打开考勤开关，指定为 true 即可，否则 false
     */
    void updateInvolveStatusForEmps(@WebParam(name = "empIDs") List<String> empIDs,
                             @WebParam(name = "isInvolve") boolean isInvolve);

    /**设置考勤开关，即：更新是否参加考勤的状态
     * @param deptID 部门ID
     * @param isInvolve 是否参与考勤，如果打开考勤开关，指定为 true 即可，否则 false
     */
    void updateInvolveStatusForDept(@WebParam(name = "deptID") String deptID,
                             @WebParam(name = "isInvolve") boolean isInvolve);
}
