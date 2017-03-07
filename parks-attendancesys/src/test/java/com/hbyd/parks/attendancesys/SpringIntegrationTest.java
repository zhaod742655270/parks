package com.hbyd.parks.attendancesys;


import junit.framework.Assert;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 测试 Spring 和其他组件的集成
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class SpringIntegrationTest {
    @Resource
    DozerBeanMapper dozerMapper;

    @Test
    public void testDozer(){
        Assert.assertNotNull(dozerMapper);
    }
}
