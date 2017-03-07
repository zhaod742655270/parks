package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.AcceptanceQuery;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.officesys.AcceptanceDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/4/25
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({AcceptanceDTO.class})
public interface AcceptanceWS extends BaseWS<AcceptanceDTO>, RecoverableWS {

    public PageBeanEasyUI getPageBeanByContractID(String id,AcceptanceQuery page);

    public List<ContractGatheringDTO> getProjectByYear(String year);

    public List<UserDTO> getPurchaser();

    public List<UserDTO> getProjectManager();

    public String getUserFK(String userName);

    public String getContractFK(String contractName,String sheetName,String contractNo);

}
