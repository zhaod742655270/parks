package com.hbyd.parks.client.meetsys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.managesys.RoleDTO;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.meetsys.MeetRoomDTO;
import com.hbyd.parks.ws.meetsys.MeetRoomWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2015/3/9.
 */
@Controller
@Scope("prototype")
public class MeetRoomAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {
    private static final long serialVersionUID = 1L;
    private QueryBeanEasyUI page = new QueryBeanEasyUI();
    //add、edit 提交的参数
    private MeetRoomDTO meetRoom;


    //delete使用的meetRoomId
    private String meetRoomId;

    //搜索提交的参数
    private String key;

    private String meetRoomName;

    public QueryBeanEasyUI getModel() {
        return page;
    }

    @Resource
    private MeetRoomWS meetRoomWS;

    private Gson gson = new Gson();

    public String meetRoomList() throws IOException {

        String hql = "where name like ?";
        List params = new ArrayList();
        params.add("%"+key + "%");

        PageBeanEasyUI list = key == null ? meetRoomWS.getPageBean(page, "", null) : meetRoomWS.getPageBean(page, hql, params.toArray());
        //如果meetRooms为null，需要实例化一下。如果不实例化，序列化为json时没有meetrooms属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }
    public String getAllMeetRooms() {
        List<MeetRoomDTO> list = meetRoomWS.findAll();
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;
    }

    public String addMeetRoom() {
        AjaxMessage message = new AjaxMessage();
        try {
            meetRoomWS.save(meetRoom);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editMeetRoom() {
        AjaxMessage message = new AjaxMessage();
        try {
            meetRoomWS.update(meetRoom);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteMeetRoom() {
        AjaxMessage message = new AjaxMessage();
        try {
            meetRoomWS.delByID(meetRoomId);

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
    public void setMeetRoom(MeetRoomDTO meetRoom){this.meetRoom=meetRoom;}
    public MeetRoomDTO getMeetRoom(){return this.meetRoom;}
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getMeetRoomId() {
        return meetRoomId;
    }
    public void setMeetRoomId(String meetRoomId) {
        this.meetRoomId = meetRoomId;
    }
    public String getMeetRoomName() {
        return meetRoomName;
    }
    public void setMeetRoomName(String meetRoomName) {
        this.meetRoomName =meetRoomName;
    }

}
