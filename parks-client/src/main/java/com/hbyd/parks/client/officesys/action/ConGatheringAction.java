package com.hbyd.parks.client.officesys.action;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.hbyd.parks.client.util.ComboHelper;
import com.hbyd.parks.client.util.ExportExcelHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.hql.HqlQuery;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.common.model.*;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.dto.officesys.ContractGatheringLogDTO;
import com.hbyd.parks.dto.officesys.GatheringSumDTO;
import com.hbyd.parks.dto.officesys.PaymentDTO;
import com.hbyd.parks.ws.managesys.PriviledgeWS;
import com.hbyd.parks.ws.officesys.ContractGatheringLogWS;
import com.hbyd.parks.ws.officesys.ContractGatheringPostilWS;
import com.hbyd.parks.ws.officesys.ContractGatheringWS;
import com.hbyd.parks.ws.officesys.PaymentWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/2
 */
@Controller
@Scope("prototype")
@Module(description = "收款合同管理")
public class ConGatheringAction extends ActionSupport implements ModelDriven<ConQueryBean> {

    private static final long serialVersionUID = 1L;
    private ConQueryBean page = new ConQueryBean();
    private QueryBeanEasyUI qBean = new QueryBeanEasyUI();
    private Gson gson = new Gson();

    private ContractGatheringDTO con;

    private ContractGatheringLogDTO conLog;

    private String actionUrl;

    @Resource
    private ContractGatheringWS contractGatheringWS;

    @Resource
    private ContractGatheringPostilWS contractGatheringPostilWS;

    @Resource
    private ContractGatheringLogWS contractGatheringLogWS;

    @Resource
    private PriviledgeWS priviledgeWS;

    @Resource
    private PaymentWS paymentWS;

    //删除记录的操作
    private String id;

    //用于将旧合同的付款记录转移到新合同
    private String oldConId;
    private String oldConName;
    private String newConId;

    public String conGatheringList() throws Exception {

        PageBeanEasyUI list = contractGatheringWS.getPageBeanByConQueryBean(getPage());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }


    public String acceptanceConGatheringList() throws Exception {

        PageBeanEasyUI list = contractGatheringWS.getAcceptancePageBean(getPage());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    @Operation(type="汇总")
    public String gatheringSum() throws Exception {
            GatheringSumDTO dto = contractGatheringWS.getGatheringSum(getPage());
            String result = gson.toJson(dto);
            JsonHelper.writeJson(result);
            return null;
    }

    @Operation(type="新增收款合同清单")
    public String addConGathering() {
        AjaxMessage message = new AjaxMessage();
        try {
            fillZero();
            ContractGatheringDTO dto = contractGatheringWS.save(con);
            UserDTO u = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
            ContractGatheringLogDTO conLog = new ContractGatheringLogDTO();
            conLog.setUserName(u.getUserName());
            conLog.setOperateType("新建");
            if (con.getAmount() != null){
                conLog.setAmount(con.getAmount().toString());
            }else{
                con.setAmount((double)0);
                conLog.setAmount("0");
            }
            if (con.getOncredit() != null) {
                conLog.setOncredit(con.getOncredit().toString());
            }else {
                con.setOncredit((double) 0);
                conLog.setOncredit("0");
            }
            if (con.getReceived() != null){
                conLog.setReceived(con.getReceived().toString());
            }else {
                con.setReceived((double) 0);
                conLog.setReceived("0");
            }
            if (con.getReceiveNo() != null){
                conLog.setReceiveNo(con.getReceiveNo().toString());
            }else {
                con.setReceiveNo((double) 0);
                conLog.setReceiveNo("0");
            }
            if (con.getRemain() != null) {
                conLog.setRemain(con.getRemain().toString());
            }else {
                con.setRemain((double) 0);
                conLog.setRemain("0");
            }
            if (con.getGross() != null){
                conLog.setGross(con.getGross().toString());
            }else {
                con.setGross((double) 0);
                conLog.setGross("0");
            }
            conLog.setId("");
            conLog.setGatheringFK(dto.getId());
            conLog.setContractNo(dto.getContractNo());
            conLog.setDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));

            contractGatheringLogWS.save(conLog);
            con.setId(dto.getId());
            contractGatheringWS.update(con);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    //将ContractGatheringDTO中数值用null的填充为0
    private void fillZero() {
        if (con.getAmount()==null){
            con.setAmount((double)0);
        }
        if (con.getOncredit()==null){
            con.setOncredit((double) 0);
        }
        if (con.getReceived()==null){
            con.setReceived((double) 0);
        }
        if (con.getReceiveNo()==null){
            con.setReceiveNo((double) 0);
        }
        if (con.getGross()==null){
            con.setGross((double) 0);
        }
    }

    public String authorization() {
        AjaxMessage message = new AjaxMessage();
        try {
            UserDTO user = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
            if(!user.getUserName().equals("super")) {
                boolean hasPri = priviledgeWS.validatePriviledge(user.getId(), actionUrl);
                if (!hasPri) {
                    message.setSuccess(false);
                    message.setMessage("您没有权限");
                }
            }
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="编辑收款合同清单")
    public String editConGathering() {
        AjaxMessage message = new AjaxMessage();
        try {

            ContractGatheringDTO dto=contractGatheringWS.getByID(con.getId());

            UserDTO u = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
            ContractGatheringLogDTO conLog = new ContractGatheringLogDTO();
            conLog.setUserName(u.getUserName());
            conLog.setOperateType("修改");
            conLog.setContractNo(dto.getContractNo());
            if((!con.getAmount().toString().equals(dto.getAmount().toString()))||(!con.getOncredit().toString().equals(dto.getOncredit().toString()))||
                    (!con.getReceived().toString().equals(dto.getReceived().toString()))||(!con.getReceiveNo().toString().equals(dto.getReceiveNo().toString()))
                    ||(!con.getRemain().toString().equals(dto.getRemain().toString()))) {
                //填充日志的DTO
                fileLogDto(dto, conLog);

                if (con.getGross() != null) {
                    conLog.setGross(con.getGross().toString());
                } else {
                    conLog.setGross("0");
                }
                conLog.setGatheringFK(con.getId());
                conLog.setDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
                contractGatheringLogWS.save(conLog);
            }
            contractGatheringWS.update(con);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    private void fileLogDto(ContractGatheringDTO dto, ContractGatheringLogDTO conLog) {
        if (con.getAmount() != null){
            conLog.setAmount(con.getAmount().toString());
            if(dto.getAmount()!=null){
            conLog.setAmountChange(con.getAmount() - dto.getAmount());
            }else {
                conLog.setAmountChange(con.getAmount());
            }
        }else{
            con.setAmount((double)0);
            conLog.setAmount("0");
            if(dto.getAmount()!=null){
                conLog.setAmountChange(0 - dto.getAmount());
            }else {
                conLog.setAmountChange((double)0);
            }

        }
        if (con.getOncredit() != null){
            conLog.setOncredit(con.getOncredit().toString());
            if(dto.getOncredit()!=null){
            conLog.setOncreditChange(con.getOncredit() - dto.getOncredit());
            }else {
                conLog.setOncreditChange(con.getOncredit());
            }
        }else {
            con.setOncredit((double)0);
            conLog.setOncredit("0");
            if(dto.getOncredit()!=null) {
                conLog.setOncreditChange(0 - dto.getOncredit());
            }else {
                conLog.setOncreditChange((double)0);
            }
        }

        if (con.getReceived() != null){
            conLog.setReceived(con.getReceived().toString());
            if(dto.getReceived()!=null){
            conLog.setReceivedChange(con.getReceived() - dto.getReceived());
            }else {
                conLog.setReceivedChange(con.getReceived());
            }
        }else {
            con.setReceived((double)0);
            conLog.setReceived("0");
            if(dto.getReceived()!=null) {
                conLog.setReceivedChange(0 - dto.getReceived());
            }else {
                conLog.setReceivedChange((double)0);
            }
        }

        if (con.getReceiveNo() != null){
            conLog.setReceiveNo(con.getReceiveNo().toString());
            if(dto.getReceiveNo()!=null){
            conLog.setReceiveNoChange(con.getReceiveNo() - dto.getReceiveNo());
            }else {
                conLog.setReceiveNoChange(con.getReceiveNo());
            }
        }else {
            con.setReceiveNo((double)0);
            conLog.setReceiveNo("0");
            if(dto.getReceiveNo()!=null){
                conLog.setReceiveNoChange(0- dto.getReceiveNo());
            }else {
                conLog.setReceiveNoChange((double)0);
            }


        }

        if (con.getRemain() != null){
            conLog.setRemain(con.getRemain().toString());
            if(dto.getRemain()!=null){
            conLog.setRemainChange(con.getRemain() - dto.getRemain());
            }else {
                conLog.setRemainChange(con.getRemain());
            }
        }else {
            con.setRemain((double)0);
            conLog.setRemain("0");
            if(dto.getRemain()!=null){
                conLog.setRemainChange(0 - dto.getRemain());
            }else {
                conLog.setRemainChange((double)0);
            }

        }
    }

    @Operation(type="删除收款合同清单")
    public String deleteConGathering() {
        AjaxMessage message = new AjaxMessage();
        try {
            //删除记录以及标签
            contractGatheringWS.delByID(id);
            //伪删
            contractGatheringWS.delFake(id);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="新增收款合同批注")
    public String addPostil() {
        AjaxMessage message = new AjaxMessage();
        try {
            contractGatheringPostilWS.save(con.getContractGatheringPostil());

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="编辑收款合同批注")
    public String editPostil() {
        AjaxMessage message = new AjaxMessage();
        try {
            contractGatheringPostilWS.update(con.getContractGatheringPostil());

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

  //付款合同列表
    public String paymentList() throws Exception {

        PageBeanEasyUI list = contractGatheringWS.getPageBeanByConQueryBean(getPage());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    //复制付款合同到新的收款合同下
    public String transformPayment(){
        AjaxMessage message = new AjaxMessage();
        try {
            PaymentQuery paymentQuery = new PaymentQuery();
            paymentQuery.setSort("contractSn");
            paymentQuery.setOrder("desc");
            paymentQuery.setRows(1000);
            paymentQuery.setContractName(oldConName);
            PageBeanEasyUI payment = paymentWS.getPageBeanByPaymentQuery(paymentQuery);
            if(payment.getRows() != null) {
                List<PaymentDTO> list = payment.getRows();
                for (PaymentDTO paymentDTO : list) {
                    ContractGatheringDTO conDTO = contractGatheringWS.getByID(newConId);
                    //对于其它类型，情况特殊，下面单独设置
                    if(!conDTO.getProjectType().equals("其它项目")) {
                        //更新关联的收款合同
                        paymentDTO.getContractGatherings().clear();
                        paymentDTO.getContractGatherings().add(conDTO);
                        //同时将项目类型与年度更新为新项目的数据
                        paymentDTO.setContractName(conDTO.getContractName());
                        paymentDTO.setContractType(conDTO.getProjectType());
                        paymentDTO.setSheetName(conDTO.getSheetName());
                    //其它项目的情况
                    }else{
                        //更新关联的收款合同,指定为"其它"
                        ContractGatheringDTO otherDTO = new ContractGatheringDTO();
                        otherDTO.setId("others");
                        paymentDTO.getContractGatherings().clear();
                        paymentDTO.getContractGatherings().add(otherDTO);
                        //同时将项目类型与年度更新为新项目的数据，项目名称为年度+项目类型+类
                        paymentDTO.setContractName(conDTO.getSheetName() + conDTO.getProjectType() + "类");
                        paymentDTO.setContractType(conDTO.getProjectType());
                        paymentDTO.setSheetName(conDTO.getSheetName());
                    }
                    //更新
                    paymentWS.update(paymentDTO);
                }
            }
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="导出Excel")
    public void exportExcel() throws Exception {
        AjaxMessage message = new AjaxMessage();
        try {
            getqBean().setSort("projectSn");
            getqBean().setOrder("desc");
            getqBean().setRows(10000);
            ExportExcelHelper.exportConGathering(getDataRows(),getPage().getSheetNameQuery());
        }catch(Exception e){
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

    private List getDataRows()
    {
        String[] projectTypes = {"弱电项目","零星项目","贸易项目","其它项目"};
        List<ContractGatheringDTO> dataRow = new ArrayList();
        for(int i=0;i<projectTypes.length;i++) {
            String hql = getOutputHql() + " and projectType like '%" + projectTypes[i] + "%'";
            HqlQuery query = new HqlQuery(hql);
            Object[] params = query.getParametersValue();
            PageBeanEasyUI pageBean = contractGatheringWS.getPageBeanByHQL(getqBean(), hql, params);
            if(pageBean.getRows() != null) {
                dataRow.addAll(pageBean.getRows());

                //总和行
                if (projectTypes[i] != "其它项目") {
                    ContractGatheringDTO conGathering = new ContractGatheringDTO();
                    conGathering.setContractName(projectTypes[i] + "小计");
                    double amount = 0;     //合同金额
                    double receive = 0;    //已收款金额
                    double receiveNo = 0;  //未收款金额
                    double oncredit = 0;   //挂账金额
                    double remain = 0;     //剩余金额
                    List<ContractGatheringDTO> row = pageBean.getRows();
                    for (int j = 0; j < row.size(); j++) {
                        if (row.get(j).getAmount() != null)
                            amount += row.get(j).getAmount();
                        if (row.get(j).getReceived() != null)
                            receive += row.get(j).getReceived();
                        if (row.get(j).getReceiveNo() != null)
                            receiveNo += row.get(j).getReceiveNo();
                        if (row.get(j).getOncredit() != null)
                            oncredit += row.get(j).getOncredit();
                        if (row.get(j).getRemain() != null)
                            remain += row.get(j).getRemain();
                    }
                    conGathering.setAmount(amount);
                    conGathering.setReceived(receive);
                    conGathering.setReceiveNo(receiveNo);
                    conGathering.setOncredit(oncredit);
                    conGathering.setRemain(remain);
                    dataRow.add(conGathering);
                }
            }
        }
        return dataRow;
    }

    private String getOutputHql()
    {
        String hql = "from ContractGathering where isValid=true";
        if (!Strings.isNullOrEmpty(getPage().getConNameQuery())) {
            hql += " and contractName like '%" + getPage().getConNameQuery() + "%'";
        }

        if (!Strings.isNullOrEmpty(getPage().getProjectTypeQuery())) {
            hql += " and projectType like '%" + getPage().getProjectTypeQuery() + "%'";
        }

        if (!Strings.isNullOrEmpty(getPage().getProjectManagerQuery())) {
            hql += " and projectManager like '%" + getPage().getProjectManagerQuery() + "%'";
        }

        if (!Strings.isNullOrEmpty(getPage().getComFirstQuery())) {
            hql += " and companyFirst like '%" + getPage().getComFirstQuery() + "%'";
        }

        if (getPage().getAmountQuery() != null) {
            hql += " and amount='" + getPage().getAmountQuery() + "'";
        }

        if (getPage().getReceivedQuery() != null) {
            hql += " and received='" + getPage().getReceivedQuery() + "'";
        }

        if (getPage().getOncreditQuery() != null) {
            hql += " and oncredit='" + getPage().getOncreditQuery() + "'";
        }

        if (!Strings.isNullOrEmpty(getPage().getSheetNameQuery())) {
            hql += " and sheetName like '%" + getPage().getSheetNameQuery() + "%'";
        }

        if (!Strings.isNullOrEmpty(getPage().getContractNoYDQuery())) {
            hql += " and contractNoYD like '%" + getPage().getContractNoYDQuery() + "%'";
        }

        if (!Strings.isNullOrEmpty(getPage().getContractNoQuery())) {
            hql += " and contractNo like '%" + getPage().getContractNoQuery() + "%'";
        }

        if (!Strings.isNullOrEmpty(getPage().getCompanySecondQuery())) {
            hql += " and companySecond like '%" + getPage().getCompanySecondQuery() + "%'";
        }

        if (!Strings.isNullOrEmpty(getPage().getSignDateQuery())) {
            hql += " and signDate='" + getPage().getSignDateQuery() + "'";
        }

        if (getPage().getReceiveNoQuery() != null) {
            hql += " and receiveNo='" + getPage().getReceiveNoQuery() + "'";
        }

        if (getPage().getRemainQuery() != null) {
            hql += " and remain='" + getPage().getRemainQuery() + "'";
        }

        if (getPage().getGrossQuery() != null) {
            hql += " and gross='" + getPage().getGrossQuery() + "'";
        }

        if (!Strings.isNullOrEmpty(getPage().getProjectDirectorQuery())) {
            hql += " and projectDirector like '%" + getPage().getProjectDirectorQuery() + "%'";
        }

        if (getPage().getStampQuery() != null) {
            hql += " and stamp='" + getPage().getStampQuery() + "'";
        }

        if (getPage().getIsCompletedQuery() != null) {
            hql += " and isCompleted='" + getPage().getIsCompletedQuery() + "'";
        }

        return hql;
    }

    public void getContractList(){
        List<ContractGatheringDTO> lists = new ArrayList<>();
        List<ContractGatheringDTO> listsL = contractGatheringWS.getContractNameBySheetAndType("","零星项目");
        List<ContractGatheringDTO> listsR = contractGatheringWS.getContractNameBySheetAndType("","弱电项目");
        List<ContractGatheringDTO> listsQ = contractGatheringWS.getContractNameBySheetAndType("","其他项目");

        if(listsL != null){
            lists.addAll(listsL);
        }
        if(listsR != null){
            lists.addAll(listsR);
        }
        if(listsQ != null){
            lists.addAll(listsQ);
        }

        List<Combobox> project = ComboHelper.getContractNameCombobox(lists);
        String result = gson.toJson(project);
        JsonHelper.writeJson(result);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConQueryBean getModel() {
        return getPage();
    }

    public ContractGatheringDTO getCon() {
        return con;
    }

    public void setCon(ContractGatheringDTO con) {
        this.con = con;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public ConQueryBean getPage() {
        return page;
    }

    public void setPage(ConQueryBean page) {
        this.page = page;
    }

    public QueryBeanEasyUI getqBean() {
        return qBean;
    }

    public void setqBean(QueryBeanEasyUI qBean) {
        this.qBean = qBean;
    }

    public String getOldConId() {
        return oldConId;
    }

    public void setOldConId(String oldConId) {
        this.oldConId = oldConId;
    }

    public String getNewConId() {
        return newConId;
    }

    public void setNewConId(String newConId) {
        this.newConId = newConId;
    }

    public String getOldConName() {
        return oldConName;
    }

    public void setOldConName(String oldConName) {
        this.oldConName = oldConName;
    }
}
