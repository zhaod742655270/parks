package com.hbyd.parks.client.supportsys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.meetsys.MeetRoomDTO;
import com.hbyd.parks.dto.supportsys.DeviceDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.DeviceDescRelationDTO;
import com.hbyd.parks.ws.supportsys.DeviceDescRelationWS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by feng on 2015/3/11.
 */
@Controller
@Scope("prototype")
public class DeviceDescRelationAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {


    private QueryBeanEasyUI page = new QueryBeanEasyUI();

    public QueryBeanEasyUI getModel() {
        return page;
    }
    private String deviceId;
    private DeviceDescRelationDTO deviceDescRelation;
    private String id;
    private String roomId;
    private Gson gson = new Gson();
   @Resource
    private DeviceDescRelationWS deviceDescRelationWS;

    public String distribute() {
        AjaxMessage message = new AjaxMessage();
        try {
            deviceDescRelation=new DeviceDescRelationDTO();
            deviceDescRelation.setId(deviceId);
            deviceDescRelation.setRoomFK(roomId);
            deviceDescRelationWS.save(deviceDescRelation);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("分配失败");
            e.printStackTrace();
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String withdraw() {
        AjaxMessage message = new AjaxMessage();
        try {
            deviceDescRelationWS.delByID(id);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("收回失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

  public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

  public DeviceDescRelationWS getDeviceDescRelationWS() {
      return deviceDescRelationWS;
  }

    public void setDeviceDescRelationWS(DeviceDescRelationWS deviceDescRelationWS) {
        this.deviceDescRelationWS = deviceDescRelationWS;
    }
    public DeviceDescRelationDTO getDeviceDescRelation() {
        return deviceDescRelation;
    }

    public void setDeviceDescRelationDTO(DeviceDescRelationDTO deviceDescRelation) {
        this.deviceDescRelation = deviceDescRelation;
    }
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


}
