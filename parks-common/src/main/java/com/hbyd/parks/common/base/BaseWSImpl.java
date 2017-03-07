package com.hbyd.parks.common.base;

import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.common.util.ValHelper;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static com.hbyd.parks.common.cxf.FaultBuilder.NOT_ALLOWED;
import static com.hbyd.parks.common.cxf.FaultBuilder.getSoapFault;
import static com.hbyd.parks.common.util.ReflectionUtil.hasField;
import static com.hbyd.parks.common.util.ValHelper.*;
import static org.hibernate.criterion.DetachedCriteria.forClass;
import static org.hibernate.criterion.Projections.rowCount;
import static org.hibernate.criterion.Restrictions.eq;

/**
 * WebService 的通用实现
 * @param <D> DTO 原则上必须是 BaseDTO 的子类，考虑到特殊情况，这里不做限制
 * @param <E> Entity 原则上必须是 BaseEntity 的子类，考虑到特殊情况，这里不做限制
 */
public abstract class BaseWSImpl<D, E> implements BaseWS<D>, RecoverableWS{

    protected Logger logger = Logger.getLogger(this.getClass());

    protected Class<D> dClass;
    protected Class<E> eClass;

    @Resource(name = "defaultBaseDaoImpl")
    protected BaseDao baseDao;

    @Resource
    protected DozerBeanMapper dozerMapper;

    @Resource
    protected SessionFactory sessionFactory;

    protected BaseWSImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        dClass = (Class<D>) pt.getActualTypeArguments()[0];
        eClass = (Class<E>) pt.getActualTypeArguments()[1];
    }

    public Class<D> getdClass() {
        return dClass;
    }

    public void setdClass(Class<D> dClass) {
        this.dClass = dClass;
    }

    public Class<E> geteClass() {
        return eClass;
    }

    public void seteClass(Class<E> eClass) {
        this.eClass = eClass;
    }

    /**验证参数是否合法
     * @param id 实体 ID
     * @param name 实体名称
     */
    protected final void validate(String id, String name) {
//      添加用户时，还没有 id, 但需要校验 name 是否存在，因此 id 可以为 null or empty
//        if (id == null || id.trim().equals("")) {
//            throw new RuntimeException("id can't be empty or null!");
//        }

        if (name == null || name.trim().equals("")) {
            throw new RuntimeException("name can't be empty or null!");
        }
    }

    @Override
    public boolean check(String id, String name) {
        validate(id, name);
        return checkExist(id, name);
    }

    /**检查实体名称是否存在
     * @param id 实体 ID
     * @param name 实体名称
     * @return 如果存在返回 true, 否则 false;
     */
    protected boolean checkExist(String id, String name){
        throw new RuntimeException("该方法尚未实现");
    }

    /**
     * 删除时的确认操作：确认没有子记录
     *
     * @param id  实体 ID
     * @param msg 如有子记录，禁止删除时需要给出的提示信息
     */
    protected void confirmDel(String id, String msg) {
        DetachedCriteria criteria = forClass(eClass).add(eq("parent.id", id));
        if(baseDao.getCnt(criteria) > 0){
            throw getSoapFault(msg, NOT_ALLOWED);
        }
    }

    /**
     * {@link BaseWSImpl#confirmDel(java.lang.String, java.lang.String)}
     */
    protected void confirmDel(String id){
        confirmDel(id, "有子记录未删除，操作终止");
    }

    @Override
    public void delByID(String id) {
        notNullOrEmpty(id);
//        BaseEntity target = (BaseEntity) baseDao.getById(eClass, id);

//      特殊实体主键需要手动添加，非 Hib 生成，因此不能继承 BaseEntity, 下面是为了兼容此类情形
        Object target = baseDao.getById(eClass, id);

//      处理自身关联：有下级，禁止删除
        if(hasField(target, "parent")){
            confirmDel(id);
        }
//      解除外部关联
        disconnectFake(id);

//      删除自身
        baseDao.delete(eClass, id);
    }

   /* @Override
    public void delFake(String id) {
        notNullOrEmpty(id);
        notNull(id, "删除时，ID/ID列表不能为NULL");

//      处理自关联，
       if( unbindSelf(id)>0){
           try {
               throw new Exception("请检查是否有子记录");
           } catch (Exception e) {
               e.printStackTrace();
           }
       }

//      执行伪删
        String hql = "update " + eClass.getName() + " set isValid = :isValid where id = :id";
        sessionFactory.getCurrentSession().createQuery(hql)
                .setBoolean("isValid", false)
                .setString("id", id)
                .executeUpdate();
    }*/

    @Override
    public void delFake(String id) {
        notNullOrEmpty(id);
        RecoverableEntity target = (RecoverableEntity) baseDao.getById(eClass, id);

//      处理自身关联：有下级，禁止删除
        if(hasField(target, "parent")){
            confirmDel(id);
        }
//      解除外部关联
        disconnect(id);

//      "删除"自身
        target.setValid(false);
        baseDao.update(target);
    }


    /**
     * 处理父子关联
     * 1. 有父子关系
     * 1.1 无子记录 -> 允许删除
     * 1.2 有子记录 -> 禁止删除
     * 2. 无父子关系 -> 允许删除
     *
     * @param id 实体ID
     */
    protected int unbindSelf(String id) {
        DetachedCriteria criteria = forClass(eClass);
        if (hasField(eClass, "parent")) {//有上下级关系
            criteria .add(eq("parent.id", id));
            criteria.add(eq("isValid", true));
        }
        return ((Long)criteria.getExecutableCriteria(sessionFactory.getCurrentSession())
                .setProjection(rowCount())
                .uniqueResult())
                .intValue();
    }

    /**
     * 解除实体的外部关联（关联并不意味着有外键，指的是逻辑关联），在删除实体自身前执行
     * @param id    删除目标的 ID
     */
    protected void disconnect(String id){
//        默认没有外部关联
    }

    /**
     * 解除实体的外部关联（关联并不意味着有外键，指的是逻辑关联），在删除实体自身前执行
     * @param id    删除目标的 ID
     */
    protected void disconnectFake(String id) {
//        默认没有外部关联
    }

//  默认更新操作：对象没有任何关联，仅有普通属性
    @Override
    public void update(D dto) {
        throw new RuntimeException("方法尚未实现!");
    }

//  默认保存操作：对象没有任何关联，仅有普通属性
    @Override
    public D save(D dto) {
        throw new RuntimeException("方法尚未实现!");
    }

    @Override
    public final PageBeanEasyUI getPageBean(QueryBeanEasyUI queryBean, String hql_where, Object... params) {

        ValHelper.notNull(queryBean, "queryBean cannot be null!");
        ValHelper.notNull(hql_where, "hql_where cannot be null!");

        String hql = "from " + eClass.getSimpleName() + " " + hql_where;

        PageBeanEasyUI pageBean = baseDao.getPageBean(queryBean, hql, params);

        pageBean.setRows(getDTOList(pageBean.getRows()));

        return pageBean;
    }

    /**将实体分页转换为 DTO 分页
     * @param total 总记录数
     * @param rows 实体分页
     * @param queryBean 分页参数
     * @return DTO 分页
     */
    protected final PageBeanEasyUI getDTOPageBean(Long total, List rows, QueryBeanEasyUI queryBean) {
        PageBeanEasyUI pageBean = new PageBeanEasyUI();

        pageBean.setQueryBean(queryBean);
        pageBean.setTotal(total.intValue());
        pageBean.setRows(getDTOList(rows));

        return pageBean;
    }

    @Override
    public final PageBeanEasyUI getPageBeanByHQL(QueryBeanEasyUI queryBean, String hql, Object... params) {
        PageBeanEasyUI pageBean = baseDao.getPageBean(queryBean, hql, params);
        pageBean.setRows(getDTOList(pageBean.getRows()));
        return pageBean;
    }

    /**将实体列表转化为对应的 DTO 列表
     * @param eList 实体列表
     * @return DTO 列表
     */
    protected final List getDTOList(List eList){
        List dList = new ArrayList(eList.size());
        for (Object entity : eList) {
            dList.add(dozerMapper.map(entity, dClass));
        }
        return dList;
    }

    @Override
    public List<D> findAll() {
//        baseDao.findAll();//不能使用这行代码，因为不知道查询的是 BaseEntity 哪个子类
        List<E> eList = baseDao.findListByCriteria(forClass(eClass));
        return getDTOList(eList);
    }

    @Override
    public List<D> findAllValid(){
        DetachedCriteria criteria = forClass(eClass);
        criteria.addOrder(Order.asc("id"));
        if(hasIsValid(eClass)){//如果有 valid 字段
            criteria.add(eq("isValid", true));
            List<E> eList = baseDao.findListByCriteria(criteria);
            return getDTOList(eList);
        }else{//否则默认全部有效
            return findAll();
        }
    }

    @Override
    public D getByID(String id) {
        ValHelper.notNullOrEmpty(id);

/*
//      id 只可能是 String 和 int 类型
        String idTypeName = null;
        try {
            logger.error("BaseWSImpl.getById: " + eClass.getSimpleName());
//          getField 只查找 public 修饰的字段，因此 BaseEntity 中的 id 需要使用 public 修饰
            idTypeName = eClass.getField("id").getType().getSimpleName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Object found;

        if("int".equals(idTypeName) || "Integer".equals(idTypeName)){
            found = baseDao.getById(eClass, Integer.parseInt(id));
        }else{
            found = baseDao.getById(eClass, id);
        }
*/
        Object found = baseDao.getById(eClass, id);

        if (found == null) {
            return null;
        } else {
            return dozerMapper.map(found, dClass);
        }
    }

    @Override
    public int getRowCount(){
        return baseDao.getRowCount(eClass);
    }

    /**实体是否有 isValid 字段
     * @param clazz 实体类
     * @return true/false
     */
    protected boolean hasIsValid(Class clazz){
        String field = "isValid";
        try {
//            期望 getSuperclass() 取到 RecoverableEntity, 继承来的字段只能通过 getSuperclass() 来获取
            clazz.getSuperclass().getDeclaredField(field);
            return true;
        } catch (NoSuchFieldException e) {
            logger.info(String.format("当前类 %s 没有 %s 字段", eClass.getSimpleName(), field));
            return false;
        }
    }
}
