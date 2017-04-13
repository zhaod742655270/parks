package com.hbyd.parks.client.officesys.action;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.hbyd.parks.client.util.ComboHelper;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.common.model.AcceptanceQuery;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.Combobox;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.officesys.AcceptanceDTO;
import com.hbyd.parks.dto.officesys.AcceptanceExamineDTO;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.ws.officesys.AcceptanceExamineWS;
import com.hbyd.parks.ws.officesys.AcceptancePostilWS;
import com.hbyd.parks.ws.officesys.AcceptanceWS;
import com.hbyd.parks.ws.officesys.ContractGatheringWS;
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
import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/4/25
 */
@Controller
@Scope("prototype")
@Module(description = "验收管理")
public class AcceptanceAction extends ActionSupport implements ModelDriven<AcceptanceQuery> {
    private static final long serialVersionUID = 1L;
    private AcceptanceQuery page = new AcceptanceQuery();
    private Gson gson = new Gson();

    private AcceptanceDTO acceptance;

    private String userID;

    @Resource
    private AcceptanceWS acceptanceWS;

    @Resource
    private ContractGatheringWS contractGatheringWS;

    @Resource
    private AcceptancePostilWS acceptancePostilWS;

    @Resource
    private AcceptanceExamineWS acceptanceExamineWS;

    private String year;

    private String id;

    private String sheetName;

    private String contractNo;

    private File file;

    private String fileContentType;// 封装文件类型

    private String fileFileName;// 封装文件名

    private String arrivalTime;
    private Float arrivalQuantity;
    private Float remainQuantity;
    private String direction;
    private boolean packages;
    private boolean appearance;
    private boolean datasheet;
    private boolean POST;
    private String operation;
    private String arrivalNote;
    private String productID;

    private String jsonAllRows;


    public String acceptanceList() throws Exception {
        PageBeanEasyUI list= acceptanceWS.getPageBeanByContractID(id,page);
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;

    }

    public String getProject() throws Exception{
        List<ContractGatheringDTO> lists=acceptanceWS.getProjectByYear(year);
        if(lists==null){
            lists=new ArrayList<>();
        }
        List<Combobox> project = ComboHelper.getContractNameCombobox(lists);
        String result = gson.toJson(project);
        JsonHelper.writeJson(result);
        return null;
    }

    public String getPurchaser() throws Exception{
        List<UserDTO> lists=acceptanceWS.getPurchaser();
        if(lists==null){
            lists=new ArrayList<>();
        }
        List<Combobox> project = ComboHelper.getPurchaserNameCombobox(lists);
        String result = gson.toJson(project);
        JsonHelper.writeJson(result);
        return null;
    }

    public String getProjectManager() throws Exception{
        List<UserDTO> lists=acceptanceWS.getProjectManager();
        if(lists==null){
            lists=new ArrayList<>();
        }
        List<Combobox> project = ComboHelper.getPurchaserNameCombobox(lists);
        String result = gson.toJson(project);
        JsonHelper.writeJson(result);
        return null;
    }

    @Operation(type="新增验收清单")
    public String addAcceptance() {
        AjaxMessage message = new AjaxMessage();
        try {

         acceptanceWS.save(acceptance);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }


//新增清单时判断是否有权限新增
    public String findPermission(){
        AjaxMessage message = new AjaxMessage();
        try {
            UserDTO user = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
            if(user.getDepartment().getDeptName().equals("采购部")||user.getDepartment().getDeptName().equals("工程部")
                    ||user.getUserName().equals("super")) {
                message.setMessage("purchases");
            }else {
                message.setMessage("noPermission");
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

    /*@Operation(type="修改验收清单")
    public String saveRow() {
        AjaxMessage message = new AjaxMessage();
        try {
            AcceptanceDTO dto=acceptanceWS.getByID(id);
            dto.setArrivalTime(arrivalTime);
            dto.setArrivalQuantity(arrivalQuantity);
            dto.setRemainQuantity(remainQuantity);
            dto.setDirection(direction);
            dto.setPOST(POST);
            dto.setPackages(packages);
            dto.setAppearance(appearance);
            dto.setDatasheet(datasheet);
            dto.setOperation(operation);
            dto.setArrivalNote(arrivalNote);
            dto.setProductID(productID);
            acceptanceWS.update(dto);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }*/

    @Operation(type="保存验收清单所有修改")
    public void saveAllRows(){
        AjaxMessage message = new AjaxMessage();
        try {
            String update = getJsonAllRows();
            List<LinkedTreeMap> listUpdated = new ArrayList();
            listUpdated = gson.fromJson(update,listUpdated.getClass());

            for(int i=0;i<listUpdated.size();i++){
                LinkedTreeMap map = listUpdated.get(i);
                //填充DTO
                AcceptanceDTO dto=acceptanceWS.getByID(map.get("id").toString());
                dto.setArrivalTime(map.get("arrivalTime").toString());
                if(!Strings.isNullOrEmpty(map.get("arrivalQuantity").toString())) {
                    dto.setArrivalQuantity(Float.parseFloat(map.get("arrivalQuantity").toString()));
                }else{
                    dto.setArrivalQuantity(0.0f);
                }
                if(!Strings.isNullOrEmpty(map.get("remainQuantity").toString())) {
                    dto.setRemainQuantity(Float.parseFloat(map.get("remainQuantity").toString()));
                }else{
                    dto.setRemainQuantity(0.0f);
                }
                dto.setDirection(map.get("direction").toString());
                if(map.get("POSTName").toString().equals("true")|| map.get("POSTName").toString().equals("异常")) {
                    dto.setPOST(true);
                }else{
                    dto.setPOST(false);
                }
                if(map.get("packagesName").toString().equals("true")|| map.get("packagesName").toString().equals("有破损")){
                    dto.setPackages(true);
                }else{
                    dto.setPackages(false);
                }
                if(map.get("appearanceName").toString().equals("true")|| map.get("appearanceName").toString().equals("有缺陷")){
                    dto.setAppearance(true);
                }else{
                    dto.setAppearance(false);
                }
                if(map.get("datasheetName").toString().equals("true")|| map.get("datasheetName").toString().equals("缺项")){
                    dto.setDatasheet(true);
                }else{
                    dto.setDatasheet(false);
                }
                dto.setOperation(map.get("operation").toString());
                dto.setArrivalNote(map.get("arrivalNote").toString());
                dto.setRunnedTime(map.get("runnedTime").toString());
                dto.setProductID(map.get("productID").toString());

                //是否定制部分
                if(map.get("customName").toString().equals("true")||map.get("customName").toString().equals("是")) {
                    dto.setCustom(true);
                    dto.setInsulationLeather(map.get("insulationLeather").toString());
                    dto.setDiameter(map.get("diameter").toString());
                    dto.setAllLength(map.get("allLength").toString());
                    dto.setThick(map.get("thick").toString());
                    dto.setOutsideDiameter(map.get("outsideDiameter").toString());
                    dto.setSize(map.get("size").toString());
                    dto.setColor(map.get("color").toString());
                    dto.setMaterial(map.get("material").toString());
                    dto.setBoreDistance(map.get("boreDistance").toString());
                }else{
                    dto.setCustom(false);
                    dto.setInsulationLeather("");
                    dto.setDiameter("");
                    dto.setAllLength("");
                    dto.setThick("");
                    dto.setOutsideDiameter("");
                    dto.setSize("");
                    dto.setColor("");
                    dto.setMaterial("");
                    dto.setBoreDistance("");
                }
                acceptanceWS.update(dto);
            }
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="修改验收清单")
    public String editAcceptance() {
        AjaxMessage message = new AjaxMessage();
        try {
            AcceptanceDTO dto=acceptanceWS.getByID(acceptance.getId());
            dto.setSN(acceptance.getSN());
            dto.setPurchaserID(acceptance.getPurchaserID());
            dto.setEquipmentName(acceptance.getEquipmentName());
            dto.setBrand(acceptance.getBrand());
            dto.setTechnicalParameter(acceptance.getTechnicalParameter());
            dto.setSpecification(acceptance.getSpecification());
            dto.setUnit(acceptance.getUnit());
            dto.setQuantity(acceptance.getQuantity());
            dto.setPrice(acceptance.getPrice());
            dto.setValence(acceptance.getValence());
            dto.setRequiredArrivalTime(acceptance.getRequiredArrivalTime());
            dto.setInventory(acceptance.getInventory());
            dto.setTestNote(acceptance.getTestNote());
            dto.setBudgetNote(acceptance.getBudgetNote());
            dto.setSupplier(acceptance.getSupplier());
            dto.setPurchaseQuantity(acceptance.getPurchaseQuantity());
            dto.setProjectManagerID(acceptance.getProjectManagerID());
            acceptanceWS.update(dto);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="删除验收清单")
    public String deleteAcceptance() {
        AjaxMessage message = new AjaxMessage();
        try {
            //伪删
            acceptanceWS.delFake(id);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="清除该项目下所有验收清单")
    public String deleteAcceptanceAll(){
        AjaxMessage message = new AjaxMessage();
        try {
            page.setSort("SN");
            page.setOrder("asc");
            page.setRows(1000);
            PageBeanEasyUI list= acceptanceWS.getPageBeanByContractID(id,page);
            if(list.getRows() != null) {
                List<AcceptanceDTO> acceptanceDTOs = list.getRows();
                for(AcceptanceDTO dto : acceptanceDTOs){
                    acceptanceWS.delFake(dto.getId());
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

    @Operation(type="新增验收清单批注")
    public String addPostil() {
        AjaxMessage message = new AjaxMessage();
        try {
            acceptancePostilWS.save(acceptance.getAcceptancePostilDTO());

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="修改验收清单批注")
    public String editPostil() {
        AjaxMessage message = new AjaxMessage();
        try {
            acceptancePostilWS.update(acceptance.getAcceptancePostilDTO());

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    @Operation(type="上传验收清单表格")
    public String uploadExcel() {
        AjaxMessage message = new AjaxMessage();
        try {
            if(fileFileName.endsWith("xls")){                      //excel2003
                analysisExcelHSSF();
            }else if(fileFileName.endsWith("xlsx")){               //excel2007
                analysisExcelXSSF();
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

    @Operation(type="上传序列号表格")
    public String uploadSN() {
        AjaxMessage message = new AjaxMessage();
        try {
            analysisExcelSN();
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public  void analysisExcelSN() throws Exception{
        InputStream is = new FileInputStream(getFile());
        POIFSFileSystem fs = new POIFSFileSystem(is);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        String SN="";

        //sheet的下表从0开始,获取第一张sheet表
        Sheet readsheet = wb.getSheetAt(0);

        int firstRowIndex = readsheet.getFirstRowNum();
        int lastRowIndex = readsheet.getLastRowNum();


        // 读取数据行
        for (int rowIndex = firstRowIndex + 2; rowIndex <= lastRowIndex-1; rowIndex++) {

            Row currentRow = readsheet.getRow(rowIndex);// 当前行
            Cell SNCell = currentRow.getCell(1);
            String SNCellValue = this.getCellValue(SNCell, true);// 序号 0
            SN+=SNCellValue+",";
        }
        is.close();

        AcceptanceDTO dto=acceptanceWS.getByID(id);
        dto.setProductID(SN);
        acceptanceWS.update(dto);

    }

    public  void analysisExcelHSSF() throws Exception{
            InputStream is = new FileInputStream(getFile());
            POIFSFileSystem fs = new POIFSFileSystem(is);
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            //sheet的下表从0开始,获取第一张sheet表
            Sheet readsheet = wb.getSheetAt(0);

            int firstRowIndex = readsheet.getFirstRowNum();
            int lastRowIndex = readsheet.getLastRowNum();

           // 读取年度
            Row sheetNameRow = readsheet.getRow(firstRowIndex);
            Cell sheetNameCell = sheetNameRow.getCell(1);
            String sheetNameCellValue = this.getCellValue(sheetNameCell, true);

            // 读取合同号
            Row contractNoRow = readsheet.getRow(firstRowIndex+1);
            Cell contractNoCell = contractNoRow.getCell(1);
            String contractNoCellValue = this.getCellValue(contractNoCell, true);

            // 读取项目名称
            Row firstRow = readsheet.getRow(firstRowIndex+2);
            Cell cell0 = firstRow.getCell(1);
            String cellValue0 = this.getCellValue(cell0, true);
            String contractID= acceptanceWS.getContractFK(cellValue0,sheetNameCellValue,contractNoCellValue);


           // 读取采购人
            Row secondRow = readsheet.getRow(firstRowIndex+3);
            Cell cell1 = secondRow.getCell(1);
            String cellValue1 = this.getCellValue(cell1, true);
            String purchaserID= acceptanceWS.getUserFK(cellValue1);


            // 读取项目经理
            Row thirdRow = readsheet.getRow(firstRowIndex+4);
            Cell cell2 = thirdRow.getCell(1);
            String cellValue2 = this.getCellValue(cell2, true);
            String projectManagerID=acceptanceWS.getUserFK(cellValue2);

            // 读取数据行
            for (int rowIndex = firstRowIndex + 6; rowIndex <= lastRowIndex; rowIndex++) {
                AcceptanceDTO dto = new AcceptanceDTO();
                Row currentRow = readsheet.getRow(rowIndex);// 当前行
                Cell SNCell = currentRow.getCell(0);
                String SNCellValue = this.getCellValue(SNCell, true);// 序号 0
                dto.setSN(Float.parseFloat(SNCellValue));

                Cell equipmentNameCell = currentRow.getCell(1);
                String equipmentNameCellValue = this.getCellValue(equipmentNameCell, true);// 设备名称 1
                dto.setEquipmentName(equipmentNameCellValue);

                Cell brandCell = currentRow.getCell(2);
                String brandCellValue = this.getCellValue(brandCell, true);// 品牌 2
                dto.setBrand(brandCellValue);

                Cell specificationCell = currentRow.getCell(3);
                String specificationCellValue = this.getCellValue(specificationCell, true);// 规格型号 3
                dto.setSpecification(specificationCellValue);

                Cell technicalParameterCell = currentRow.getCell(4);
                String technicalParameterCellValue = this.getCellValue(technicalParameterCell, true);// 技术参数 4
                dto.setTechnicalParameter(technicalParameterCellValue);

                Cell unitCell = currentRow.getCell(5);
                String unitCellValue = this.getCellValue(unitCell, true);// 单位 5
                dto.setUnit(unitCellValue);

                Cell quantityCell = currentRow.getCell(6);
                String quantityCellValue = this.getCellValue(quantityCell, true);// 数量 6
                if ("".equals(quantityCellValue)) {
                    dto.setQuantity((float) 0);
                } else {
                    dto.setQuantity(Float.parseFloat(quantityCellValue));
                }

                Cell priceCell = currentRow.getCell(7);
                String priceCellValue = this.getCellValue(priceCell, true);// 单价 7
                if ("".equals(priceCellValue)) {
                    dto.setPrice((double) 0);
                } else {
                    dto.setPrice(Double.parseDouble(priceCellValue));
                }

                Cell valenceCell = currentRow.getCell(8);
                String valenceCellValue = this.getCellValue(valenceCell, true);// 合价 8
                if ("".equals(valenceCellValue)) {
                    dto.setValence((double) 0);
                } else {
                    dto.setValence(Double.parseDouble(valenceCellValue));
                }

                Cell requiredArrivalTimeCell = currentRow.getCell(9);                   //要求到货时间 9
                if (requiredArrivalTimeCell == null){
                    dto.setRequiredArrivalTime("");
                } else if (requiredArrivalTimeCell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
                    String requiredArrivalTimeCellValue = this.getCellValue(requiredArrivalTimeCell, true);
                    dto.setRequiredArrivalTime(requiredArrivalTimeCellValue);
                } else {
                    Date requiredDate = requiredArrivalTimeCell.getDateCellValue();
                    if (requiredDate != null) {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        format = DateFormat.getDateInstance(DateFormat.MEDIUM);
                        String requiredArrivalTimeCellValue = format.format(requiredDate);
                        dto.setRequiredArrivalTime(requiredArrivalTimeCellValue);
                    } else {
                        dto.setRequiredArrivalTime("");
                    }
                }

                Cell inventoryCell = currentRow.getCell(10);
                String inventoryCellValue = this.getCellValue(inventoryCell, true);// 库存 10
                dto.setInventory(inventoryCellValue);

                Cell testNoteCell = currentRow.getCell(11);
                String testNoteCellValue = this.getCellValue(testNoteCell, true);// 测试记录 11
                dto.setTestNote(testNoteCellValue);

                Cell budgetNoteCell = currentRow.getCell(12);
                String budgetNoteCellValue = this.getCellValue(budgetNoteCell, true);// 预算备注 12
                dto.setBudgetNote(budgetNoteCellValue);

                Cell supplierCell = currentRow.getCell(13);
                String supplierCellValue = this.getCellValue(supplierCell, true);// 供应商 13
                dto.setSupplier(supplierCellValue);

                Cell purchaseQuantityCell = currentRow.getCell(14);
                String purchaseQuantityCellValue = this.getCellValue(purchaseQuantityCell, true);// 采购数量 14
                if ("".equals(purchaseQuantityCellValue)) {
                    dto.setPurchaseQuantity((float) 0);
                } else {
                    dto.setPurchaseQuantity(Float.parseFloat(purchaseQuantityCellValue));
                }
                dto.setPurchaserID(purchaserID);
                dto.setProjectManagerID(projectManagerID);
                dto.setContractID(contractID);
                acceptanceWS.save(dto);
            }
        is.close();

    }

    public  void analysisExcelXSSF() throws Exception{
        XSSFWorkbook wb = new XSSFWorkbook(file);

        //sheet的下表从0开始,获取第一张sheet表
        XSSFSheet readsheet = wb.getSheetAt(0);

        int firstRowIndex = readsheet.getFirstRowNum();
        int lastRowIndex = readsheet.getLastRowNum();

        // 读取年度
        XSSFRow sheetNameRow = readsheet.getRow(firstRowIndex);
        Cell sheetNameCell = sheetNameRow.getCell(1);
        String sheetNameCellValue = this.getCellValue(sheetNameCell, true);

        // 读取合同号
        XSSFRow contractNoRow = readsheet.getRow(firstRowIndex+1);
        Cell contractNoCell = contractNoRow.getCell(1);
        String contractNoCellValue = this.getCellValue(contractNoCell, true);

        // 读取项目名称
        XSSFRow firstRow = readsheet.getRow(firstRowIndex+2);
        Cell cell0 = firstRow.getCell(1);
        String cellValue0 = this.getCellValue(cell0, true);
        String contractID= acceptanceWS.getContractFK(cellValue0,sheetNameCellValue,contractNoCellValue);


        // 读取采购人
        XSSFRow secondRow = readsheet.getRow(firstRowIndex+3);
        Cell cell1 = secondRow.getCell(1);
        String cellValue1 = this.getCellValue(cell1, true);
        String purchaserID= acceptanceWS.getUserFK(cellValue1);


        // 读取项目经理
        XSSFRow thirdRow = readsheet.getRow(firstRowIndex+4);
        Cell cell2 = thirdRow.getCell(1);
        String cellValue2 = this.getCellValue(cell2, true);
        String projectManagerID=acceptanceWS.getUserFK(cellValue2);

        // 读取数据行
        for (int rowIndex = firstRowIndex + 6; rowIndex <= lastRowIndex; rowIndex++) {
            AcceptanceDTO dto=new AcceptanceDTO();
            XSSFRow currentRow = readsheet.getRow(rowIndex);// 当前行
            Cell SNCell = currentRow.getCell(0);
            String SNCellValue = this.getCellValue(SNCell, true);// 序号 0
            dto.setSN(Float.parseFloat(SNCellValue));

            Cell equipmentNameCell = currentRow.getCell(1);
            String equipmentNameCellValue = this.getCellValue(equipmentNameCell, true);// 设备名称 1
            dto.setEquipmentName(equipmentNameCellValue);

            Cell brandCell = currentRow.getCell(2);
            String brandCellValue = this.getCellValue(brandCell, true);// 品牌 2
            dto.setBrand(brandCellValue);

            Cell specificationCell = currentRow.getCell(3);
            String specificationCellValue = this.getCellValue(specificationCell, true);// 规格型号 3
            dto.setSpecification(specificationCellValue);

            Cell technicalParameterCell = currentRow.getCell(4);
            String technicalParameterCellValue = this.getCellValue(technicalParameterCell, true);// 技术参数 4
            dto.setTechnicalParameter(technicalParameterCellValue);

            Cell unitCell = currentRow.getCell(5);
            String unitCellValue = this.getCellValue(unitCell, true);// 单位 5
            dto.setUnit(unitCellValue);

            Cell quantityCell = currentRow.getCell(6);
            String quantityCellValue = this.getCellValue(quantityCell, true);// 数量 6
            if("".equals(quantityCellValue)){
                dto.setQuantity((float) 0);
            }else {
                dto.setQuantity(Float.parseFloat(quantityCellValue));
            }

            Cell priceCell = currentRow.getCell(7);
            String priceCellValue = this.getCellValue(priceCell, true);// 单价 7
            if("".equals(priceCellValue)){
                dto.setPrice((double) 0);
            }else {
                dto.setPrice(Double.parseDouble(priceCellValue));
            }

            Cell valenceCell = currentRow.getCell(8);
            String valenceCellValue = this.getCellValue(valenceCell, true);// 合价 8
            if("".equals(valenceCellValue)){
                dto.setValence((double) 0);
            }else {
                dto.setValence(Double.parseDouble(valenceCellValue));
            }

            Cell requiredArrivalTimeCell = currentRow.getCell(9);                   //要求到货时间 9
            if (requiredArrivalTimeCell == null){
                dto.setRequiredArrivalTime("");
            } else if(requiredArrivalTimeCell.getCellType() != Cell.CELL_TYPE_NUMERIC){
                String requiredArrivalTimeCellValue = this.getCellValue(requiredArrivalTimeCell, true);
                dto.setRequiredArrivalTime(requiredArrivalTimeCellValue);
            }else {
                Date requiredDate = requiredArrivalTimeCell.getDateCellValue();
                if (requiredDate != null) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    format = DateFormat.getDateInstance(DateFormat.MEDIUM);
                    String requiredArrivalTimeCellValue = format.format(requiredDate);
                    dto.setRequiredArrivalTime(requiredArrivalTimeCellValue);
                } else {
                    dto.setRequiredArrivalTime("");
                }
            }

            Cell inventoryCell = currentRow.getCell(10);
            String inventoryCellValue = this.getCellValue(inventoryCell, true);// 库存 10
            dto.setInventory(inventoryCellValue);

            Cell testNoteCell = currentRow.getCell(11);
            String testNoteCellValue = this.getCellValue(testNoteCell, true);// 测试记录 11
            dto.setTestNote(testNoteCellValue);

            Cell budgetNoteCell = currentRow.getCell(12);
            String budgetNoteCellValue = this.getCellValue(budgetNoteCell, true);// 预算备注 12
            dto.setBudgetNote(budgetNoteCellValue);

            Cell supplierCell = currentRow.getCell(13);
            String supplierCellValue = this.getCellValue(supplierCell, true);// 供应商 13
            dto.setSupplier(supplierCellValue);

            Cell purchaseQuantityCell = currentRow.getCell(14);
            String purchaseQuantityCellValue = this.getCellValue(purchaseQuantityCell, true);// 采购数量 14
            if ("".equals(purchaseQuantityCellValue)) {
                dto.setPurchaseQuantity((float) 0);
            } else {
                dto.setPurchaseQuantity(Float.parseFloat(purchaseQuantityCellValue));
            }
            dto.setPurchaserID(purchaserID);
            dto.setProjectManagerID(projectManagerID);
            dto.setContractID(contractID);
            acceptanceWS.save(dto);
        }
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

    //审核
    @Operation(type="添加审核记录")
    public void addExamine() throws Exception{
        AjaxMessage message = new AjaxMessage();
        try {
            AcceptanceDTO dto = acceptanceWS.getByID(id);
            dto.setExamine(true);
            dto.setPurchaseOperate(false);
            acceptanceWS.update(dto);

            //同时保存审核时间的记录
            AcceptanceExamineDTO examineDTO = new AcceptanceExamineDTO();
            examineDTO.setAcceptanceId(id);
            examineDTO.setExamineDate(DateTime.now().toString());
            examineDTO.setExamineType("审核通过");
            acceptanceExamineWS.save(examineDTO);
        }catch(Exception ex){
            message.setSuccess(false);
            message.setMessage(ex.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="取消审核")
    public void deleteExamine() throws Exception{
        AjaxMessage message = new AjaxMessage();
        try {
            AcceptanceDTO dto = acceptanceWS.getByID(id);
            dto.setExamine(false);
            dto.setPurchaseOperate(false);
            acceptanceWS.update(dto);

            //同时保存审核时间的记录
            AcceptanceExamineDTO examineDTO = new AcceptanceExamineDTO();
            examineDTO.setAcceptanceId(id);
            examineDTO.setExamineDate(DateTime.now().toString());
            examineDTO.setExamineType("取消审核通过");
            acceptanceExamineWS.save(examineDTO);
        }catch(Exception ex){
            message.setSuccess(false);
            message.setMessage(ex.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }

    @Operation(type="查询审核记录")
    public void getExamineList() throws Exception{
        PageBeanEasyUI list=acceptanceExamineWS.getPageBeanByAcceptanceId(id);
        if(list.getRows() == null){
            list.setRows(new ArrayList());
        }
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
    }

    //保存进货数量修改标志
    @Operation(type="保存进货数量标志")
    public void savePurchaseOperate() throws Exception{
        AjaxMessage message = new AjaxMessage();
        try {
            AcceptanceDTO dto = acceptanceWS.getByID(id);
            dto.setPurchaseOperate(true);
            acceptanceWS.update(dto);
        }catch(Exception ex){
            message.setSuccess(false);
            message.setMessage(ex.getMessage());
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
    }


    public AcceptanceQuery getModel() {
        return page;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AcceptanceDTO getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(AcceptanceDTO acceptance) {
        this.acceptance = acceptance;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Float getArrivalQuantity() {
        return arrivalQuantity;
    }

    public void setArrivalQuantity(Float arrivalQuantity) {
        this.arrivalQuantity = arrivalQuantity;
    }

    public Float getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(Float remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isPackages() {
        return packages;
    }

    public void setPackages(boolean packages) {
        this.packages = packages;
    }

    public boolean isAppearance() {
        return appearance;
    }

    public void setAppearance(boolean appearance) {
        this.appearance = appearance;
    }

    public boolean isDatasheet() {
        return datasheet;
    }

    public void setDatasheet(boolean datasheet) {
        this.datasheet = datasheet;
    }

    public boolean isPOST() {
        return POST;
    }

    public void setPOST(boolean POST) {
        this.POST = POST;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getArrivalNote() {
        return arrivalNote;
    }

    public void setArrivalNote(String arrivalNote) {
        this.arrivalNote = arrivalNote;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getJsonAllRows() {
        return jsonAllRows;
    }

    public void setJsonAllRows(String jsonAllRows) {
        this.jsonAllRows = jsonAllRows;
    }
}
