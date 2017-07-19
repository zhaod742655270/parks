package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.ConQueryBean;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.ContractGatheringDao;
import com.hbyd.parks.dao.officesys.ContractGatheringLogDao;
import com.hbyd.parks.dao.officesys.ContractGatheringPostilDao;
import com.hbyd.parks.dao.officesys.PaymentDao;
import com.hbyd.parks.domain.officesys.ContractGathering;
import com.hbyd.parks.domain.officesys.ContractGatheringLog;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.dto.officesys.GatheringSumDTO;
import com.hbyd.parks.ws.officesys.ContractGatheringWS;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Len on 2016/3/3.
 */
public class ContractGatheringWSImpl extends BaseWSImpl<ContractGatheringDTO, ContractGathering> implements ContractGatheringWS {
    @Resource
    private ContractGatheringDao contractGatheringDao;

    @Resource
    private ContractGatheringLogDao contractGatheringLogDao;

    @Resource
    private ContractGatheringPostilDao contractGatheringPostilDao;

    @Resource
    private PaymentDao paymentDao;

    @Override
    public void delByID(String id) {
        ContractGathering contractGathering = contractGatheringDao.getById(id);
//        contractGathering.getContractGatheringPostil().setContractGathering(null);
//        如果有批注，先删除批注
        if (contractGathering.getContractGatheringPostil() != null) {
            String postilID = contractGathering.getContractGatheringPostil().getId();
            contractGathering.getContractGatheringPostil().setContractGathering(null);
            contractGatheringPostilDao.delete(postilID);
        }
        if(contractGathering.getContractGatheringLog()!=null){
            List<ContractGatheringLog> list=contractGathering.getContractGatheringLog();
            for(int i=0;i<list.size();i++){
               contractGatheringLogDao.delete(contractGathering.getContractGatheringLog().get(i));
            }
       }
        contractGathering.setPayments(null);
        contractGatheringDao.update(contractGathering);
    }

    @Override
    public void update(ContractGatheringDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");

        ContractGathering target = contractGatheringDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");


        String contractName=target.getContractName();
        String sheetName=target.getSheetName();
        if(contractName!=null&&contractName!="") {

            /*
            // Delete By Zhao_d 2017-07-03
            //由于不再使用合同名称来关联收款与付款合同了，因此不再需要同时更新付款合同的合同名称
        DetachedCriteria criteria = DetachedCriteria.forClass(Payment.class)
                .add(eq("contractName", contractName))
                .add(eq("sheetName", sheetName));
        List<Payment> list = criteria.getExecutableCriteria(baseDao.getCurrSession()).list();
//        List<PaymentDTO> lists=new ArrayList<>();
        PaymentDTO paymentDTO=new PaymentDTO();
        for(int i=0;i<list.size();i++){
           dozerMapper.map(paymentDTO, list.get(i));

            paymentDTO.setContractName(dto.getContractName());
            paymentDTO.setContractType(dto.getProjectType());
            dozerMapper.map(paymentDTO, list.get(i));
            paymentDao.update(list.get(i));
        }*/

            //更新只涉及普通属性
            dto.setContractGatheringPostil(null);

        /*if(Strings.isNullOrEmpty(dto.getLinkContractId())){
            dto.setLinkContractId(null);
         }

        if(!Strings.isNullOrEmpty(dto.getLinkContractId())) {
            target.setContractGathering(null);
        }*/

            dozerMapper.map(dto, target);

            contractGatheringDao.update(target);
        }

    }

    @Override
    public ContractGatheringDTO save(ContractGatheringDTO dto) {
        ValHelper.idNotExist(dto.getId());

//      保存只涉及普通属性
        dto.setContractGatheringPostil(null);

        /*
        if(Strings.isNullOrEmpty(dto.getLinkContractId())){
            dto.setLinkContractId(null);
        }*/

        ContractGathering target = dozerMapper.map(dto, ContractGathering.class);
        contractGatheringDao.save(target);
        return dozerMapper.map(target, ContractGatheringDTO.class);
    }

    @Override
    public PageBeanEasyUI getPageBeanByConQueryBean(ConQueryBean queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ContractGathering.class);

        criteria = getCriteria(criteria, queryBean);
        PageBeanEasyUI pageBeanEasyUI = contractGatheringDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public PageBeanEasyUI getAcceptancePageBean(ConQueryBean queryBean) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ContractGathering.class)
                                                    .add(eq("isValid", true))
                                                    .add(ge("sheetName", "2015"))
                                                    .add(in("projectType", new String[]{"弱电项目", "零星项目"}));

        criteria = getCriteria(criteria, queryBean);
        PageBeanEasyUI pageBeanEasyUI = contractGatheringDao.getPageBean(queryBean, criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public GatheringSumDTO getGatheringSum(ConQueryBean queryBean) {

        DetachedCriteria criteria = DetachedCriteria.forClass(ContractGathering.class)
                .setProjection(Projections.projectionList()
                                .add(Projections.rowCount(), "count")
                                .add(Projections.sum("amount"), "amountSum")
                                .add(Projections.sum("received"), "receivedSum")
                                .add(Projections.sum("receiveNo"), "receiveNoSum")
                                .add(Projections.sum("oncredit"), "oncreditSum")
                                .add(Projections.sum("remain"), "remainSum")
                                .add(Projections.sum("gross"), "grossSum")
                );
        criteria = getCriteria(criteria, queryBean);
        Criteria cri = criteria.getExecutableCriteria(baseDao.getCurrSession());
        cri.setResultTransformer(Transformers.aliasToBean(GatheringSumDTO.class));
//        Object result = cri.uniqueResult();
        GatheringSumDTO result = (GatheringSumDTO) cri.uniqueResult();
        return result;

    }


    @Override
    public List<ContractGatheringDTO> getContractNameBySheetAndType(String sheetName,String contractType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ContractGathering.class);
        if(!Strings.isNullOrEmpty(sheetName)){
            criteria.add(eq("sheetName", sheetName));
        }
        if(!Strings.isNullOrEmpty(contractType)){
            criteria.add(eq("projectType", contractType));
        }

        List<ContractGathering> list = criteria.getExecutableCriteria(baseDao.getCurrSession()).list();
        return getDTOList(list);
    }


    private DetachedCriteria getCriteria(DetachedCriteria criteria, ConQueryBean queryBean) {

           criteria.add(eq("isValid", true));

        if (!Strings.isNullOrEmpty(queryBean.getConNameQuery()))
            criteria.add(like("contractName", "%" + queryBean.getConNameQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getProjectTypeQuery()))
            criteria.add(like("projectType", "%" + queryBean.getProjectTypeQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getProjectManagerQuery()))
            criteria.add(like("projectManager", "%" + queryBean.getProjectManagerQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getComFirstQuery()))
            criteria.add(like("companyFirst", "%" + queryBean.getComFirstQuery() + "%"));

        if (queryBean.getAmountQuery() != null)
            criteria.add(eq("amount", queryBean.getAmountQuery()));

        if (queryBean.getReceivedQuery() != null)
            criteria.add(eq("received", queryBean.getReceivedQuery()));

        if (queryBean.getOncreditQuery() != null)
            criteria.add(eq("oncredit", queryBean.getOncreditQuery()));

        if (!Strings.isNullOrEmpty(queryBean.getSheetNameQuery()))
            criteria.add(like("sheetName", "%" + queryBean.getSheetNameQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getContractNoYDQuery()))
            criteria.add(like("contractNoYD", "%" + queryBean.getContractNoYDQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getContractNoQuery()))
            criteria.add(like("contractNo", "%" + queryBean.getContractNoQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getCompanySecondQuery()))
            criteria.add(like("companySecond", "%" + queryBean.getCompanySecondQuery() + "%"));

        if (!Strings.isNullOrEmpty(queryBean.getSignDateQuery()))
            criteria.add(eq("signDate", queryBean.getSignDateQuery()));

        if (queryBean.getReceiveNoQuery() != null)
            criteria.add(eq("receiveNo", queryBean.getReceiveNoQuery()));

        if (queryBean.getRemainQuery() != null)
            criteria.add(eq("remain", queryBean.getRemainQuery()));

        if (queryBean.getGrossQuery() != null)
            criteria.add(eq("gross", queryBean.getGrossQuery()));

        if (!Strings.isNullOrEmpty(queryBean.getProjectDirectorQuery()))
            criteria.add(like("projectDirector", "%" + queryBean.getProjectDirectorQuery() + "%"));

        if (queryBean.getStampQuery() != null)
            criteria.add(eq("stamp", queryBean.getStampQuery()));

        if (queryBean.getIsCompletedQuery() != null)
            criteria.add(eq("isCompleted", queryBean.getIsCompletedQuery()));

        if(!Strings.isNullOrEmpty(queryBean.getLinkContract())){
            criteria.createAlias("contractGathering","contractGathering").add(eq("contractGathering.id",queryBean.getLinkContract()));
        }

        return criteria;
    }
}
