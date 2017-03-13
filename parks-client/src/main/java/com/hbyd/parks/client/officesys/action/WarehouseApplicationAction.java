package com.hbyd.parks.client.officesys.action;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.WarehouseApplicationQuery;
import com.hbyd.parks.dto.officesys.WarehouseApplicationDTO;
import com.hbyd.parks.dto.officesys.WarehouseApplicationProDTO;
import com.hbyd.parks.ws.officesys.WarehouseApplicationProWS;
import com.hbyd.parks.ws.officesys.WarehouseApplicationWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Zhao_d on 2017/1/23.
 */
@Controller
@Scope("prototype")
@Module(description = "申请单信息")
public class WarehouseApplicationAction extends ActionSupport implements ModelDriven<WarehouseApplicationQuery> {

    @Resource
    private WarehouseApplicationWS warehouseApplicationWS;

    @Resource
    private WarehouseApplicationProWS warehouseApplicationProWS;

    private WarehouseApplicationQuery query = new WarehouseApplicationQuery();
    private WarehouseApplicationDTO warehouseApplication = new WarehouseApplicationDTO();
    private Gson gson = new Gson();
    private String id;
    private File file;
    private String recordPersonID;
    private String recordDate;

    public void applicationList(){
        PageBeanEasyUI list = warehouseApplicationWS.getPageBeanByQueryBean(query);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public void applicationProList(){
        PageBeanEasyUI list = warehouseApplicationProWS.getPageBeanByQueryBean(query, getId());
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    public void editWarehouseApplication(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseApplicationWS.update(warehouseApplication);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void deleteWarehouseApplication(){
        AjaxMessage massage = new AjaxMessage();
        try{
            warehouseApplicationWS.delFake(id);
        }catch(Exception e){
            massage.setSuccess(false);
            massage.setMessage(e.getMessage());
        }finally {
            String result = gson.toJson(massage);
            JsonHelper.writeJson(result);
        }
    }

    public void importExcel() throws Exception{
        AjaxMessage message = new AjaxMessage();
        try{
            if(file.getPath().endsWith("xlsx")){                      //office2007及以后
                importExcelForXSSF(file);
            }else{                                                   //office2003
                importExcelForHSSF(file);
            }
        }catch(Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }finally{
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    public void importExcelForXSSF(File file) throws Exception {
        //读取文件
        XSSFWorkbook xwb = new XSSFWorkbook(file);

        //获取第一个sheet表
        XSSFSheet readsheet = xwb.getSheetAt(0);
        int firstRowIndex = readsheet.getFirstRowNum();     //第一行编号
        int lastRowIndex = readsheet.getLastRowNum();       //最后一行编号

        // 读取标头(申请单名称)
        XSSFRow titleRow = readsheet.getRow(firstRowIndex);
        String applyName = titleRow.getCell(0).getStringCellValue();

        //保存项目
        WarehouseApplicationDTO applyDTO = new WarehouseApplicationDTO();
        applyDTO.setName(applyName);
        applyDTO.setNumber(warehouseApplicationWS.getNewNumber());
        applyDTO.setRecordPersonID(recordPersonID);
        applyDTO.setRecordDate(recordDate);
        applyDTO = warehouseApplicationWS.save(applyDTO);

        //读取数据
        int SN = 1;
        for(int index=firstRowIndex+2;index<lastRowIndex;index++) {
            WarehouseApplicationProDTO product = new WarehouseApplicationProDTO();
            XSSFRow row = readsheet.getRow(index);          // 获取当前行

            //如果部件名称为空，则此行不读取
            if(row.getCell(1).getStringCellValue().equals("")){
                continue;
            }
            product.setSND(String.valueOf(SN));
            product.setParentIdFK(applyDTO.getId());
            product.setProductName(row.getCell(1).getStringCellValue());
            product.setProductModelNumber(row.getCell(2).getStringCellValue());
            row.getCell(3).setCellType(CellType.STRING);
            product.setProductSpecifications(row.getCell(3).getStringCellValue());
            product.setProductBrand(row.getCell(5).getStringCellValue());
            product.setProductUnit(row.getCell(7).getStringCellValue());
            row.getCell(8).setCellType(CellType.STRING);
            if(!Strings.isNullOrEmpty(row.getCell(8).getStringCellValue())) {
                product.setQuantity(Double.valueOf(row.getCell(8).getStringCellValue()));
                product.setQuantityInput(Double.valueOf(row.getCell(8).getStringCellValue()));
            }else{
                product.setQuantity(0d);
                product.setQuantityInput(0d);
            }
            product.setNote(row.getCell(10).getStringCellValue());
            product.setQuantityInput(product.getQuantity());

            warehouseApplicationProWS.save(product);
            SN ++;
        }
    }

    public void importExcelForHSSF(File file) throws Exception {
        //读取文件
        HSSFWorkbook xwb = new HSSFWorkbook(new FileInputStream(file));

        //获取第一个sheet表
        HSSFSheet readsheet = xwb.getSheetAt(0);
        int firstRowIndex = readsheet.getFirstRowNum();     //第一行编号
        int lastRowIndex = readsheet.getLastRowNum();       //最后一行编号

        // 读取标头(申请单名称)
        HSSFRow titleRow = readsheet.getRow(firstRowIndex);
        String applyName = titleRow.getCell(0).getStringCellValue();

        //保存项目
        WarehouseApplicationDTO applyDTO = new WarehouseApplicationDTO();
        applyDTO.setName(applyName);
        applyDTO.setNumber(warehouseApplicationWS.getNewNumber());
        applyDTO.setRecordPersonID(recordPersonID);
        applyDTO.setRecordDate(recordDate);
        applyDTO = warehouseApplicationWS.save(applyDTO);

        //读取数据
        int SN = 1;
        for(int index=firstRowIndex+2;index<lastRowIndex;index++) {
            WarehouseApplicationProDTO product = new WarehouseApplicationProDTO();
            HSSFRow row = readsheet.getRow(index);          // 获取当前行

            //如果部件名称为空，则此行不读取
            if(row.getCell(1).getStringCellValue().equals("")){
                continue;
            }
            product.setSND(String.valueOf(SN));
            product.setParentIdFK(applyDTO.getId());
            product.setProductName(row.getCell(1).getStringCellValue());
            product.setProductModelNumber(row.getCell(2).getStringCellValue());
            row.getCell(3).setCellType(CellType.STRING);
            product.setProductSpecifications(row.getCell(3).getStringCellValue());
            product.setProductBrand(row.getCell(5).getStringCellValue());
            product.setProductUnit(row.getCell(7).getStringCellValue());
            row.getCell(8).setCellType(CellType.STRING);
            if(!Strings.isNullOrEmpty(row.getCell(8).getStringCellValue())) {
                product.setQuantity(Double.valueOf(row.getCell(8).getStringCellValue()));
                product.setQuantityInput(Double.valueOf(row.getCell(8).getStringCellValue()));
            }else{
                product.setQuantity(0d);
                product.setQuantityInput(0d);
            }
            product.setNote(row.getCell(10).getStringCellValue());

            warehouseApplicationProWS.save(product);
            SN ++;
        }
    }

    @Override
    public WarehouseApplicationQuery getModel(){
        return query;
    }

    public WarehouseApplicationQuery getQuery() {
        return query;
    }

    public void setQuery(WarehouseApplicationQuery query) {
        this.query = query;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public WarehouseApplicationDTO getWarehouseApplication() {
        return warehouseApplication;
    }

    public void setWarehouseApplication(WarehouseApplicationDTO warehouseApplication) {
        this.warehouseApplication = warehouseApplication;
    }

    public String getRecordPersonID() {
        return recordPersonID;
    }

    public void setRecordPersonID(String recordPersonID) {
        this.recordPersonID = recordPersonID;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
