package com.hbyd.parks.daoImpl.hibernate.attendancesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.dao.attendancesys.ShiftAssignDao;
import com.hbyd.parks.domain.attendancesys.ShiftAssign;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * 排班 DAO
 */
@Repository
public class ShiftAssignDaoImpl extends BaseDaoImpl<ShiftAssign, String> implements ShiftAssignDao {
    @Override
    public ShiftAssign getByEmpId(String empId, int year, int month) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(eq("employee.id", empId))
                .add(eq("yearAndMonth", DateUtil.getYearAndMonth(year, month)));

        return findOneByCriteria(criteria);//某年某月只可能有一条排班记录
    }

    @Override
    public String getShift(String empId, Date date) {
        DateTime dateTime = new DateTime(date);
        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();

        ShiftAssign sa = getByEmpId(empId, year, month);

        if (sa == null) {
            return null;
        }

        return sa.getDayShift(day);
    }

    @Override
    public ShiftAssign getShiftAssign(String yearAndMonth, String empId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ShiftAssign.class)
                .add(Restrictions.eq("yearAndMonth", yearAndMonth))
                .add(Restrictions.eq("employee.id", empId));

        return findOneByCriteria(criteria);
    }

    @Override
    public boolean usedWithinShiftAssign(String shiftId) {
        String query_shift_assign = "select count(*) as refCnt from attend_shift_assign where \n" +
                "s1 = ? OR\n" +
                "s2 = ? OR\n" +
                "s3 = ? OR\n" +
                "s4 = ? OR\n" +
                "s5 = ? OR\n" +
                "s6 = ? OR\n" +
                "s7 = ? OR\n" +
                "s8 = ? OR\n" +
                "s9 = ? OR\n" +
                "s10 = ? OR\n" +
                "s11 = ? OR\n" +
                "s12 = ? OR\n" +
                "s13 = ? OR\n" +
                "s14 = ? OR\n" +
                "s15 = ? OR\n" +
                "s16 = ? OR\n" +
                "s17 = ? OR\n" +
                "s18 = ? OR\n" +
                "s19 = ? OR\n" +
                "s20 = ? OR\n" +
                "s21 = ? OR\n" +
                "s22 = ? OR\n" +
                "s23 = ? OR\n" +
                "s24 = ? OR\n" +
                "s25 = ? OR\n" +
                "s26 = ? OR\n" +
                "s27 = ? OR\n" +
                "s28 = ? OR\n" +
                "s29 = ? OR\n" +
                "s30 = ? OR\n" +
                "s31 = ?\n";
        SQLQuery qsa = getCurrSession().createSQLQuery(query_shift_assign);
        for (int i = 0; i < 31; i++) {
            qsa.setParameter(i, shiftId);
        }

        Object refCnt = qsa.addScalar("refCnt", StandardBasicTypes.LONG).uniqueResult();

        return ((Long)refCnt).intValue() > 0;
    }
}
