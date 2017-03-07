package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.ws.attendancesys.CalcWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 测试考勤计算服务
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/attendancesys/applicationContext_CXF_Client.xml"})
public class CalcWSImplTest {
    @Resource
    private CalcWS calcWS;

    @Test
    public void testRecalcDept() throws ParseException {
        String deptId = "68";
        Date begin = DateUtil.parseDate("2014-11-01");
        Date end = DateUtil.parseDate("2014-11-01");
        calcWS.recalcDept(deptId, begin, end);
    }

    /**
     * 测试数据:
     * 人员
     *      ID-212647
     * 部门
     *      ID-68
     *      Name-河北远东系统集成事业部
     */
    @Test
    public void testRecalcEmps() throws ParseException {
        List<String> empIds = Arrays.asList("212647");
        Date begin = DateUtil.parseDate("2014-11-01");
        Date end = DateUtil.parseDate("2014-11-01");

        calcWS.recalcEmps(empIds, begin, end);
    }
}
