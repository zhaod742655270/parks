package com.hbyd.parks.daoImpl.hibernate.managesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.managesys.PriviledgeDao;
import com.hbyd.parks.domain.managesys.Priviledge;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by allbutone on 14-7-15.
 */
@Repository
public class PriviledgeDaoImpl extends BaseDaoImpl<Priviledge,String> implements PriviledgeDao{

//  TODO 直接使用 HQL Delete 删除会效率好一些，下面采用的是先查后删
    @Override
    public void delPris(String id, String type){
        List<Priviledge> list = getCurrSession().createCriteria(clazz)
                .add(Restrictions.eq("priOwnerType", type))
                .add(Restrictions.eq("priOwnerId", id)).list();

        if(list.size() > 0){
            for (Priviledge pri : list) {
                delete(pri);
            }
        }
    }
}
