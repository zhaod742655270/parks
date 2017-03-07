package com.hbyd.parks.client.attendancesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.ExportExcelHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.ws.attendancesys.AtteInfoWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created by len on 14-8-4.
 */
@Controller
@Scope("prototype")
public class AttendanceRecordAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {

    private QueryBeanEasyUI page = new QueryBeanEasyUI();
    //搜索提交的参数

    private String deptId;
    private String userName;
    private String date;
    private List<String> timeSpan1Option;
    private List<String> timeSpan2Option;

    @Override
    public QueryBeanEasyUI getModel() {
        return page;
    }
    private Gson gson = new Gson();
    @Resource
    private AtteInfoWS atteInfoWS;

    public void exportExcel() throws IOException {
        String result = "";
        //如果时间为空 不查询
        if (!date.equals("")) {
            page.setSorts(new String[]{"tai.deptFK"});
            page.setOrders(new String[]{"asc"});
            page.setRows(Integer.MAX_VALUE);
            List parameters = new ArrayList();
            String hql = getQueryString(parameters);
            PageBeanEasyUI list = atteInfoWS.getPageBeanByHQL(page, hql, parameters.toArray());

            ExportExcelHelper.exporAccessEvent(list.getRows());

        }

    }


    public String attendanceRecordList() {


        String result = "";
        //如果时间为空 不查询
        if (date.equals("")) {
            result = "{'total':0,'rows':[]}";
        } else {
            List parameters = new ArrayList();
            String hql = getQueryString(parameters);
            PageBeanEasyUI list = atteInfoWS.getPageBeanByHQL(page, hql, parameters.toArray());
            // 如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
            if (list.getRows() == null) list.setRows(new ArrayList());
            result = gson.toJson(list);
        }
        JsonHelper.writeJson(result);
        return null;

    }

    private String getQueryString(List parameters) {
        StringBuffer queryBuf = new StringBuffer(" select distinct tai from AtteInfo tai left join fetch tai.details as tad  left join fetch tad.interval  as ti ");
        queryBuf.append(" where tai.id in( select distinct ai.id from AtteInfo ai left join  ai.details as ad  left join  ad.interval as i ");
        // boolean firstClause = true;

        if (date != null) {

            try {
                Date d = DateUtil.parseDate(date);
                parameters.add(d);
                queryBuf.append(" where ai.day =?");
            } catch (Exception e) {

            }

        }
        if (deptId != null && !deptId.equals("")) {
            queryBuf.append(" and ai.deptFK =?");
            parameters.add(deptId);
        }
        if (userName != null && !userName.equals("")) {
            queryBuf.append(" and ai.empName like ?");
            String name = "%" + userName + "%";
            parameters.add(name);
        }
        if (timeSpan1Option != null && timeSpan2Option == null) {
            queryBuf.append(" and i.type = '上午时段' ");
            for (String option : timeSpan1Option) {
                if (option.equals("迟到")) {
                    queryBuf.append(" and ad.arriveLateNum>0 ");
                } else if (option.equals("早退")) {
                    queryBuf.append(" and ad.leaveEarlyNum>0 ");
                } else if (option.equals("旷工")) {
                    queryBuf.append(" and ad.absentNum>0 ");
                } else if (option.equals("请假")) {
                    queryBuf.append(" and ad.leaveNum>0 ");
                }
            }
        }
        if (timeSpan1Option == null && timeSpan2Option != null) {
            queryBuf.append(" and i.type = '下午时段' ");
            for (String option : timeSpan2Option) {
                if (option.equals("迟到")) {
                    queryBuf.append(" and ad.arriveLateNum>0 ");
                } else if (option.equals("早退")) {
                    queryBuf.append(" and ad.leaveEarlyNum>0 ");
                } else if (option.equals("旷工")) {
                    queryBuf.append(" and ad.absentNum>0 ");
                } else if (option.equals("请假")) {
                    queryBuf.append(" and ad.leaveNum>0 ");
                }
            }
        }
        if (timeSpan1Option != null && timeSpan2Option != null) {
            Set set = new HashSet();
            for (String option : timeSpan1Option) {
                set.add(option);
            }
            for (String option : timeSpan2Option) {
                set.add(option);
            }
            for (Object option : set) {
                if (option.equals("迟到")) {
                    queryBuf.append(" and ai.arriveLateCount>0 ");
                } else if (option.equals("早退")) {
                    queryBuf.append(" and ai.leaveEarlyCount>0 ");
                } else if (option.equals("旷工")) {
                    queryBuf.append(" and ai.absentCount>0 ");
                } else if (option.equals("请假")) {
                    queryBuf.append(" and ai.leaveCount>0 ");
                }
            }
        }
        queryBuf.append(" )");
        return queryBuf.toString();
    }


    public String getDeptId() {
        return deptId;
    }


    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public List<String> getTimeSpan1Option() {
        return timeSpan1Option;
    }

    public void setTimeSpan1Option(List<String> timeSpan1Option) {
        this.timeSpan1Option = timeSpan1Option;
    }

    public List<String> getTimeSpan2Option() {
        return timeSpan2Option;
    }

    public void setTimeSpan2Option(List<String> timeSpan2Option) {
        this.timeSpan2Option = timeSpan2Option;
    }
}
