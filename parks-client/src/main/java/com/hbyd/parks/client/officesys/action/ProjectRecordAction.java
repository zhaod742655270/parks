package com.hbyd.parks.client.officesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.RecordQueryBean;
import com.hbyd.parks.dto.officesys.ProjectRecordDTO;
import com.hbyd.parks.ws.officesys.ProjectRecordWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/5/9
 */
@Controller
@Scope("prototype")
@Module(description = "项目备案")
public class ProjectRecordAction extends ActionSupport implements ModelDriven<RecordQueryBean> {
    private static final long serialVersionUID = 1L;
    private RecordQueryBean page = new RecordQueryBean();
    private Gson gson = new Gson();

    private ProjectRecordDTO record;

    private String id;

    @Resource
    private ProjectRecordWS projectRecordWS;


    public String projectRecordList() throws Exception {
        PageBeanEasyUI list= projectRecordWS.getPageBeanByRecordBean(page);
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    @Operation(type="新增项目备案清单")
    public String addProjectRecord() {
        AjaxMessage message = new AjaxMessage();
        try {
             projectRecordWS.save(record);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="修改项目备案清单")
    public String editProjectRecord() {
        AjaxMessage message = new AjaxMessage();
        try {
           projectRecordWS.update(record);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="删除项目备案清单")
    public String deleteProjectRecord() {
        AjaxMessage message = new AjaxMessage();
        try {
            projectRecordWS.delFake(id);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }


    public RecordQueryBean getModel() {
        return page;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProjectRecordDTO getRecord() {
        return record;
    }

    public void setRecord(ProjectRecordDTO record) {
        this.record = record;
    }
}
