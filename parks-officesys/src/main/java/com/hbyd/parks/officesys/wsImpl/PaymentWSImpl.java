package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.PaymentQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.ContractGatheringDao;
import com.hbyd.parks.dao.officesys.PaymentDao;
import com.hbyd.parks.dao.officesys.PaymentLogDao;
import com.hbyd.parks.dao.officesys.PaymentPostilDao;
import com.hbyd.parks.domain.officesys.ContractGathering;
import com.hbyd.parks.domain.officesys.Payment;
import com.hbyd.parks.domain.officesys.PaymentLog;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.dto.officesys.PaymentDTO;
import com.hbyd.parks.dto.officesys.PaymentSumDTO;
import com.hbyd.parks.ws.officesys.PaymentWS;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Created by zhang_f on 2016/3/16.
 */
public class PaymentWSImpl extends BaseWSImpl<PaymentDTO, Payment> implements PaymentWS {
    @Resource
    private PaymentDao paymentDao;

    @Resource
    private PaymentLogDao paymentLogDao;

    @Resource
    private PaymentPostilDao paymentPostilDao;

    @Resource
    private ContractGatheringDao contractGatheringDao;

    @Override
    public void delByID(String id) {
       Payment payment = paymentDao.getById(id);
        if (payment.getPaymentPostil() != null) {
            String paymentID = payment.getPaymentPostil().getId();
            payment.getPaymentPostil().setPayment(null);
            paymentPostilDao.delete(paymentID);
        }

        if(payment.getPaymentLogs()!=null){
            List<PaymentLog> list=payment.getPaymentLogs();
            for(int i=0;i<list.size();i++){
                paymentLogDao.delete(payment.getPaymentLogs().get(i));
            }
        }
        payment.setContractGatherings(null);
        paymentDao.update(payment);
    }

    @Override
    public void update(PaymentDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");

        Set<ContractGatheringDTO> contractGatherings = dto.getContractGatherings();
        dto.setContractGatherings(null);

        Payment target = paymentDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");

        dozerMapper.map(dto, target);

////      更新只涉及普通属性
//        dto.setContractGatheringPostil(null);

        if(contractGatherings == null || contractGatherings.size()==0){//没有收款合同
            target.setContractGatherings(null);
        }else{//收款合同一定是存在的
            Iterable<ContractGathering> contracts = Iterables.transform(contractGatherings, new Function<ContractGatheringDTO, ContractGathering>() {
                @Nullable
                @Override
                public ContractGathering apply(ContractGatheringDTO input) {
                    return contractGatheringDao.loadById(input.getId());
                }
            });
            target.setContractGatherings(Sets.newHashSet(contracts));
        }

        paymentDao.update(target);
    }

    @Override
    public PaymentDTO save(PaymentDTO dto) {
        ValHelper.idNotExist(dto.getId());

////      保存只涉及普通属性
//        dto.setContractGatheringPostil(null);

        Set<ContractGatheringDTO> contractGatherings = dto.getContractGatherings();

        dto.setContractGatherings(null);

        Payment target = dozerMapper.map(dto, Payment.class);

        if(contractGatherings == null || contractGatherings.size()==0){//没有收款合同
            target.setContractGatherings(null);
        }else{//收款合同一定是存在的
            Iterable<ContractGathering> contracts = Iterables.transform(contractGatherings, new Function<ContractGatheringDTO, ContractGathering>() {
                @Nullable
                @Override
                public ContractGathering apply(ContractGatheringDTO input) {
                    return contractGatheringDao.getById(input.getId());
                }
            });
            target.setContractGatherings(Sets.newHashSet(contracts));
        }
        paymentDao.save(target);
        return dozerMapper.map(target, PaymentDTO.class);
    }

    @Override
    public PageBeanEasyUI getPageBeanByPaymentQuery(PaymentQuery queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Payment.class);

        criteria = getCriteria(criteria, queryBean);
        PageBeanEasyUI pageBeanEasyUI = paymentDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public PaymentSumDTO getPaymentSum(PaymentQuery queryBean) {

        DetachedCriteria criteria = DetachedCriteria.forClass(Payment.class)
                .setProjection(Projections.projectionList()
                                .add(Projections.rowCount(), "count")
                                .add(Projections.sum("contractSum"), "amountSum")
                                .add(Projections.sum("payment"), "paymentSum")
                                .add(Projections.sum("paymentNo"), "paymentNoSum")

                );
        criteria = getCriteria(criteria, queryBean);
        Criteria cri = criteria.getExecutableCriteria(baseDao.getCurrSession());
        cri.setResultTransformer(Transformers.aliasToBean(PaymentSumDTO.class));
        PaymentSumDTO result = (PaymentSumDTO) cri.uniqueResult();
        return result;

    }

    private DetachedCriteria getCriteria(DetachedCriteria criteria, PaymentQuery queryBean) {

            criteria.add(eq("isValid", true));

        if (!Strings.isNullOrEmpty(queryBean.getContractNO()))
            criteria.add(eq("contractNO", queryBean.getContractNO()));

        if (!Strings.isNullOrEmpty(queryBean.getContractName()))
            criteria.createAlias("contractGatherings","contractGatherings")
                    .add(Restrictions.eq("contractGatherings.contractName",queryBean.getContractName()));

        if (!Strings.isNullOrEmpty(queryBean.getPersonQuery()))
            criteria.add(like("purchasePerson", "%" + queryBean.getPersonQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getContentQuery()))
            criteria.add(like("purchaseContent", "%" + queryBean.getContentQuery() + "%"));

        if (queryBean.getContractSumQuery() != null)
            criteria.add(eq("contractSum", queryBean.getContractSumQuery()));

        if (!Strings.isNullOrEmpty(queryBean.getCompanySecondQuery()))
            criteria.add(like("companySecond", "%" + queryBean.getCompanySecondQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getContractTypeQuery()))
            criteria.add(eq("contractType", queryBean.getContractTypeQuery()));

        if (!Strings.isNullOrEmpty(queryBean.getSheetNameQuery()))
            criteria.add(eq("sheetName", queryBean.getSheetNameQuery()));

        return criteria;
    }

    public List<PaymentDTO> getPaymentListByGathering(String id){
        ContractGathering gathering = contractGatheringDao.getById(id);
        List<Payment> list = gathering.getPayments();
        return getDTOList(list);
    }
}
