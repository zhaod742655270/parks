package com.hbyd.parks.client.officesys.action;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.hbyd.parks.client.queryBean.QuotationQueryBean;
import com.hbyd.parks.client.util.ExportExcelHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.hql.HqlQuery;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QuotationQuery;
import com.hbyd.parks.dto.officesys.QuotationDTO;
import com.hbyd.parks.dto.officesys.QuotationProjectDTO;
import com.hbyd.parks.ws.officesys.QuotationProjectWS;
import com.hbyd.parks.ws.officesys.QuotationWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
* Created by IntelliJ IDEA
* Author:Zhang_F
* Data:2016/4/25
*/
@Controller
@Scope("prototype")
@Module(description = "报价管理")
public class QuotationAction extends ActionSupport implements ModelDriven<QuotationQuery> {
    private static final long serialVersionUID = 1L;
    private QuotationQuery page = new QuotationQuery();
    private Gson gson = new Gson();

    private QuotationQueryBean qBean = new QuotationQueryBean();

    private QuotationDTO quotation;
    private QuotationProjectDTO project;
    private String id;
    private File file;
    private String fileFileName;// 封装文件名

    private String projectId;
    private String projectName;

    @Resource
    private QuotationWS quotationWS;
    @Resource
    private QuotationProjectWS quotationProjectWS;

    public String projectList() throws Exception {
        PageBeanEasyUI list= quotationProjectWS.getProjectPageBeanByQueryBean(getPage());
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    public String quotationList() throws Exception {
        PageBeanEasyUI list= quotationWS.getQuotationPageBeanByQueryBean(projectId, getPage());
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    @Operation(type="新增项目")
    public String addProject() throws Exception{
        AjaxMessage message = new AjaxMessage();
        try {
            quotationProjectWS.save(project);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="修改项目")
    public String editProject() {
        AjaxMessage message = new AjaxMessage();
        try {
            quotationProjectWS.update(project);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="删除项目")
    public String deleteProject() {
        AjaxMessage message = new AjaxMessage();
        try {
            //伪删
            quotationProjectWS.delFake(id);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="新增设备报价记录")
    public String addQuotation() {
        AjaxMessage message = new AjaxMessage();
        try {
            quotationWS.save(quotation);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }


    @Operation(type="修改设备报价记录")
    public String editQuotation() {
        AjaxMessage message = new AjaxMessage();
        try {
            quotationWS.update(quotation);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="删除设备报价记录")
    public String deleteQuotation() {
        AjaxMessage message = new AjaxMessage();
        try {
            //伪删
            quotationWS.delFake(id);

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
            qBean.setSort("name");
            qBean.setOrder("asc");
            qBean.setRows(10000);
            String hql = getOutputHql();
            HqlQuery query = new HqlQuery(hql);
            Object[] params = query.getParametersValue();
            PageBeanEasyUI pageBean = quotationWS.getPageBeanByHQL(qBean, hql, params);
            ExportExcelHelper.exportQuotation(pageBean.getRows(), projectName);
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

    public void importExcel() throws Exception{
        AjaxMessage message = new AjaxMessage();
        try{
            if(fileFileName.endsWith("xls")) {                      //excel2003
                importEcxelForHSSF(file);
            }else if(fileFileName.endsWith("xlsx")){               //excel2007
                importEcxelForXSSF(file);
            }
        }catch(Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally{
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    /**
     * 根据查询条件获得hql
     * @return hql
     */
    private String getOutputHql()
    {
        String hql = "from Quotation where isValid=true and projectIdFK='"+projectId + "'";
        if(!Strings.isNullOrEmpty(page.getNameQuery())){
            hql += " and name like '%" + page.getNameQuery() + "%'";
        }

        if(!Strings.isNullOrEmpty(page.getSpecificationQuery())){
            hql += " and specification like '%" + page.getSpecificationQuery() + "%'";
        }

        if(!Strings.isNullOrEmpty(page.getBrandQuery())){
            hql += " and brand like '%" + page.getBrandQuery() + "%'";
        }
        return hql;
    }

    public void importEcxelForHSSF(File file) throws Exception{
        //读取文件
        InputStream is = new FileInputStream(file);
        POIFSFileSystem fs = new POIFSFileSystem(is);
        HSSFWorkbook wb = new HSSFWorkbook(fs);

        //获取第一个sheet表
        Sheet readsheet = wb.getSheetAt(0);
        int firstRowIndex = readsheet.getFirstRowNum();     //第一行编号
        int lastRowIndex = readsheet.getLastRowNum();       //最后一行编号

        // 读取标头(项目)
        Row titleRow = readsheet.getRow(firstRowIndex);
        String projectName = titleRow.getCell(0).getStringCellValue();

        //保存项目
        QuotationProjectDTO project = new QuotationProjectDTO();
        project.setSheetName(String.valueOf(DateTime.now().getYear()));
        project.setProjectName(projectName);
        project = quotationProjectWS.save(project);

        //读取数据
        for(int index=firstRowIndex+2;index<lastRowIndex;index++){
            QuotationDTO quotation = new QuotationDTO();
            Row row = readsheet.getRow(index);          // 获取当前行

            //如果检测到"以上合计"，则表示已经到总和部分，停止读取
            if(row.getCell(2).getStringCellValue().equals("以上合计")){
                break;
            }

            quotation.setProjectIdFK(project.getId());                      //项目ID

            Cell nameCell = row.getCell(1);
            String nameCellValue = this.getCellValue(nameCell, true);       // 名称1
            quotation.setName(nameCellValue);

            Cell brandCell = row.getCell(2);
            String brandCellValue = this.getCellValue(brandCell, true);       // 品牌2
            quotation.setBrand(brandCellValue);

            Cell specificationCell = row.getCell(3);
            String specificationCellValue = this.getCellValue(specificationCell, true);       // 规格型号3
            quotation.setSpecification(specificationCellValue);

            Cell unitCell = row.getCell(4);
            String unitCellValue = this.getCellValue(unitCell, true);       // 单位4
            quotation.setUnit(unitCellValue);

            Cell numCell = row.getCell(5);
            String numCellValue = this.getCellValue(numCell, true);       // 数量5
            quotation.setQuantity(getRoundHALFUPFloat(numCellValue,2));

            Cell priceCell = row.getCell(6);
            String priceCellValue = this.getCellValue(priceCell, true);       // 单价6
            quotation.setPrice(getRoundHALFUPFloat(priceCellValue,2));

            Cell valenceCell = row.getCell(7);
            String valenceCellValue = this.getCellValue(valenceCell, true);       // 合价7
            quotation.setValence(getRoundHALFUPFloat(valenceCellValue,2));

            Cell ratioCell = row.getCell(8);
            String ratioCellValue = this.getCellValue(ratioCell, true);       // 系数8
            quotation.setRatio(getRoundHALFUPFloat(ratioCellValue,2));

            Cell costPriceCell = row.getCell(9);
            String costPriceCellValue = this.getCellValue(costPriceCell, true);       // 成本单价9
            quotation.setCostPrice(getRoundHALFUPFloat(costPriceCellValue,2));

            Cell costValenceCell = row.getCell(10);
            String costValenceCellValue = this.getCellValue(costValenceCell, true);       // 成本合价10
            quotation.setCostValence(getRoundHALFUPFloat(costValenceCellValue,2));

            Cell noteCell = row.getCell(11);
            String noteCellValue = this.getCellValue(noteCell, true);       // 备注11
            quotation.setNote(noteCellValue);

            quotationWS.save(quotation);        //保存数据
        }
        is.close();
    }

    public void importEcxelForXSSF(File file) throws Exception{
        //读取文件
        XSSFWorkbook xwb = new XSSFWorkbook(file);

        //获取第一个sheet表
        XSSFSheet readsheet = xwb.getSheetAt(0);
        int firstRowIndex = readsheet.getFirstRowNum();     //第一行编号
        int lastRowIndex = readsheet.getLastRowNum();       //最后一行编号

        // 读取标头(项目)
        XSSFRow titleRow = readsheet.getRow(firstRowIndex);
        String projectName = titleRow.getCell(0).getStringCellValue();

        //保存项目
        QuotationProjectDTO project = new QuotationProjectDTO();
        project.setSheetName(String.valueOf(DateTime.now().getYear()));
        project.setProjectName(projectName);
        project = quotationProjectWS.save(project);

        //读取数据
        for(int index=firstRowIndex+2;index<lastRowIndex;index++){
            QuotationDTO quotation = new QuotationDTO();
            XSSFRow row = readsheet.getRow(index);          // 获取当前行

            //如果检测到"以上合计"，则表示已经到总和部分，停止读取
            if(row.getCell(2).getStringCellValue().equals("以上合计")){
                break;
            }

            quotation.setProjectIdFK(project.getId());                      //项目ID

            Cell nameCell = row.getCell(1);
            String nameCellValue = this.getCellValue(nameCell, true);       // 名称1
            quotation.setName(nameCellValue);

            Cell brandCell = row.getCell(2);
            String brandCellValue = this.getCellValue(brandCell, true);       // 品牌2
            quotation.setBrand(brandCellValue);

            Cell specificationCell = row.getCell(3);
            String specificationCellValue = this.getCellValue(specificationCell, true);       // 规格型号3
            quotation.setSpecification(specificationCellValue);

            Cell unitCell = row.getCell(4);
            String unitCellValue = this.getCellValue(unitCell, true);       // 单位4
            quotation.setUnit(unitCellValue);

            Cell numCell = row.getCell(5);
            String numCellValue = this.getCellValue(numCell, true);       // 数量5
            quotation.setQuantity(getRoundHALFUPFloat(numCellValue,2));

            Cell priceCell = row.getCell(6);
            String priceCellValue = this.getCellValue(priceCell, true);       // 单价6
            quotation.setPrice(getRoundHALFUPFloat(priceCellValue,2));

            Cell valenceCell = row.getCell(7);
            String valenceCellValue = this.getCellValue(valenceCell, true);       // 合价7
            quotation.setValence(getRoundHALFUPFloat(valenceCellValue,2));

            Cell ratioCell = row.getCell(8);
            String ratioCellValue = this.getCellValue(ratioCell, true);       // 系数8
            quotation.setRatio(getRoundHALFUPFloat(ratioCellValue,2));

            Cell costPriceCell = row.getCell(9);
            String costPriceCellValue = this.getCellValue(costPriceCell, true);       // 成本单价9
            quotation.setCostPrice(getRoundHALFUPFloat(costPriceCellValue,2));

            Cell costValenceCell = row.getCell(10);
            String costValenceCellValue = this.getCellValue(costValenceCell, true);       // 成本合价10
            quotation.setCostValence(getRoundHALFUPFloat(costValenceCellValue,2));

            Cell noteCell = row.getCell(11);
            String noteCellValue = this.getCellValue(noteCell, true);       // 备注11
            quotation.setNote(noteCellValue);

            quotationWS.save(quotation);        //保存数据
        }
        xwb.close();
    }

    private String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }

        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(CellType.STRING);
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    /**
     * 获取拥有指定小数位的float型数据
     * @param num 原数值       //String型，浮点型会导致精准度丢失
     * @param bit 小数位
     * @return 结果
     */
    public float getRoundHALFUPFloat(String num,int bit){
        if(Strings.isNullOrEmpty(num)){
            return 0f;
        }else {
            BigDecimal b = new BigDecimal(num);
            float fResult = b.setScale(bit, BigDecimal.ROUND_HALF_UP).floatValue();       //四舍五入
            return fResult;
        }
    }

    public QuotationQuery getModel() {return page;}

    public QuotationDTO getQuotation() {
        return quotation;
    }

    public void setQuotation(QuotationDTO quotation) {
        this.quotation = quotation;
    }

    public QuotationProjectDTO getProject() throws Exception{
        return project;
    }

    public void setProject(QuotationProjectDTO project) throws Exception{
        this.project = project;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuotationQuery getPage() {
        return page;
    }

    public void setPage(QuotationQuery page) {
        this.page = page;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
}
