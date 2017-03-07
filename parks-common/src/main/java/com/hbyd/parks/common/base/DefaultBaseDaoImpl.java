package com.hbyd.parks.common.base;

import org.springframework.stereotype.Repository;

/**
 * 默认的 BaseDao 实现
 */
@Repository
public class DefaultBaseDaoImpl extends BaseDaoImpl<BaseEntity, String> implements BaseDao<BaseEntity, String> {
}
