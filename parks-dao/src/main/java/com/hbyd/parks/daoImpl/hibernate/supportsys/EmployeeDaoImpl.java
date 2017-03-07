package com.hbyd.parks.daoImpl.hibernate.supportsys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.supportsys.EmployeeDao;
import com.hbyd.parks.domain.supportsys.Employee;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * 人员 DAO 实现
 */
@Repository("empDao")
public class EmployeeDaoImpl extends BaseDaoImpl<Employee, String> implements EmployeeDao {
    @Override
    public List<Employee> getBySimilarName(String name) {
        String hql = "from Employee where empName like ?";
        return getCurrSession().createQuery(hql).setParameter(0, "%" + name + "%").list();
    }

    @Override
    public Employee getByName(String name) {
        return getByName("empName", name);
    }

    @Override
    public void updateBatchStatus(String hql, Object[] params, boolean isInvolve) {
        Session session = getCurrSession();
        Query query = session.createQuery(hql);

        assignParams(query, params);

        ScrollableResults results = query
                //explicitly set the CacheMode to disable interaction with the second-level cache.
                .setCacheMode(CacheMode.IGNORE)//Override the current session cache mode, just for this query.
                .scroll();

        int count = 0;
        while (results.next()) {//if there is another result
            Employee employee = (Employee) results.get(0);
            employee.setIsInvolve(isInvolve);

            if (++count % 10 == 0) {//每 10 条记录一批
                // flush a batch of updates and release memory:
                // When making new objects persistent
                // flush() and then clear() the session regularly in order to control the size of the first-level cache.
                session.flush();
                session.clear();
            }
        }
//        最后不足 10 条的记录会在 Session.close()时关闭
//        getCurrSession().close();//这个由 Spring AOP 来实现
    }

    @Override
    public void updateBatchStatus(DetachedCriteria criteria, boolean isInvolve) {
        Session session = getCurrSession();
        Iterator<Employee> iter = findListByCriteria(criteria).iterator();

        int count = 0;
        while(iter.hasNext()){
            Employee emp = iter.next();
            emp.setIsInvolve(isInvolve);

            if (++count % 10 == 0) {//每 10 条记录一批
                //flush a batch of updates and release memory:
                session.flush();
                session.clear();
            }
        }
    }

    @Override
    public List<Employee> getEmpsWithinDept(String deptId){
        DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class)
                .add(Restrictions.eq("department.id", deptId));

        return findListByCriteria(criteria);
    }
}
