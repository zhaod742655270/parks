package com.hbyd.parks.attendancesys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.dto.attendancesys.AtteInfoDTO;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.attendancesys.AtteInfoWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by allbutone on 2014/9/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/attendancesys/applicationContext_CXF_Client.xml"})
public class AtteInfoWSImplTest {
    @Resource
    private AtteInfoWS atteInfoWS;

    @Test
    public void testGetPageBean(){
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setOrders(new String[]{"asc"});
        queryBean.setSorts(new String[]{"empId"});
        queryBean.setPage(1);
        queryBean.setRows(3);

        String hql_where = "";
        Object[] params = Arrays.asList().toArray();

        PageBeanEasyUI pageBean = atteInfoWS.getPageBean(queryBean, hql_where, params);

        List rows = pageBean.getRows();
        Gson g = new Gson();
        for (Object row : rows) {
            AtteInfoDTO info = (AtteInfoDTO) row;
            System.out.println(g.toJson(info));
        }
    }
    @Test
    public void testGetPageBeanByHQL(){
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setOrders(new String[]{"asc"});
        queryBean.setSorts(new String[]{"empId"});
        queryBean.setPage(1);
        queryBean.setRows(3);

//        不推荐，from AtteInfo ai, AtteDetail ad 会造成 ai cross join ad, 然后通过 where 过滤
//        String hql = "select distinct ai from AtteInfo ai, AtteDetail ad, Interval i where ai.id=ad.atteInfo.id and ad.interval.id=i.id";

//        推荐
        String hql = "select distinct ai from AtteInfo ai left join fetch ai.details as ds left join fetch ds.interval where ds.leaveNum>=0";

        Object[] params = new Object[]{};

        PageBeanEasyUI pageBean = atteInfoWS.getPageBeanByHQL(queryBean, hql, params);

        List rows = pageBean.getRows();
        Gson g = new Gson();
        for (Object row : rows) {
            AtteInfoDTO info = (AtteInfoDTO) row;
            System.out.println(g.toJson(info));
        }
        System.out.println(pageBean.getTotal());
    }
}
