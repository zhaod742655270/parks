package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.dto.attendancesys.HolidayDTO;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.attendancesys.HolidayWS;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/attendancesys/applicationContext_CXF_Client.xml"})
public class HolidayWSImplTest {

    @Resource
    private HolidayWS holidayWS;

    private HolidayDTO save1;
    private HolidayDTO save2;
    private HolidayDTO save3;
    private HolidayDTO save4;

    /**
     * 测试用的时间字符串
     */
    private String date = "2014-12-12 12:33:12";

    @After
    public void clean(){
        for (HolidayDTO holidayDTO : holidayWS.findAll()) {
            holidayWS.delByID(holidayDTO.getId());
        }
    }
    /**
     * 添加测试数据
     */
    @Before
    public void init() {
        HolidayDTO h1 = new HolidayDTO();
        h1.setName("test_name_1");
        h1.setStartDate(date);
        save1 = holidayWS.save(h1);

        HolidayDTO h2 = new HolidayDTO();
        h2.setName("test_name_2");
        h2.setStartDate(date);
        save2 = holidayWS.save(h2);

        HolidayDTO h3 = new HolidayDTO();
        h3.setName("test_name_3");
        h3.setStartDate(date);
        save3 = holidayWS.save(h3);

        HolidayDTO h4 = new HolidayDTO();
        h4.setName("test_name_4");
        h4.setStartDate(date);
        save4 = holidayWS.save(h4);
    }

    @Test
    public void testValidateAspect() throws Exception {
//        测试切面参数验证
//        holidayWS.delByID(null);//单参方法
//        holidayWS.findAll();//无参方法
//        holidayWS.check(null, null);//多参方法
    }

    /**
     * 测试：删除的目标实体不存在
     *
     * @throws Exception
     */
    @Test(expected = SOAPFaultException.class)
    public void testDelByID_2() throws Exception {
        holidayWS.delByID("不存在的ID");
    }


    /**
     * 测试：更新的实体没有ID
     *
     * @throws Exception
     */
    @Test(expected = SOAPFaultException.class)
    public void testUpdate() throws Exception {
        HolidayDTO hd = new HolidayDTO();
        hd.setName("ahaha");
        holidayWS.update(hd);
    }

    /**
     * 测试：更新的目标实体不存在
     *
     * @throws Exception
     */
    @Test(expected = SOAPFaultException.class)
    public void testUpdate_2() throws Exception {
        HolidayDTO hd = new HolidayDTO();
        hd.setId("不存在的ID");
        hd.setName("ahaha");
        holidayWS.update(hd);
    }

    @Test
    public void testCrud() throws Exception {
//        1. 更新实体信息
        save1.setName("new_name_2");

//        2. 更新
        holidayWS.update(save1);

//        2.1. 断言更新成功
        Assert.assertEquals("new_name_2", holidayWS.getByID(save1.getId()).getName());

//        3. 删除
        holidayWS.delByID(save1.getId());

//        3.1 检查是否成功删除
        Assert.assertNull(holidayWS.getByID(save1.getId()));
    }

    @Test
    public void testFindAll() throws Exception {
        List<HolidayDTO> all = holidayWS.findAll();
        for (HolidayDTO holidayDTO : all) {
            System.out.println(holidayDTO.getStartDate());
        }

        Assert.assertTrue(all.size() >= 4);
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

//        PageBeanEasyUI pageBean = holidayWS.getPageBean(queryBean, hql_where, params.toArray());
        PageBeanEasyUI pageBean = holidayWS.getPageBean(queryBean, "", null);

        for (Object o : pageBean.getRows()) {
            HolidayDTO hd = (HolidayDTO) o;
            System.out.println(hd.getName());
        }

        Assert.assertEquals(3, pageBean.getRows().size());
    }

    @Test
    public void testGetPageBean_2(){
//        PageBeanEasyUI pageBean = holidayWS.getPageBean(null, null, null);
    }

    @Test
    public void testGetByName() throws Exception {
        HolidayDTO found = holidayWS.getByName("test_name_1");
        Assert.assertNotNull(found);
    }

    @Test
    public void testCheck(){
//        不要使用 save1, 因为已经在 testCrud() 中被删除了

//        断言和其他记录重名
        Assert.assertTrue(holidayWS.check(save2.getId(),"test_name_4"));//存在

//        断言没有重名
        assertFalse(holidayWS.check(save2.getId(), "test_name_ha_ha"));//不存在

//        断言可以维持原名称，这行可能失败，因为每次测试都插入名称为 test_name_2 的记录，需要使用 tearDown() 在测试后将表清空
        assertFalse(holidayWS.check(save2.getId(), "test_name_2"));//不存在
    }
}
