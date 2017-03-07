package com.hbyd.parks.daoImpl.hibernate.officesys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.officesys.ProjectRecordDao;
import com.hbyd.parks.domain.officesys.ProjectRecord;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.gt;
import static org.hibernate.criterion.Restrictions.le;


@Repository
public class ProjectRecordDaoImpl extends BaseDaoImpl<ProjectRecord, String> implements ProjectRecordDao {
    @Override
    public List<ProjectRecord> getProjectRecordList() {
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
                 DetachedCriteria criteria = DetachedCriteria.forClass(ProjectRecord.class)
                .add(eq("isValid", true))
                .add(le("biddingDate", new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())))
               .add(gt("biddingDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        return findListByCriteria(criteria);
    }
}
