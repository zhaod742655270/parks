package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.dto.managesys.ResBtnDTO;
import com.hbyd.parks.dto.managesys.ResMenuDTO;
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
 * Created by allbutone on 14-7-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/managesys/applicationContext_CXF_Client.xml"})
public class ResAppWSImplTest {
    @Resource
    private ResAppWS resAppWS;

    @Resource
    private ResMenuWS resMenuWS;

    @Resource
    private ResBtnWS resBtnWS;

    @Test
    public void testFindAll(){
        List<ResAppDTO> dtos = resAppWS.findAll();

        Assert.assertTrue(dtos.size()>0);

        for (ResAppDTO resAppDTO : dtos) {
            System.out.println(resAppDTO.getAppName());
        }
    }

    /**
     * 测试服务的增删改查
     */
    @Test
    public void testCrud(){
//        准备测试数据

//        子系统
        ResAppDTO app = new ResAppDTO();
        app.setAppName("test_app_name");

//        子系统：保存
        ResAppDTO appDTO = resAppWS.save(app);

//        菜单
        ResMenuDTO menu_1 = new ResMenuDTO();
        menu_1.setParentId("");
        menu_1.setMenuName("test_menu_name_1");
        menu_1.setAppId(appDTO.getId());

//        菜单：保存
        ResMenuDTO menu_1_DTO = resMenuWS.save(menu_1);

        ResMenuDTO menu_2 = new ResMenuDTO();
        menu_2.setMenuName("test_menu_name_2");
        menu_2.setParentId(menu_1_DTO.getId());
        menu_2.setAppId(appDTO.getId());

//        菜单：保存
        ResMenuDTO menu_2_DTO = resMenuWS.save(menu_2);

//        按钮
        ResBtnDTO btn_1 = new ResBtnDTO();
        btn_1.setBtnName("btn_name_1");
        btn_1.setMenuId(menu_2_DTO.getId());

        ResBtnDTO btn_2 = new ResBtnDTO();
        btn_2.setBtnName("btn_name_2");
        btn_2.setMenuId(menu_2_DTO.getId());

//        按钮：保存
        resBtnWS.save(btn_1);
        resBtnWS.save(btn_2);

//        断言子系统有两个子菜单
//        Assert.assertEquals(2, appDTO.getResMenus().size());//wrong, no menus, 菜单是在 appDTO 返回后保存的
        Assert.assertEquals(2, resAppWS.getByID(appDTO.getId()).getResMenus().size());

//        断言两个子菜单件有从属关系
        Assert.assertTrue(menu_2_DTO.getParentId().equals(menu_1_DTO.getId()));

//        断言菜单和按钮的从属关系
        Assert.assertTrue(btn_1.getMenuId().equals(menu_2_DTO.getId()));
        Assert.assertTrue(btn_2.getMenuId().equals(menu_2_DTO.getId()));

//        断言菜单下的按钮个数
//        Assert.assertEquals(2, menu_2_DTO.getResBtns().size());//wrong, no buttons, 按钮是在 menu_2_DTO 返回后保存的
        Assert.assertEquals(2, resMenuWS.getByID(menu_2_DTO.getId()).getResBtns().size());


//        子系统：修改，只涉及自身信息的更改
        ResAppDTO appToUpdate = resAppWS.getByID(appDTO.getId());
        appToUpdate.setAppName("test_appName_new");
        resAppWS.update(appToUpdate);
        Assert.assertEquals("test_appName_new", resAppWS.getByID(appDTO.getId()).getAppName());


//        删除子系统：删除
        resAppWS.delByID(appDTO.getId());

//        断言全部删除成功：查询
        Assert.assertNull(resAppWS.getByID(appDTO.getId()));
        Assert.assertNull(resMenuWS.getByID(menu_1_DTO.getId()));
        Assert.assertNull(resMenuWS.getByID(menu_2_DTO.getId()));
        Assert.assertNull(resBtnWS.getByID(btn_1.getId()));
        Assert.assertNull(resBtnWS.getByID(btn_2.getId()));
        Assert.assertNull(resAppWS.getByName("test_appName_new"));
    }
}
