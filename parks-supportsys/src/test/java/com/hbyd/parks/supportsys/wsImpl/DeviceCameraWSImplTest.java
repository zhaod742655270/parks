package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.DeviceCameraDTO;
import com.hbyd.parks.ws.supportsys.DeviceCameraWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试：DeviceCameraWSImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class DeviceCameraWSImplTest {

    private Gson gson = new Gson();

    @Resource
    private DeviceCameraWS deviceCameraWS;

    @Test
    public void testFindAll(){
        List<DeviceCameraDTO> all = deviceCameraWS.findAll();

        if(all == null){
            System.out.println("没有记录");
        }else{
            System.out.println("共有" + all.size() + "条记录");
//            System.out.println(gson.toJson(all.get(0)));
            for (DeviceCameraDTO deviceCameraDTO : all) {
                System.out.println(gson.toJson(deviceCameraDTO));
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
        PageBeanEasyUI pageBean = deviceCameraWS.getPageBean(queryBean, hql_where, params);

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
        DeviceCameraDTO c = new DeviceCameraDTO();

//      外键关联
        c.setParentId("00207AD1-085F-4A59-88FF-807280D7103E");//Camera DiscVal - 5
        c.setTypeId("10");
        c.setRegionId("f4fa7f01-fbf7-43e0-ad7c-fa515acce756");

//      非空属性
        c.setOneType("one_test");
        c.setDeviceId("191919");
        c.setState("1");

        c.setChannelNum("channel_test");
        c.setHaveHolder("holder_test");
        DeviceCameraDTO saved = deviceCameraWS.save(c);
        System.out.println(saved.getId());

        assert "one_test".equals(saved.getOneType());
        assert "channel_test".equals(saved.getChannelNum());

//      更新
        saved.setChannelNum("channel_test_new");
        saved.setOneType("one_test_new");
        deviceCameraWS.update(saved);

//      查找
        DeviceCameraDTO updated = deviceCameraWS.getByID(saved.getId());
        assert "one_test_new".equals(updated.getOneType());
        assert "channel_test_new".equals(updated.getChannelNum());
        System.out.println(gson.toJson(updated));

//      删除
        deviceCameraWS.delByID(saved.getId());
        assert deviceCameraWS.getByID(saved.getId()) == null;
    }
}
