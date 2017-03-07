package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.EmployeeDTO;
import com.hbyd.parks.ws.supportsys.EmployeeWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 测试人员服务
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class EmployeeWSImplTest {
//  测试之前，确保有测试数据：10 号部门
    private String oldDeptID = "10";
    private String newDeptID = "101";
    private String oldDate = "2014-12-23 12:09:20";
    private String newDate = "2014-11-23 08:09:20";
    private String oldName = "oldName";
    private String newName = "newName";

    @Resource
    private EmployeeWS employeeWS;

    @Test
    public void testCrud(){
        EmployeeDTO dto = new EmployeeDTO();

//        填充普通属性
        dto.setBeginTime(oldDate);
        dto.setEmpName(oldName);
        dto.setPhotoName("hello world");

//        填充关联属性
        dto.setDeptId(oldDeptID);
        dto.setEmpTypeId("10001");//54所正式员工
        dto.setEmpDutyId("14024");//研究员
        dto.setEmpTitleId("15001");//高级工程师

//        执行保存
        EmployeeDTO saved = employeeWS.save(dto);

        assertEquals(oldDeptID, saved.getDeptId());
        assertEquals(oldDate, saved.getBeginTime());
        assertEquals(oldName, saved.getEmpName());
        assertEquals("54所正式员工", saved.getEmpTypeName());
        assertEquals("研究员", saved.getEmpDutyName());
        assertEquals("高级工程师", saved.getEmpTitleName());


//        填充普通属性
        saved.setEmpName(newName);
        saved.setBeginTime(newDate);
        saved.setPhotoName(null);

//        填充关联属性
        saved.setDeptId(newDeptID);
        saved.setEmpTypeId("10014");//临时工
        saved.setEmpDutyId("14010");//产品部主任
        saved.setEmpTitleId("15024");//生产部主任

//        执行更新
        employeeWS.update(saved);

//        执行获取
        String id = saved.getId();
        EmployeeDTO updated = employeeWS.getByID(id);

        assertNull(updated.getPhotoName());
        assertEquals(newDeptID, updated.getDeptId());
        assertEquals(newDate, updated.getBeginTime());
        assertEquals(newName, updated.getEmpName());
        assertEquals("临时工", updated.getEmpTypeName());
        assertEquals("产品部主任", updated.getEmpDutyName());
        assertEquals("生产部主任", updated.getEmpTitleName());

//        伪删除
        employeeWS.delFake(id);
        assertEquals(false, employeeWS.getByID(id).isValid());

//        真删除
//        employeeWS.delByID(id);
//        assertNull(employeeWS.getByID(id));
    }

    @Test
    public void testFindAll(){
        for (EmployeeDTO employeeDTO : employeeWS.findAll()) {
            System.out.println(employeeDTO.getDeptName());
        }
    }

    @Test
    public void testTrivial(){
//        employeeWS.delByID("10");
        EmployeeDTO emp = employeeWS.getByID("100");
        System.out.println(emp.getDeptName());
    }

    @Test
    public void testPageBean(){
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
            queryBean.setSorts(new String[]{"empname"});
            queryBean.setOrders(new String[]{"asc"});
            queryBean.setPage(1);
            queryBean.setRows(2);
//        String hql_where = "where empname like ?";
//        Object[] params = Arrays.asList("%Nam%").toArray();

        String hql_where = "where photoName = ?";
        Object[] params = Arrays.asList("hello world").toArray();

        PageBeanEasyUI pageBean = employeeWS.getPageBean(queryBean, hql_where, params);

        assertTrue(pageBean.getRows().size() >= 1);

        for (Object o : pageBean.getRows()) {
            System.out.println(new Gson().toJson(o));
        }

    }

    @Test
    public void testSearchSimilarName(){
        List<EmployeeDTO> list = employeeWS.getBySimilarName("Nam");
        for (EmployeeDTO employeeDTO : list) {
            System.out.println(employeeDTO.getEmpName());
        }
    }

    @Test
    public void testUpdateInvolveStatusForEmps(){
//        1. 插入测试数据
//        EmployeeDTO e1 = new EmployeeDTO();
//        e1.setDeptFK(oldDeptID);
//        e1.setBeginTime(oldDate);
//        e1.setEmpName(oldName);
//        e1.setgetIsInvolve(oldInvolve);//false
//
//        EmployeeDTO e2 = new EmployeeDTO();
//        e2.setDeptFK(oldDeptID);
//        e2.setBeginTime(oldDate);
//        e2.setEmpName(oldName);
//        e2.setgetIsInvolve(oldInvolve);//false

//        2. 执行保存
//        EmployeeDTO s1 = employeeWS.save(e1);
//        EmployeeDTO s2 = employeeWS.save(e2);

//        employeeWS.updateInvolveStatusForEmps(Arrays.asList(s1.getId(),s2.getId()), true);
//
//        Assert.assertTrue(employeeWS.getByID(s1.getId()).getIsInvolve());
//        Assert.assertTrue(employeeWS.getByID(s2.getId()).getIsInvolve());

        employeeWS.updateInvolveStatusForEmps(Arrays.asList("24c686bf481179d30148117b09fa0000"), false);

    }
}
