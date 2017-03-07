package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.dao.attendancesys.ShiftBindingDao;
import com.hbyd.parks.daoImpl.hibernate.AppConfig;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@TransactionConfiguration
public class ShiftBindingDaoImplTest {

    @Resource
    private ShiftBindingDao shiftBindingDao;

    @Test
    @Transactional
    public void testUsedWithinShiftBinding() throws Exception {
        boolean b = shiftBindingDao.usedWithinShiftBinding("821bc4dd-b919-4150-a90a-4387054a929d");
        Assert.assertTrue(b);
    }
}