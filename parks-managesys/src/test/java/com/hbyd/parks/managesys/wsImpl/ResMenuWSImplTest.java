package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.dto.managesys.ResBtnDTO;
import com.hbyd.parks.dto.managesys.ResMenuDTO;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.managesys.ResAppWS;
import com.hbyd.parks.ws.managesys.ResBtnWS;
import com.hbyd.parks.ws.managesys.ResMenuWS;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by allbutone on 14-7-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/managesys/applicationContext_CXF_Client.xml"})
public class ResMenuWSImplTest {
    @Resource
    private ResAppWS resAppWS;

    @Resource
    private ResMenuWS resMenuWS;

    @Resource
    private ResBtnWS resBtnWS;

    @Test
    public void testFindAll(){
        for (ResMenuDTO resMenuDTO : resMenuWS.findAll()) {
            System.out.println(resMenuDTO.getMenuName());
        }
    }

    @Test
    public void testTestGetPageBean(){
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
            queryBean.setPage(1);
            queryBean.setRows(3);
            queryBean.setOrders(new String[]{"asc"});
            queryBean.setSorts(new String[]{"menuName"});
        String hql_where = "where menuName like ?";
        Object[] params = new Object[]{"menu_name%"};

        PageBeanEasyUI pageBean = resMenuWS.getPageBean(queryBean, hql_where, params);

        for (Object o : pageBean.getRows()) {
            ResMenuDTO menu = (ResMenuDTO) o;
            System.out.println(menu.getMenuName());
            System.out.println(menu.getAppId());
            System.out.println(menu.getAppName());
            System.out.println(menu.getParentName());
        }
    }

    @Test
    public void testCrud(){
//        准备测试数据

//        子系统
        ResAppDTO app = new ResAppDTO();
        app.setAppName("app_name");
        app = resAppWS.save(app);

//        顶级菜单
        ResMenuDTO menu_parent = new ResMenuDTO();
        menu_parent.setParentId(null);
        menu_parent.setMenuName("menu_name_parent");
        menu_parent.setAppId(app.getId());
        menu_parent = resMenuWS.save(menu_parent);

//        子菜单
        ResMenuDTO menu_child = new ResMenuDTO();
        menu_child.setParentId(menu_parent.getId());
        menu_child.setMenuName("menu_name_child");
        menu_child.setAppId(app.getId());
        menu_child = resMenuWS.save(menu_child);

//        子菜单下的按钮
        ResBtnDTO btn_1 = new ResBtnDTO();
        btn_1.setBtnName("btn_name_1");
        btn_1.setMenuId(menu_child.getId());
        btn_1 = resBtnWS.save(btn_1);

        ResBtnDTO btn_2 = new ResBtnDTO();
        btn_2.setBtnName("btn_name_2");
        btn_2.setMenuId(menu_child.getId());
        btn_2 = resBtnWS.save(btn_2);

//        断言保存成功
        Assert.assertNotNull(resAppWS.getByID(app.getId()));
        Assert.assertNotNull(resMenuWS.getByID(menu_parent.getId()));
        Assert.assertNotNull(resMenuWS.getByID(menu_child.getId()));
        Assert.assertNotNull(resBtnWS.getByID(btn_1.getId()));
        Assert.assertNotNull(resBtnWS.getByID(btn_2.getId()));

//        断言菜单关联正确
        Assert.assertTrue(menu_child.getParentId().equals(menu_parent.getId()));
//        断言菜单和按钮关联正确
        Assert.assertEquals(2, resMenuWS.getByID(menu_child.getId()).getResBtns().size());


//        修改顶级菜单
        ResAppDTO app_2 = new ResAppDTO();
        app_2.setAppName("app_name_2");
        app_2 = resAppWS.save(app_2);

        menu_parent = resMenuWS.getByID(menu_parent.getId());
        menu_parent.setAppId(app_2.getId());
        resMenuWS.update(menu_parent);

//        断言子菜单的所属子系统也同时修改
        menu_child = resMenuWS.getByID(menu_child.getId());
        Assert.assertEquals(app_2.getId(), menu_child.getAppId());
//        断言子菜单的父菜单没有变化
        Assert.assertEquals(menu_parent.getId(),menu_child.getParentId());

//        修改子菜单
        menu_child.setMenuName("menu_child_new_name");
        resMenuWS.update(menu_child);
        menu_child = resMenuWS.getByID(menu_child.getId());
//        断言子菜单名称变化
        Assert.assertEquals("menu_child_new_name", menu_child.getMenuName());
//        断言子菜单的父菜单没有变化
        Assert.assertEquals(menu_parent.getId(),menu_child.getParentId());

//        删除顶级菜单
        resMenuWS.delByID(menu_parent.getId());

//        断言顶级菜单的级联删除
        Assert.assertNull(resMenuWS.getByID(menu_parent.getId()));
        Assert.assertNull(resMenuWS.getByID(menu_child.getId()));
        Assert.assertNull(resBtnWS.getByID(btn_1.getId()));
        Assert.assertNull(resBtnWS.getByID(btn_2.getId()));
    }
}
