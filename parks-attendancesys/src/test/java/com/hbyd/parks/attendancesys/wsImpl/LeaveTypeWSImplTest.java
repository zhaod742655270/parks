package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.dto.attendancesys.LeaveTypeDTO;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.attendancesys.LeaveTypeWS;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/attendancesys/applicationContext_CXF_Client.xml"})
public class LeaveTypeWSImplTest {

    @Resource
    private LeaveTypeWS leaveTypeWS;

    private LeaveTypeDTO s1;
    private LeaveTypeDTO s2;
    private LeaveTypeDTO s3;
    private LeaveTypeDTO s4;

    /**
     * 准备测试数据
     */
    @Before
    public void init(){
        LeaveTypeDTO l1 = new LeaveTypeDTO();
        l1.setName("test_name_1");
        s1 = leaveTypeWS.save(l1);

        LeaveTypeDTO l2 = new LeaveTypeDTO();
        l2.setName("test_name_2");
        s2 = leaveTypeWS.save(l2);

        LeaveTypeDTO l3 = new LeaveTypeDTO();
        l3.setName("test_name_3");
        s3 = leaveTypeWS.save(l3);

        LeaveTypeDTO l4 = new LeaveTypeDTO();
        l4.setName("test_name_4");
        s4 = leaveTypeWS.save(l4);
    }

    @Test(expected = SOAPFaultException.class)
    public void testDel_1(){
        leaveTypeWS.delByID("不存在的 ID");
    }

    @Test(expected = SOAPFaultException.class)
    public void testUpdate_1(){
        LeaveTypeDTO ld = new LeaveTypeDTO();//id 不存在
        leaveTypeWS.update(ld);
    }

    @Test(expected = SOAPFaultException.class)
    public void testUpdate_2(){
        LeaveTypeDTO ld = new LeaveTypeDTO();
        ld.setId("不存在的ID");
        leaveTypeWS.update(ld);
    }

    @Test
    public void testCrud() throws Exception {
//        1. 更新字段信息
        s1.setName("new_name");
//        2. 更新
        leaveTypeWS.update(s1);
//        2.1 断言更新成功
        Assert.assertEquals("new_name", leaveTypeWS.getByID(s1.getId()).getName());

//        3. 删除
        leaveTypeWS.delByID(s1.getId());
//        3.1 断言删除成功
        Assert.assertNull(leaveTypeWS.getByID(s1.getId()));
    }

    @Test
    public void testGetPageBean() throws Exception {
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setOrders(new String[]{"asc"});
        queryBean.setSorts(new String[]{"name"});
        queryBean.setPage(1);
        queryBean.setRows(3);

        String hql_where = "where name like ?";
        List params = Arrays.asList("test_name%");

        PageBeanEasyUI pageBean = leaveTypeWS.getPageBean(queryBean, hql_where, params.toArray());

        Assert.assertEquals(3, pageBean.getRows().size());
    }

    @Test
    public void testCheck(){
//        不要使用 s1, 因为已经在 testCrud() 中被删除了

//        断言和其他记录重名
        junit.framework.Assert.assertTrue(leaveTypeWS.check(s2.getId(), "test_name_4"));//存在

//        断言没有重名
        junit.framework.Assert.assertFalse(leaveTypeWS.check(s2.getId(), "test_name_ha_ha"));//不存在

//        断言可以维持原名称，这行可能失败，因为每次测试都插入名称为 test_name_2 的记录，需要使用 tearDown() 在测试后将表清空
        junit.framework.Assert.assertFalse(leaveTypeWS.check(s2.getId(), "test_name_2"));//不存在
    }

    @After
    public void clean(){
        for (LeaveTypeDTO dto : leaveTypeWS.findAll()) {
            leaveTypeWS.delByID(dto.getId());
        }
    }
}
