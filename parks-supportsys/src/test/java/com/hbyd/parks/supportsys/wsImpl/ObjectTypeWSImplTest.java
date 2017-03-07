package com.hbyd.parks.supportsys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.dto.supportsys.ObjectTypeDTO;
import com.hbyd.parks.dto.supportsys.PredefObjectDTO;
import com.hbyd.parks.ws.supportsys.ObjectTypeWS;
import com.hbyd.parks.ws.supportsys.PredefObjectWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class ObjectTypeWSImplTest {
    @Resource
    private ObjectTypeWS objectTypeWS;
    @Resource
    private PredefObjectWS predefObjectWS;

    @Test
    public void testFindAllPredefObject(){
        for (PredefObjectDTO pre : predefObjectWS.findAll()) {
            System.out.println(pre.getId());
            System.out.println(pre.getName());
        }
    }

    @Test
    public void testFindAllObjectType(){
        for (ObjectTypeDTO od : objectTypeWS.findAll()) {
            System.out.println(od.getId());
            System.out.println(od.getTypeName());
        }
    }

    @Test
    public void testCrudObjectType(){
//      保存
        ObjectTypeDTO dto = new ObjectTypeDTO();
        dto.setTypeName("test_type_name");
        dto.setTypeCode("test_type_code");
        dto.setValid(true);
        dto.setObjectFK(predefObjectWS.getByID("10").getId());
        ObjectTypeDTO saved = objectTypeWS.save(dto);

        System.out.println(saved.getId());

//      更新
        saved.setObjectFK("17");
        saved.setTypeName("test_2_name");
        objectTypeWS.update(saved);

//      查询
        ObjectTypeDTO byID = objectTypeWS.getByID(saved.getId());
        System.out.println(new Gson().toJson(byID));

//      删除
        objectTypeWS.delFake(saved.getId());
    }
}