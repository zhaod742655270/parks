package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.dto.attendancesys.GlobalDefDTO;
import com.hbyd.parks.common.enums.AssertType;
import com.hbyd.parks.common.enums.RestDay;
import com.hbyd.parks.ws.attendancesys.GlobalDefWS;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/attendancesys/applicationContext_CXF_Client.xml"})
public class GlobalDefWSImplTest {

    private String oldShiftID = "24c686bf47c2e0a40147c2e291f70004";
    private String newShiftID = "24c686bf47c2e0a40147c2e28e660000";

    private List<RestDay> oldDays = Arrays.asList(RestDay.星期一, RestDay.星期四);
//    private List<RestDay> oldDays = Arrays.asList();
    private List<RestDay> newDays = Arrays.asList();
//    private List<RestDay> newDays = Arrays.asList(RestDay.星期二, RestDay.星期日);

    private AssertType oldType = AssertType.休息;
    private AssertType newType = AssertType.旷工;

    @Resource
    private GlobalDefWS globalDefWS;

//    准备的测试数据
    private GlobalDefDTO saved;

    @Before
    public void setUp(){
//        准备保存
        GlobalDefDTO dd = new GlobalDefDTO();
        dd.setShiftID(oldShiftID);
        dd.setRestDays(oldDays);
        dd.setInvalidAssert(oldType);

//        保存
        saved = globalDefWS.save(dd);

        Assert.assertNotNull(saved.getId());

        System.out.println(saved.getRestDays());
        System.out.println(saved.getInvalidAssert());
    }

    @Test
    public void testCrud(){
//        准备更新
        saved.setShiftID(newShiftID);
        saved.setRestDays(newDays);
        saved.setInvalidAssert(newType);

//        更新
        globalDefWS.update(saved);

        GlobalDefDTO updated = globalDefWS.getByID(saved.getId());
        Assert.assertEquals(newShiftID, updated.getShiftID());
        if (updated.getRestDays()!= null && updated.getRestDays().size()!=0) {
            assertThat(updated.getRestDays(), hasItem(newDays.get(0)));
//          为防止集合的追加拷贝，需要确认集合内元素的个数
            Assert.assertEquals(newDays.size(), updated.getRestDays().size());
        }
        System.out.println("restDays Updated: " + updated.getRestDays());

        assertThat(updated.getInvalidAssert(), is(newType));


//        删除测试数据
//        defaultDefinitionWS.delByID(saved.getId());
//        Assert.assertNull(defaultDefinitionWS.getByID(saved.getId()));
    }

    /**
     * 测试乐观锁: 测试数据库中已存在的记录是否能够连续更新
     */
    @Test
    public void testUpdate(){
        GlobalDefDTO toUpdate = globalDefWS.getByID(saved.getId());

        toUpdate.setShiftID(newShiftID);
        toUpdate.setRestDays(newDays);
        toUpdate.setInvalidAssert(newType);

//        更新
        globalDefWS.update(toUpdate);
        GlobalDefDTO updated = globalDefWS.getByID(toUpdate.getId());

        //删除测试数据
        globalDefWS.delByID(saved.getId());
    }

}