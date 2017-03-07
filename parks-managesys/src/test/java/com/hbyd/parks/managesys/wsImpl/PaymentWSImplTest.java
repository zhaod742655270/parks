package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.dto.officesys.PaymentDTO;
import com.hbyd.parks.ws.officesys.PaymentWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/managesys/applicationContext_CXF_Client.xml"})
public class PaymentWSImplTest {

    @Resource
    private PaymentWS paymentWS;

    private int rowCntBefore;
    private int rowCntAfter;

//    @Before
//    public void init() {
//        rowCntBefore = paymentWS.getRowCount();
//    }

//    @After
//    public void cleanUp() {
//        rowCntAfter = paymentWS.getRowCount();
//        System.out.println("rows affected：" + (rowCntAfter - rowCntBefore));
//    }

    @Test//ok
    public void testCrud() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setContractName("test");
        ContractGatheringDTO dto1=new ContractGatheringDTO();
        dto1.setId("03189699-2B8A-4AE7-ACF4-A4EF1C8B11B8");
        Set<ContractGatheringDTO> sets=new HashSet<>();
        sets.add(dto1);
        paymentDTO.setContractGatherings(sets);

        PaymentDTO newDTO = paymentWS.save(paymentDTO);

    }


}
