package com.hbyd.parks.common.model;

import java.io.Serializable;
import java.util.List;

/**为 EasyUI 封装的分页结果类
 * @author ren_xt
 *
 */
public class PageBeanEasyUI implements Serializable{
	/**
	 * 查询参数 Bean
	 */
	private QueryBeanEasyUI queryBean;
	/**
	 * 总记录数
	 */
	private int total;
	/**
	 * 分页数据
	 */
	private List rows;
	
	public PageBeanEasyUI() {
		super();
	}
	public PageBeanEasyUI(int total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public PageBeanEasyUI(QueryBeanEasyUI queryBean, int total, List rows) {
		super();
		this.queryBean = queryBean;
		this.total = total;
		this.rows = rows;
	}
	public QueryBeanEasyUI getQueryBean() {
		return queryBean;
	}
	public void setQueryBean(QueryBeanEasyUI queryBean) {
		this.queryBean = queryBean;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
