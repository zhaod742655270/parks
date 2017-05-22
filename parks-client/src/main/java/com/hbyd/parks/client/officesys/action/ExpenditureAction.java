package com.hbyd.parks.client.officesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.ComboHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.model.*;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.officesys.ExpenditureDTO;
import com.hbyd.parks.dto.officesys.ProjectRecordDTO;
import com.hbyd.parks.ws.managesys.UserWS;
import com.hbyd.parks.ws.officesys.ExpenditureWS;
import com.hbyd.parks.ws.officesys.ProjectRecordWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhao_d on 2017/2/23.
 */
@Controller
@Scope("prototype")
@Module(description = "经费支出记录")
public class ExpenditureAction extends ActionSupport implements ModelDriven<ExpenditureQuery>{

    @Resource
    private ExpenditureWS expenditureWS;

    @Resource
    private UserWS userWS;

    @Resource
    private ProjectRecordWS projectRecordWS;

    private ExpenditureQuery query = new ExpenditureQuery();
    private ExpenditureDTO expenditure = new ExpenditureDTO();
    private String id;
    private Gson gson = new Gson();

    public void expenditureList(){
        PageBeanEasyUI list = expenditureWS.getPageBeanByQueryBean(query);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public void addExpenditure(){
        AjaxMessage massage = new AjaxMessage();
        try{
            if(expenditure.getRecordPersonId() == null) {
                massage.setSuccess(false);
                massage.setMessage("项目经理填写有误");
            }else {
                expenditureWS.save(expenditure);
            }
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void editExpenditure(){
        AjaxMessage massage = new AjaxMessage();
        try{
            if(expenditure.getRecordPersonId() == null) {
                massage.setSuccess(false);
                massage.setMessage("项目经理填写有误");
            }else {
                expenditureWS.update(expenditure);
            }
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void deleteExpenditure(){
        AjaxMessage massage = new AjaxMessage();
        try{
            expenditureWS.delFake(id);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    //获得经办人人员列表
    public void getRecordPersonList(){
        //按名称顺序排序
        //排除管理员与超级管理员
        QueryBeanEasyUI query = new QueryBeanEasyUI(1, 1000, "nickname", "asc");
        String hql_where = "WHERE isValid=true AND userName!='super' AND userName!='admin'";
        List<UserDTO> lists = userWS.getPageBean(query, hql_where).getRows();
        if(lists==null){
            lists=new ArrayList<>();
        }
        List<Combobox> project = ComboHelper.getNicknameCombobox(lists);
        String result = gson.toJson(project);
        JsonHelper.writeJson(result);
    }

    //获得项目名称列表
    public void getProjectList(){
        List<ProjectRecordDTO> lists = projectRecordWS.findAllValid();
        if(lists==null){
            lists=new ArrayList<>();
        }
        List<Combobox> project = ComboHelper.getProjectNameCombobox(lists);   //根据数据填充下拉框
        String result = gson.toJson(project);
        JsonHelper.writeJson(result);
    }

    @Override
    public ExpenditureQuery getModel() {
        return query;
    }

    public ExpenditureQuery getQuery() {
        return query;
    }

    public void setQuery(ExpenditureQuery query) {
        this.query = query;
    }

    public ExpenditureDTO getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(ExpenditureDTO expenditure) {
        this.expenditure = expenditure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
