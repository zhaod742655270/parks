package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.DeviceDTO;
import com.hbyd.parks.ws.supportsys.DeviceWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试人员服务
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class DeviceWSImplTest {

    private Gson gson = new Gson();

    @Resource
    private DeviceWS deviceWS;

    @Test
    public void testFindAll(){
        List<DeviceDTO> all = deviceWS.findAll();

        if(all == null){
            System.out.println("没有记录");
        }else{
            System.out.println("共有" + all.size() + "条记录");
//            System.out.println(gson.toJson(all.get(0)));
            for (DeviceDTO deviceDTO : all) {
                System.out.println(gson.toJson(deviceDTO));
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
        PageBeanEasyUI pageBean = deviceWS.getPageBean(queryBean, hql_where, params);

        if(pageBean.getTotal() == 0){
            System.out.println("没有符合条件的记录");
        }else{
            for (Object o : pageBean.getRows()) {
                System.out.println(gson.toJson(o));
            }
        }
    }

    @Test
    public void testGetById(){
        DeviceDTO byID = deviceWS.getByID("0017EF4B-DE22-42C0-90A5-452B2B1A2CB7");
        System.out.println(gson.toJson(byID));
    }

    @Test
    public void testCrud(){
        String roomFK = "509e19cd-0f2c-4363-979e-4d4d045866a7";
        String deptFK = "10";
        String roomFKNew = "99f230c5-9fdb-4ffb-88a6-cafbb530314d";
        String deptFKNew = "100";

//      测试保存
        DeviceDTO dto = new DeviceDTO();

//      非空约束
        dto.setOneType("one_type_test");
        dto.setDeviceId("device_id_test");
        dto.setState("state_test");

        dto.setRoomFK(roomFK);
        dto.setDeptFK(deptFK);

//      外键关联
        dto.setTypeId("1");//设备类型：读头
        dto.setParentId("00207AD1-085F-4A59-88FF-807280D7103E");
        dto.setRegionId("67b44421-3a14-4371-9ba6-987c98b89094");

        DeviceDTO saved = deviceWS.save(dto);
        System.out.println(saved.getId());

//      断言保存
        assert roomFK.equals(saved.getRoomFK());
        assert deptFK.equals(saved.getDeptFK());

//      更新
        saved.setOneType("one_type_new");
        saved.setDeviceId("device_id_new");
        saved.setState("state_new");

        saved.setRoomFK(roomFKNew);
        saved.setDeptFK(deptFKNew);

        deviceWS.update(saved);

//      断言更新
        DeviceDTO updated = deviceWS.getByID(saved.getId());
        assert roomFKNew.equals(updated.getRoomFK());
        assert deptFKNew.equals(updated.getDeptFK());

//      删除
//        deviceWS.delByID(saved.getId());
//      断言删除
//        assert deviceWS.getByID(saved.getId()) == null;

//        伪删除
//        deviceWS.delFake(saved.getId());
    }

    @Test
    public void testGet(){
        DeviceDTO dto = deviceWS.getByID("0017EF4B-DE22-42C0-90A5-452B2B1A2CB7");
        System.out.println(gson.toJson(dto));
    }
}
