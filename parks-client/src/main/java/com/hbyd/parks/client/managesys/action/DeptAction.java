package com.hbyd.parks.client.managesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.DeptHelper;
import com.hbyd.parks.client.util.TreeHelper;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.EasyUITree;
import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.ws.managesys.DeptWS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hbyd.parks.client.util.JsonHelper.writeJson;
import static com.hbyd.parks.client.util.JsonHelper.writeMsg;
import static com.hbyd.parks.common.util.ReflectionUtil.invokeWsMethod;

/**
 * Created by len on 14-7-14.
 */
@Controller
@Scope("prototype")
public class DeptAction extends ActionSupport implements ModelDriven<DepartmentDTO> {

    private static final long serialVersionUID = 1L;

    //add、edit 提交的参数
    private DepartmentDTO dept = new DepartmentDTO();

    //这个两个变量是tree拖拽时，将信息保存到数据库所使用的
    private String targetNodeId;
    private String sourceNodeId;
    private String point;

    @Resource
    private DeptWS deptWS;

    private Gson gson = new Gson();

    public String getTree() {
        List<DepartmentDTO> list = deptWS.findAllValid();
        List<EasyUITree> trees = TreeHelper.getDeptTree(list);
        String result = gson.toJson(trees);
        writeJson(result);
        return null;
    }

    //通过deptId找到当前部门以及子部门
    public String getTreeByDeptId() {
        UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
        String deptId = user.getDepartment().getId();
        // String deptId="38";
        List<DepartmentDTO> list;
        if (deptId == null) {
            list = deptWS.findAllValid();
        } else {
            DepartmentDTO parent = deptWS.getByID(deptId);
            list = DeptHelper.getDeptChildren(parent);
        }
        List<EasyUITree> trees = TreeHelper.getDeptTree(list);
        String result = gson.toJson(trees);
        writeJson(result);
        return null;
    }

    public String getDeptsByDeptId() {
        UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
        String deptId = user.getDepartment().getId();
        // String deptId="38";
        List<DepartmentDTO> list;
        if (deptId == null) {
            list = deptWS.findAll();
        } else {
            DepartmentDTO parent = deptWS.getByID(deptId);
            list = DeptHelper.getDeptChildren(parent);
        }

        String result = gson.toJson(list);
        writeJson(result);
        return null;
    }

    public String deptList() throws IOException {

        List<DepartmentDTO> list = deptWS.findAllValid();
        String result = gson.toJson(list);
        writeJson(result);
        return null;

    }

    public String moveDept() {
        AjaxMessage<DepartmentDTO> message = new AjaxMessage<>();
        try {

            DepartmentDTO sourceNode = deptWS.getByID(sourceNodeId);
            //如果point为append，源节点的父id就是目标节点的id
            if (point.equals("append")) {
                sourceNode.setParentId(targetNodeId);
            }
            //如果point为top、bottom，源节点的和目标节点的父id相同
            else {
                DepartmentDTO targetNode = deptWS.getByID(targetNodeId);
                sourceNode.setParentId(targetNode.getParentId());
            }

            deptWS.update(sourceNode);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("获取部门信息出错");

        } finally {
            String result = gson.toJson(message);
            writeJson(result);
        }
        return null;
    }

    public String getDept() {
        AjaxMessage<DepartmentDTO> message = new AjaxMessage<>();
        try {
            DepartmentDTO deptByID = deptWS.getByID(dept.getId());
            ArrayList<DepartmentDTO> data = new ArrayList<>();
            data.add(deptByID);
            message.setSuccess(true);
            message.setData(data);


        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("获取部门信息出错");

        } finally {
            String result = gson.toJson(message);
            writeJson(result);
        }

        return null;
    }

    public String addDept() {
        AjaxMessage message = new AjaxMessage();
        try {
            DepartmentDTO deptDTO = deptWS.save(dept);
            ArrayList<DepartmentDTO> data = new ArrayList<>();
            data.add(deptDTO);
            message.setData(data);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            writeJson(result);
        }
        return null;
    }

    public String editDept() {
        AjaxMessage message = new AjaxMessage();
        try {
            deptWS.update(dept);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            writeJson(result);
        }
        return null;
    }

    public String deleteDept() {
        String methodName = "delFake";
        Class[] paramTypes = {String.class};
        Object[] args = {dept.getId()};

        writeMsg(invokeWsMethod(deptWS, methodName, paramTypes, args));

        return null;
    }

    public String validateNameExist() {

        return null;
    }

    @Override
    public DepartmentDTO getModel() {
        return dept;
    }

    public String getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(String targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }
}
