package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.DeviceDescFiregroupDTO;
import com.hbyd.parks.ws.supportsys.DeviceDescFiregroupWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试：DeviceDescFiregroupWSImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class DeviceDescFiregroupWSImplTest {

    private Gson gson = new Gson();

    @Resource
    private DeviceDescFiregroupWS deviceDescFiregroupWS;

    @Test
    public void testFindAll(){
        List<DeviceDescFiregroupDTO> all = deviceDescFiregroupWS.findAll();

        if(all == null){
            System.out.println("没有记录");
        }else{
            System.out.println("共有" + all.size() + "条记录");
//            System.out.println(gson.toJson(all.get(0)));
            for (DeviceDescFiregroupDTO deviceDescFiregroupDTO : all) {
                System.out.println(gson.toJson(deviceDescFiregroupDTO));
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
        PageBeanEasyUI pageBean = deviceDescFiregroupWS.getPageBean(queryBean, hql_where, params);

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
        DeviceDescFiregroupDTO dto = new DeviceDescFiregroupDTO();
        dto.setName("name");

//      执行保存
        DeviceDescFiregroupDTO saved = deviceDescFiregroupWS.save(dto);

//      断言保存
        assert "name".equals(saved.getName());

//      更新
        saved.setName("name_new");

//      执行更新
        deviceDescFiregroupWS.update(saved);
        DeviceDescFiregroupDTO updated = deviceDescFiregroupWS.getByID(saved.getId());

//      断言更新
        assert "name_new".equals(updated.getName());

//      删除
        deviceDescFiregroupWS.delByID(saved.getId());
        assert deviceDescFiregroupWS.getByID(saved.getId()) == null;
    }
}
