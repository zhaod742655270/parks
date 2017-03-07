package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.attendancesys.ShiftBindingDao;
import com.hbyd.parks.domain.attendancesys.ShiftBinding;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 班次绑定 DAO
 */
@Repository
public class ShiftBindingDaoImpl extends BaseDaoImpl<ShiftBinding, String> implements ShiftBindingDao {
    @Override
    public List<ShiftBinding> getShiftBindings(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq("regularShift.id", id))
//                order by idx, dayToBound --这样便于前台顺序展示班次绑定
                .addOrder(Order.asc("idx"))
                .addOrder(Order.asc("dayToBound"));

        //如果没有，Hibernate 返回一个空集合，而非 null
        return findListByCriteria(criteria);
    }

    @Override
    public boolean usedWithinShiftBinding(String shiftId){
        String query_shift_binding = "select count(*) as refCnt from attend_shift_binding where shiftFK = ?";
        SQLQuery qsb = getCurrSession().createSQLQuery(query_shift_binding);
        int index = 0;
        qsb.setParameter(index, shiftId);
        Object refCnt = qsb.addScalar("refCnt", StandardBasicTypes.LONG).uniqueResult();

        return ((Long)refCnt).intValue() > 0;
    }
}
