package com.hbyd.parks.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * HQL 语句辅助生成类， 可以通过方法链调用
 * 
 * @author ren_xt
 */
public class HqlHelper {

	private String fromClause; // From子句
	private String whereClause = ""; // Where子句
	private String orderByClause = ""; // OrderBy子句

	private List<Object> parameters = new ArrayList<Object>(); // 查询参数列表

	private HqlHelper() {
		//如果 HqlHelper 作为 WebMethod 的参数，需要无参构造方法，但 HqlHelper 又不允许无参构造，因此这里设置为 private
	}
	
	/**
	 * 构造时生成 From 子句，目标类型由参数决定
	 * 
	 * @param clazz 目标实体类的 Class 对象
	 * @param alias 昵称
	 */
	@SuppressWarnings("rawtypes")
	public HqlHelper(Class clz, String alias) {
		fromClause = "FROM " + clz.getSimpleName() + " " + alias;
	}
	@SuppressWarnings("rawtypes")
	public HqlHelper(Class clz){
		fromClause = "FROM " + clz.getSimpleName() + " ";
	}
	
	/**
	 * 构造时生成 From 子句，目标类型由调用时的泛型决定，该方法测试不通过
	 */
	/*
	@SuppressWarnings("unchecked")
	public HqlHelper(){
		//获取父类泛型的具体类型，由于 HqlHelper 没有带有泛型的父类， 因此不能这么用
		this.dClass = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		fromClause = "FROM " + dClass.getSimpleName() + " ";
	}
	*/

	/**
	 * 拼接Where子句（添加的多个过滤条件之间是使用AND连接的）
	 * 
	 * @param condition
	 *            一个过滤条件 - JDBC 风格，如：.addWhereCondition("name like ?", new Object[]{"tom%"})
	 *            不支持 .addWhereCondition("name like :name", new Object[]{"tom%"}), 因为命名参数是从
	 *            1 索引的，结构为：<1, <0, "tom%">>，而 BaseDaoImpl 的 getPageBean 方法是按 0-based index 处理的
	 * @param params 查询参数数组
	 */
	public HqlHelper addWhereCondition(String condition, Object... params) {
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}

		if (params != null && params.length > 0) {
			for (Object param : params) {
				this.parameters.add(param);
			}
		}

		return this;
	}

	/**
	 * 如果第一个参数为true，则拼接Where子句（添加的多个过滤条件之间是使用AND连接的）
	 * 
	 * @param append
	 * @param condition
	 *            一个过滤条件
	 * @param params
	 */
	public HqlHelper addWhereCondition(boolean append, String condition,
			Object... params) {
		if (append) {
			addWhereCondition(condition, params);
		}
		return this;
	}

	/**
	 * 拼接OrderBy子句
	 * 
	 * @param propertyName
	 * @param isAsc
	 */
	public HqlHelper addOrderByProperty(String propertyName, boolean isAsc) {
		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName
					+ (isAsc ? " ASC" : " DESC");
		} else {
			orderByClause += ", " + propertyName + (isAsc ? " ASC" : " DESC");
		}
		return this;
	}

	/**
	 * 如果第一个参数为true，则拼接OrderBy子句
	 * 
	 * @param propertyName
	 * @param isAsc
	 */
	public HqlHelper addOrderByProperty(boolean append, String propertyName,
			boolean isAsc) {
		if (append) {
			addOrderByProperty(propertyName, isAsc);
		}
		return this;
	}

	/**
	 * 获取查询数据列表的的HQL语句
	 * 
	 * @return
	 */
	public String getQueryListHql() {
		return fromClause + whereClause + orderByClause;
	}

	/**
	 * 获取查询总记录数的的HQL语句
	 * 
	 * @return
	 */
	public String getQueryCountHql() {
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}

	/**获取参数列表
	 * @return 查询参数列表
	 * @author ren_xt
	 */
	public List<Object> getParameterList() {
		return parameters;
	}
	
	/** 获取参数数组，方便作为可变参数， HibernateTemplate 等模板的查询方法接收可变参数作为查询参数
	 * @return 查询参数数组
	 * @author ren_xt
	 */
	public Object[] getParameterArray(){
		return parameters.toArray();
	}

}
