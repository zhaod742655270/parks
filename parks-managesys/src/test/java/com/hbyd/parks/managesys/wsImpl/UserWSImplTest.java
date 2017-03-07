package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.dto.managesys.RoleDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.ws.managesys.DeptWS;
import com.hbyd.parks.ws.managesys.RoleWS;
import com.hbyd.parks.ws.managesys.UserWS;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by allbutone on 14-7-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/managesys/applicationContext_CXF_Client.xml"})
public class UserWSImplTest {
    private Set<RoleDTO> roles_to_save;
    private Set<RoleDTO> roles_to_update;

    private DepartmentDTO dept_to_save;
    private DepartmentDTO dept_to_update;

    @Resource
    private UserWS userWS;

    @Resource
    private RoleWS roleWS;

    @Resource
    private DeptWS deptWS;

    @Before//这个注解很容易添加错误
    public void init(){
        RoleDTO r1 = null;
        RoleDTO r2 = null;
        RoleDTO r3 = null;

        DepartmentDTO d1 = null;
        DepartmentDTO d2 = null;


        //准备测试数据
        r1 = new RoleDTO();
        r1.setRoleName("manager");
        r1.setRoleDesc("manage everything");

        r2 = new RoleDTO();
        r2.setRoleName("director");
        r2.setRoleDesc("direct all people");

        r3 = new RoleDTO();
        r3.setRoleName("driver");
        r3.setRoleDesc("drives the car");

        d1 = new DepartmentDTO();
        d1.setDeptName("hr_dept");
        d1.setAbbrName("hr");

        d2 = new DepartmentDTO();
        d2.setDeptName("tech_dept");
        d2.setAbbrName("tech");

        r1 = roleWS.save(r1);
        r2 = roleWS.save(r2);
        r3 = roleWS.save(r3);

        d1 = deptWS.save(d1);
        d2 = deptWS.save(d2);

        roles_to_save = new HashSet<>(Arrays.asList(r1,r2));
        roles_to_update = new HashSet<>(Arrays.asList(r3));

        dept_to_save = d1;
        dept_to_update = d2;
    }

    @Test
    public void testGetByName(){
        String name = "admin";
        UserDTO userD = userWS.getByName(name);
        System.out.println(userD.getId());
    }

    @Test
    public void testCrud() throws Exception {
        UserDTO u = new UserDTO();
        u.setUserName("jack");
        u.setPassword("123");
        u.setRoles(roles_to_save);
        u.setDepartment(dept_to_save);

        UserDTO user_saved = userWS.save(u);

//      断言保存
        Assert.assertEquals(2, user_saved.getRoles().size());
        Assert.assertEquals("hr", user_saved.getDepartment().getAbbrName());

//      修改添加的用户
        user_saved.setRoles(roles_to_update);
        user_saved.setDepartment(dept_to_update);
        userWS.update(user_saved);

//      查询修改后的用户
        String id = user_saved.getId();
        user_saved = userWS.getByID(id);

//      断言修改
        Assert.assertEquals(1, user_saved.getRoles().size());
        Assert.assertEquals("tech", user_saved.getDepartment().getAbbrName());

//      删除用户
        userWS.delByID(id);
//      断言删除
        Assert.assertNull(userWS.getByID(id));
    }
}
