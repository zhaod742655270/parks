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
import com.hbyd.parks.dto.officesys.PaymentDTO;
import com.hbyd.parks.dto.officesys.PaymentLogDTO;
import com.hbyd.parks.dto.officesys.PaymentSumDTO;
import com.hbyd.parks.ws.officesys.ContractGatheringWS;
import com.hbyd.parks.ws.officesys.PaymentLogWS;
import com.hbyd.parks.ws.officesys.PaymentPostilWS;
import com.hbyd.parks.ws.officesys.PaymentWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/16
 */
@Controller
@Scope("prototype")
@Module(description = "付款合同记录")
public class PaymentAction extends ActionSupport implements ModelDriven<PaymentQuery> {

    private static final long serialVersionUID = 1L;
    private PaymentQuery page = new PaymentQuery();
    private Gson gson = new Gson();
    private QueryBeanEasyUI qBean = new QueryBeanEasyUI();
    @Resource
    private PaymentWS paymentWS;

    @Resource
    private PaymentLogWS paymentLogWS;

    @Resource
    private PaymentPostilWS paymentPostilWS;

    @Resource
    private ContractGatheringWS contractGatheringWS;

    private PaymentDTO pay;

    private String id;

    private String sheetName;

    private String contractType;

    private String type;

    private String[] belongContractNames;

    private String contractId;

    public String paymentList() throws Exception {

        PageBeanEasyUI list = paymentWS.getPageBeanByPaymentQuery(getPage());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    public String paymentListByGathering() throws Exception{
        List<PaymentDTO> paymentDTOs = paymentWS.getPaymentListByGathering(contractId);
        String result = gson.toJson(paymentDTOs);
        JsonHelper.writeJson(result);
        return null;
    }

    public String getContractName() throws Exception{
        if(contractType.indexOf("1")>=0){
            type="弱电项目";
        }else if(contractType.indexOf("2")>=0) {
            type="贸易项目";
        }else if(contractType.indexOf("3")>=0) {
            type="其它项目";
        }else if(contractType.indexOf("4")>=0) {
            type="洽商项目";
        }else {
            type="零星项目";
        }
        List<ContractGatheringDTO> payment=contractGatheringWS.getContractNameBySheetAndType(sheetName,type);
        if(payment==null){
            payment=new ArrayList<>();
        }
        List<Combobox> contractNames = ComboHelper.getContractNameCombobox(payment);
        String result = gson.toJson(contractNames);
        JsonHelper.writeJson(result);
        return null;
    }

    @Operation(type="新增付款合同清单")
    public String addPayment() {
        AjaxMessage message = new AjaxMessage();
        try {
            PaymentDTO fullPayment=getFullPayment();
            PaymentDTO dto = paymentWS.save(fullPayment);
            UserDTO u = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
            PaymentLogDTO paymentLog = new PaymentLogDTO();
            paymentLog.setUserName(u.getUserName());
            paymentLog.setOperateType("新建");
            if (pay.getContractSum() != null){
                paymentLog.setContractSum(pay.getContractSum());
            }else{
                pay.setContractSum((double) 0);
                paymentLog.setContractSum((double) 0);
            }
            if (pay.getPayment() != null) {
                paymentLog.setPayment(pay.getPayment());
            }else {
                pay.setPayment((double) 0);
                paymentLog.setPayment((double) 0);
            }
            if (pay.getPaymentNo() != null){
                paymentLog.setPaymentNo(pay.getPaymentNo());
            }else {
                pay.setPaymentNo((double) 0);
                paymentLog.setPaymentNo((double) 0);
            }

            paymentLog.setParentFK(dto.getId());
            paymentLog.setDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));

            paymentLogWS.save(paymentLog);
            pay.setId(dto.getId());
            paymentWS.update(pay);


        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="修改付款合同清单")
    public String editPayment() {
        AjaxMessage message = new AjaxMessage();
        try {
            PaymentDTO fullPayment=getFullPayment();
            PaymentDTO dto=paymentWS.getByID(fullPayment.getId());

            UserDTO u = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
            PaymentLogDTO conLog = new PaymentLogDTO();
            conLog.setUserName(u.getUserName());
            conLog.setOperateType("修改");
            if((!pay.getContractSum().toString().equals(dto.getContractSum().toString()))||(!pay.getPayment().toString().equals(dto.getPayment().toString()))||
                    (!pay.getPaymentNo().toString().equals(dto.getPaymentNo().toString())) ){
                //填充日志的DTO
                fileLogDTO(dto, conLog);
                conLog.setParentFK(pay.getId());
                conLog.setDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
                paymentLogWS.save(conLog);
            }
            paymentWS.update(pay);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="删除付款合同清单")
    public String deletePayment() {
        AjaxMessage message = new AjaxMessage();
        try {
            //删除记录以及标签
            paymentWS.delByID(id);
            //伪删
            paymentWS.delFake(id);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

   //统计
   @Operation(type="汇总")
    public String sumPayment() throws Exception {
        PaymentSumDTO dto = paymentWS.getPaymentSum(getPage());
        String result = gson.toJson(dto);
        JsonHelper.writeJson(result);
        return null;
    }

    @Operation(type="新增付款合同批注")
    public String addPostil() {
        AjaxMessage message = new AjaxMessage();
        try {
            paymentPostilWS.save(pay.getPaymentPostil());

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="修改付款合同批注")
    public String editPostil() {
        AjaxMessage message = new AjaxMessage();
        try {
            paymentPostilWS.update(pay.getPaymentPostil());

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

  //填充Log
    private void fileLogDTO(PaymentDTO dto, PaymentLogDTO paymentLog) {
        if (pay.getContractSum() != null){
            paymentLog.setContractSum(pay.getContractSum());
            if(dto.getContractSum()!=null){
                paymentLog.setContractSumChange(pay.getContractSum() - dto.getContractSum());
            }else {
                paymentLog.setContractSumChange(pay.getContractSum());
            }
        }else{
            pay.setContractSum((double) 0);
            paymentLog.setContractSum((double) 0);
            if(dto.getContractSum()!=null){
                paymentLog.setContractSumChange(0 - dto.getContractSum());
            }else {
                paymentLog.setContractSumChange((double) 0);
                paymentLog.setContractSumChange((double) 0);
            }

        }
        if (pay.getPayment() != null){
            paymentLog.setPayment(pay.getPayment());
            if(dto.getPayment()!=null){
                paymentLog.setPaymentChange(pay.getPayment() - dto.getPayment());
            }else {
                paymentLog.setPaymentChange(pay.getPayment());
            }
        }else {
            pay.setPayment((double) 0);
            paymentLog.setPayment((double)0);
            if(dto.getPayment()!=null) {
                paymentLog.setPaymentChange(0 - dto.getPayment());
            }else {
                paymentLog.setPaymentChange((double) 0);
            }
        }

        if (pay.getPaymentNo() != null){
            paymentLog.setPaymentNo(pay.getPaymentNo());
            if(dto.getPaymentNo()!=null){
                paymentLog.setPaymentNoChange(pay.getPaymentNo() - dto.getPaymentNo());
            }else {
                paymentLog.setPaymentNoChange(pay.getPaymentNo());
            }
        }else {
            pay.setPaymentNo((double) 0);
            paymentLog.setPaymentNo((double)0);
            if(dto.getPaymentNo()!=null) {
                paymentLog.setPaymentNoChange(0 - dto.getPaymentNo());
            }else {
                paymentLog.setPaymentNoChange((double)0);
            }
        }

    }

  //得到完整的付款合同
    private PaymentDTO getFullPayment() {
        if(pay.getContractSum()==null){
            pay.setContractSum((double) 0);
        }
        if(pay.getPayment()==null){
            pay.setPayment((double) 0);
        }
        if(pay.getPaymentNo()==null){
            pay.setPaymentNo((double) 0);
        }
        PaymentDTO result = pay;
        Set<ContractGatheringDTO> contracts = new HashSet<>();
        if (belongContractNames != null) {
            for (int i = 0; i < belongContractNames.length; i++) {

                ContractGatheringDTO contractGatheringDTO = new ContractGatheringDTO();
                contractGatheringDTO.setId(belongContractNames[i]);
                contracts.add(contractGatheringDTO);
            }
            result.setContractGatherings(contracts);
        }
        return result;
    }

    @Operation(type="导出Excel")
    public void exportExcel()
    {
        AjaxMessage message = new AjaxMessage();
        try{
            getqBean().setOrder("desc");
            getqBean().setSort("contractSn");
            getqBean().setRows(10000);
            String hql = getOutputHql();
            HqlQuery query = new HqlQuery(hql);
            Object[] params = query.getParametersValue();
            PageBeanEasyUI pageBean = paymentWS.getPageBeanByHQL(getqBean(),hql,params);
            ExportExcelHelper.exportPayment(pageBean.getRows());
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

    private String getOutputHql()
    {
        String hql = "from Payment where isValid=true";
        if(!Strings.isNullOrEmpty(getPage().getContractName())){
            hql += " and contractName like '%" + getPage().getContractName() + "%'";
        }
        if(!Strings.isNullOrEmpty(getPage().getSheetNameQuery())){
            hql += " and sheetName='" + getPage().getSheetNameQuery() + "'";
        }
        if(!Strings.isNullOrEmpty(getPage().getCompanySecondQuery())){
            hql += " and companySecond like '%" + getPage().getCompanySecondQuery() + "%'";
        }
        if(!Strings.isNullOrEmpty(getPage().getPersonQuery())){
            hql += " and purchasePerson like '%" + getPage().getPersonQuery() + "%'";
        }
        if(getPage().getContractSumQuery() != null){
            hql += " and contractSum='" + getPage().getContractSumQuery() + "'";
        }
        if(!Strings.isNullOrEmpty(getPage().getContractTypeQuery())){
            hql += " and contractType='" + getPage().getContractTypeQuery() + "'";
        }
        return hql;
    }

    public PaymentQuery getModel() {
        return getPage();
    }

    public PaymentDTO getPay() {
        return pay;
    }

    public void setPay(PaymentDTO pay) {
        this.pay = pay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String[] getBelongContractNames() {
        return belongContractNames;
    }

    public void setBelongContractNames(String[] belongContractNames) {
        this.belongContractNames = belongContractNames;
    }

    public PaymentQuery getPage() {
        return page;
    }

    public void setPage(PaymentQuery page) {
        this.page = page;
    }

    public QueryBeanEasyUI getqBean() {
        return qBean;
    }

    public void setqBean(QueryBeanEasyUI qBean) {
        this.qBean = qBean;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
