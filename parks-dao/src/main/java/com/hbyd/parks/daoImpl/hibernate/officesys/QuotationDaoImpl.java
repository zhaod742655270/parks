package com.hbyd.parks.daoImpl.hibernate.officesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.officesys.QuotationDao;
import com.hbyd.parks.domain.officesys.Quotation;
import org.springframework.stereotype.Repository;


@Repository
public class QuotationDaoImpl extends BaseDaoImpl<Quotation, String> implements QuotationDao {
}
