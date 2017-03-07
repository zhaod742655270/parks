package com.hbyd.parks.daoImpl.hibernate.doorsys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.common.constant.CardStatus;
import com.hbyd.parks.dao.doorsys.AccessEventDao;
import com.hbyd.parks.domain.doorsys.AccessEvent;
import com.hbyd.parks.domain.supportsys.Card;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * 刷卡事件
 */
@Repository
public class AccessEventDaoImpl extends BaseDaoImpl<AccessEvent, String> implements AccessEventDao {
    @Override
    public List<AccessEvent> getEvents(String empId, Date beginDT, Date endDT){
//      查询人员的有效卡
        DetachedCriteria findCards = DetachedCriteria.forClass(Card.class)
                .add(eq("ownId", empId))
                .add(eq("status", CardStatus.VALID));
//      人员的有效卡
        List<Card> cards = findCards.getExecutableCriteria(getCurrSession()).list();
        DetachedCriteria findEvents = DetachedCriteria.forClass(AccessEvent.class)
                .add(ge("eventTime", beginDT))
                .add(le("eventTime", endDT));//JPA 比较日期时间

        if(cards.size() > 0){
            List<String> cardNos = new ArrayList<>();
            for (Card card : cards) {
                cardNos.add(card.getCardNo());
            }

            findCards.add(in("cardNo", cardNos));
        } else {//员工没有卡
            return null;
        }

        return findListByCriteria(findEvents);
    }
}
