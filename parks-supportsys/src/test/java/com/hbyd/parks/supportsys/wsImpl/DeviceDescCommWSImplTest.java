package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.DeviceDescCommDTO;
import com.hbyd.parks.ws.supportsys.DeviceDescCommWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试：DeviceDescCommWSImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class DeviceDescCommWSImplTest {

    private Gson gson = new Gson();

    @Resource
    private DeviceDescCommWS deviceDescCommWS;

    @Test
    public void testFindAll(){
        List<DeviceDescCommDTO> all = deviceDescCommWS.findAll();

        if(all == null){
            System.out.println("没有记录");
        }else{
            System.out.println("共有" + all.size() + "条记录");
//            System.out.println(gson.toJson(all.get(0)));
            for (DeviceDescCommDTO deviceDescCommDTO : all) {
                System.out.println(gson.toJson(deviceDescCommDTO));
            }
        }
    }

    @Test
    public void testGetPageBean(){
        QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
        queryBean.setOrder("asc");
        queryBean.setSort("id");
        queryBean.setPage(1);
        queryBean.setRows(10);

        String hql_where = "";
        Object[] params = new Object[]{};
        PageBeanEasyUI pageBean = deviceDescCommWS.getPageBean(queryBean, hql_where, params);

        if(pageBean.getTotal() == 0){
            System.out.println("没有符合条件的记录");
        }else{
            for (Object o : pageBean.getRows()) {
                System.out.println(gson.toJson(o));
            }
        }
    }

    @Test
    public void testCrud(){
//      保存
        DeviceDescCommDTO dto = new DeviceDescCommDTO();
        dto.setIp("ip");
        dto.setPort(1);
        dto.setGateway("ga");

//      执行保存
        DeviceDescCommDTO saved = deviceDescCommWS.save(dto);

//      断言保存
        assert "ip".equals(saved.getIp());
        assert 1 == saved.getPort();
        assert "ga".equals(saved.getGateway());

//      更新
        saved.setIp("ip_new");
        saved.setPort(2);
        saved.setGateway("ga_new");

//      执行更新
        deviceDescCommWS.update(saved);
        DeviceDescCommDTO updated = deviceDescCommWS.getByID(saved.getId());

//      断言更新
        assert "ip_new".equals(updated.getIp());
        assert 2 == updated.getPort();
        assert "ga_new".equals(updated.getGateway());

//      删除
        deviceDescCommWS.delByID(saved.getId());
        assert deviceDescCommWS.getByID(saved.getId()) == null;
    }
}
