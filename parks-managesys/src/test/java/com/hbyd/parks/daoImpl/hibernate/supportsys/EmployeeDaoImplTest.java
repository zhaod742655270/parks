package com.hbyd.parks.daoImpl.hibernate.supportsys;

import com.hbyd.parks.daoImpl.hibernate.AppConfig;
import com.hbyd.parks.domain.supportsys.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration 的 locations (or value) 和 classes 只能指定一个
@ContextConfiguration(classes = {AppConfig.class})
@TransactionConfiguration
public class EmployeeDaoImplTest {

    @Resource
    private EmployeeDaoImpl employeeDao;

    @Test
    @Transactional
    public void testGetEmpsWithinDept() throws Exception {
        List<Employee> empsWithinDept = employeeDao.getEmpsWithinDept("2f71e2c8-1520-4c94-bce6-b16121f01f1f");
        System.out.println(empsWithinDept.size());
    }
}