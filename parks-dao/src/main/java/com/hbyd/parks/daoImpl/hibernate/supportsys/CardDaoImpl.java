package com.hbyd.parks.daoImpl.hibernate.supportsys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.supportsys.CardDao;
import com.hbyd.parks.domain.supportsys.Card;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * 卡
 */
@Repository
public class CardDaoImpl extends BaseDaoImpl<Card, String> implements CardDao {
    @Override
    public Card getByCardNo(String cardNo){
        DetachedCriteria criteria = DetachedCriteria.forClass(this.clazz)
                .add(eq("cardNo", cardNo));
        return findOneByCriteria(criteria);
    }
}
