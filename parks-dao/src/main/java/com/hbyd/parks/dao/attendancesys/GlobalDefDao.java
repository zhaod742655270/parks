package com.hbyd.parks.dao.attendancesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.attendancesys.GlobalDef;

/**
 * Created by allbutone on 2014/8/27.
 */
public interface GlobalDefDao extends BaseDao<GlobalDef, String>{
    /**查询最新的全局定义
     * @return 全局定义
     */
    public GlobalDef getLatest();
}
