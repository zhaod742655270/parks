package com.hbyd.parks.doorsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.doorsys.AccessEventDTO;
import com.hbyd.parks.ws.doorsys.AccessEventWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/doorsys/applicationContext_CXF_Client.xml"})
public class AccessEventWSImplTest {

    @Resource
    private AccessEventWS accessEventWS;

    @Test
    public void testGetPageBean() {
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setOrders(new String[]{"asc"});
        queryBean.setSort("id");
        queryBean.setPage(1);
        queryBean.setRows(5);

        String hql_where = "";

        Object[] params = new Object[]{};

        PageBeanEasyUI pageBean = accessEventWS.getPageBean(queryBean, hql_where, params);

        List rows = pageBean.getRows();

        if(rows == null){
            System.out.println("未查询到符合条件的刷卡记录");
        }else {
            Gson gson = new Gson();
            for (Object row : rows) {
                AccessEventDTO dto = (AccessEventDTO) row;
                System.out.println(gson.toJson(dto));
            }
        }
    }

    @Test
    public void testGetPageBeanByHQL() {
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setOrders(new String[]{"asc"});
        queryBean.setSort("id");
        queryBean.setPage(1);
        queryBean.setRows(5);

        String hql = "from AccessEvent";

        Object[] params = new Object[]{};

        PageBeanEasyUI pageBean = accessEventWS.getPageBeanByHQL(queryBean, hql, params);

        List rows = pageBean.getRows();

        if(rows == null){
            System.out.println("未查询到符合条件的刷卡记录");
        }else {
            Gson gson = new Gson();
            for (Object row : rows) {
                AccessEventDTO dto = (AccessEventDTO) row;
                System.out.println(gson.toJson(dto));
            }
        }
    }
}