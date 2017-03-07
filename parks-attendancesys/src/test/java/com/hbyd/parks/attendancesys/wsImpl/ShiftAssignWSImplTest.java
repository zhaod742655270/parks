package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.constant.Unit;
import com.hbyd.parks.common.enums.ShiftType;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.dto.attendancesys.RegularShiftDTO;
import com.hbyd.parks.dto.attendancesys.ShiftAssignDTO;
import com.hbyd.parks.dto.attendancesys.ShiftBindingDTO;
import com.hbyd.parks.dto.attendancesys.ShiftDTO;
import com.hbyd.parks.ws.attendancesys.RegularShiftWS;
import com.hbyd.parks.ws.attendancesys.ShiftAssignWS;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.joda.time.DateTimeConstants.*;

/**
 * 测试：排班服务
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/attendancesys/applicationContext_CXF_Client.xml")
public class ShiftAssignWSImplTest {

    @Resource
    private RegularShiftWS regularShiftWS;

    @Resource
    private ShiftAssignWS shiftAssignWS;
    private RegularShiftDTO saved;

    /**
     * 排班前准备测试数据
     */
    @Before
    public void prepare() {
        RegularShiftDTO r = new RegularShiftDTO();
        r.setName("一周");
        r.setUnitCnt(1);

        r.setUnit(Unit.周);
//        r.setUnit(Unit.日);

        ShiftBindingDTO s1 = new ShiftBindingDTO();
        s1.setDayToBound(MONDAY);
        s1.setIdx(1);

        s1.setShiftFK("24c6869c48af851d0148af9683490000");
        s1.setShiftType(ShiftType.NORMAL);

        ShiftBindingDTO s2 = new ShiftBindingDTO();
        s2.setDayToBound(TUESDAY);
        s2.setIdx(1);

        s2.setShiftFK("24c6869c48af851d0148af9683490000");
        s2.setShiftType(ShiftType.NORMAL);

        ShiftBindingDTO s3 = new ShiftBindingDTO();
        s3.setDayToBound(WEDNESDAY);
        s3.setIdx(1);

        s3.setShiftFK("24c6869c48af851d0148af9683490000");
        s3.setShiftType(ShiftType.NORMAL);

        ShiftBindingDTO s4 = new ShiftBindingDTO();
        s4.setDayToBound(THURSDAY);
        s4.setIdx(1);

        s4.setShiftFK("24c6869c48a538e70148a53a00d70000");
        s4.setShiftType(ShiftType.NORMAL);

        ShiftBindingDTO s5 = new ShiftBindingDTO();
        s5.setDayToBound(FRIDAY);
        s5.setIdx(1);

        s5.setShiftFK("24c6869c48a538e70148a53a00d70000");
        s5.setShiftType(ShiftType.NORMAL);

        ShiftBindingDTO s6 = new ShiftBindingDTO();
        s6.setDayToBound(SATURDAY);
        s6.setIdx(1);

        s6.setShiftFK("24c6869c48a538e70148a53a00d70000");
        s6.setShiftType(ShiftType.NORMAL);

        ShiftBindingDTO s7 = new ShiftBindingDTO();
        s7.setDayToBound(SUNDAY);
        s7.setIdx(1);

        s7.setShiftFK("24c6869c48a538e70148a53a00d70000");
        s7.setShiftType(ShiftType.NORMAL);

        r.setBindings(Arrays.asList(s1,s2,s3,s4,s5,s6,s7));

        saved = regularShiftWS.save(r);
    }

    @Test
    public void testDelById(){
        shiftAssignWS.delByID("029c3edb-19f2-4c56-aa6e-87ab1328f7bc");
    }

/*
    @Test
    public void testGetShiftAssignResult(){
//      同年跨月
//        int beginYear = 2014;
//        int beginMonth = 10;
//        int endYear = 2014;
//        int endMonth = 12;

//      跨一年
//        int beginYear = 2014;
//        int beginMonth = 10;
//        int endYear = 2015;
//        int endMonth = 12;

//      跨多年
        int beginYear = 2014;
        int beginMonth = 10;
        int endYear = 2017;
        int endMonth = 12;

//      非法时段：起始日期晚于结束日期
//        int beginYear = 2015;
//        int beginMonth = 10;
//        int endYear = 2014;
//        int endMonth = 12;

        String id = "30706";//人员ID
        boolean queryType = false;//查询个人的排班

//        String id = "61";//部门ID, 河北远东通信系统工程有限公司总部，人数：60
//        boolean queryType = true;//查询部门的排班

        List<ShiftAssignDTO> shiftAssignResult = shiftAssignWS.getShiftAssignResult(beginYear, beginMonth, endYear, endMonth, id, queryType);
        for (ShiftAssignDTO dto : shiftAssignResult) {
            System.out.println(new Gson().toJson(dto));
        }
    }
*/

    @Test
    public void testGetPageBean(){
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setRows(10);
        queryBean.setPage(1);
        queryBean.setOrders(new String[]{"asc"});
        queryBean.setSorts(new String[]{"id"});

//        String hql_where = "where yearAndMonth = ?";
//        Object[] params = new String[]{"2014-09"};

        String hql_where = "where yearAndMonth >= ? and yearAndMonth <= ? and employee.id in (100,190093)";
        Object[] params = new String[]{"2013-12","2015-03"};

        PageBeanEasyUI pageBean = shiftAssignWS.getPageBean(queryBean, hql_where, params);

        List rows = pageBean.getRows();

        if(rows != null){
            for (Object row : rows) {
                ShiftAssignDTO sa = (ShiftAssignDTO) row;
                System.out.println(sa.getId());
                System.out.println(sa.getYearAndMonth() + "\t" + sa.getEmpName());
            }
        }
    }

    @Test
    public void testAssignShiftForEmp() throws ParseException {
//  测试排班：日
//      测试时段：一月整
//        Date begin = DateUtil.parseDate("2014-10-1");//2014-10-1 是周三
//        Date end = DateUtil.parseDate("2014-10-31");
//      测试时段：不满一月
//        Date begin = DateUtil.parseDate("2014-10-4");//2014-10-1 是周六
//        Date end = DateUtil.parseDate("2014-10-12");
//      测试时段：跨月不跨年
//        Date begin = DateUtil.parseDate("2014-9-28");//2014-9-28 是周日
//        Date end = DateUtil.parseDate("2014-10-12");
//      测试时段：跨年
        Date begin = DateUtil.parseDate("2013-4-20");//2013-12-20 是周五
        Date end = DateUtil.parseDate("2013-10-12");

        String empId = "100";
        String regularShiftId = saved.getId();

        shiftAssignWS.assignShiftForEmp(begin, end, empId, regularShiftId);
    }

    @Test
    public void testAssignShiftDept() throws ParseException{
        Date begin = DateUtil.parseDate("2014-10-1");//2014-10-1 是周三
        Date end = DateUtil.parseDate("2014-10-31");
        String deptId = "61";//河北远东通信系统工程有限公司总部，人数：60
        String regularShiftId = saved.getId();

        shiftAssignWS.assignShiftForDept(begin, end, deptId, regularShiftId);
    }

    @Test
    public void testUpdateDayShiftForEmp(){
        String shiftAssignId = "006d958e-72b9-4c55-bd41-99c2d1f65ef8";
        int day = 1;

//        String shiftId = "测试用的 shiftId";
        String shiftId = null;

        shiftAssignWS.updateDayShiftForEmp(shiftAssignId, day, shiftId);
    }

//    @Test
    @Test(expected = Exception.class)
    public void testGetDayShift() throws ParseException {
        Date date = DateUtil.parseDate("2014-10-1");
//        String empId = "198970";
        String empId = "不存在的人员ID";
        ShiftDTO dayShift = shiftAssignWS.getDayShift(date, empId);

        System.out.println(dayShift);
        System.out.println(dayShift.getId());
        System.out.println(dayShift.getName());
        System.out.println(dayShift.getDescription());
        System.out.println(dayShift.getShiftType());
    }
}