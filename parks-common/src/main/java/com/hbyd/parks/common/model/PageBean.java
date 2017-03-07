package com.hbyd.parks.common.model;

import java.util.List;


/**自定义的分页类
 * @author ren_xt
 *
 * @param <T> 具体的实体类型
 */
public class PageBean<T> {

	// 传递的参数或配置的值
	private int currentPage; // 当前页
	private int pageSize; // 每页显示多少条记录

	// 查询数据库
	private int recordCount; // 总记录数
	private List<T> recordList; // 本页的数据列表，只是暂存在当前 PageBean 中，没什么其他的作用

	// 需要计算
	private int pageCount; // 总页数
	private int beginPageIndex; // 页码列表的开始索引
	private int endPageIndex; // 页码列表的结束索引
	
	
	private PageBean(){
		//WebMethod 使用 PageBean 做为返回值，需要无参构造方法，这里声明为 private
	}

	/**
	 * 只接受4个必要的属性值，会自动的计算出其他3个属性的值
	 * 
	 * @param currentPage 当前页码
	 * @param pageSize 每页显示的记录数
	 * @param recordCount 记录总数
	 * @param recordList 当前页的记录列表
	 */
	public PageBean(int currentPage, int pageSize, int recordCount, List<T> recordList) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.recordList = recordList;

		// 计算 pageCount
		this.pageCount = (recordCount + pageSize - 1) / pageSize;

		// 计算beginPageIndex与endPageIndex
		// 1、总页码数<=10，就全部显示
		if (pageCount <= 10) {
			beginPageIndex = 1;
			endPageIndex = pageCount;
		}
		// 2、总页码数>10
		else {
			// 1，显示当前页附近的共10个页码（前4个+当前页+后5个）
			beginPageIndex = currentPage - 4;
			endPageIndex = currentPage + 5;
			// 2，前面不足4个页码时，显示前10个页码
			if (beginPageIndex < 1) {
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			// 3，后面不足5个页码时，显示后10个页码
			else if (endPageIndex > pageCount) {
				endPageIndex = pageCount;
				beginPageIndex = pageCount - 9; // 因为显示时包含两个边界，所以这里要减9。例：13-10=3，从3到13是显示11个页码，所以要13-9，显示从4到13共10个页码
			}
		}

	}

	public List<T> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

}
