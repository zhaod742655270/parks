package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.dto.attendancesys.LeaveRecordDTO;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.attendancesys.LeaveRecordWS;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/attendancesys/applicationContext_CXF_Client.xml"})
public class LeaveRecordWSImplTest {

    @Resource
    private LeaveRecordWS leaveRecordWS;

    private String typeid_before;
    private String typeid_after;
    private String empid_before;
    private String empid_after;

    @Test
    public void testCrud() throws Exception {
//0. 准备的测试数据，视具体情况而作修改
        typeid_before = "00C57E79-77AC-4F47-81C8-B09C56BEF765";
        typeid_after = "33C57E79-77AC-4F47-81C8-B09C56BEF767";
        empid_before = "10";
        empid_after = "100";

//1. save
        LeaveRecordDTO r = new LeaveRecordDTO();

//        1.1 设置关联属性
        r.setEmpId(empid_before);
        r.setTypeID(typeid_before);

//        1.2 设置普通属性
        r.setCause("something happpened");
        r.setFromDate("2011-10-15 09:08:10");
        r.setToDate("2013-04-25 19:23:02");

        LeaveRecordDTO saved = leaveRecordWS.save(r);

//        1.3 断言保存成功
        Assert.assertEquals(empid_before, saved.getEmpId());

//2. update
//        2.1 更新普通属性
        saved.setFromDate("2015-10-15 09:08:10");
        saved.setToDate("2018-04-25 19:23:02");

//        2.2 更新关联属性
        saved.setTypeID(typeid_after);
        saved.setEmpId(empid_after);

        leaveRecordWS.update(saved);

//        2.3 断言更新成功
        Assert.assertEquals(empid_after, leaveRecordWS.getByID(saved.getId()).getEmpId());

//3. delete
//        3.1 删除
        leaveRecordWS.delByID(saved.getId());
//        3.2 断言删除
        Assert.assertNull(leaveRecordWS.getByID(saved.getId()));
    }

    @Test
    public void testGetPageBean() throws Exception {

        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setPage(1);
        queryBean.setRows(3);
        queryBean.setSorts(new String[]{"fromDate"});
        queryBean.setOrders(new String[]{"desc"});

        String hql_where = "where cause like ?";
        Object[] params = Arrays.asList("%").toArray();

        PageBeanEasyUI pageBean = leaveRecordWS.getPageBean(queryBean, hql_where, params);

        List<LeaveRecordDTO> rows = pageBean.getRows();

        for (LeaveRecordDTO row : rows) {
            System.out.println(row.getFromDate());
        }
    }

    @Test
    public void testGetRecords() throws Exception {
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setPage(1);
        queryBean.setRows(3);
        queryBean.setSorts(new String[]{"fromDate"});
        queryBean.setOrders(new String[]{"asc"});

        String empId = "10";

//        String fromDate = "2015-10-04 09:08:10";
        String fromDate = "";
//        String toDate = "2020-04-25 19:23:02";
        String toDate = "";

        PageBeanEasyUI records = leaveRecordWS.getRecords(queryBean, empId, fromDate, toDate);

        List<LeaveRecordDTO> rows = records.getRows();
        for (LeaveRecordDTO row : rows) {
            System.out.println(row.getFromDate());
        }

    }
}