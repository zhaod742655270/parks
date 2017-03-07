package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.dto.attendancesys.IntervalDTO;
import com.hbyd.parks.dto.attendancesys.ShiftDTO;
import com.hbyd.parks.ws.attendancesys.IntervalWS;
import com.hbyd.parks.ws.attendancesys.ShiftWS;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/attendancesys/applicationContext_CXF_Client.xml"})
public class IntervalWSImplTest {

    @Resource
    private IntervalWS intervalWS;

    @Resource
    private ShiftWS shiftWS;
    private ShiftDTO s;
    private IntervalDTO i1;
    private IntervalDTO i2;
    private IntervalDTO i3;
    private IntervalDTO i1_save;
    private IntervalDTO i2_save;
    private IntervalDTO i3_save;
    private ShiftDTO s_save;

//    测试使用的时间，格式为：HH:mm:ss
//    private String time = new DateTime(2014,12,31,12,34,44).toString("HH:mm:ss");//12:34:44
    private String time = "12:34";


    @Before
    public void init() {
//        插入测试数据，由于 Interval 字段太多，只测试部分字段

//        班次
        s = new ShiftDTO();
        s.setName("test_name_1_" + UUID.randomUUID().toString());
        s_save = shiftWS.save(s);
        String shiftID = s_save.getId();

//        时段
        i1 = new IntervalDTO();
        i1.setNeedSignIn(false);
        i1.setEarlyDeadLine(time);
        i1.setShiftID(shiftID);

        i2 = new IntervalDTO();
        i2.setNeedSignIn(false);
        i2.setEarlyDeadLine(time);
        i2.setShiftID(shiftID);

        i3 = new IntervalDTO();
        i3.setNeedSignIn(true);
        i3.setEarlyDeadLine(time);
        i3.setShiftID(shiftID);

        i1_save = intervalWS.save(i1);
        i2_save = intervalWS.save(i2);
        i3_save = intervalWS.save(i3);
    }

    @Test
    public void testDelByID() throws Exception {
        intervalWS.delByID(i2_save.getId());
    }

    /**测试普通字段的更新
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {

//        1. 更新普通字段
        i1_save.setLateDelay(22);
//        2. 执行更新
        intervalWS.update(i1_save);
//        3. 断言
        IntervalDTO updated = intervalWS.getByID(i1_save.getId());
        Assert.assertEquals(22, updated.getLateDelay());

    }

    /**测试外键字段的更新
     * @throws Exception
     */
    @Test
    public void testUpdateFK() throws Exception {
        ShiftDTO newShift = new ShiftDTO();
        newShift.setName("shift_name_" + UUID.randomUUID().toString());
        newShift = shiftWS.save(newShift);

        System.out.println("old shift id: " + i1_save.getShiftID());
        System.out.println("new shift id: " + newShift.getId());

//        1. 更新关联字段
        i1_save.setShiftID(newShift.getId());

//        2. 执行更新操作
        intervalWS.update(i1_save);

//        3. 断言
        IntervalDTO updated = intervalWS.getByID(i1_save.getId());
        Assert.assertEquals(newShift.getId(), updated.getShiftID());
    }

    @Test
    public void testGetByID() throws Exception {
        Assert.assertNull(intervalWS.getByID("不存在的ID"));

        IntervalDTO i4 = new IntervalDTO();
        i4.setLateDelay(123);
        i4.setEarlyDeadLine(time);

        ShiftDTO s3 = new ShiftDTO();
        s3.setName("shit_name_test_" + UUID.randomUUID().toString());

        i4.setShiftID(shiftWS.save(s3).getId());

        IntervalDTO i4_saved = intervalWS.save(i4);

        Assert.assertNotNull(intervalWS.getByID(i4_saved.getId()));
        System.out.println(i4_saved.getEarlyDeadLine());//12:34:44
    }

    @Test
    public void testSave() throws Exception {
        IntervalDTO i4 = new IntervalDTO();
        i4.setLateDelay(123);
        i4.setEarlyDeadLine(time);

        ShiftDTO s3 = new ShiftDTO();
        s3.setName("shit_name_test_" + UUID.randomUUID().toString());

        i4.setShiftID(shiftWS.save(s3).getId());

        intervalWS.save(i4);
    }

    @Test
    public void testFindAll() throws Exception {
        int size = intervalWS.findAll().size();
        System.out.println(size);
        Assert.assertTrue(size >0);
    }
}
