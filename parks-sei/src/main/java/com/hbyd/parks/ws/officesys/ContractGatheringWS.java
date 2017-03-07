package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.ConQueryBean;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.dto.officesys.GatheringSumDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.List;


/**
 * @author ren_xt
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({ContractGatheringDTO.class})
public interface ContractGatheringWS extends BaseWS<ContractGatheringDTO>, RecoverableWS {
    public PageBeanEasyUI getPageBeanByConQueryBean(ConQueryBean queryBean);
    public PageBeanEasyUI getAcceptancePageBean(ConQueryBean queryBean);
    public GatheringSumDTO getGatheringSum(ConQueryBean queryBean);

    public List<ContractGatheringDTO> getContractNameBySheetAndType(String sheetName,String contractType);


}
