package com.hbyd.parks.ws.officesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.RecordQueryBean;
import com.hbyd.parks.dto.officesys.ProjectRecordDTO;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/4/25
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({ProjectRecordDTO.class})
public interface ProjectRecordWS extends BaseWS<ProjectRecordDTO>, RecoverableWS {

    public PageBeanEasyUI getPageBeanByRecordBean(RecordQueryBean queryBean);


}
