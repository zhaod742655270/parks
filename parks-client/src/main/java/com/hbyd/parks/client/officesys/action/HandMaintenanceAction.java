package com.hbyd.parks.client.officesys.action;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.hbyd.parks.client.util.ComboHelper;
import com.hbyd.parks.client.util.ExportExcelHelper;
import com.hbyd.parks.client.util.ExportWordHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.common.model.*;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.officesys.HandMaintenanceDTO;
import com.hbyd.parks.ws.managesys.PriviledgeWS;
import com.hbyd.parks.ws.managesys.UserWS;
import com.hbyd.parks.ws.officesys.HandMaintenanceWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhao_d on 2016/12/14.
 */
@Controller
@Scope("prototype")
@Module(description = "售后维护跟踪记录")
public class HandMaintenanceAction extends ActionSupport implements ModelDriven<HandMaintenanceQuery> {
    private Gson gson = new Gson();
    private HandMaintenanceDTO maintenance = new HandMaintenanceDTO();
    private HandMaintenanceQuery query = new HandMaintenanceQuery();
    private String userID;      //登录者ID,用于使故障处理时的处理人、测试人等只能选择登录人自己
    private String oldUserID;   //之前填入的人员ID,用途同上
    private String assignPath = "officesys/handMaintenance/getMaintenanceListForAssign";        //查询被指派记录的地址
    private String allPath = "officesys/handMaintenance/getMaintenanceList";                    //查询所有记录的地址
    private String regPath = "officesys/handMaintenance/getMaintenanceListForReg";          //只能查询自己记录的数据的地址

    @Resource
    private HandMaintenanceWS handMaintenanceWS;

    @Resource
    private UserWS userWS;

    @Resource
    private PriviledgeWS priviledgeWS;

    public void maintenanceList(){
        PageBeanEasyUI list = getPageBean();        //获得数据列表
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public PageBeanEasyUI getPageBean(){
        PageBeanEasyUI list = new PageBeanEasyUI();
        //获得当前登录的用户
        UserDTO user = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
        //判断该用户是否有查询被指派记录的权限
        boolean hasPriAssign = priviledgeWS.validatePriviledge(user.getId(), assignPath);
        //判断该用户是否有查询所有记录的权限
        boolean hasPriAll = priviledgeWS.validatePriviledge(user.getId(), allPath);
        //判断该用户是否有查询自己添加的记录的权限
        boolean hasPriReg = priviledgeWS.validatePriviledge(user.getId(), regPath);
        if(hasPriAll){
            list = handMaintenanceWS.getPageBeanByQueryBean(query);
        }else if(hasPriAssign || hasPriReg){
            if(hasPriAssign) {
                query.setAssignPersonQuery(user.getId());
            }
            if(hasPriReg) {
                query.setCheckPersonQuery(user.getId());
            }
            list = handMaintenanceWS.getPageBeanByQueryBean(query);
        }else{
            list.setRows(new ArrayList());
        }
        return list;
    }

    @Operation(type="添加记录")
    public void addMaintenance(){
        AjaxMessage message = new AjaxMessage();
        try{
            handMaintenanceWS.save(maintenance);
        }catch(Exception e){
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="修改记录")
    public void editMaintenance(){
        AjaxMessage message = new AjaxMessage();
        try{
            handMaintenanceWS.update(maintenance);
        }catch(Exception e){
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="删除记录")
    public void deleteMaintenance(){
        AjaxMessage message = new AjaxMessage();
        try{
            handMaintenanceWS.delFake(query.getIdQuery());  //伪删
        }catch(Exception e){
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    /**
     * 自动获得编号
     */
    public void getNewNumber(){
        query.setSort("id");
        query.setOrder("asc");
        query.setRows(1000);
        String number = handMaintenanceWS.getNewNumber(query);
        if(number.length() == 10){
            JsonHelper.writeJson(number);
        }
    }

    /**
     * 获得记录人员的可选列表
     */
    public void getRegPerson(){
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

    /**
     * 获得操作人员的可选列表
     */
    public void getHandlePerson(){
        List<UserDTO> lists = userWS.findAllValid();
        if(lists==null){
            lists=new ArrayList<>();
        }
        //如果请求中带有userID和oldUserID，则只返回该用户信息和之前已填入的用户信息
        if(!Strings.isNullOrEmpty(userID)){
            for(int i=0;i<lists.size();i++){
                if(!lists.get(i).getId().equals(userID) && !lists.get(i).getId().equals(oldUserID)){
                    lists.remove(i);
                    i--;
                }
            }
        }
        List<Combobox> project = ComboHelper.getNicknameCombobox(lists);
        String result = gson.toJson(project);
        JsonHelper.writeJson(result);
    }

    /**
     * 导出Word格式产品维修记录表
     */
    public void exportWord(){
        AjaxMessage message = new AjaxMessage();
        try{
            //查询数据
            query.setSort("id");
            query.setOrder("desc");
            query.setRows(1);
            PageBeanEasyUI pageBean = handMaintenanceWS.getPageBeanByQueryBean(query);
            HandMaintenanceDTO dto = (HandMaintenanceDTO)pageBean.getRows().get(0);

            //获得替换字段与替换数据对
            Map<String,String> map = getDataMap(dto);

            String classPath = this.getClass().getClassLoader().getResource("").getFile();      //获得项目部署位置
            String file = classPath + "accessory/售后管理模板/002-产品维修记录表.docx";          //模板文件地址
            file = URLDecoder.decode(file,"utf-8");
            String fileName = java.net.URLEncoder.encode(dto.getProjectName(),"UTF-8");                  //生成文件名
            fileName += ".docx";
            ExportWordHelper.writeWord(file,fileName,map);
        }catch(Exception e){
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    /**
     * 获取数据与替换字段对
     */
    public  Map<String,String> getDataMap(HandMaintenanceDTO dto) {
        Map<String, String> map = new HashMap<>();
        map.put("projectName", dto.getProjectName());
        map.put("number", dto.getNumber());
        map.put("productName", dto.getProductName());
        map.put("quantity", String.valueOf(dto.getQuantity()));
        map.put("registerPerson", dto.getRegisterPersonName());
        map.put("registerDate", dto.getRegisterDate());
        map.put("hopeEndDate", dto.getHopeEndDate());
        map.put("approvePerson", dto.getApprovePersonName());
        map.put("approveDate", dto.getApproveDate());
        map.put("faultContent", dto.getFaultContent());
        map.put("reportPerson", dto.getReportPersonName());
        map.put("productNo", dto.getProductNo());
        map.put("firmwareVersion", dto.getFirmwareVersion());
        map.put("handwareVersion", dto.getHandwareVersion());
        map.put("faultVerify", dto.getFaultVerify());
        map.put("verifyPerson", dto.getVerifyPersonName());
        map.put("techAnalysis", dto.getTechAnalysis());
        map.put("AnalyPerson", dto.getAnalyPersonName());
        map.put("repairContent", dto.getRepairContent());
        map.put("repairPerson", dto.getReportPersonName());
        map.put("repairResult", dto.getRepairResult());
        map.put("testPerson", dto.getTestPersonName());
        map.put("checkCost", dto.getCheckCost());
        map.put("manhourCost", dto.getManhourCost());
        map.put("materialsCost", dto.getMaterialsCost());
        map.put("repairCost", dto.getRepairCost());

        //repairBasis
        Boolean[] basisBool = {false,false,false,false};
        String[] basis = dto.getRepairBasis().split(";");
        String basis4Text = "";
        for(String s : basis){
            if(s.equals("产品说明书")){
                basisBool[0] = true;
            }else if(s.equals("技术要求")){
                basisBool[1] = true;
            }else if(s.equals("工程设计方案")){
                basisBool[2] = true;
            }else if(!Strings.isNullOrEmpty(s)){
                basisBool[3] = true;
                basis4Text = basis4Text + s;
            }
        }
        map.put("basisText",basis4Text);
        for(int j = 0;j<basisBool.length;j++){
            if(basisBool[j].equals(true)){
                map.put("basis" + String.valueOf(j+1),"☑");
            }else{
                map.put("basis" + String.valueOf(j+1),"□");
            }
        }

        //repairType
        Boolean[] typeBool = {false,false,false,false,false,false,false,
                false,false,false,false,false,false,false,false,false};
        String[] type = dto.getRepairType().split(";");
        String typeText = "";
        for(String s : type){
            if(s.equals("功能")){
                typeBool[0] = true;
            }else if(s.equals("性能")){
                typeBool[1] = true;
            }else if(s.equals("产品选型")){
                typeBool[2] = true;
            }else if(s.equals("高温")){
                typeBool[3] = true;
            }else if(s.equals("低温")){
                typeBool[4] = true;
            }else if(s.equals("湿热")){
                typeBool[5] = true;
            }else if(s.equals("防水")){
                typeBool[6] = true;
            }else if(s.equals("振动")){
                typeBool[7] = true;
            }else if(s.equals("跌落")){
                typeBool[8] = true;
            }else if(s.equals("绝缘电阻")){
                typeBool[9] = true;
            }else if(s.equals("抗电强度")){
                typeBool[10] = true;
            }else if(s.equals("外观")){
                typeBool[11] = true;
            }else if(s.equals("外壳防护")){
                typeBool[12] = true;
            }else if(s.equals("机械强度")){
                typeBool[13] = true;
            }else if(s.equals("静电放电")){
                typeBool[14] = true;
            }else if(!Strings.isNullOrEmpty(s)){
                typeBool[15] = true;
                typeText = typeText + s;
            }
        }
        map.put("typeText",typeText);
        for(int j = 0;j<typeBool.length;j++){
            if(typeBool[j].equals(true)){
                map.put("type" + String.valueOf(j+1),"☑");
            }else{
                map.put("type" + String.valueOf(j+1),"□");
            }
        }

        return map;
    }

    /**
     * 导出列表
     */
    public void exportExcel() throws Exception{
        AjaxMessage message = new AjaxMessage();
        try {
            query.setOrder("asc");
            query.setSort("number");
            query.setRows(10000);
            PageBeanEasyUI pageBean = getPageBean();
            ExportExcelHelper.exportHandMaintenance(pageBean.getRows());
        }catch(Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally{
            //由于导出文件时Response存在冲突，因此只在导出失败时返回信息
            if(!message.getSuccess()) {
                String result = gson.toJson(message);
                JsonHelper.writeJson(result);
            }
        }
    }

    @Override
    public HandMaintenanceQuery getModel() {
        return getQuery();
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public HandMaintenanceDTO getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(HandMaintenanceDTO maintenance) {
        this.maintenance = maintenance;
    }

    public HandMaintenanceQuery getQuery() {
        return query;
    }

    public void setQuery(HandMaintenanceQuery query) {
        this.query = query;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOldUserID() {
        return oldUserID;
    }

    public void setOldUserID(String oldUserID) {
        this.oldUserID = oldUserID;
    }
}
