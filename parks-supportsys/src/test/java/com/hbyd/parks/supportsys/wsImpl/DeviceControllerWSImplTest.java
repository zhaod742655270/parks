package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.DeviceControllerDTO;
import com.hbyd.parks.ws.supportsys.DeviceControllerWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试：DeviceControllerWSImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class DeviceControllerWSImplTest {

    private Gson gson = new Gson();

    @Resource
    private DeviceControllerWS deviceControllerWS;

    @Test
    public void testFindAll(){
        List<DeviceControllerDTO> all = deviceControllerWS.findAll();

        if(all == null){
            System.out.println("没有记录");
        }else{
            System.out.println("共有" + all.size() + "条记录");
//            System.out.println(gson.toJson(all.get(0)));
            for (DeviceControllerDTO deviceControllerDTO : all) {
                System.out.println(gson.toJson(deviceControllerDTO));
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
        PageBeanEasyUI pageBean = deviceControllerWS.getPageBean(queryBean, hql_where, params);

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
        String parentId = "0017EF4B-DE22-42C0-90A5-452B2B1A2CB7";
        String devTypeId = "1";
        String regionId = "f4fa7f01-fbf7-43e0-ad7c-fa515acce756";

        String parentIdNew = "000B7722-0378-4C04-94B9-7CCB0D48123E";
        String devTypeIdNew = "10";
        String regionIdNew = "67b44421-3a14-4371-9ba6-987c98b89094";

//      保存
        DeviceControllerDTO dto = new DeviceControllerDTO();

//      关联字段
        dto.setParentId(parentId);//上级设备
        dto.setTypeId(devTypeId);//设备类型
        dto.setRegionId(regionId);//区域

//      普通字段
//        非空
        dto.setOneType("one_type");
        dto.setDeviceId("device_id");
        dto.setState("state");
//        特有
        dto.setDoorCount(1);

//      执行保存
        DeviceControllerDTO saved = deviceControllerWS.save(dto);
        System.out.println(saved.getId());

//      断言
//        普通非空
        assert "one_type".equals(saved.getOneType());
//        普通特有
        assert 1 == dto.getDoorCount();
//        关联字段
        assert parentId.equals(saved.getParentId());
        assert regionId.equals(saved.getRegionId());
        assert devTypeId.equals(saved.getTypeId());

//      更新
//        普通非空
        saved.setOneType("one_type_new");
//        普通特有
        saved.setDoorCount(2);
//        关联字段
        saved.setParentId(parentIdNew);
        saved.setRegionId(regionIdNew);
        saved.setTypeId(devTypeIdNew);

//      执行更新
        deviceControllerWS.update(saved);

//      执行查找
        DeviceControllerDTO updated = deviceControllerWS.getByID(saved.getId());
//      断言
//        普通非空
        assert "one_type_new".equals(updated.getOneType());
//        普通特有
        assert 2 == updated.getDoorCount();
//        关联字段
        assert parentIdNew.equals(updated.getParentId());
        assert regionIdNew.equals(updated.getRegionId());
        assert devTypeIdNew.equals(updated.getTypeId());

//      执行删除
        deviceControllerWS.delByID(saved.getId());
        assert deviceControllerWS.getByID(saved.getId()) == null;
    }

}
