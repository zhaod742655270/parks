package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.DeviceDescTypeDTO;
import com.hbyd.parks.ws.supportsys.DeviceDescTypeWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试：DeviceDescTypeWSImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class DeviceDescTypeWSImplTest {

    private Gson gson = new Gson();

    @Resource
    private DeviceDescTypeWS deviceDescTypeWS;

    @Test
    public void testFindAll(){
        List<DeviceDescTypeDTO> all = deviceDescTypeWS.findAll();

        if(all == null){
            System.out.println("没有记录");
        }else{
            System.out.println("共有" + all.size() + "条记录");
//            System.out.println(gson.toJson(all.get(0)));
            for (DeviceDescTypeDTO deviceDescTypeDTO : all) {
                System.out.println(gson.toJson(deviceDescTypeDTO));
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
        PageBeanEasyUI pageBean = deviceDescTypeWS.getPageBean(queryBean, hql_where, params);

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
        String resAppFK = "24c6869c477ca5e701477ca81eb80000";//支撑平台
        String resAppFKNew = "24c6869c482eb73801482f2319720000";//考勤系统

//      保存
        DeviceDescTypeDTO dto = new DeviceDescTypeDTO();
        dto.setOneType("oneType");
        dto.setResAppFk(resAppFK);

//      执行保存
        DeviceDescTypeDTO saved = deviceDescTypeWS.save(dto);
//      断言保存
        assert "oneType".equals(saved.getOneType());
        assert resAppFK.equals(saved.getResAppFk());

//      更新
        saved.setOneType("oneType_new");
        saved.setResAppFk(resAppFKNew);

//      执行更新
        deviceDescTypeWS.update(saved);
        DeviceDescTypeDTO updated = deviceDescTypeWS.getByID(saved.getId());
//      断言更新
        assert "oneType_new".equals(updated.getOneType());
        assert resAppFKNew.equals(updated.getResAppFk());

//      删除
        deviceDescTypeWS.delByID(saved.getId());
        assert deviceDescTypeWS.getByID(saved.getId()) == null;
    }
}
