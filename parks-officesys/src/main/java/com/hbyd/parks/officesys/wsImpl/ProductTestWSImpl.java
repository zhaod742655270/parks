package com.hbyd.parks.officesys.wsImpl;

import com.google.common.base.Strings;
import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.ProductTestQuery;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.ProductTestDao;
import com.hbyd.parks.domain.officesys.ProductTest;
import com.hbyd.parks.dto.officesys.ProductTestDTO;
import com.hbyd.parks.ws.officesys.ProductTestWS;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by Zhao_d on 2016/12/28.
 */
public class ProductTestWSImpl extends BaseWSImpl<ProductTestDTO,ProductTest> implements ProductTestWS {

    @Resource
    private ProductTestDao productTestDao;

    @Override
    public PageBeanEasyUI getPageBeanByQueryBean(ProductTestQuery query){
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductTest.class);
        //添加查询条件
        criteria.add(eq("isValid",true));
        if(!Strings.isNullOrEmpty(query.getIdQuery())){
            criteria.add(eq("id",query.getIdQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getProjectNameQuery())){
            criteria.add(like("projectName",query.getProjectNameQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getProductNameQuery())){
            criteria.add(like("productName",query.getProductNameQuery()));
        }
        if(!Strings.isNullOrEmpty(query.getRegisterPersonQuery())){
            criteria.createAlias("registerPerson","registerPerson")
                    .add(eq("registerPerson.id",query.getRegisterPersonQuery()));
        }
        /*if(!Strings.isNullOrEmpty(query.getTestPersonQuery())){
            criteria.add(like("testPerson",query.getTestPersonQuery()));
        }*/
        if(!Strings.isNullOrEmpty(query.getNumberQuery())){
            criteria.add(like("number",query.getNumberQuery()));
        }
        if (!Strings.isNullOrEmpty(query.getRegDateBegQuery())) {
            criteria.add(ge("registerDate", query.getRegDateBegQuery()));
        }
        if (!Strings.isNullOrEmpty(query.getRegDateEndQuery())) {
            criteria.add(le("registerDate", query.getRegDateEndQuery()));
        }
        //指派人与提交人，用于权限限制
        if(!Strings.isNullOrEmpty(query.getAssignPersonQuery()) || !Strings.isNullOrEmpty(query.getCheckPersonQuery())){
            Disjunction dis = Restrictions.disjunction();
            dis.add(eq("assignPerson.id",query.getAssignPersonQuery()));
            dis.add(eq("registerPerson.id",query.getCheckPersonQuery()));
            criteria.add(dis);
        }
        PageBeanEasyUI pageBeanEasyUI = productTestDao.getPageBean(query,criteria);
        List list = getDTOList(pageBeanEasyUI.getRows());
        pageBeanEasyUI.setRows(list);
        return pageBeanEasyUI;
    }

    @Override
    public ProductTestDTO save(ProductTestDTO dto){
        ValHelper.idNotExist(dto.getId());
        ProductTest target = dozerMapper.map(dto,ProductTest.class);
        productTestDao.save(target);
        return dozerMapper.map(target, ProductTestDTO.class);
    }

    @Override
    public void update(ProductTestDTO dto){
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");
        ProductTest target = productTestDao.getById(dto.getId());
        ValHelper.notNull(target,"更新的目标不存在!");
        //首先将所有关联的表置空，否则会以为要级联更新关联表的主键而报错
        if (!Strings.isNullOrEmpty(dto.getRegisterPersonID())) {
            target.setRegisterPerson(null);
        }
        if (!Strings.isNullOrEmpty(dto.getApprovePersonID())) {
            target.setApprovePerson(null);
        }
        if (!Strings.isNullOrEmpty(dto.getTestPersonID())) {
            target.setTestPerson(null);
        }
        if(!Strings.isNullOrEmpty(dto.getAssignPersonId())) {
            target.setAssignPerson(null);
        }

        dozerMapper.map(dto, target);
        productTestDao.update(target);
    }

    public String getNewNumber(ProductTestQuery query){
        String number = "";
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductTest.class);
        String dateNow = DateTime.now().toString("yyyyMMdd");
        criteria.add(eq("isValid",true));
        criteria.add(like("number","%" + dateNow + "%"));
        PageBeanEasyUI pageBeanEasyUI = productTestDao.getPageBean(query,criteria);
        String count = String.valueOf(pageBeanEasyUI.getTotal() + 1);
        if(count.length() == 1){
            number = dateNow + "0" + count;
        }else{
            number = dateNow + count;
        }
        return number;
    }
}
