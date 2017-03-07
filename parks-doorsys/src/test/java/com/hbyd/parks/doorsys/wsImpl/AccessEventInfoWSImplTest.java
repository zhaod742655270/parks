package com.hbyd.parks.doorsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.doorsys.AccessEventInfoDTO;
import com.hbyd.parks.dto.doorsys.AccessEventStatusDTO;
import com.hbyd.parks.ws.doorsys.AccessEventInfoWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/doorsys/applicationContext_CXF_Client.xml"})
public class AccessEventInfoWSImplTest {

    private Gson gson = new Gson();

    @Resource
    private AccessEventInfoWS accessEventInfoWS;

    @Test
    public void testGetPageBean() {
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setOrders(new String[]{"asc"});
        queryBean.setSort("id");
        queryBean.setPage(1);
        queryBean.setRows(5);

        String hql_where = "";

        Object[] params = new Object[]{};

        PageBeanEasyUI pageBean = accessEventInfoWS.getPageBean(queryBean, hql_where, params);

        List rows = pageBean.getRows();

        if(rows == null){
            System.out.println("未查询到符合条件的刷卡记录");
        }else {
            Gson gson = new Gson();
            for (Object row : rows) {
                System.out.println(gson.toJson(row));
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

        String hql = "from AccessEventInfoLatest";

        Object[] params = new Object[]{};

        PageBeanEasyUI pageBean = accessEventInfoWS.getPageBeanByHQL(queryBean, hql, params);

        List rows = pageBean.getRows();

        if(rows == null){
            System.out.println("未查询到符合条件的刷卡记录");
        }else {
            Gson gson = new Gson();
            for (Object row : rows) {
                System.out.println(gson.toJson(row));
            }
        }
    }

    @Test
    public void testCrud(){
//      SecondaryTable 内有对应信息
        AccessEventInfoDTO dto = accessEventInfoWS.getByID("6FCBF68F-2775-4FFB-92E2-5D5AF5C70F13");
        System.out.println(gson.toJson(dto));

//      SecondaryTable 内无对应信息
        AccessEventInfoDTO dto2 = accessEventInfoWS.getByID("7D7C06D1-7A0F-4C07-9BBC-1C8382980088");
        System.out.println(gson.toJson(dto2));
    }

    @Test
    public void testGetStatus(){
        AccessEventStatusDTO status = accessEventInfoWS.getStatus();
        System.out.println(gson.toJson(status));
    }
}