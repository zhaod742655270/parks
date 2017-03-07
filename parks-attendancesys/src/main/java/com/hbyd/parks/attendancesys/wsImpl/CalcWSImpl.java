package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.attendancesys.service.CalcService;
import com.hbyd.parks.dao.supportsys.EmployeeDao;
import com.hbyd.parks.domain.supportsys.Employee;
import com.hbyd.parks.ws.attendancesys.CalcWS;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 考勤计算服务实现
 */
public class CalcWSImpl implements CalcWS {

    @Resource
    private CalcService calcService;

    @Resource
    private EmployeeDao employeeDao;

    @Override
    public void recalcDept(String deptId, Date begin, Date end) {
//      查询某部门下参与考勤的人员
        DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class)
                .add(Restrictions.eq("department.id", deptId))
                .add(Restrictions.eq("isInvolve", true));

        List<Employee> emps = employeeDao.findListByCriteria(criteria);

        if (emps.size() > 0) {
            for (Employee emp : emps) {
                calcService.recalc(emp.getId(), begin, end);
            }
        }
    }

    @Override
    public void recalcEmps(List<String> empIds, Date begin, Date end) {
        if (empIds.size() > 0) {
            for (String empId : empIds) {
                calcService.recalc(empId, begin, end);
            }
        }
    }
}
