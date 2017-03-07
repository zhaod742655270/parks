package com.hbyd.parks.daoImpl.hibernate.officesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.officesys.AcceptanceDao;
import com.hbyd.parks.domain.officesys.Acceptance;
import org.springframework.stereotype.Repository;


@Repository
public class AcceptanceDaoImpl extends BaseDaoImpl<Acceptance, String> implements AcceptanceDao {
}
