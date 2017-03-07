package com.hbyd.parks.supportsys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.managesys.DeptDao;
import com.hbyd.parks.dao.supportsys.EmployeeDao;
import com.hbyd.parks.dao.supportsys.ObjectTypeDao;
import com.hbyd.parks.domain.supportsys.Employee;
import com.hbyd.parks.dto.supportsys.EmployeeDTO;
import com.hbyd.parks.ws.supportsys.EmployeeWS;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 服务实现:人员
 */
public class EmployeeWSImpl extends BaseWSImpl<EmployeeDTO, Employee> implements EmployeeWS {

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private DeptDao deptDao;

    @Resource
    private ObjectTypeDao objectTypeDao;

    @Override
    public void update(EmployeeDTO dto) {
//      主键
        String empId = dto.getId();
//      外键
        String deptId = dto.getDeptId();
        String empTypeId = dto.getEmpTypeId();
        String empDutyId = dto.getEmpDutyId();
        String empTitleId = dto.getEmpTitleId();

//        1. 参数验证
        ValHelper.notNull(empId, "更新实体必须有 ID");
        ValHelper.notNull(deptId, "所属部门 ID 不能为 NULL");

//        1.1 防止拷贝关联对象
        dto.setDeptId(null);
        dto.setEmpTypeId(null);
        dto.setEmpDutyId(null);
        dto.setEmpTitleId(null);

//        2. 获取目标实体
        Employee target = employeeDao.getById(empId);

//        3. 更新普通属性
        dozerMapper.map(dto, target);


//        4. 填充关联属性
        target.setDepartment(deptDao.getById(deptId));
        target.setEmpType(objectTypeDao.getById(empTypeId));
        target.setEmpDuty(objectTypeDao.getById(empDutyId));
        target.setEmpTitle(objectTypeDao.getById(empTitleId));

//        4.1 特殊字段
//        由于 map-null=false, 所以 photoName=null 不会映射过来，这里需要手动填充
        if (dto.getPhotoName() == null) {
            target.setPhotoName(null);
        }
//      isValid默认值为false，赋值操作在这里做了，手动改为true
        target.setValid(true);
//        5. 更新
        employeeDao.update(target);

    }

    @Override
    public EmployeeDTO save(EmployeeDTO dto) {
//      主键
        String empId = dto.getId();
//      外键
        String deptId = dto.getDeptId();
        String empTypeId = dto.getEmpTypeId();
        String empDutyId = dto.getEmpDutyId();
        String empTitleId = dto.getEmpTitleId();

//        1. 参数验证
        ValHelper.idNotExist(empId);
        ValHelper.notNull(deptId, "所属部门 ID 不能为 NULL");

//        1.1 防止拷贝关联对象
        dto.setDeptId(null);
        dto.setEmpTypeId(null);
        dto.setEmpDutyId(null);
        dto.setEmpTitleId(null);

//        2. 填充普通属性
        Employee target = dozerMapper.map(dto, Employee.class);
        target.setIsInvolve(true);//默认参与考勤
        target.setValid(true);//新增即有效

//        3. 填充关联属性
        target.setDepartment(deptDao.getById(deptId));
        target.setEmpType(objectTypeDao.getById(empTypeId));
        target.setEmpDuty(objectTypeDao.getById(empDutyId));
        target.setEmpTitle(objectTypeDao.getById(empTitleId));

//        4. 保存
        Employee saved = employeeDao.save(target);

        return dozerMapper.map(saved, EmployeeDTO.class);
    }

    //    员工的名称是可以重复的
    @Override
    public List<EmployeeDTO> getBySimilarName(String name) {
        List<Employee> list = employeeDao.getBySimilarName(name);
        List<EmployeeDTO> dList = new ArrayList<>(list.size());

        for (Employee emp : list) {
            dList.add(dozerMapper.map(emp, EmployeeDTO.class));
        }

        return dList;
    }

    @Override
    public boolean checkExist(String id, String name) {
        Employee emp = employeeDao.getByName(name);

        if (emp != null && !id.equals(emp.getId())) {
            return true;//"已存在"
        }

        return false;//不存在
    }

    @Override
    public void updateInvolveStatusForEmps(List<String> empIDs, boolean isInvolve) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
        criteria.add(Restrictions.in("id", empIDs));

        employeeDao.updateBatchStatus(criteria, isInvolve);
    }

    @Override
    public void updateInvolveStatusForDept(String deptID, boolean isInvolve) {
        String hql = "from Employee e where e.department.id = ?";
        Object[] params = Arrays.asList(deptID).toArray();

        employeeDao.updateBatchStatus(hql, params, isInvolve);
    }
}
