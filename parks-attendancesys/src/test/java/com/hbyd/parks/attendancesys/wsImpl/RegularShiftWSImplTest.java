package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.constant.Unit;
import com.hbyd.parks.common.enums.ShiftType;
import com.hbyd.parks.dto.attendancesys.RegularShiftDTO;
import com.hbyd.parks.dto.attendancesys.ShiftBindingDTO;
import com.hbyd.parks.ws.attendancesys.RegularShiftWS;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 测试：规律班次
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/attendancesys/applicationContext_CXF_Client.xml")
public class RegularShiftWSImplTest {

    @Resource
    private RegularShiftWS regularShiftWS;

    @Before
    public void prepare(){
        for (int i = 0; i < 3; i++) {
            RegularShiftDTO r = new RegularShiftDTO();
            r.setName("custom_1");
            r.setUnitCnt(20);
            r.setUnit(Unit.日);

            ShiftBindingDTO s1 = new ShiftBindingDTO();
            s1.setDayToBound(1);
            s1.setIdx(1);

            s1.setShiftFK("24c6869c48a538e70148a53a00d70000");
            s1.setShiftType(ShiftType.NORMAL);

            List<ShiftBindingDTO> bindings = Arrays.asList(s1);
            r.setBindings(bindings);

            regularShiftWS.save(r);
        }
    }

    @Test
    public void foo(){

    }


    /**
     * 主要测试：集合属性不应随规律班次加载
     * 测试结果：select this_.id as id7_0_, this_.name as name7_0_, this_.unit as unit7_0_, this_.unitCnt as unitCnt7_0_ from attend_regular_shift this_
     */
    @Test
    public void testFindAll(){
        List<RegularShiftDTO> all = regularShiftWS.findAll();

        for (RegularShiftDTO dto : all) {
//          普通属性已加载：
            System.out.println(dto.getId());
            System.out.println(dto.getName());
            System.out.println(dto.getUnit());
            System.out.println(dto.getUnitCnt());
//          断言集合属性未加载：
            org.junit.Assert.assertNull(dto.getBindings());
        }
    }

    @Test
    public void testGetShiftBindings(){
        List<ShiftBindingDTO> sbs = regularShiftWS.getShiftBindings("18005c74-d0cf-47f5-bc4c-a7496a41015c");
        //后台如果返回的是空集合，前台接收到的就是 null

        if (sbs != null){
            System.out.println(sbs.size());
            for (ShiftBindingDTO sb : sbs) {
                System.out.println(sb.getId());
                System.out.println(sb.getDayToBound());
                System.out.println(sb.getIdx());
                System.out.println(sb.getRegularShiftFK());
                System.out.println(sb.getShiftFK());
                System.out.println(sb.getShiftName());
                System.out.println(sb.getShiftType());
            }
        }
    }

    @Test
    public void testCrud(){
//      保存
        RegularShiftDTO r = new RegularShiftDTO();
        r.setName("custom_1");
        r.setUnitCnt(20);
        r.setUnit(Unit.日);

        ShiftBindingDTO s1 = new ShiftBindingDTO();
        s1.setDayToBound(1);
        s1.setIdx(1);
        s1.setShiftType(ShiftType.NORMAL);
        s1.setShiftFK("24c6869c48a538e70148a53a00d70000");

        ShiftBindingDTO s2 = new ShiftBindingDTO();
        s2.setDayToBound(2);
        s2.setIdx(2);
        s2.setShiftType(ShiftType.NORMAL);
        s2.setShiftFK("24c6869c48a538e70148a53a00d70000");

        List<ShiftBindingDTO> bindings = Arrays.asList(s1, s2);
        r.setBindings(bindings);

        RegularShiftDTO saved = regularShiftWS.save(r);

        RegularShiftDTO byID = regularShiftWS.getByID(saved.getId());
        Assert.notNull(byID);
        org.junit.Assert.assertEquals(2, byID.getBindings().size());

//      更新
        ShiftBindingDTO s3 = new ShiftBindingDTO();
        s3.setDayToBound(3);
        s3.setIdx(3);
        s3.setShiftType(ShiftType.NORMAL);
        s3.setShiftFK("24c6869c48a538e70148a53a00d70000");

        List<ShiftBindingDTO> bindings1 = Arrays.asList(s3);

        saved.setBindings(bindings1);
        regularShiftWS.update(saved);

        org.junit.Assert.assertEquals(1, saved.getBindings().size());

//      删除
        regularShiftWS.delByID(saved.getId());
        org.junit.Assert.assertNull(regularShiftWS.getByID(saved.getId()));
    }

    @Test
//    @Test(expected = Exception.class)
    public void testNormalShiftBinding(){
//      保存
        RegularShiftDTO r = new RegularShiftDTO();
        r.setName("custom_1");
        r.setUnitCnt(20);
        r.setUnit(Unit.日);

        ShiftBindingDTO s1 = new ShiftBindingDTO();
        s1.setDayToBound(1);
        s1.setIdx(1);

//        s1.setShiftFK("");//not ok
//        s1.setShiftFK(null);//not ok
        s1.setShiftFK("24c6869c48a538e70148a53a00d70000");

//        s1.setShiftType(null);//not ok
        s1.setShiftType(ShiftType.NORMAL);//ok

        List<ShiftBindingDTO> bindings = Arrays.asList(s1);
        r.setBindings(bindings);

        regularShiftWS.save(r);
    }

    @Test
    public void testSpecialShiftBinding(){
//      保存
        RegularShiftDTO r = new RegularShiftDTO();
        r.setName("custom_1");
        r.setUnitCnt(20);
        r.setUnit(Unit.日);

        ShiftBindingDTO s1 = new ShiftBindingDTO();
        s1.setDayToBound(1);
        s1.setIdx(1);
        s1.setShiftFK("24c6869c48a538e70148a53a00d70000");

        s1.setShiftType(ShiftType.REST);//指定特殊班次
//        s1.setShiftType(null);//shiftType 不能为 null

        List<ShiftBindingDTO> bindings = Arrays.asList(s1);
        r.setBindings(bindings);

        regularShiftWS.save(r);
    }
}
