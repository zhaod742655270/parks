package com.hbyd.parks.daoImpl.hibernate.supportsys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.supportsys.ObjectTypeDao;
import com.hbyd.parks.domain.supportsys.ObjectType;
import org.springframework.stereotype.Repository;

@Repository
public class ObjectTypeDaoImpl extends BaseDaoImpl<ObjectType, String> implements ObjectTypeDao {
}
