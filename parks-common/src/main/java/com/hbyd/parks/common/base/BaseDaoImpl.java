package com.hbyd.parks.common.base;

import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.common.util.PaginationUtil;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Resource;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.hibernate.criterion.Projections.rowCount;


/**DAO 抽象实现
 * @author ren_xt
 *
 * @param <T> 具体操作的实体类型
 */
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T, I extends Serializable> implements BaseDao<T, I> {

    /**
     * 当前类及其子类均可使用该 logger 记录日志
     */
    protected Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private SessionFactory sessionFactory;
	
	protected Class<T> clazz;

	public BaseDaoImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) pt.getActualTypeArguments()[0];

        logger.info("current dao is: " + this.getClass().getSimpleName() + " , clazz within it is: " + clazz.getSimpleName());

//        日志输出如下，注意 DefaultBaseDaoImpl 的 clazz 是 BaseEntity, BaseEntity 是抽象类
//        |    concrete dao    | clazz within it |
//        |--------------------|-----------------|
//        | DefaultBaseDaoImpl | BaseEntity      |
//        | AtteDetailDaoImpl  | AtteDetail      |
//        | AtteInfoDaoImpl    | AtteInfo        |
//        | ...                | ...             |

	}

    @Override
    public int getRowCount(){
        return ((Long) getCurrSession().createCriteria(clazz).setProjection(Projections.rowCount())
                .uniqueResult())
                .intValue();
    }

    @Override
    public int getRowCount(Class<? extends BaseEntity> clazz){
        return ((Long)getCurrSession().createCriteria(clazz).setProjection(Projections.rowCount())
                .uniqueResult())
                .intValue();
    }

    @Override
    public int getRowCount(String sql){
        return (int) getCurrSession().createSQLQuery(sql).uniqueResult();
    }

    @Override
    public void flush() {
        getCurrSession().flush();//同步 Session 缓存到数据库
        getCurrSession().clear();//清理 Session 缓存
    }

    @Override
    public PageBeanEasyUI getPageBean(QueryBeanEasyUI queryBean, DetachedCriteria criteria) {
        Criteria cri = criteria.getExecutableCriteria(getCurrSession());
        Long total = (Long) cri.setProjection(rowCount()).uniqueResult();

//        一旦执行 DetachedCriteria -> Criteria 的转化，对 Criteria 的修改也会导致 DetachedCriteria
//        的修改，造成 DetachedCriteria 无法复用，下述断言可以通过：
//        assert criteria.getExecutableCriteria(getCurrSession()) == cri;
//        为了实现 DetachedCriteria 的复用，可以变通如下(注意顺序)：
        cri.setProjection(null);//取消之前的投影设定
        cri.setResultTransformer(Criteria.ROOT_ENTITY);//返回整个实体，而非投影
//        cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//返回整个实体，而非投影

//        查询分页数据
        int page = queryBean.getPage();
        int rows = queryBean.getRows();

        PaginationUtil.addOrders(cri, queryBean.getOrders(), queryBean.getSorts());
        List list = cri
                .setFirstResult((page - 1) * rows)
                .setMaxResults(rows)
                .list();

        return new PageBeanEasyUI(queryBean,total.intValue(), list);
    }
    @Override
    public void clearSecLevelCache(Class... classes) {
        for (int i = 0; i < classes.length; i++) {
            Cache cache = getCurrSession().getSessionFactory().getCache();
            cache.evictEntityRegion(classes[i]);
        }
    }

    /**根据名称查询实体
     * @param nameField 名称字段
     * @param nameVal 名称
     * @return 实体
     */
    protected final T getByName(String nameField, String nameVal) {
        String hql = new StringBuilder().append("from ")
                .append(clazz.getSimpleName())
                .append(" where ")
                .append(nameField)
                .append(" = ?").toString();

        List<T> list = getCurrSession().createQuery(hql).setParameter(0, nameVal).list();

        if(list.size() == 0){
            return null;
        }else{
            return list.get(0);
        }
    }

    @Override
    public List<T> getBatch(DetachedCriteria criteria, int offSet, int batchSize) {
        return  criteria.getExecutableCriteria(getCurrSession())
                .setFirstResult(offSet)
                .setMaxResults(batchSize)
                .list();
    }

    @Override
	public T save(T entity) {
		getCurrSession().save(entity);
		return entity;
	}
	
	@Override
	public void saveOrUpdate(T entity) {
        getCurrSession().merge(entity);
	}
	
	@Override
	public void update(T entity) {
		getCurrSession().merge(entity);
	}
	
	@Override
	public void delete(I id) {
		Object obj = getCurrSession().get(clazz, id);
		getCurrSession().delete(obj);
	}

    @Override
    public void delete(T entity) {
        getCurrSession().delete(entity);
    }

    @Override
    public void delete(Class eClass, String id) {
        Object entity = getCurrSession().load(eClass, id);
        getCurrSession().delete(entity);
    }

    @Override
	public void deleteAll() {
		String sql = "delete " + clazz.getSimpleName();// "FROM" keyword is optional
		getCurrSession().createQuery(sql).executeUpdate();
	}
	
	@Override
	public T getById(Serializable id) {
		if (id == null) {
			return null;
		}
		return (T) getCurrSession().get(clazz, id);
	}

    @Override
	public T loadById(Serializable id) {
		if(id == null){
			return null;
		}
		return (T) getCurrSession().load(clazz, id);
	}
/*
	@Override
	public T loadByIdWithLock(I id, boolean lock) {
		
		if(id == null){
			return null;
		}
		
		T entity;
		
		if (lock)
			entity = (T) getCurrSession().load(clazz, id, LockOptions.UPGRADE);//Transaction will obtain a database lock immediately
		else
			entity = (T) getCurrSession().load(clazz, id);

		return entity;
	}
	
//	QBE 查询可以参考：17.6. Example queries

    @Override
	public List<T> findBySimilarExample(final T entity) {
		if (entity == null) {
			return findAll();
		} else {
			// filter on properties set in the entity
			HibernateCallback<List<T>> callback = new HibernateCallback<List<T>>() {
				public List<T> doInHibernate(Session session)
						throws HibernateException {
//					Example.create(t): t 的所有非空字段都将作为比较字段，t 的主外键字段和 Null 值字段默认不参与比较
					Example ex = Example.create(entity)// Create a new instance, which includes all non-null properties by default,Version properties, identifiers and associations are ignored. By default, null valued properties are excluded.
							.ignoreCase()//  Ignore case for all string-valued properties
							.enableLike(MatchMode.ANYWHERE);// Use the "like" operator for all string-valued properties
					return session.createCriteria(clazz).add(ex).list();
				}
			};
			return getHibernateTemplate().execute(callback);
		}
	}

	@Override
    public List<T> findByExactExample(T exampleEntity, String[] excludeProperty) {  
        Criteria crit = getCurrSession().createCriteria(clazz);  
        Example example =  Example.create(exampleEntity);  
        for (String exclude : excludeProperty) {  
            example.excludeProperty(exclude);  
        }  
        crit.add(example);  
        return crit.list();  
    }
*/
	
	@Override
	public List<T> getByIds(String idField, I[] ids) {
		if (ids == null || ids.length == 0) {
			return null;
		}

		// return getSession().createQuery("FROM User WHERE id in (:ids)").setParameterList("ids", ids).list();
		
		return getCurrSession().createCriteria(clazz)
				.add(Restrictions.in(idField, ids))
				.list();
	}

	@Override
	public List<T> findAll() {
		return getCurrSession().createCriteria(clazz).list();
	}
	
	@Override
	public List<T> findListByCriteria(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getCurrSession()).list();
	}

    @Override
    public T findOneByCriteria(DetachedCriteria criteria) {
        Criteria executableCriteria = criteria.getExecutableCriteria(getCurrSession());
        return (T) executableCriteria.uniqueResult();
    }

    @Override
    public ScrollableResults getScrollableResults(DetachedCriteria criteria){
        return criteria.getExecutableCriteria(getCurrSession())
                .setFetchSize(1)
                .setReadOnly(true)
                .scroll(ScrollMode.FORWARD_ONLY);
    }

    @Override
    public List<T> findPageByCriteria(DetachedCriteria criteria, QueryBeanEasyUI queryBean){
        Criteria cri = criteria.getExecutableCriteria(getCurrSession());

        String[] orders = queryBean.getOrders();
        String[] sorts = queryBean.getSorts();
        int page = queryBean.getPage();
        int rows = queryBean.getRows();

//      1. 排序
        addOrders(cri, orders, sorts);

//      2. 分页
        cri.setFirstResult((page - 1) * rows)
            .setMaxResults(rows);

        return cri.list();
    }

    @Override
    public void addOrders(Criteria criteria, String[] orders, String[] sorts) {
        if(orders.length != sorts.length){
            throw new RuntimeException("orders.length must equal to sorts.length!");
        }

        for (int i = 0; i < orders.length; i++) {
            String order = orders[i];
            String sort = sorts[i];

            if("asc".equals(order)){
                criteria.addOrder(Order.asc(sort));
            }else if("desc".equals(order)){
                criteria.addOrder(Order.desc(sort));
            }else{
                throw new RuntimeException("order must be asc or desc!");
            }
        }
    }

    @Override
    public Long getCnt(DetachedCriteria criteria) {
        criteria.setProjection(Projections.rowCount());//select count(*)
        Object o = criteria.getExecutableCriteria(getCurrSession()).uniqueResult();

//      uniqueResult() 可能为 NULL, 处理如下：
        return o == null ? new Long(0) : (Long) o;
    }

    @Override
    public PageBeanEasyUI getPageBean(QueryBeanEasyUI queryBean, String hql, Object[] params) {
//      查询总数
        Query queryCnt = getCurrSession().createQuery(hql);
        assignParams(queryCnt, params);
        int total = queryCnt.list().size();

//      查询分页
//        添加排序
        Query queryList = getCurrSession()
                .createQuery(addOrders(hql, queryBean.getOrders(), queryBean.getSorts()));
//        添加分页
        int page = queryBean.getPage();
        int rows = queryBean.getRows();

        queryList.setFirstResult((page - 1) * rows);
        queryList.setMaxResults(rows);
//        注入参数
        assignParams(queryList, params);

        PageBeanEasyUI pageBean = new PageBeanEasyUI();
        pageBean.setQueryBean(queryBean);
        pageBean.setRows(queryList.list());
        pageBean.setTotal(total);

        return pageBean;
    }

    @Override
    public String addOrders(String hql, String[] orders, String[] sorts) {

        if(orders == null || sorts == null){
            throw new NullPointerException("分页参数：sorts 和 orders 都不能为 NULL");
        }

        if(orders.length != sorts.length){
            throw new RuntimeException("orders.length must equal to sorts.length!");
        }

        StringBuilder sb = new StringBuilder(hql).append(" order by ");

        for (int i = 0; i < orders.length; i++) {
            String order = orders[i];
            String sort = sorts[i];

            sb.append(sort).append(" ").append(order).append(",");
        }

        sb.setLength(sb.length() - 1);//去除最后的逗号

        return sb.toString();
    }

    /**为 Query 添加查询参数
	 * @param query Query 对象
	 * @param params 参数列表
	 * @author ren_xt
	 */
	protected void assignParams(Query query, Object[] params){
		if(params != null && params.length > 0){
			for(int i = 0; i < params.length; i++){

//             修正前台查询参数 java.util.Date 传递到后台反序列化为 XMLGregorianCalendar 而不是所需 java.util.Date 的问题
               Object param = params[i];
               if(param instanceof XMLGregorianCalendar){//javax.xml.datatype.XMLGregorianCalendar
                    params[i] = ((XMLGregorianCalendar)param).toGregorianCalendar().getTime();
               }

               query.setParameter(i, params[i]);
			}
		}
	}
	
	/**
	 * 获取当前可用的Session
	 * 
	 * @return Session
	 */
    @Override
	public Session getCurrSession() {
		return sessionFactory.getCurrentSession();
	}

    @Override
    public <E> E getById(Class<E> eClass, Serializable id) {
        return (E) getCurrSession().get(eClass, id);
    }
}
