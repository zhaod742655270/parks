package com.hbyd.parks.officesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.dao.officesys.PaymentDao;
import com.hbyd.parks.dao.officesys.PaymentPostilDao;
import com.hbyd.parks.domain.officesys.Payment;
import com.hbyd.parks.domain.officesys.PaymentPostil;
import com.hbyd.parks.dto.officesys.PaymentPostilDTO;
import com.hbyd.parks.ws.officesys.PaymentPostilWS;

import javax.annotation.Resource;

/**
 * Created by Len on 2016/3/3.
 */
public class PaymentPostilWSImpl extends BaseWSImpl<PaymentPostilDTO, PaymentPostil> implements PaymentPostilWS {


    @Resource
    private PaymentPostilDao paymentPostilDao;

    @Resource
    private PaymentDao paymentDao;

    @Override
    public void update(PaymentPostilDTO dto) {
        ValHelper.notNull(dto.getId(), "更新操作接收的 ID 不能为 NULL!");

        PaymentPostil target = paymentPostilDao.getById(dto.getId());
        ValHelper.notNull(target, "更新的目标不存在!");
        ValHelper.notNull(dto.getParentID(),"付款合同的ID不能为null");//关联的合同id不能为空
        //找出所属合同
        Payment payment = paymentDao.getById(dto.getParentID());
        ValHelper.notNull(payment, "关联的付款合同不存在!");


        dozerMapper.map(dto, target);
        target.setPayment(payment);
        paymentPostilDao.update(target);
    }

    @Override
    public PaymentPostilDTO save(PaymentPostilDTO dto) {
        ValHelper.idNotExist(dto.getId());//id不能存在
        ValHelper.notNull(dto.getParentID(),"付款合同的ID不能为null");//关联的合同id不能为空
        //找出所属合同
        Payment payment = paymentDao.getById(dto.getParentID());
        ValHelper.notNull(payment, "关联的付款合同不存在!");
        //对象转化
        PaymentPostil target = dozerMapper.map(dto, PaymentPostil.class);
        //将合同值添加上
        target.setPayment(payment);
        paymentPostilDao.save(target);
        return dozerMapper.map(target, PaymentPostilDTO.class);
    }

}
