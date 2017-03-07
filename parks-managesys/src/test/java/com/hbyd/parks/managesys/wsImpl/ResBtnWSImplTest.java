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
import java.util.List;

/**
 * Created by allbutone on 14-7-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/managesys/applicationContext_CXF_Client.xml"})
public class ResBtnWSImplTest {
    @Resource
    private ResAppWS resAppWS;

    @Resource
    private ResMenuWS resMenuWS;

    @Resource
    private ResBtnWS resBtnWS;

//    下面是测试时使用的字段
    private ResAppDTO app;
    private ResMenuDTO menu_parent;
    private ResMenuDTO menu_child;
    private ResMenuDTO menu_child_2;
    private ResBtnDTO btn_1;
    private ResBtnDTO btn_2;

    @Test
    public void testTestGetPageBean(){
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setPage(1);
        queryBean.setRows(4);
        queryBean.setOrders(new String[]{"asc"});
        queryBean.setSorts(new String[]{"btnName"});
        String hql_where = "where btnName like ?";
        Object[] params = new Object[]{"btn_name%"};

        PageBeanEasyUI pageBean = resBtnWS.getPageBean(queryBean, hql_where, params);

        for (Object o : pageBean.getRows()) {
            ResBtnDTO btn = (ResBtnDTO) o;
            System.out.println(btn.getBtnName());
            System.out.println(btn.getMenuName());
        }
    }

    @Test
    public void testCrud(){
//        准备测试数据

//        子系统
        app = new ResAppDTO();
        app.setAppName("app_name");
        app = resAppWS.save(app);

//        顶级菜单
        menu_parent = new ResMenuDTO();
        menu_parent.setParentId(null);
        menu_parent.setMenuName("menu_name_parent");
        menu_parent.setAppId(app.getId());
        menu_parent = resMenuWS.save(menu_parent);

//        子菜单
        menu_child = new ResMenuDTO();
        menu_child.setParentId(menu_parent.getId());
        menu_child.setMenuName("menu_name_child");
        menu_child.setAppId(app.getId());
        menu_child = resMenuWS.save(menu_child);

        menu_child_2 = new ResMenuDTO();
        menu_child_2.setParentId(menu_parent.getId());
        menu_child_2.setMenuName("menu_name_child_2");
        menu_child_2.setAppId(app.getId());
        menu_child_2 = resMenuWS.save(menu_child_2);

//        子菜单下的按钮
        btn_1 = new ResBtnDTO();
        btn_1.setBtnName("btn_name_1");
        btn_1.setMenuId(menu_child.getId());
        btn_1 = resBtnWS.save(btn_1);

        btn_2 = new ResBtnDTO();
        btn_2.setBtnName("btn_name_2");
        btn_2.setMenuId(menu_child.getId());
        btn_2 = resBtnWS.save(btn_2);

//        断言保存成功
        Assert.assertNotNull(resAppWS.getByID(app.getId()));
        Assert.assertNotNull(resMenuWS.getByID(menu_parent.getId()));
        Assert.assertNotNull(resMenuWS.getByID(menu_child.getId()));
        Assert.assertNotNull(resBtnWS.getByID(btn_1.getId()));
        Assert.assertNotNull(resBtnWS.getByID(btn_2.getId()));

//        断言菜单的子系统正确
        Assert.assertEquals(app.getId(), resMenuWS.getByID(menu_parent.getId()).getAppId());
        Assert.assertEquals(app.getId(), resMenuWS.getByID(menu_child.getId()).getAppId());
        Assert.assertEquals(app.getId(), resMenuWS.getByID(menu_child_2.getId()).getAppId());
        Assert.assertEquals(3, resAppWS.getByID(app.getId()).getResMenus().size());

//        断言菜单关联正确
        Assert.assertTrue(menu_child.getParentId().equals(menu_parent.getId()));
//        断言菜单和按钮关联正确
        Assert.assertEquals(2, resMenuWS.getByID(menu_child.getId()).getResBtns().size());

//        添加按钮
        ResBtnDTO btn_3 = new ResBtnDTO();
        btn_3.setBtnName("btn_3_name");
        btn_3.setMenuId(menu_child.getId());
        btn_3 = resBtnWS.save(btn_3);

//        断言按钮添加成功
        Assert.assertEquals("btn_3_name", btn_3.getBtnName());
        Assert.assertEquals(3, resMenuWS.getByID(menu_child.getId()).getResBtns().size());

//        修改按钮
        btn_3.setMenuId(menu_child_2.getId());
        btn_3.setBtnName("btn_3_new_name");
        resBtnWS.update(btn_3);

//        断言修改成功
        Assert.assertEquals("btn_3_new_name", resBtnWS.getByID(btn_3.getId()).getBtnName());
        Assert.assertEquals(1, resMenuWS.getByID(menu_child_2.getId()).getResBtns().size());

//        断言查询
        List<ResBtnDTO> btns = resBtnWS.findAll();
        Assert.assertTrue(btns.size() > 0);

//        删除按钮
        resBtnWS.delByID(btn_3.getId());
//        断言删除成功
        Assert.assertNull(resBtnWS.getByID(btn_3.getId()));
    }
}
