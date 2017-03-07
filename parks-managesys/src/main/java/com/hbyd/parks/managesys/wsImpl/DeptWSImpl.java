package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.dao.managesys.DeptDao;
import com.hbyd.parks.domain.managesys.Department;
import com.hbyd.parks.domain.managesys.User;
import com.hbyd.parks.domain.supportsys.Employee;
import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.ws.managesys.DeptWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;

import static com.hbyd.parks.common.cxf.FaultBuilder.NOT_ALLOWED;
import static com.hbyd.parks.common.cxf.FaultBuilder.getSoapFault;
import static com.hbyd.parks.common.util.ValHelper.idNotExist;
import static com.hbyd.parks.common.util.ValHelper.notNull;
import static org.hibernate.criterion.DetachedCriteria.forClass;
import static org.hibernate.criterion.Restrictions.eq;

public class DeptWSImpl extends BaseWSImpl<DepartmentDTO, Department> implements DeptWS {
    @Resource
    private DeptDao deptDao;

    @Override
    public DepartmentDTO save(DepartmentDTO deptDTO) {
        idNotExist(deptDTO.getId());

        //保存时只涉及上级部门
        String parentId = deptDTO.getParentId();

        //置空关联字段
        deptDTO.setChildDepts(null);//保存时不涉及子部门，直接忽略
        deptDTO.setParentId(null);

        //复制普通属性
        Department target = dozerMapper.map(deptDTO, Department.class);

        if (parentId == null || "".equals(parentId)) {//未指定上级部门
            target.setParent(null);
        } else {//界面指定的部门一定是存在的
            target.setParent(deptDao.getById(parentId));
        }

        deptDao.save(target);
        return dozerMapper.map(target, DepartmentDTO.class);
    }

    @Override
    public void update(DepartmentDTO deptDTO) {
        String id = deptDTO.getId();
        notNull(id, "更新操作接收的 ID 不能为 NULL");

        Department target = deptDao.getById(id);
        notNull(target, "更新目标不存在!");

        //更新时只涉及上级部门
        String parentId = deptDTO.getParentId();

        //置空关联字段
        deptDTO.setChildDepts(null);//不涉及子部门，直接忽略
        deptDTO.setParentId(null);

        //复制普通属性
        dozerMapper.map(deptDTO, target);

        if (parentId == null || "".equals(parentId)) {//未指定上级部门
            target.setParent(null);
        } else {//指定上级部门，一定存在，因为是界面选择的
            target.setParent(deptDao.getById(parentId));
        }

        deptDao.update(target);
    }

    @Override
    public boolean checkExist(String id, String name) {
        Department dept = deptDao.getByName(name);

        if (dept != null && !id.equals(dept.getId())) {//如果已经存在且修改的不是当前部门
            return true;
        }

        return false;
    }

    @Override
    protected void disconnect(String id) {
//      部门下有用户不让删
        DetachedCriteria criteria = forClass(User.class)
                .add(eq("department.id", id));
        if(baseDao.getCnt(criteria) > 0){
            throw getSoapFault("部门无法删除：部门下有用户", NOT_ALLOWED);
        }

//      部门下有人员不让删
        DetachedCriteria criteria1 = forClass(Employee.class)
                .add(eq("department.id", id));

        if(baseDao.getCnt(criteria1) > 0){
            throw getSoapFault("部门无法删除：部门下有人员", NOT_ALLOWED);
        }
    }

    @Override
    protected void disconnectFake(String id) {
        disconnect(id);
    }
}
