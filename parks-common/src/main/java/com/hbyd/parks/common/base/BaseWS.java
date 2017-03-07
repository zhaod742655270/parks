package com.hbyd.parks.common.base;

import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;

import javax.jws.WebParam;
import java.util.List;


/**
 * 基础服务
 *
 * @param <D> DTO 类型，原则上必须是 BaseDTO 的子类，考虑到特殊情况，这里不做限制
 */
public interface BaseWS<D> {

    /**
     * 检查名称是否重复
     *
     * @param id   roleId
     * @param name roleName
     * @return 如果重复，返回 true, 否则 false
     */
    boolean check(@WebParam(name = "id") String id, @WebParam(name = "name") String name);

    /**
     * 获取分页结果，仅适用于单表操作
     *
     * @param queryBean 分页对象
     * @param hql_where HQL 的 where 子句
     * @param params    查询条件构成的数组
     * @return 分页对象
     * @author ren_xt
     */
    PageBeanEasyUI getPageBean(
            @WebParam(name = "queryBean") QueryBeanEasyUI queryBean,
            @WebParam(name = "hql_where") String hql_where,
            @WebParam(name = "params") Object... params);

    /**动态查询，适用于多表查询
     * @param queryBean 分页对象
     * @param hql       HQL 语句
     * @param params    查询条件构成的数组
     * @return 分页对象
     */
    PageBeanEasyUI getPageBeanByHQL(
            @WebParam(name = "queryBean") QueryBeanEasyUI queryBean,
            @WebParam(name = "hql") String hql,
            @WebParam(name = "params") Object... params);

    /**删除
     * @param id ID
     */
    void delByID(@WebParam(name = "id") String id);

    /**更新
     * @param dto 传输实体
     */
    void update(@WebParam(name = "dto") D dto);

    /**查询所有有效实体
     * @return 如果有 isValid 字段，仅返回其值为 true 的记录，否则返回全部记录（如果没有 isValid 字段，默认全部记录有效）
     */
    List<D> findAllValid();

    /**按 ID 查询
     * @param id ID
     * @return 传输实体
     */
    D getByID(@WebParam(name = "id") String id);

    /**保存
     * @param dto 传输实体
     * @return 保存后的传输实体
     */
    D save(@WebParam(name = "dto") D dto);

    /**查询所有
     * @return 传输实体列表
     */
    List<D> findAll();

    /**实体行数
     * @return 行数
     */
    int getRowCount();
}
