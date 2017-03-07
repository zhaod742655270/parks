package com.hbyd.parks.dao.managesys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.managesys.Priviledge;

/**
 * Created by allbutone on 14-7-15.
 */
public interface PriviledgeDao extends BaseDao<Priviledge, String> {
    /**删除权限
     * @param id      owner_id(属主ID)
     * @param type    owner_type(属主类型)
     */
    void delPris(String id, String type);
}
