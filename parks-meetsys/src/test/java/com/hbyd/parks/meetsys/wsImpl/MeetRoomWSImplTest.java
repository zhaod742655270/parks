package com.hbyd.parks.meetsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.meetsys.MeetRoomDTO;
import com.hbyd.parks.ws.meetsys.MeetRoomWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试：MeetRoomWSImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/meetsys/applicationContext_CXF_Client.xml"})
public class MeetRoomWSImplTest {

    private Gson gson = new Gson();

    @Resource
    private MeetRoomWS meetRoomWS;

    @Test
    public void testFindAll(){
        List<MeetRoomDTO> all = meetRoomWS.findAll();

        if(all == null){
            System.out.println("没有记录");
        }else{
            System.out.println("共有" + all.size() + "条记录");
//            System.out.println(gson.toJson(all.get(0)));
            for (MeetRoomDTO meetRoomDTO : all) {
                System.out.println(gson.toJson(meetRoomDTO));
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
        PageBeanEasyUI pageBean = meetRoomWS.getPageBean(queryBean, hql_where, params);

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
        String regionId = "67b44421-3a14-4371-9ba6-987c98b89094";
        String regionIdNew = "f4fa7f01-fbf7-43e0-ad7c-fa515acce756";

//      保存
        MeetRoomDTO dto = new MeetRoomDTO();
        dto.setCapacity("1");
        dto.setDescInfo("info_test");
        dto.setName("name_test");

        dto.setRegionId(regionId);

//      执行保存
        MeetRoomDTO saved = meetRoomWS.save(dto);

//      断言保存
        assert "1".equals(saved.getCapacity());
        assert "info_test".equals(saved.getDescInfo());
        assert "name_test".equals(saved.getName());
        assert regionId.equals(saved.getRegionId());

//      更新
        saved.setCapacity("1New");
        saved.setDescInfo("info_testNew");
        saved.setName("name_testNew");
        saved.setRegionId(regionIdNew);

//      执行更新
        meetRoomWS.update(saved);
        MeetRoomDTO updated = meetRoomWS.getByID(saved.getId());

//      断言更新
        "1New".equals(updated.getCapacity());
        "info_testNew".equals(updated.getDescInfo());
        "name_testNew".equals(updated.getName());
        regionIdNew.equals(updated.getRegionId());

//      删除
//        meetRoomWS.delByID(saved.getId());
//        assert meetRoomWS.getByID(saved.getId()) == null;
    }
}
