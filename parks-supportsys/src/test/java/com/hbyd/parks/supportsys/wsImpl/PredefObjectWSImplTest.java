package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.supportsys.ObjectTypeWS;
import com.hbyd.parks.ws.supportsys.PredefObjectWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class PredefObjectWSImplTest {

    @Resource
    private PredefObjectWS predefObjectWS;

    @Resource
    private ObjectTypeWS objectTypeWS;

    @Test
    public void testPageBean(){
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setSorts(new String[]{"id"});
        queryBean.setOrders(new String[]{"asc"});
        queryBean.setPage(1);
        queryBean.setRows(2);

        String hql_where = "where preObj.id = ?";
        Object[] params = Arrays.asList("10").toArray();
        PageBeanEasyUI pageBean = objectTypeWS.getPageBean(queryBean, hql_where, params);

//        String hql = "from ObjectType t where t.object.id = ?";
//        Object[] params = Arrays.asList("10").toArray();
//        PageBeanEasyUI pageBean = objectTypeWS.getPageBeanByHQL(queryBean, hql, params);

        for (Object o : pageBean.getRows()) {
            System.out.println(new Gson().toJson(o));
        }
    }
}