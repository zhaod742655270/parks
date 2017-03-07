package com.hbyd.parks.client.doorsys.action;

import com.google.common.base.Strings;
import com.hbyd.parks.client.queryBean.AccessEventQueryBean;
import com.hbyd.parks.client.util.DeptHelper;
import com.hbyd.parks.client.util.ExportExcelHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.hql.HqlQuery;
import com.hbyd.parks.common.hql.Predicate;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.dto.doorsys.AccessEventStatusDTO;
import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.supportsys.DeviceDescRelationDTO;
import com.hbyd.parks.ws.doorsys.AccessEventInfoWS;
import com.hbyd.parks.ws.managesys.DeptWS;
import com.hbyd.parks.ws.supportsys.DeviceDescRelationWS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.hbyd.parks.common.hql.Predicate.*;
import static com.hbyd.parks.common.util.SqlHelper.getStringCandidadtes;

/**
 * 刷卡记录 Action
 *
 * @author ren_xt
 */
@Controller
@Scope("prototype")
public class AccessEventAction extends ActionSupport implements ModelDriven<AccessEventQueryBean> {
    //  模型(查询模型)
    private AccessEventQueryBean qBean = new AccessEventQueryBean();

    @Override
    public AccessEventQueryBean getModel() {
        return qBean;
    }

    //  服务
    @Resource
    private AccessEventInfoWS accessEventInfoWS;

    @Resource
    private DeptWS deptWS;

    @Resource
    private DeviceDescRelationWS deviceDescRelationWS;

    /**
     * 通过车次号，得到随行的打卡记录
     *
     * @return
     * @throws Exception
     */
    public String getAccompanyList() throws Exception {
        qBean.setRows(10000);
        qBean.setOrder("asc");
        qBean.setPage(1);
        qBean.setSort("passSn");
        accessEventList();
        return null;
    }

    /**
     * @return
     * @throws Exception
     */
    public String accessEventList() throws Exception {
        HqlQuery query = new HqlQuery(" from AccessEventInfo ai");
        String hql = hql(query, 0);
        Object[] params = query.getParametersValue();
        query(hql, params);
        return null;
    }

    /**
     * 返回进出记录实时表的数据
     *
     * @return
     * @throws Exception
     */
    public String accessEventLatestList() throws Exception {
        HqlQuery query = new HqlQuery(" from AccessEventInfoLatest ai");
        String hql = hql(query, 1);
        Object[] params = query.getParametersValue();
        query(hql, params);
        return null;
    }

    /**
     * 查询状态信息：园区内员工人数，园区内访客人数，园区内员工车辆数，园区内访客车辆数
     */
    public String getStatus() throws Exception {
        AccessEventStatusDTO status = accessEventInfoWS.getStatus();
        JsonHelper.writeJson(status);
        return null;
    }

    public String accessEventRelateMeetRoomList() throws Exception {


        HqlQuery query = new HqlQuery(" from AccessEventInfo ai");

        String hql = hql(query, 0);
        Object[] params = query.getParametersValue();
        query(hql, params);
        return null;
    }

    private List<String> getDeviceIdByRoomId() {
        AccessEventQueryBean q = new AccessEventQueryBean();
        q.setPage(1);
        q.setRows(10000);
        q.setOrder("asc");
        q.setSort("id");
        HqlQuery query = new HqlQuery(" from DeviceDescRelation dr ");
        query.and(eq("dr.roomFK", qBean.getRoomId()));
        Object[] params = query.getParametersValue();
        PageBeanEasyUI page = deviceDescRelationWS.getPageBeanByHQL(q, query.toString(), params);
        List<String> deviceIds = new ArrayList<>();
        List<DeviceDescRelationDTO> list = page.getRows();
        for (DeviceDescRelationDTO d : list) {
            deviceIds.add(d.getId());
        }
        return  deviceIds;
    }

    /**
     * 统一的刷卡查询
     *
     * @param q     用于指定从哪个实体查询,
     * @param qType 如果为0查询出入记录查询，如果为1是出入实时记录查询,2会议签到查询用
     * @return HQL
     */
    private String hql(HqlQuery q, int qType) {

        if (qBean.isCardNoCheck()) {
            q.and(like("cardNo", "%" + qBean.getCardNo() + "%"));
        }
        if (qBean.isDeptIdCheck() && !qBean.getDeptId().equals("")) {
            q.and(eq("deptId", qBean.getDeptId()));
        }
        //如果没有选择部门,且当前用户部门非顶级部门的，查询当前用户部门以及子部门下的人员
        else if (getCurrentUser().getDepartment().getParentId() != null) {
            String inDepts = "deptId in " + getCurrentAndChildrenDeptIds();
            q.and(new Predicate(inDepts));
        }
        if (qBean.isDoorNameCheck()) {
            q.and(like("doorName", "%" + qBean.getDoorName() + "%"));
        }

        if (qBean.isPersonNameCheck()) {
            q.and(like("personName", "%" + qBean.getPersonName() + "%"));
        }
        if (qBean.isPlateNoCheck()) {
            q.and(or(like("upPlateNo", "%" + qBean.getPlateNo() + "%"), like("regPlateNo", "%" + qBean.getPlateNo() + "%")));
        }
        if (!Strings.isNullOrEmpty(qBean.getPassId())) {
            q.and(eq("passId", qBean.getPassId()));
        }
        if(!Strings.isNullOrEmpty(qBean.getRoomId())){
            q.and(in("devUUID",getDeviceIdByRoomId().toArray()));
        }
            String recordType = qBean.getRecordType();
        if (!Strings.isNullOrEmpty(recordType)) {
            switch (recordType) {
                case "1":
                    break;
                case "2":
                    q.and(new Predicate("personType is not null"));
                    if (qType == 1)
                        q.and(eq("personOrCarFlag", false));
                    break;
                case "3":
                    q.and(eq("personType", "0"));
                    if (qType == 1)
                        q.and(eq("personOrCarFlag", false));
                    break;
                case "4":
                    q.and(eq("personType", "1"));
                    if (qType == 1)
                        q.and(eq("personOrCarFlag", false));
                    break;
                case "5":
                    q.and(new Predicate("carType is not null"));
                    if (qType != 1) q.and(new Predicate("passSn='1'"));
                    break;
                case "6":
                    q.and(eq("carType", "0"));
                    if (qType == 1) q.and(eq("personOrCarFlag", true));
                    else q.and(new Predicate("passSn='1'"));
                    break;
                case "7":
                    q.and(eq("carType", "1"));
                    if (qType == 1) q.and(eq("personOrCarFlag", true));
                    else q.and(new Predicate("passSn='1'"));
                    break;
            }
        }
        //如果设备类型不为空且不等于-1
        if (!Strings.isNullOrEmpty(qBean.getDeviceType()) && !qBean.getDeviceType().equals("-1")) {
            q.and(eq("devType", qBean.getDeviceType()));
        }
        String bt = qBean.getBeginTime();
        String et = qBean.getEndTime();

//如果客户端不指定，传到这里就是空字符串
        try {
            if (bt != null && !bt.trim().equals("")) {
                q.and(ge("eventtime", DateUtil.parseDateTime(bt)));
            }
            if (et != null && !et.trim().equals("")) {
                q.and(le("eventtime", DateUtil.parseDateTime(et)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return q.getQueryString();
    }


    /**
     * 查询结果
     *
     * @param hql    HQL 语句
     * @param params 查询参数，如果没有，就传入 NULL
     */
    private void query(String hql, Object[] params) {
        PageBeanEasyUI pageBean = accessEventInfoWS.getPageBeanByHQL(qBean, hql, params);

//      如果没有数据，Dozer 会将服务端的空集合，在客户端反序列化为 null
        if (pageBean.getRows() == null) {
            pageBean.setRows(new ArrayList());
        }

        JsonHelper.writeJson(pageBean);
    }

    private UserDTO getCurrentUser() {
        UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
        return user;
    }

    private String getCurrentAndChildrenDeptIds() {

        String deptId = getCurrentUser().getDepartment().getId();
        DepartmentDTO deptDTO = deptWS.getByID(deptId);
        List<String> deptParam = DeptHelper.getDeptChildrenIds(deptDTO);
        return getStringCandidadtes(deptParam);
    }

    /**
     * 导出刷卡数据的excel
     *
     * @throws IOException
     */
    public void exportExcel() throws IOException {


        qBean.setSorts(new String[]{"eventtime"});
        qBean.setOrders(new String[]{"desc"});
        qBean.setRows(10000);
        HqlQuery query = new HqlQuery(" from AccessEventInfo ai");
        String hql = hql(query, 0);
        Object[] params = query.getParametersValue();
        PageBeanEasyUI pageBean = accessEventInfoWS.getPageBeanByHQL(qBean, hql, params);
        ExportExcelHelper.exporAccessEvent(pageBean.getRows());

    }

}
