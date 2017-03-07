package com.hbyd.parks.daoImpl.hibernate.officesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.officesys.PaymentLogDao;
import com.hbyd.parks.domain.officesys.PaymentLog;
import org.springframework.stereotype.Repository;

/**
 * Created by Len on 2016/3/3.
 */
@Repository
public class PaymentLogDaoImpl extends BaseDaoImpl<PaymentLog, String> implements PaymentLogDao {
}
