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
import com.hbyd.parks.dto.officesys.ProductTestDTO;
import com.hbyd.parks.ws.managesys.PriviledgeWS;
import com.hbyd.parks.ws.managesys.UserWS;
import com.hbyd.parks.ws.officesys.ProductTestWS;
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
 * Created by Zhao_d on 2016/12/28.
 */
@Controller
@Scope("prototype")
@Module(description = "测试登记记录")
public class ProductTestAction extends ActionSupport implements ModelDriven<ProductTestQuery> {

    @Resource
    private ProductTestWS productTestWS;

    @Resource
    private UserWS userWS;

    @Resource
    private PriviledgeWS priviledgeWS;

    private Gson gson = new Gson();
    private ProductTestDTO productTest = new ProductTestDTO();
    private ProductTestQuery query = new ProductTestQuery();
    private String assignPath = "officesys/productTest/getProductTestListForAssign";    //查询被指派记录的地址
    private String allPath = "officesys/productTest/getProductTestList";                //查询所有记录的地址
    private String regPath = "officesys/productTest/getProductTestListForReg";          //只能查询本人填写记录的地址

    public void productTestList(){
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
            list = productTestWS.getPageBeanByQueryBean(query);
        }else if(hasPriAssign || hasPriReg){
            if(hasPriAssign) {
                query.setAssignPersonQuery(user.getId());
            }
            if(hasPriReg) {
                query.setCheckPersonQuery(user.getId());
            }
            list = productTestWS.getPageBeanByQueryBean(query);
        }else{
            list.setRows(new ArrayList());
        }
        return list;
    }

    @Operation(type="添加记录")
    public void addProductTest(){
        AjaxMessage message = new AjaxMessage();
        try{
            productTestWS.save(productTest);
        }catch(Exception e){
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="修改记录")
    public void editProductTest(){
        AjaxMessage message = new AjaxMessage();
        try{
            productTestWS.update(productTest);
        }catch(Exception e){
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="删除记录")
    public void deleteProductTest(){
        AjaxMessage message = new AjaxMessage();
        try{
            productTestWS.delFake(query.getIdQuery());  //伪删
        }catch(Exception e){
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    /**
     * 获得编号
     */
    public void getNewNumber(){
        query.setSort("id");
        query.setOrder("asc");
        query.setRows(1000);
        String number = productTestWS.getNewNumber(query);
        if(number.length() == 10){
            JsonHelper.writeJson(number);
        }
    }

    /**
     * 获得记录人员可选列表
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
     * 获得操作人员可选列表
     */
    public void getHandlePerson(){
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
     * 导出测试登记表
     */
    public void exportWord(){
        AjaxMessage message = new AjaxMessage();
        try{
            //查询数据
            query.setSort("id");
            query.setOrder("desc");
            query.setRows(1);
            PageBeanEasyUI pageBean = productTestWS.getPageBeanByQueryBean(query);
            ProductTestDTO dto = (ProductTestDTO)pageBean.getRows().get(0);

            //获得替换字段与替换数据对
            Map<String,String> map = getDataMap(dto);

            String classPath = this.getClass().getClassLoader().getResource("").getFile();      //获得项目部署位置
            String file = classPath + "accessory/售后管理模板/001-测试登记表.docx";          //模板文件地址
            file = URLDecoder.decode(file,"utf-8");
            String fileName = java.net.URLEncoder.encode(dto.getProductName(),"UTF-8");                  //生成文件名
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
    public  Map<String,String> getDataMap(ProductTestDTO dto) {
        Map<String, String> map = new HashMap<>();
        map.put("productName", dto.getProductName());
        map.put("number", dto.getNumber());
        map.put("extractPosition", dto.getExtractPosition());
        map.put("registerPerson", dto.getRegisterPersonName());
        map.put("registerDate", dto.getRegisterDate());
        map.put("hopeEndDate", dto.getHopeEndDate());
        map.put("approvePerson", dto.getApprovePersonName());
        map.put("approveDate", dto.getApproveDate());
        map.put("quantity", String.valueOf(dto.getQuantity()));
        map.put("testDesc", dto.getTestDesc());
        map.put("testPerson", dto.getTestPersonName());
        map.put("planBegDate", dto.getPlanBegDate());
        map.put("planEndDate", dto.getPlanEndDate());
        map.put("begDate", dto.getBegDate());
        map.put("endDate", dto.getEndDate());
        map.put("finishedBug", dto.getFinishedBug());
        map.put("unfinishedBug", dto.getUnfinishedBug());
        map.put("finallyName", dto.getFinallyName());
        map.put("finallyPosition", dto.getFinallyPosition());
        map.put("summany", dto.getSummany());
        map.put("note", dto.getNote());

        //testBasis
        Boolean[] basisBool = {false,false,false,false};
        String[] basis = dto.getTestBasis().split(";");
        String basisText = "";
        for(String s : basis){
            if(s.equals("说明书")){
                basisBool[0] = true;
            }else if(s.equals("技术要求")){
                basisBool[1] = true;
            }else if(s.equals("测试用例")){
                basisBool[2] = true;
            }else if(!Strings.isNullOrEmpty(s)){
                basisBool[3] = true;
                basisText = basisText + s;
            }
        }
        map.put("basisText",basisText);
        for(int j = 0;j<basisBool.length;j++){
            if(basisBool[j].equals(true)){
                map.put("basis" + String.valueOf(j+1),"☑");
            }else{
                map.put("basis" + String.valueOf(j+1),"□");
            }
        }

        //testType
        Boolean[] typeBool = {false,false,false,false,false,false,false,
                false,false,false,false,false,false,false,false,false};
        String[] type = dto.getTestType().split(";");
        String typeText = "";
        for(String s : type){
            if(s.equals("功能")){
                typeBool[0] = true;
            }else if(s.equals("性能")){
                typeBool[1] = true;
            }else if(s.equals("高温")){
                typeBool[2] = true;
            }else if(s.equals("低温")){
                typeBool[3] = true;
            }else if(s.equals("湿热")){
                typeBool[4] = true;
            }else if(s.equals("振动")){
                typeBool[5] = true;
            }else if(s.equals("跌落")){
                typeBool[6] = true;
            }else if(s.equals("防水")){
                typeBool[7] = true;
            }else if(s.equals("卡片测试")){
                typeBool[8] = true;
            }else if(s.equals("绝缘电阻")){
                typeBool[9] = true;
            }else if(s.equals("抗电强度")){
                typeBool[10] = true;
            }else if(s.equals("外壳防护")){
                typeBool[11] = true;
            }else if(s.equals("外观")){
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
            ExportExcelHelper.exportProductTest(pageBean.getRows());
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
    public ProductTestQuery getModel() {
        return query;
    }

    public ProductTestQuery getQuery() {
        return query;
    }

    public void setQuery(ProductTestQuery query) {
        this.query = query;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public ProductTestDTO getProductTest() {
        return productTest;
    }

    public void setProductTest(ProductTestDTO productTest) {
        this.productTest = productTest;
    }
}
