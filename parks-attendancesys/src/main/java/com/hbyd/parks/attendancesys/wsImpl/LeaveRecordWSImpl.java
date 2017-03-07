package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.dao.attendancesys.LeaveRecordDao;
import com.hbyd.parks.dao.attendancesys.LeaveTypeDao;
import com.hbyd.parks.dao.supportsys.EmployeeDao;
import com.hbyd.parks.domain.attendancesys.LeaveRecord;
import com.hbyd.parks.domain.attendancesys.LeaveType;
import com.hbyd.parks.domain.supportsys.Employee;
import com.hbyd.parks.dto.attendancesys.LeaveRecordDTO;
import com.hbyd.parks.common.hql.HqlQuery;
import com.hbyd.parks.common.hql.Predicate;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.attendancesys.LeaveRecordWS;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.common.util.ValHelper;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 请假记录服务，涉及关联实体
 */
public class LeaveRecordWSImpl extends BaseWSImpl<LeaveRecordDTO, LeaveRecord> implements LeaveRecordWS {

    @Resource
    private LeaveRecordDao leaveRecordDao;

    @Resource
    private LeaveTypeDao leaveTypeDao;

    @Resource
    private EmployeeDao employeeDao;


    @Override
    public void delByID(String id) {
        leaveRecordDao.delete(id);//id never null, if not found, throw exception
    }

    @Override
    public void update(LeaveRecordDTO dto) {//dto never null
        String record_id = dto.getId();
        String emp_id = dto.getEmpId();
        String type_id = dto.getTypeID();

//        1. 更新验证
        ValHelper.notNull(record_id, "更新实体必须有 ID");
        ValHelper.notNull(type_id, "请假类型 ID 不能为 NULL");
        ValHelper.notNull(emp_id, "请假员工 ID 不能为 NULL");

//        2. 获取目标实体
        LeaveRecord record = leaveRecordDao.getById(record_id);

//        3. 获取关联属性
        LeaveType type = leaveTypeDao.getById(type_id);
        ValHelper.notNull(type, "请假类型不存在");
        Employee emp = employeeDao.getById(emp_id);
        ValHelper.notNull(emp, "请假员工不存在");

//        4.1 置 NULL 关联字段
        dto.setEmpId(null);
        dto.setTypeID(null);
//        4.2 更新普通属性
        dozerMapper.map(dto, record);//none-null copy

//        5. 更新关联属性
        record.setLeaveType(type);
        record.setEmployee(emp);

//        6. 更新
        leaveRecordDao.update(record);
    }

    @Override
    public LeaveRecordDTO save(LeaveRecordDTO dto) {
        String record_id = dto.getId();
        String emp_id = dto.getEmpId();
        String type_id = dto.getTypeID();

//        1. 验证
        ValHelper.idNotExist(record_id);
        ValHelper.notNull(type_id, "请假类型 ID 不能为 NULL");
        ValHelper.notNull(emp_id, "请假员工 ID 不能为 NULL");

//        1. 转换为目标对象
        LeaveRecord record = dozerMapper.map(dto, LeaveRecord.class);//这里不同于 update, 是直接转换，而非从数据库获取目标实体

//        2. 关联请假类型
        LeaveType type = leaveTypeDao.getById(type_id);
        ValHelper.notNull(type, "请假类型不存在");
        record.setLeaveType(type);

//        3. 关联请假人员
        Employee emp = employeeDao.getById(emp_id);
        ValHelper.notNull(emp, "员工不存在");
        record.setEmployee(emp);

//        4. 保存
        LeaveRecord save = leaveRecordDao.save(record);

        return dozerMapper.map(save, LeaveRecordDTO.class);
    }

    @Override
    public PageBeanEasyUI getRecords(QueryBeanEasyUI queryBean, String empId, String fromDate, String toDate) throws ParseException {
//        1. trim whitespaces
        empId = empId.trim();
        fromDate = fromDate.trim();
        toDate = toDate.trim();

//        2. construct hql
        HqlQuery query = new HqlQuery(LeaveRecord.class);

        if(!"".equals(empId)){
            query.and(Predicate.eq("employee.id", empId));
        }

        if(!"".equals(fromDate)){
            query.and(Predicate.gt("fromDate", DateUtil.parseDateTime(fromDate)));
        }

        if(!"".equals(toDate)){
            query.and(Predicate.lt("toDate", DateUtil.parseDateTime(toDate)));
        }

        //query.getQueryString() 返回的是： from ... where ... order by ...
        //query.getPredicate() 返回的是： where ...
        return getPageBean(queryBean, query.getPredicate(), query.getParametersValue());
    }
}
