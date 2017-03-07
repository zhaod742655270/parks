package com.hbyd.parks.supportsys.wsImpl;

import com.hbyd.parks.common.cxf.FaultBuilder;
import com.hbyd.parks.dto.supportsys.RegionDTO;
import com.hbyd.parks.ws.supportsys.RegionWS;
import org.apache.cxf.binding.soap.SoapFault;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.ws.soap.SOAPFaultException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/supportsys/applicationContext_CXF_Client.xml"})
public class RegionWSImplTest {
    @Resource
    private RegionWS regionWS;

    @Test
    public void testCrud() throws Exception {
//        保存
        RegionDTO dto = new RegionDTO();
        dto.setParentFK(null);
        dto.setValid(true);
        dto.setRegionDesc("region_desc_1");
        dto.setRegionName("region_name_1");
        RegionDTO saved = regionWS.save(dto);

        String id = saved.getId();

        RegionDTO dto2 = new RegionDTO();
        dto2.setParentFK(id);
        dto2.setValid(true);
        dto2.setRegionDesc("region_desc_2");
        dto2.setRegionName("region_name_2");
        RegionDTO saved2 = regionWS.save(dto2);

        String id2 = saved2.getId();

        Assert.assertTrue(saved2.getParentFK().equals(id));

//        删除
        try {
            regionWS.delByID(id);
        } catch (SOAPFaultException e) {
            SoapFault fault = (SoapFault) e.getCause();
            if(FaultBuilder.NOT_ALLOWED == fault.getStatusCode()){
                System.out.println(fault.getReason());
            }
        }

//        更新
        saved2.setParentFK(null);
        saved2.setValid(false);
        regionWS.update(saved2);

//        获取
        RegionDTO byID = regionWS.getByID(id2);
        Assert.assertEquals(null, byID.getParentFK());
        Assert.assertEquals(false, byID.isValid());
    }
}