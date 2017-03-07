package com.hbyd.parks.client.attendancesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.DeptHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.ws.attendancesys.ShiftAssignWS;
import com.hbyd.parks.ws.managesys.DeptWS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hbyd.parks.common.util.SqlHelper.getStringCandidadtes;

/**
 * Created by len on 14-8-4.
 */
@Controller
@Scope("prototype")
public class shiftAssignAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {

    private QueryBeanEasyUI page = new QueryBeanEasyUI();
    //查询用到的参数
    private String beginMonth;
    private String endMonth;
    private String empName;
    //排班用到的参数
    private Date begin;
    private Date end;
    private String deptId;
    private String regularShiftId;
    private List<String> empIds;
    //更新某人某天的班次
    private String shiftAssignId;
    private int day;
    private String shiftId;


    @Override

    public QueryBeanEasyUI getModel() {
        return page;
    }

    private Gson gson = new Gson();

    @Resource
    private ShiftAssignWS shiftAssignWS;

    @Resource
    private DeptWS deptWS;

    public String shiftAssignList() {

        String hql = "where yearAndMonth>=? and yearAndMonth<=? ";
        List params = new ArrayList();
        params.add(beginMonth);
        params.add(endMonth);

        UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
        String tempId = user.getDepartment().getId();
        DepartmentDTO deptDTO = deptWS.getByID(tempId);
        List<String> deptParam = DeptHelper.getDeptChildrenIds(deptDTO);
        hql += " and employee.department.id in " + getStringCandidadtes(deptParam);
        if (deptId != null && !deptId.equals("")) {
            hql += " and employee.department.id=?";
            params.add(deptId);
        }
        if (empIds != null) {
            hql += " and employee.id in " + getStringCandidadtes(empIds);
        }
        if (empName != null && !empName.equals("")) {
            hql += " and employee.empName like ?";
            params.add(empName + "%");
        }
        PageBeanEasyUI list = shiftAssignWS.getPageBean(page, hql, params.toArray());
        // 如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    public String assignShiftForDept() {
        AjaxMessage message = new AjaxMessage();
        try {
            shiftAssignWS.assignShiftForDept(begin, end, deptId, regularShiftId);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("排班失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String assignShiftForEmp() {
        AjaxMessage message = new AjaxMessage();
        try {
            shiftAssignWS.assignShiftForEmp(begin, end, empIds.get(0), regularShiftId);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String assignShiftForEmps() {
        AjaxMessage message = new AjaxMessage();
        try {
            int size = empIds.size();
            String[] emps = empIds.toArray(new String[size]);
            shiftAssignWS.assignShiftForEmps(begin, end, emps, regularShiftId);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("排班失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String updateDayShiftForEmp() {
        AjaxMessage message = new AjaxMessage();
        try {
            shiftAssignWS.updateDayShiftForEmp(shiftAssignId, day, shiftId);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("班次修改失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }


        return null;
    }

    public String deleteShiftAssign() {
        AjaxMessage message = new AjaxMessage();
        try {
            shiftAssignWS.delByID(shiftAssignId);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("删除失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String validateNameExist() {

        return null;
    }


    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


    public String getRegularShiftId() {
        return regularShiftId;
    }

    public void setRegularShiftId(String regularShiftId) {
        this.regularShiftId = regularShiftId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftAssignId() {
        return shiftAssignId;
    }

    public void setShiftAssignId(String shiftAssignId) {
        this.shiftAssignId = shiftAssignId;
    }


    public String getBeginMonth() {
        return beginMonth;
    }

    public void setBeginMonth(String beginMonth) {
        this.beginMonth = beginMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<String> getEmpIds() {
        return empIds;
    }

    public void setEmpIds(List<String> empIds) {
        this.empIds = empIds;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
