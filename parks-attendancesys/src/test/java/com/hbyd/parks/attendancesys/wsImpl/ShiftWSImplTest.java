package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.enums.ShiftType;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.attendancesys.ShiftDTO;
import com.hbyd.parks.ws.attendancesys.ShiftWS;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/attendancesys/applicationContext_CXF_Client.xml"})
public class ShiftWSImplTest {

    @Resource
    private ShiftWS shiftWS;
    private ShiftDTO s1;
    private ShiftDTO s2;
    private ShiftDTO s3;

    /**
     * 准备测试数据
     */
//    @Before
    public void init(){
        s1 = new ShiftDTO();
        s1.setName("test_name_1");
        s1.setDescription("test_desc_1");
        s1.setShiftType(ShiftType.REST);

        s2 = new ShiftDTO();
        s2.setName("test_name_2");
        s2.setDescription("test_desc_2");
        s2.setShiftType(ShiftType.REST);

        s3 = new ShiftDTO();
        s3.setName("test_name_3");
        s3.setDescription("test_desc_3");
        s3.setShiftType(ShiftType.REST);

        shiftWS.save(s1);
        shiftWS.save(s2);
        shiftWS.save(s3);
    }

    @Test
    public void testCrud() throws Exception {
//        增
        ShiftDTO shiftDTO = new ShiftDTO();
        shiftDTO.setName("test_name");
        shiftDTO.setDescription("test_desc");
        shiftDTO.setShiftType(ShiftType.REST);
        ShiftDTO saved = shiftWS.save(shiftDTO);

        String id = saved.getId();
        Assert.assertNotNull(id);

//        改
        saved.setName("test_new_name");//更新已保存的实体，其中包含ID 字段
        saved.setShiftType(ShiftType.NONE);
        shiftWS.update(saved);
        Assert.assertEquals("test_new_name", shiftWS.getByID(id).getName());

//        删
//        shiftWS.delByID(id);
//        Assert.assertNull(shiftWS.getByID(id));
    }

    @Test
    public void testCheck() throws Exception {
        ShiftDTO shiftDTO = new ShiftDTO();
        shiftDTO.setName("test_name" + UUID.randomUUID().toString());
        shiftDTO.setDescription("test_desc");
        ShiftDTO saved = shiftWS.save(shiftDTO);

        Assert.assertFalse(shiftWS.check(saved.getId(), saved.getName()));//检查原有名称
        Assert.assertTrue(shiftWS.check(saved.getId(), s2.getName()));//检查已占名称
    }

    @Test(expected = SOAPFaultException.class)
    public void testDelByID() throws Exception {
        shiftWS.delByID("不存在的ID");
    }

    /**
     * 测试：如果班次被 shift_binding 或 shift_assign 引用，不允许删除
     */
    @Test(expected = SOAPFaultException.class)
    public void testDelByID_2(){
//        shiftWS.delByID("821bc4dd-b919-4150-a90a-4387054a929d");//shift_binding 使用的班次
        shiftWS.delByID("24c6869c48a538e70148a53a00d70000");//shift_assign 使用的班次
    }

    @Test(expected = SOAPFaultException.class)
    public void testUpdate() throws Exception {
        s2.setId("不存在的ID");
        shiftWS.update(s2);
    }

    @Test
    public void testGetByID() throws Exception {
        Assert.assertNull(shiftWS.getByID("不存在的ID"));
    }

    @Test
    public void testFindAll() throws Exception {
        List<ShiftDTO> all = shiftWS.findAll();
        System.out.println(all.size());
        Assert.assertTrue(all.size()>=3);
    }

    @Test
    public void testGetByName() throws Exception {
        Assert.assertNotNull(shiftWS.getByName(s2.getName()));//已存在的名称
        Assert.assertNull(shiftWS.getByName("不存在的名称"));
    }

    @Test
    public void testGetPageBean() throws Exception {
        QueryBeanEasyUI queryBeanEasyUI = new QueryBeanEasyUI();
            queryBeanEasyUI.setPage(1);
            queryBeanEasyUI.setRows(2);
            queryBeanEasyUI.setSorts(new String[]{"name"});
            queryBeanEasyUI.setOrders(new String[]{"asc"});

        String hql_where = "where name like ?";
        List<String> params = Arrays.asList("test_name%");

        PageBeanEasyUI pageBean = shiftWS.getPageBean(queryBeanEasyUI, hql_where, params.toArray());

        Assert.assertNotNull(pageBean.getRows());
        Assert.assertTrue(pageBean.getTotal()>=3);
        Assert.assertEquals(2, pageBean.getRows().size());
    }

    @Test
    public void testFindValidShifts(){
        List<ShiftDTO> validShifts = shiftWS.findValidShift();

        if(validShifts == null){
            System.out.println("没有查询到班次");
        }else {
            for (ShiftDTO validShift : validShifts) {
                System.out.println(validShift.getName() + "\t" + validShift.getShiftType());
            }
        }
    }
    @Test
    public void testFindValidNormal(){
        List<ShiftDTO> validShifts = shiftWS.findValidNormal();

        if(validShifts == null){
            System.out.println("没有查询到班次");
        }else {
            for (ShiftDTO validShift : validShifts) {
                System.out.println(validShift.getName() + "\t" + validShift.getShiftType());
            }
        }
    }
    @Test
    public void testFindAllNormal(){
        List<ShiftDTO> allNormal = shiftWS.findAllNormal();
        for (ShiftDTO shiftDTO : allNormal) {
            System.out.println(shiftDTO.getName() + "\t" + shiftDTO.getShiftType());
        }
    }
}
