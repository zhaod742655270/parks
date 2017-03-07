package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.dto.managesys.PriviledgeDTO;
import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.dto.managesys.ResBtnDTO;
import com.hbyd.parks.dto.managesys.ResMenuDTO;
import com.hbyd.parks.common.constant.TypeConstant;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.managesys.PriviledgeWS;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by allbutone on 14-7-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/managesys/applicationContext_CXF_Client.xml"})
public class PriviledgeWSImplTest {
    @Resource
    private PriviledgeWS priviledgeWS;

    @Test
    public void testGetResPriDTOs(){
        String roleId = "1";
        String appId = "1";
        List<PriviledgeDTO> pris = priviledgeWS.getResPriDTOs(roleId, appId);
        System.out.println("子系统下的权限个数为： " + pris.size());
        for(PriviledgeDTO pri : pris){
            System.out.println(pri.getId());
        }
    }

    @Test
    public void testUpdateBatch(){
        PriviledgeDTO pri_1 = new PriviledgeDTO();
            pri_1.setPriResType(TypeConstant.SYS_APP);
            pri_1.setPriResId("1");
            pri_1.setPriOwnerType(TypeConstant.ROLE);
            pri_1.setPriResId("1");

        priviledgeWS.updateBatch("1","1",Arrays.asList(pri_1));
    }

    @Test
    public void testGetApps() throws Exception {
        String userId = "1";
        Collection<ResAppDTO> apps = priviledgeWS.getApps(userId);

        for (ResAppDTO app : apps) {
            System.out.println(app.getAppName());
        }

        Assert.assertTrue(apps.size()>0);
    }

    @Test
    public void testGetMenus() {
        String userId = "1";
        String appId = "1";
        Collection<ResMenuDTO> menus = priviledgeWS.getMenus(userId,appId);

        for (ResMenuDTO menu : menus) {
            System.out.println(menu.getMenuName());
        }

        Assert.assertTrue(menus.size()>0);
    }

    @Test
    public void testGetButtons(){
        String userId = "1";
        String menuId = "1";

        Collection<ResBtnDTO> buttons = priviledgeWS.getButtons(userId, menuId);

        for (ResBtnDTO button : buttons) {
            System.out.println(button.getBtnName());
        }

        Assert.assertTrue(buttons.size()>0);
    }



    @Test
    public void testGetPageBean(){
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setPage(1);
        queryBean.setRows(5);
        queryBean.setSorts(new String[]{"priId"});
        queryBean.setOrders(new String[]{"asc"});

        String hql_where = "where priOwnerType = ?";

        List params = Arrays.asList("role");

        PageBeanEasyUI pageBean = priviledgeWS.getPageBean(queryBean, hql_where, params.toArray());
        System.out.println(pageBean.getTotal());
        if(pageBean.getRows() != null){
            for (Object o : pageBean.getRows()) {
                PriviledgeDTO pd = (PriviledgeDTO) o;
                System.out.println(pd.getId() + ", " + pd.getPriOwnerType());
            }
        }else{
            System.out.println("the query result is empty!");
        }

        Assert.assertEquals(5, pageBean.getRows().size());
    }

    @Test
    public void testCheckUser() {
        boolean result = priviledgeWS.checkUser("username_1", "123");
        Assert.assertTrue(result);
    }
}
