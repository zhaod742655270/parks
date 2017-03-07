package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.common.model.ConQueryBean;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.dto.officesys.GatheringSumDTO;
import com.hbyd.parks.ws.officesys.ContractGatheringWS;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/managesys/applicationContext_CXF_Client.xml"})
public class ContractGatheringWSImplTest {

    @Resource
    private ContractGatheringWS contractGatheringWS;

    private int rowCntBefore;
    private int rowCntAfter;

    @Before
    public void init() {
        rowCntBefore = contractGatheringWS.getRowCount();
    }

    @After
    public void cleanUp() {
        rowCntAfter = contractGatheringWS.getRowCount();
        System.out.println("rows affected：" + (rowCntAfter - rowCntBefore));
    }

    @Test//ok
    public void testCrud() {
        ContractGatheringDTO gatheringDTO = new ContractGatheringDTO();
        gatheringDTO.setContractName("test");

        ContractGatheringDTO newDTO = contractGatheringWS.save(gatheringDTO);

        contractGatheringWS.update(newDTO);
        contractGatheringWS.delByID(newDTO.getId());
    }


    @Test//OK
    public void testGetPageBean() {
        ConQueryBean queryBean = new ConQueryBean();
        queryBean.setPage(1);
        queryBean.setRows(10);
        queryBean.setSorts(new String[]{"projectSn"});
        queryBean.setOrders(new String[]{"desc"});

        String hql_where = "where 1=1";
        List params = new ArrayList();
        params.add("3971");


        PageBeanEasyUI pageBean = contractGatheringWS.getPageBeanByConQueryBean(queryBean);


        for (Object o : pageBean.getRows()) {
            ContractGatheringDTO role = (ContractGatheringDTO) o;
            System.out.println(role.getContractName());
            System.out.println(role.getContractNo());
        }
        System.out.println(pageBean.getTotal());
        System.out.println(pageBean.getQueryBean().getSorts());
    }

    @Test//OK
    public void testGetSum() {
        ConQueryBean queryBean = new ConQueryBean();
        queryBean.setPage(1);
        queryBean.setRows(10);
        queryBean.setSorts(new String[]{"projectSn"});
        queryBean.setOrders(new String[]{"desc"});

        GatheringSumDTO sumDTO = contractGatheringWS.getGatheringSum(queryBean);
    }
}
