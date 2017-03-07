package com.hbyd.parks.common.util;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

/**
 * 分页相关的辅助方法
 */
public class PaginationUtil {
    /**为 Criteria 添加排序
     * @param criteria Criteria 对象
     * @param orders 升序还是降序
     * @param sorts 排序的字段
     */
    public static void addOrders(Criteria criteria, String[] orders, String[] sorts) {
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

    /**为 HQL 添加 order by 子句
     * @param hql [select ...] from ... where ...
     * @param orders 升序还是降序
     * @param sorts 排序的字段
     * @return 带有 order by 的 HQL
     */
    public static String addOrders(String hql, String[] orders, String[] sorts) {

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
}
