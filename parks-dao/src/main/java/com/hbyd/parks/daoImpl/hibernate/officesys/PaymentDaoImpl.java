package com.hbyd.parks.daoImpl.hibernate.officesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.officesys.PaymentDao;
import com.hbyd.parks.domain.officesys.Payment;
import org.springframework.stereotype.Repository;

/**
 * Created by zhang_f on 2016/3/16.
 */
@Repository
public class PaymentDaoImpl extends BaseDaoImpl<Payment, String> implements PaymentDao {
}
