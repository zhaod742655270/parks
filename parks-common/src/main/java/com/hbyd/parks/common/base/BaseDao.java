package com.hbyd.parks.common.base;

import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * DAO 的通用接口
 * @author ren_xt
 * @param <T>  Entity
 * @param <I>  ID
 */
public interface BaseDao<T, I extends Serializable> {

    /**获取一批刷卡记录用于计算考勤
     * @param criteria     查询条件
     * @param offSet       从第几条开始取(0-based)
     * @param batchSize    每批处理的记录数
     * @return 刷卡记录列表
     */
    List<T> getBatch(DetachedCriteria criteria, int offSet, int batchSize);

	/**
	 * 保存实体
	 * @param entity 实体对象
	 */
	T save(T entity);
	
	/**
	 * 删除所有实体
	 * @author ren_xt
	 */
	void deleteAll();
	
	/**
	 * 保存或更新实体
	 * @author ren_xt
	 */
	void saveOrUpdate(T entity);

	/**
	 * 更新实体
	 * @param entity 实体对象
	 */
	void update(T entity);

	/**
	 * 删除实体
	 * @param id
	 */
	void delete(I id);

    /**删除实体
     * @param entity 实体对象
     */
    void delete(T entity);

    /**删除某类实体
     * @param eClass 实体类别
     * @param id 实体ID
     */
    void delete(Class<? extends BaseEntity> eClass, String id);

	/**获取实体
	 * @param id ID
	 * @return 如果id 为null 或 查询无果，返回 null, 否则返回实体对象
	 */
	T getById(Serializable id);

    /**
     * 清理二级缓存
     * @param classes    目标实体类别
     */
    void clearSecLevelCache(Class... classes);
	
	/**获取实体
	 * @param id ID
	 * @return 如果id 为null 或 查询无果，返回 null, 否则返回实体对象
	 * @author ren_xt
	 */
	T loadById(Serializable id);

	/**获取实体
	 * @param id ID
	 * @param lock 是否为读取的数据加锁
	 * @return 如果id 为null 或 查询无果，返回 null, 否则返回实体对象
	 * @author ren_xt
	 */
//	T loadByIdWithLock(I id, boolean lock);
	
	/**样板相似匹配，适用于：和样板实例的字符串属性进行模糊匹配，不区分大小写<br/>
	 * Get all instances that match the properties that are set in the given
	 * object using a standard Query by Example method.
	 * @param exampleEntity 样板实例
	 * @return 匹配的实体列表（a list of beans that match the example.）
	 * @author ren_xt
	 */
//	List<T> findBySimilarExample(T exampleEntity);
	
	/**样板精确匹配
	 * @param exampleEntity 样板实例
	 * @param excludeProperty 样板实例中不参与比较的属性
	 * @return 匹配的实体列表
	 * @author ren_xt
	 */
//	List<T> findByExactExample(T exampleEntity, String[] excludeProperty);

	/**
	 * 获取实体
	 * @param idField ID 字段的名称
	 * @param ids 实体的 ID 数组
	 * @return 实体集合
	 * @author ren_xt
	 */
	List<T> getByIds(String idField, I[] ids);

	/**
	 * 查询所有实体
	 * @return 实体列表
	 */
	List<T> findAll();
	
	/**查询符合条件的实体列表(游离查询), 参照 17.8. Detached queries and subqueries
	 * @param criteria 查询条件，QBC
	 * @return 实体列表
	 * @author ren_xt
	 */
	List<T> findListByCriteria(DetachedCriteria criteria);

    /**返回滚动结果集
     * @param criteria 查询条件
     * @return {@link org.hibernate.ScrollableResults}
     */
    ScrollableResults getScrollableResults(DetachedCriteria criteria);

    /**查询符合条件的实体分页(游离查询), 参照 17.8. Detached queries and subqueries
     * @param criteria 查询条件，QBC
     * @param queryBean 分页条件
     * @return 实体分页
     */
    List<T> findPageByCriteria(DetachedCriteria criteria, QueryBeanEasyUI queryBean);

    /**查询符合条件的实体
     * @param criteria 查询条件
     * @return 单个实体
     */
    T findOneByCriteria(DetachedCriteria criteria);

    /**查询符合条件的实体的个数
     * @param criteria 查询条件
     * @return 符合条件的记录的个数
     */
    Long getCnt(DetachedCriteria criteria);

    /**获取实体表的行数
     * @return 行数
     */
    int getRowCount();

    /**获取实体表的行数
     * @param clazz    实体类别
     * @return 行数
     */
    int getRowCount(Class<? extends BaseEntity> clazz);

    /**查询符合条件的记录数
     * @param sql SQL 语句
     * @return 记录数
     */
    int getRowCount(String sql);

    /**
     * 批量操作时，处理完一批数据后，调用此方法，执行一个批次
     */
    void flush();

    PageBeanEasyUI getPageBean(QueryBeanEasyUI queryBean, DetachedCriteria criteria);

    /** 分页查询
     * @param queryBean 分页条件
     * @param hql   查询HQL
     * @param params 查询参数
     * @return 分页对象
     */

    PageBeanEasyUI getPageBean(QueryBeanEasyUI queryBean, String hql, Object[] params);

    /**为 HQL 添加 order by 子句
     * @param hql [select ...] from ... where ...
     * @param orders 升序还是降序
     * @param sorts 排序的字段
     * @return 带有 order by 的 HQL
     */
    String addOrders(String hql, String[] orders, String[] sorts);

    /**为 Criteria 添加排序
     * @param criteria Criteria 对象
     * @param orders 升序还是降序
     * @param sorts 排序的字段
     */
    void addOrders(Criteria criteria, String[] orders, String[] sorts);

    Session getCurrSession();

    /**查询某类实体
     * @param eClass 实体类别
     * @param id 实体 ID
     * @return 实体
     */
    <E> E getById(Class<E> eClass, Serializable id);
}
