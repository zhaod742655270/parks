package com.hbyd.parks.client.supportsys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.DeptHelper;
import com.hbyd.parks.client.util.ExportExcelHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.supportsys.EmployeeDTO;
import com.hbyd.parks.ws.managesys.DeptWS;
import com.hbyd.parks.ws.supportsys.EmployeeWS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import  com.hbyd.parks.client.util.DeptHelper;
import static com.hbyd.parks.client.util.PhotoHelper.*;
import static com.hbyd.parks.common.util.SqlHelper.getStringCandidadtes;

/**
 * Created by len on 14-7-14.
 */
@Controller
@Scope("prototype")
public class EmployeeAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {

    private static final long serialVersionUID = 1L;
    private QueryBeanEasyUI page = new QueryBeanEasyUI();

    //add、edit 提交的参数
    private EmployeeDTO emp;

    private String deptId;

    private String emptyRows = "{\"total\":0,\"rows\":[]}";

    private String isInvolve;

    //delete使用的empId
    private String empId;

    //搜索提交的参数
    private String key;

    private String empName;

    private List<String> empIds;

    private File upload;
    private String uploadFileName;
    private String uploadContentType;
    private boolean delPhotoFlag;


    public QueryBeanEasyUI getModel() {
        return page;
    }

    @Resource
    private EmployeeWS empWS;

    @Resource
    private DeptWS deptWS;

    private Gson gson = new Gson();

    public String empList() throws IOException {

        PageBeanEasyUI list = getMatchEmployee();
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);

        return null;

    }


    public String getEmpsByName() {

        String hql = "where isValid=1 and empName like ?  ";
        hql += " and department.id  in " + getCurrentAndChildrenDeptIds();
        List params = new ArrayList();
        params.add(empName + "%");
        PageBeanEasyUI pageBean = empWS.getPageBean(page, hql, params.toArray());
        String result = pageBean.getRows() == null ? emptyRows : gson.toJson(pageBean);
        JsonHelper.writeJson(result);
        return null;
    }

    private PageBeanEasyUI getMatchEmployee() {
        String hql = "where isValid=1 ";
        List params = new ArrayList();

        if (emp != null) {

            if (emp.getDeptId() != null && !emp.getDeptId().equals("")) {
                hql += " and department.id=? ";
                params.add(emp.getDeptId());
            }
            if (emp.getEmpName() != null && !emp.getEmpName().equals("")) {
                hql += " and empName like ? ";
                params.add("%" + emp.getEmpName() + "%");
            }
            if (emp.getEmpPayNo() != null && !emp.getEmpPayNo().equals("")) {
                hql += " and empPayNo like ? ";
                params.add("%" + emp.getEmpPayNo() + "%");
            }
            if (emp.getEmpIDNo() != null && !emp.getEmpIDNo().equals("")) {
                hql += " and empIDNo like ? ";
                params.add("%" + emp.getEmpIDNo() + "%");
            }
        }
        //限制用户所能管辖的部门（当前以及子部门）
        hql += " and department.id in " + getCurrentAndChildrenDeptIds();
        //为了导出Excel方法也可以使用该通用方法，手动设置page的参数.
        if (page.getSorts()==null || page.getOrders().equals(""))
        {
            page.setRows(1000000);
            page.setOrder("asc");
            page.setSort("empName");
        }
        PageBeanEasyUI list = empWS.getPageBean(page, hql, params.toArray());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null)
            list.setRows(new ArrayList());
        return list;
    }

    private String getCurrentAndChildrenDeptIds() {

        UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
        String deptId = user.getDepartment().getId();
        DepartmentDTO deptDTO = deptWS.getByID(deptId);
        List<String> deptParam = DeptHelper.getDeptChildrenIds(deptDTO);
        return getStringCandidadtes(deptParam);
    }

    public String addEmp() {
        AjaxMessage message = new AjaxMessage();
        try {
            //如果删除标志为false，并且上传照片了，员工信息关联照片，保存照片
            if (delPhotoFlag == false && upload != null) {
                //关联照片
                emp = relatedPhoto(uploadFileName, emp);
                //保存照片
                savePhoto(upload, emp.getPhotoName());
            }
            //保存员工信息
            emp = empWS.save(emp);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editEmp() {
        AjaxMessage message = new AjaxMessage();
        try {
            //获得emp的旧照片名。
            EmployeeDTO empTmp = empWS.getByID(emp.getId());
            //设置照片名，如果照片名为null，数据库该字段的值不会发生变化。
            emp.setPhotoName(empTmp.getPhotoName());
            String oldPhotoName = emp.getPhotoName();
            //删除标志为false并且有文件上传，进行关联或者修改操作。
            if (delPhotoFlag == false && upload != null) {
                //有照片的，删除旧照片。
                if (oldPhotoName != null) {
                    delPhoto(oldPhotoName);
                }
                //关联照片
                emp = relatedPhoto(uploadFileName, emp);
                //保存照片
                savePhoto(upload, emp.getPhotoName());

            }
            //删除标志为true的,置空photoName,删除照片
            else {
                emp.setPhotoName(null);
                if (oldPhotoName != null) {
                    delPhoto(oldPhotoName);
                }

            }
            //更新emp
            empWS.update(emp);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }


    public String deleteEmp() {
        AjaxMessage message = new AjaxMessage();
        try {

            empWS.delFake(empId);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("删除失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }


        return null;
    }

    public void exportExcel() throws IOException {

        PageBeanEasyUI list = getMatchEmployee();
        ExportExcelHelper.exportEmployee("员工", list.getRows());


    }

    public String switchAllEmp() {
        AjaxMessage message = new AjaxMessage();
        try {
            empWS.updateInvolveStatusForDept(deptId, isInvolve.equals("1"));

        } catch (Exception e) {
            message.setSuccess(false);
            String m = "1".equals(isInvolve) ? "开启失败" : "关闭失败";
            message.setMessage(m);
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);

        }
        return null;
    }

    public String switchEmp() {
        AjaxMessage message = new AjaxMessage();
        try {

            empWS.updateInvolveStatusForEmps(empIds, "1".equals(isInvolve));

        } catch (Exception e) {
            message.setSuccess(false);
            String m = "1".equals(isInvolve) ? "开启失败" : "关闭失败";
            message.setMessage(m);
        } finally {

            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String getEmpByDeptId() {

        String hql = "where isValid=1 and department.id = ?";
        List params = new ArrayList();
        String result = emptyRows;

        if (deptId != null) {
            params.add(deptId);
            if (key != null) {
                hql += " and empName like ?";
                params.add("%" + key + "%");
            }
            PageBeanEasyUI list = empWS.getPageBean(page, hql, params.toArray());
            //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
            if (list.getRows() == null) list.setRows(new ArrayList());
            result = gson.toJson(list);
        }
        JsonHelper.writeJson(result);
        return null;
    }

    public String validateNameExist() {

        return null;
    }

    public EmployeeDTO getEmp() {
        return this.emp;
    }

    public void setEmp(EmployeeDTO emp) {
        this.emp = emp;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }


    public String getIsInvolve() {
        return isInvolve;
    }

    public void setIsInvolve(String isInvolve) {
        this.isInvolve = isInvolve;
    }

    public List<String> getEmpIds() {
        return empIds;
    }

    public void setEmpIds(List<String> empIds) {
        this.empIds = empIds;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpName() {
        return this.empName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }


    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public boolean getDelPhotoFlag() {
        return delPhotoFlag;
    }

    public void setDelPhotoFlag(boolean delPhotoFlag) {
        this.delPhotoFlag = delPhotoFlag;
    }
}
