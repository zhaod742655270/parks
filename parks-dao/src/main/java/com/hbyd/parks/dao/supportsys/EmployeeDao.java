package com.hbyd.parks.dao.supportsys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.supportsys.Employee;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * 人员 DAO
 */
public interface EmployeeDao extends BaseDao<Employee, String>{
    /** 查询名称相似的员工列表，根据员工名称前后模糊匹配
     * @param name 员工名称
     * @return 员工列表
     */
    List<Employee> getBySimilarName(String name);

    Employee getByName(String name);

    /**批量更新人员/员工的考勤开关
     * @param hql 查询人员/员工的 HQL
     * @param params HQL 的查询参数
     * @param isInvolve 考勤的开关状态
     */
    void updateBatchStatus(String hql, Object[] params, boolean isInvolve);

    /**
     * 批量更新人员/员工的考勤开关
     * @param criteria 查询人员/员工的 DetachedCriteria
     * @param isInvolve 考勤的开关状态
     */
    void updateBatchStatus(DetachedCriteria criteria, boolean isInvolve);

    /**获取某部门下的所有人员
     * @param deptId 部门 ID
     */
    List<Employee> getEmpsWithinDept(String deptId);

}
