package com.hbyd.parks.client.supportsys.action;

import com.google.common.base.Strings;
import com.hbyd.parks.client.queryBean.DeviceQueryBean;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.hql.HqlQuery;
import com.hbyd.parks.common.hql.Predicate;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.supportsys.DeviceDescRelationWS;
import com.hbyd.parks.ws.supportsys.DeviceWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;

import static com.hbyd.parks.common.hql.Predicate.*;



/**
 * Created by Len on 2015/3/12.
 */
@Controller
@Scope("prototype")
public class DeviceAction extends ActionSupport implements ModelDriven<DeviceQueryBean> {
    private static final long serialVersionUID = 1L;
    private DeviceQueryBean qBean = new DeviceQueryBean();

    public DeviceQueryBean getModel() {
        return qBean;
    }

    //  服务
    @Resource
    private DeviceWS deviceWS;

    @Resource
    private DeviceDescRelationWS deviceDescRelationWS;

    /**
     * 查出某个会议室关联的设备列表
     *
     * @return
     * @throws Exception
     */
    public String meetRoomRelatedeviceList() throws Exception {
        HqlQuery query = new HqlQuery(" from Device di ");
        query.and(eq("di.roomFK", qBean.getRoomId()));
        Object[] params = query.getParametersValue();
        qBean.setPage(1);
        qBean.setRows(10000);
        query(qBean, query.toString(), params);
        return null;
    }

    /**
     * 查出没有关联会议室的设备列表
     *
     * @return
     * @throws Exception
     */
    public String meetRoomNotRelatedeviceList() throws Exception {
        HqlQuery query = new HqlQuery("from Device di ");
        query.and(new Predicate("di.roomFK is null"));
        query.and(or(in("oneType",new String[]{"44","4"}),eq("devTypeFk","90")));
        if (!Strings.isNullOrEmpty(qBean.getDescInfo())) {
            query.and(like("di.descInfo", "%" + qBean.getDescInfo() + "%"));
        }
        Object[] params = query.getParametersValue();
        query(qBean, query.toString(), params);
        return null;
    }

    /**
     * 查询结果
     *
     * @param hql    HQL 语句
     * @param params 查询参数，如果没有，就传入 NULL
     */
    private void query(QueryBeanEasyUI qb, String hql, Object[] params) {
        PageBeanEasyUI pageBean = deviceWS.getPageBeanByHQL(qb, hql, params);

//      如果没有数据，Dozer 会将服务端的空集合，在客户端反序列化为 null
        if (pageBean.getRows() == null) {
            pageBean.setRows(new ArrayList());
        }

        JsonHelper.writeJson(pageBean);
    }
}
