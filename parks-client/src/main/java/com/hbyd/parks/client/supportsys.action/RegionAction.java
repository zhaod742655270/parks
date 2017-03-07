package com.hbyd.parks.client.supportsys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.DeptHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.client.util.TreeHelper;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.EasyUITree;
import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.supportsys.RegionDTO;
import com.hbyd.parks.ws.managesys.DeptWS;
import com.hbyd.parks.ws.supportsys.RegionWS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 14-7-14.
 */
@Controller
@Scope("prototype")
public class RegionAction extends ActionSupport implements ModelDriven<RegionDTO> {

    private static final long serialVersionUID = 1L;

    //add、edit 提交的参数
    private RegionDTO region = new RegionDTO();

    //这个两个变量是tree拖拽时，将信息保存到数据库所使用的
    private String targetNodeId;
    private String sourceNodeId;
    private String point;

    @Resource
    private RegionWS regionWS;

    private Gson gson = new Gson();

    public String getTree() {
        List<RegionDTO> list = regionWS.findAll();
        List<EasyUITree> trees = TreeHelper.getRegionTree(list);
        String result = gson.toJson(trees);
        JsonHelper.writeJson(result);
        return null;
    }

    public String regionList() throws IOException {

        List<RegionDTO> list = regionWS.findAll();
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    public String moveRegion() {
        AjaxMessage<RegionDTO> message = new AjaxMessage<>();
        try {

            RegionDTO sourceNode = regionWS.getByID(sourceNodeId);
            //如果point为append，源节点的父id就是目标节点的id
            if (point.equals("append")) {
                sourceNode.setParentFK(targetNodeId);
            }
            //如果point为top、bottom，源节点的和目标节点的父id相同
            else {
                RegionDTO targetNode = regionWS.getByID(targetNodeId);
                sourceNode.setParentFK(targetNode.getParentFK());
            }

            regionWS.update(sourceNode);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("获取区域信息出错");

        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String getRegionById() {
        AjaxMessage<RegionDTO> message = new AjaxMessage<>();
        try {
            RegionDTO regionDTO = regionWS.getByID(region.getId());
            ArrayList<RegionDTO> data = new ArrayList<>();
            data.add(regionDTO);
            message.setSuccess(true);
            message.setData(data);


        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("获取区域信息出错");

        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String addRegion() {
        AjaxMessage message = new AjaxMessage();
        try {
            RegionDTO regionDTO = regionWS.save(region);
            ArrayList<RegionDTO> data = new ArrayList<>();
            data.add(regionDTO);
            message.setData(data);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String editRegion() {
        AjaxMessage message = new AjaxMessage();
        try {
            regionWS.update(region);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteRegion() {
        AjaxMessage message = new AjaxMessage();
        try {
            regionWS.delByID(region.getId());

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

    @Override
    public RegionDTO getModel() {
        return region;
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
