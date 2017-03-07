package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.PaymentLogQueryBean;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.PaymentLogDao;
import com.hbyd.parks.domain.officesys.Payment;
import com.hbyd.parks.domain.officesys.PaymentLog;
import com.hbyd.parks.dto.officesys.PaymentDTO;
import com.hbyd.parks.dto.officesys.PaymentLogDTO;
import com.hbyd.parks.ws.officesys.PaymentLogWS;
import org.hibernate.criterion.DetachedCriteria;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Len on 2016/3/3.
 */
public class PaymentLogWSImpl extends BaseWSImpl<PaymentLogDTO, PaymentLog> implements PaymentLogWS {
    @Resource
    private PaymentLogDao paymentLogDao;

    @Override
    public PaymentLogDTO save(PaymentLogDTO dto) {
        ValHelper.idNotExist(dto.getId());
        PaymentDTO paymentDTO=new PaymentDTO();
        paymentDTO.setId(dto.getParentFK());
        paymentDTO.setContractNO(dto.getContractNo());
        PaymentLog target = dozerMapper.map(dto, PaymentLog.class);
        Payment payment=dozerMapper.map(paymentDTO,Payment.class);
        target.setContractPayment(payment);
        paymentLogDao.save(target);
        return dozerMapper.map(target, PaymentLogDTO.class);
    }


    @Override
    public PageBeanEasyUI getPageBeanByLogQueryBean(PaymentLogQueryBean queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(PaymentLog.class)
                           .createAlias("contractPayment", "c" );
        criteria.add(eq("c.id", queryBean.getPaymentID()));

        PageBeanEasyUI pageBeanEasyUI = paymentLogDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public PageBeanEasyUI getPageBeanByDate(PaymentLogQueryBean queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(PaymentLog.class)
                                           .createAlias("contractPayment", "c");


        if (!Strings.isNullOrEmpty(queryBean.getContractName()))
        criteria.add(like("c.contractName", "%" + queryBean.getContractName() + "%"));

        if (queryBean.getContractSn()!=null)
        criteria.add(eq("c.contractSn", queryBean.getContractSn()));

        criteria.add(ge("date", queryBean.getBeginDate()));
        criteria.add(le("date", queryBean.getEndDate()));

        PageBeanEasyUI pageBeanEasyUI = paymentLogDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }



}
