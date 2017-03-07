package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.DeviceDescRelationDTO;
import com.hbyd.parks.ws.supportsys.DeviceDescRelationWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试：DeviceDescRelationWSImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class DeviceDescRelationWSImplTest {

    private Gson gson = new Gson();

    @Resource
    private DeviceDescRelationWS deviceDescRelationWS;

    @Test
    public void testFindAll(){
        List<DeviceDescRelationDTO> all = deviceDescRelationWS.findAll();

        if(all == null){
            System.out.println("没有记录");
        }else{
            System.out.println("共有" + all.size() + "条记录");
//            System.out.println(gson.toJson(all.get(0)));
            for (DeviceDescRelationDTO deviceDescRelationDTO : all) {
                System.out.println(gson.toJson(deviceDescRelationDTO));
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
        PageBeanEasyUI pageBean = deviceDescRelationWS.getPageBean(queryBean, hql_where, params);

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
        String deviceFK = "0017EF4B-DE22-42C0-90A5-452B2B1A2CB7";

        String deptFK = "10";
        String deptFKNew = "100";
        String roomFK = "8d495708-2697-416d-94aa-41fa276aba65";
        String roomFKNew = "91e1bc72-cdb9-451e-8a9b-4ec4d193ad56";

//      保存
        DeviceDescRelationDTO dto = new DeviceDescRelationDTO();
        dto.setId(deviceFK);//设备关系的ID 采用设备的ID
        dto.setDeptFK(deptFK);
        dto.setRoomFK(roomFK);

//      执行保存
        DeviceDescRelationDTO saved = deviceDescRelationWS.save(dto);

//      断言保存
        assert deptFK.equals(saved.getDeptFK());
        assert roomFK.equals(saved.getRoomFK());

//      更新
        saved.setDeptFK(deptFKNew);
        saved.setRoomFK(roomFKNew);

//      执行更新
        deviceDescRelationWS.update(saved);
        DeviceDescRelationDTO updated = deviceDescRelationWS.getByID(saved.getId());

//      断言更新
        assert deptFKNew.equals(updated.getDeptFK());
        assert roomFKNew.equals(updated.getRoomFK());

//      删除
        deviceDescRelationWS.delByID(saved.getId());
        assert deviceDescRelationWS.getByID(saved.getId()) == null;
    }

    @Test
    public void testFoo(){
        DeviceDescRelationDTO dto = new DeviceDescRelationDTO();
        dto.setId("--");
        DeviceDescRelationDTO saved = deviceDescRelationWS.save(dto);
    }
}
