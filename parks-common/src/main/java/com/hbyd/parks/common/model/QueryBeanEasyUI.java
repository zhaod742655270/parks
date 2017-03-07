package com.hbyd.parks.common.model;

/**
 * 为 EasyUI 封装的分页参数类
 *
 * @author ren_xt
 */
public class QueryBeanEasyUI {
    /**
     * 当前请求页码
     */
    private int page = 1;

    /**
     * 每页显示多少记录
     */
    private int rows = 10;

    /**
     * 排序的字段
     */
    private String[] sorts;

    /**
     * 升序还是降序
     */
    private String[] orders;

    public QueryBeanEasyUI() {
        super();
    }

    public QueryBeanEasyUI(int page, int rows, String[] sorts, String[] orders) {
        this.page = page;
        this.rows = rows;
        this.sorts = sorts;
        this.orders = orders;
    }

    public QueryBeanEasyUI(int page, int rows, String sort, String order) {
        this.page = page;
        this.rows = rows;
        this.sorts = new String[]{sort};
        this.orders =  new String[]{order};
    }

    public String[] getSorts() {
        return sorts;
    }

    public void setSorts(String[] sorts) {
        this.sorts = sorts;
    }

    public void setSort(String sort) {
        String[] sorts = sort.split(",");
        this.sorts = sorts;
    }

    public String[] getOrders() {
        return orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }

    public void setOrder(String order) {
        String[] orders = order.split(",");
        this.orders = orders;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
