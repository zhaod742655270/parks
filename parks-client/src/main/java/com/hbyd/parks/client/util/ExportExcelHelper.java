package com.hbyd.parks.client.util;

import com.hbyd.parks.dto.attendancesys.AtteDetailDTO;
import com.hbyd.parks.dto.attendancesys.AtteInfoDTO;
import com.hbyd.parks.dto.doorsys.AccessEventInfoDTO;
import com.hbyd.parks.dto.managesys.RoleDTO;
import com.hbyd.parks.dto.officesys.*;
import com.hbyd.parks.dto.supportsys.EmployeeDTO;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Len on 14-9-11.
 */
public class ExportExcelHelper {
    private static List<RoleDTO> getData() {
        List<RoleDTO> list = new ArrayList<>();
        RoleDTO role1 = new RoleDTO();
        role1.setRoleName("test1");
        role1.setRoleDesc("testdesc");
        RoleDTO role2 = new RoleDTO();
        role2.setRoleName("test1");
        role2.setRoleDesc("testdesc");
        list.add(role1);
        list.add(role2);
        return list;
    }

    private static void createCell(Row row, int column, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 导出考勤记录
     *
     * @param list 考勤集合
     * @throws IOException
     */
    public static void exportAtteRecord(List<AtteInfoDTO> list) throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        // 创建Excel工作簿对象
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("考勤记录表");
        //标题表格样式
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        titleStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        titleStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        titleStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        titleStyle.setBorderRight(BorderStyle.valueOf((short) 1));
        //设置前景色
        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置字体
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBold(true);//设置加粗
        titleStyle.setFont(font);

        //内容表格样式
        CellStyle commonStyle = wb.createCellStyle();
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置头
        Row headerRow1 = sheet.createRow(0);
        createCell(headerRow1, 0, "日期", titleStyle);
        createCell(headerRow1, 1, "部门", titleStyle);
        createCell(headerRow1, 2, "姓名", titleStyle);
        createCell(headerRow1, 3, "上午", titleStyle);
        createCell(headerRow1, 4, "", titleStyle);
        createCell(headerRow1, 5, "", titleStyle);
        createCell(headerRow1, 6, "", titleStyle);
        createCell(headerRow1, 7, "", titleStyle);
        createCell(headerRow1, 8, "", titleStyle);
        createCell(headerRow1, 9, "下午", titleStyle);
        createCell(headerRow1, 10, "", titleStyle);
        createCell(headerRow1, 11, "", titleStyle);
        createCell(headerRow1, 12, "", titleStyle);
        createCell(headerRow1, 13, "", titleStyle);
        createCell(headerRow1, 14, "", titleStyle);
        createCell(headerRow1, 15, "汇总", titleStyle);
        createCell(headerRow1, 16, "", titleStyle);
        createCell(headerRow1, 17, "", titleStyle);
        createCell(headerRow1, 18, "", titleStyle);
        headerRow1.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

        Row headerRow2 = sheet.createRow(1);
        createCell(headerRow2, 0, "", titleStyle);
        createCell(headerRow2, 1, "", titleStyle);
        createCell(headerRow2, 2, "", titleStyle);
        createCell(headerRow2, 3, "签到时间", titleStyle);
        createCell(headerRow2, 4, "签退时间", titleStyle);
        createCell(headerRow2, 5, "迟到", titleStyle);
        createCell(headerRow2, 6, "早退", titleStyle);
        createCell(headerRow2, 7, "旷工", titleStyle);
        createCell(headerRow2, 8, "请假", titleStyle);
        createCell(headerRow2, 9, "签到时间", titleStyle);
        createCell(headerRow2, 10, "签退时间", titleStyle);
        createCell(headerRow2, 11, "迟到", titleStyle);
        createCell(headerRow2, 12, "早退", titleStyle);
        createCell(headerRow2, 13, "旷工", titleStyle);
        createCell(headerRow2, 14, "请假", titleStyle);
        createCell(headerRow2, 15, "迟到", titleStyle);
        createCell(headerRow2, 16, "早退", titleStyle);
        createCell(headerRow2, 17, "旷工", titleStyle);
        createCell(headerRow2, 18, "请假", titleStyle);
        headerRow2.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
//合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0)); //合并一、二行第一列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1)); //合并一、二行第二列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2)); //合并一、二行第三列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 8)); //合并一行，第4列至第9列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 14)); //合并一行，第10列至第15列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 18)); //合并一行，第16列至第19列

//调整列宽
//        sheet.autoSizeColumn((short) 0); //调整第1列宽度
        sheet.autoSizeColumn((short) 1); //调整第2列宽度
//        sheet.autoSizeColumn((short) 2); //调整第3列宽度
//        sheet.autoSizeColumn((short) 3); //调整第4列宽度
//        sheet.autoSizeColumn((short) 4); //调整第5列宽度
//        sheet.autoSizeColumn((short) 5); //调整第6列宽度
//        sheet.autoSizeColumn((short) 6); //调整第7列宽度
//        sheet.autoSizeColumn((short) 7); //调整第8列宽度
//        sheet.autoSizeColumn((short) 8); //调整第9列宽度
//        sheet.autoSizeColumn((short) 9); //调整第10列宽度
//        sheet.autoSizeColumn((short) 10); //调整第11列宽度
//        sheet.autoSizeColumn((short) 11); //调整第12列宽度
//        sheet.autoSizeColumn((short) 12); //调整第13列宽度
//        sheet.autoSizeColumn((short) 13); //调整第14列宽度
//        sheet.autoSizeColumn((short) 14); //调整第15列宽度
//        sheet.autoSizeColumn((short) 15); //调整第16列宽度
//        sheet.autoSizeColumn((short) 16); //调整第17列宽度
//        sheet.autoSizeColumn((short) 17); //调整第18列宽度
//        sheet.autoSizeColumn((short) 18); //调整第19列宽度

        if (list != null) {
            for (int i = 2; i < list.size() + 2; i++) {

                Row row = sheet.createRow(i);
                AtteInfoDTO atteInfo = list.get(i - 2);
                createCell(row, 0, atteInfo.getDay(), commonStyle);
                createCell(row, 1, atteInfo.getDeptName(), commonStyle);
                createCell(row, 2, atteInfo.getEmpName(), commonStyle);
                for (AtteDetailDTO atteDetail : atteInfo.getDetails()) {
                    if (atteDetail.getType().equals("上午时段")) {
                        createCell(row, 3, atteDetail.getSignInTime(), commonStyle);
                        createCell(row, 4, atteDetail.getSignOutTime(), commonStyle);
                        createCell(row, 5, Long.toString(atteDetail.getArriveLateNum()), commonStyle);
                        createCell(row, 6, Long.toString(atteDetail.getLeaveEarlyNum()), commonStyle);
                        createCell(row, 7, Long.toString(atteDetail.getAbsentNum()), commonStyle);
                        createCell(row, 8, Long.toString(atteDetail.getLeaveNum()), commonStyle);
                    } else if (atteDetail.getType().equals("下午时段")) {

                        createCell(row, 9, atteDetail.getSignInTime(), commonStyle);
                        createCell(row, 10, atteDetail.getSignOutTime(), commonStyle);
                        createCell(row, 11, Long.toString(atteDetail.getArriveLateNum()), commonStyle);
                        createCell(row, 12, Long.toString(atteDetail.getLeaveEarlyNum()), commonStyle);
                        createCell(row, 13, Long.toString(atteDetail.getAbsentNum()), commonStyle);
                        createCell(row, 14, Long.toString(atteDetail.getLeaveNum()), commonStyle);
                    }
                }
                createCell(row, 15, Long.toString(atteInfo.getArriveLateCount()), commonStyle);
                createCell(row, 16, Long.toString(atteInfo.getLeaveEarlyCount()), commonStyle);
                createCell(row, 17, Long.toString(atteInfo.getAbsentCount()), commonStyle);
                createCell(row, 18, Long.toString(atteInfo.getLeaveCount()), commonStyle);
            }
        }
        resp.setContentType("application/x-download");
        resp.addHeader("Content-Disposition", "attachment;filename=" + "workbook.xls");

        OutputStream fileOut = resp.getOutputStream();
        wb.write(fileOut);
        fileOut.close();

    }

    /**
     * 导出员工数据（包含照片）
     *
     * @param sheetName sheet名称
     * @param list      员工集合
     * @throws IOException
     */
    public static void exportEmployee(String sheetName, List<EmployeeDTO> list) throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        // 创建Excel工作簿对象
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);

        //表格样式
        CellStyle commonStyle = wb.createCellStyle();
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        commonStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderRight(BorderStyle.valueOf((short) 1));

        //设置字体
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBold(false);//设置加粗
        commonStyle.setFont(font);

        // 设置头
        Row headerRow = sheet.createRow(0);
        createCell(headerRow, 0, "照片", commonStyle);
        createCell(headerRow, 1, "姓名", commonStyle);
        createCell(headerRow, 2, "部门", commonStyle);
        createCell(headerRow, 3, "性别", commonStyle);
        createCell(headerRow, 4, "工资号", commonStyle);
        createCell(headerRow, 5, "身份证号", commonStyle);
        createCell(headerRow, 6, "职务", commonStyle);
        createCell(headerRow, 7, "是否有照片", commonStyle);

        HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i + 1);
                EmployeeDTO employee = list.get(i);
                //第一列添加照片
                createCell(row, 0, "", commonStyle);
                if (employee.getPhotoName() == null) {

                    //填充最后一列是否有照片。
                    createCell(row, headerRow.getPhysicalNumberOfCells() - 1, "否", commonStyle);
                } else {
                    ByteArrayOutputStream bytePhoto = getEmpPhoto(employee.getPhotoName());
                    if (bytePhoto != null) {
                        // 有图片时，设置行高为80px;
                        row.setHeightInPoints(80);
                        // 设置图片所在列宽度为60px,注意这里单位的一个换算
                        sheet.setColumnWidth(0, (short) (35.7 * 75));
                        // sheet.autoSizeColumn(i);

                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 0, i + 1, (short) 0, i + 1);
//                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, wb.addPicture(
                                bytePhoto.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                    }
                    //填充最后一列是否有照片。
                    createCell(row, headerRow.getPhysicalNumberOfCells() - 1, "是", commonStyle);
                }
                //创建其他列。
                createCell(row, 1, employee.getEmpName(), commonStyle);
                createCell(row, 2, employee.getDeptName(), commonStyle);
                createCell(row, 3, employee.getEmpSex() == "0" ? "男" : "女", commonStyle);
                createCell(row, 4, employee.getEmpPayNo(), commonStyle);
                createCell(row, 5, employee.getEmpIDNo(), commonStyle);
                createCell(row, 6, employee.getEmpDutyName(), commonStyle);

            }
        }
        resp.setContentType("application/x-download");
        resp.addHeader("Content-Disposition", "attachment;filename=" + "workbook.xls");
        OutputStream fileOut = resp.getOutputStream();
        wb.write(fileOut);
        fileOut.close();

    }

    /**
     * 辅助导出员工的方法，通过照片名得到照片流
     *
     * @param photoName 照片名称
     * @return 照片流
     * @throws IOException
     */
    private static ByteArrayOutputStream getEmpPhoto(String photoName) throws IOException {
        try {
            //以"."分割照片名，得到扩展名。
            String[] names = photoName.split("[.]");
            String formatName = "";
            if (names.length >= 2) {
                formatName = names[names.length - 1];
            } else {
                return null;
            }

            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            //得到照片文件
            String targetDirectory = ServletActionContext.getServletContext().getRealPath("/upload");
            File file = new File(targetDirectory + "\\" + photoName);
            BufferedImage photo = ImageIO.read(file);
            //放到二进制数组里
            ImageIO.write(photo, formatName, byteArrayOut);

            return byteArrayOut;
        }
        //读取照片异常时，返回null
        catch (IOException e) {

            return null;
        }


    }

    public static void exporAccessEvent(List<AccessEventInfoDTO> list) throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        // 创建Excel工作簿对象
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet("打卡数据");

        //表格样式
        CellStyle commonStyle = wb.createCellStyle();
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        commonStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderRight(BorderStyle.valueOf((short) 1));

        //设置字体
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBold(false);
        commonStyle.setFont(font);

        // 设置头
        Row headerRow = sheet.createRow(0);
        createCell(headerRow, 0, "时间", commonStyle);
        createCell(headerRow, 1, "位置", commonStyle);
        createCell(headerRow, 2, "姓名", commonStyle);
        createCell(headerRow, 3, "部门", commonStyle);
        createCell(headerRow, 4, "卡号", commonStyle);
        createCell(headerRow, 5, "车牌号", commonStyle);
        createCell(headerRow, 6, "身份证号", commonStyle);
        createCell(headerRow, 7, "是否合法", commonStyle);
        createCell(headerRow, 8, "进出方向", commonStyle);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i + 1);
                AccessEventInfoDTO person = list.get(i);
                //创建内容列
                createCell(row, 0, person.getEventTime(), commonStyle);
                createCell(row, 1, person.getDoorName(), commonStyle);
                createCell(row, 2, person.getPersonName(), commonStyle);
                createCell(row, 3, person.getDeptName(), commonStyle);
                createCell(row, 4, person.getCardNo(), commonStyle);
                createCell(row, 5, person.getUpPlateNo(), commonStyle);
                createCell(row, 6, person.getIdNo(), commonStyle);
                createCell(row, 7, person.getIsValidPassDes(), commonStyle);
                createCell(row, 8, person.getIoType(), commonStyle);
            }
        }
        resp.setContentType("application/x-download");
        resp.addHeader("Content-Disposition", "attachment;filename=" + "workbook.xls");
        OutputStream fileOut = resp.getOutputStream();
        wb.write(fileOut);
        fileOut.close();
    }

    /**
     * 导出设备报价清单信息
     * @param list 导出数据
     * @param projectName 显示的标题
     * @throws IOException
     */
    public static void exportQuotation(List<QuotationDTO> list,String projectName) throws IOException{
        HttpServletResponse resp = ServletActionContext.getResponse();
        //创建Excel对象
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet("设备报价清单");

        //表格样式
        CellStyle commonStyle = wb.createCellStyle();
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        commonStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderRight(BorderStyle.valueOf((short) 1));

        //设置字体
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBold(false);
        commonStyle.setFont(font);

        //添加标题
        Row titleRow = sheet.createRow(0);
        sheet.createRow(1);
        createCell(titleRow,0,projectName,commonStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 12));

        //设置表头
        Row handerRow = sheet.createRow(2);
        createCell(handerRow,0,"序号",commonStyle);
        createCell(handerRow,1,"名称",commonStyle);
        createCell(handerRow,2,"规格型号",commonStyle);
        createCell(handerRow,3,"技术参数",commonStyle);
        createCell(handerRow,4,"品牌",commonStyle);
        createCell(handerRow,5,"数量",commonStyle);
        createCell(handerRow,6,"单位",commonStyle);
        createCell(handerRow,7,"单价（元）",commonStyle);
        createCell(handerRow,8,"合价（元）",commonStyle);
        createCell(handerRow,9,"系数",commonStyle);
        createCell(handerRow,10,"成本单价",commonStyle);
        createCell(handerRow,11,"成本合价",commonStyle);
        //createCell(handerRow,11,"运费",commonStyle);
        //createCell(handerRow,12,"发票类型",commonStyle);
        //createCell(handerRow,13,"质保期",commonStyle);
        //createCell(handerRow,14,"厂家负责安装调试",commonStyle);
        //createCell(handerRow,15,"报价有效期",commonStyle);
        //createCell(handerRow,16,"供货周期",commonStyle);
        //createCell(handerRow,17,"付款方式",commonStyle);
        createCell(handerRow,12,"备注",commonStyle);

        //填充数据
        if(list != null && list.size() > 0){
            for(int i=0; i<list.size();i++){
                Row row = sheet.createRow(i + 3);
                QuotationDTO quotation = list.get(i);
                createCell(row,0,String.valueOf(i+1),commonStyle);
                createCell(row,1,quotation.getName(),commonStyle);
                createCell(row,2,quotation.getSpecification(),commonStyle);
                createCell(row,3,quotation.getTechnicalParameter(),commonStyle);
                createCell(row,4,quotation.getBrand(),commonStyle);
                if(quotation.getQuantity() == null){
                    createCell(row, 5, "", commonStyle);
                } else {
                    createCell(row, 5, quotation.getQuantity().toString(), commonStyle);
                }
                createCell(row,6,quotation.getUnit(),commonStyle);
                if(quotation.getPrice() == null){
                    createCell(row, 7, "", commonStyle);
                } else {
                    createCell(row, 7, quotation.getPrice().toString(), commonStyle);
                }
                if(quotation.getValence() == null){
                    createCell(row, 8, "", commonStyle);
                } else {
                    createCell(row, 8, quotation.getValence().toString(), commonStyle);
                }
                if(quotation.getRatio() == null){
                    createCell(row, 9, "", commonStyle);
                } else {
                    createCell(row, 9, quotation.getRatio().toString(), commonStyle);
                }
                if(quotation.getCostPrice() == null){
                    createCell(row, 10, "", commonStyle);
                } else {
                    createCell(row, 10, quotation.getCostPrice().toString(), commonStyle);
                }
                if(quotation.getCostValence() == null){
                    createCell(row, 11, "", commonStyle);
                } else {
                    createCell(row, 11, quotation.getCostValence().toString(), commonStyle);
                }
                /*if(quotation.getFreight() == null) {
                    createCell(row, 11, "", commonStyle);
                }else if (quotation.getFreight()){
                    createCell(row, 11, "含", commonStyle);
                }else if(!quotation.getFreight()) {
                    createCell(row, 11, "不含", commonStyle);
                }else{
                    createCell(row, 11, "", commonStyle);
                }
                createCell(row,12,quotation.getInvoice(),commonStyle);
                createCell(row,13,quotation.getWarranty(),commonStyle);
                if(quotation.getDebugging() == null) {
                    createCell(row, 14, "", commonStyle);
                }else if (quotation.getDebugging()){
                    createCell(row, 14, "是", commonStyle);
                }else if(!quotation.getDebugging()) {
                    createCell(row, 14, "否", commonStyle);
                }else{
                    createCell(row, 14, "", commonStyle);
                }
                createCell(row,15,quotation.getValidityPeriod(),commonStyle);
                createCell(row,16,quotation.getSupplyCycle(),commonStyle);
                createCell(row,17,quotation.getPayment(),commonStyle);*/
                createCell(row,12,quotation.getNote(),commonStyle);
            }
        }
        //调整列宽（适应中文）
        for(int i=0;i<13;i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,(int)(sheet.getColumnWidth(i)*1.25));
        }
        sheet.setColumnWidth(2,20*256);
        sheet.setColumnWidth(3,20*256);
        sheet.setColumnWidth(12,25*256);
        //以流的形式将文件提交至前台
        resp.setContentType("application/x-download");
        String outFileName = java.net.URLEncoder.encode(projectName,"UTF-8");
        resp.addHeader("Content-Disposition", "attachment;filename=" + outFileName + ".xls");
        resp.setCharacterEncoding("utf-8");
        OutputStream fileOut = resp.getOutputStream();
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }

    /**
     * 导出收款合同清单
     * @param list 清单数据
     * @throws IOException
     */
    public static void exportConGathering(List<ContractGatheringDTO> list,String sheetName) throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        //创建Excel对象
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Sheet1");

        //表格样式
        CellStyle commonStyle = wb.createCellStyle();               //数据样式
        CellStyle commonStyle2 = wb.createCellStyle();              //数据中的小计部分
        CellStyle commonStyleTitle = wb.createCellStyle();          //标题样式
        CellStyle commonStyleHander = wb.createCellStyle();         //表头样式
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        commonStyleHander.setAlignment(HorizontalAlignment.CENTER);
        commonStyleHander.setVerticalAlignment(VerticalAlignment.CENTER);
        commonStyleTitle.setAlignment(HorizontalAlignment.CENTER);
        commonStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置边框
        commonStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderRight(BorderStyle.valueOf((short) 1));
        commonStyleHander.setBorderBottom(BorderStyle.valueOf((short) 1));
        commonStyleHander.setBorderTop(BorderStyle.valueOf((short) 1));
        commonStyleHander.setBorderLeft(BorderStyle.valueOf((short) 1));
        commonStyleHander.setBorderRight(BorderStyle.valueOf((short) 1));

        //设置字体
        Font font = wb.createFont();            //数据字体
        Font fontHander = wb.createFont();          //表头字体
        Font fontTitle = wb.createFont();           //标题字体
        font.setFontHeightInPoints((short) 10);//设置字体大小
        fontHander.setFontHeightInPoints((short) 10);//设置字体大小
        fontHander.setBold(true);
        fontTitle.setFontHeightInPoints((short) 14);//设置字体大小
        fontTitle.setBold(true);

        commonStyle.setFont(font);
        commonStyleHander.setFont(fontHander);
        commonStyleTitle.setFont(fontTitle);

        //设置填充颜色
        commonStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        commonStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        commonStyle2.cloneStyleFrom(commonStyle);
        commonStyle2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

        //添加标题
        sheet.createRow(0);
        Row titleRow = sheet.createRow(1);
        sheet.createRow(2);
        titleRow.setHeight((short)(2*256));
        createCell(titleRow,1,"系统集成事业部" + sheetName + "收款合同一览表",commonStyleTitle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 14));

        //设置表头
        Row handerRow = sheet.createRow(3);
        handerRow.setHeight((short)(1.4f*256));
        createCell(handerRow,1,"远东合同号",commonStyleHander);
        createCell(handerRow,2,"合同号",commonStyleHander);
        createCell(handerRow,3,"合同名称",commonStyleHander);
        createCell(handerRow,4,"甲方签约单位",commonStyleHander);
        createCell(handerRow,5,"乙方签约单位",commonStyleHander);
        createCell(handerRow,6,"签订日期",commonStyleHander);
        createCell(handerRow,7,"合同金额",commonStyleHander);
        createCell(handerRow,8,"已收款金额",commonStyleHander);
        createCell(handerRow,9,"未收款金额",commonStyleHander);
        createCell(handerRow,10,"挂账金额",commonStyleHander);
        createCell(handerRow,11,"剩余金额",commonStyleHander);
        createCell(handerRow,12,"项目经理",commonStyleHander);
        createCell(handerRow,13,"工程负责人",commonStyleHander);
        createCell(handerRow,14,"备注",commonStyleHander);

        //填充数据
        if(list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i + 4);
                row.setHeight((short)(1.4f*256));
                ContractGatheringDTO conGathering = list.get(i);
                String name =conGathering.getContractName();
                CellStyle cellStyle;
                //改变合计行的背景颜色
                if(name != null && name.substring(name.length()-2,name.length()).equals("小计")) {
                    cellStyle = commonStyle2;               //小计的行使用灰色背景色
                } else{
                    cellStyle = commonStyle;                //其他行使用白色背景色
                }

                createCell(row, 1, conGathering.getContractNoYD(), cellStyle);
                createCell(row, 2, conGathering.getContractNo(), cellStyle);
                createCell(row, 3, conGathering.getContractName(), cellStyle);
                createCell(row, 4, conGathering.getCompanyFirst(), cellStyle);
                createCell(row, 5, conGathering.getCompanySecond(), cellStyle);
                createCell(row, 6, conGathering.getSignDate(), cellStyle);
                if(conGathering.getAmount() == null){
                    createCell(row, 7, "", cellStyle);
                } else{
                    createCell(row, 7, String.format("%.2f",conGathering.getAmount()), cellStyle);
                }
                if(conGathering.getReceived() == null){
                    createCell(row, 8, "", cellStyle);
                } else{
                    createCell(row, 8, String.format("%.2f",conGathering.getReceived()), cellStyle);
                }
                if(conGathering.getReceiveNo() == null){
                    createCell(row, 9, "", cellStyle);
                } else{
                    createCell(row, 9, String.format("%.2f",conGathering.getReceiveNo()), cellStyle);
                }
                if(conGathering.getOncredit() == null){
                    createCell(row, 10, "", cellStyle);
                } else{
                    createCell(row, 10, String.format("%.2f",conGathering.getOncredit()), cellStyle);
                }
                if(conGathering.getRemain() == null){
                    createCell(row, 11, "", cellStyle);
                } else{
                    createCell(row, 11, String.format("%.2f",conGathering.getRemain()), cellStyle);
                }
                createCell(row, 12, conGathering.getProjectManager(), cellStyle);
                createCell(row, 13, conGathering.getProjectDirector(), cellStyle);
                createCell(row, 14, conGathering.getNote(), cellStyle);
                createCell(row, 15, "", null);
            }
        }

        //调整列宽（适应中文）
        for(int i=0;i<15;i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,(int)(sheet.getColumnWidth(i)*1.25));
        }
        sheet.setColumnWidth(14,25*256);

        //以流的形式将文件提交至前台
        resp.setContentType("application/x-download");
        resp.addHeader("Content-Disposition", "attachment;filename=" + "workbook.xls");
        resp.setCharacterEncoding("utf-8");
        OutputStream fileOut = resp.getOutputStream();
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }

    public static void exportPayment(List<PaymentDTO> list) throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        //创建Excel对象
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet("付款合同清单");

        //表格样式
        CellStyle commonStyle = wb.createCellStyle();               //数据样式
        CellStyle commonStyleHander = wb.createCellStyle();         //表头样式
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        commonStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderRight(BorderStyle.valueOf((short) 1));
        //设置字体
        Font fontHander = wb.createFont();          //表头字体
        Font font = wb.createFont();                //数据字体
        fontHander.setFontHeightInPoints((short) 10);//设置字体大小
        fontHander.setBold(true);       //设置加粗
        font.setFontHeightInPoints((short) 10);//设置字体大小

        commonStyle.setFont(font);
        commonStyleHander.cloneStyleFrom(commonStyle);
        commonStyleHander.setFont(fontHander);

        //空一行
        sheet.createRow(0);

        //设置表头
        Row handerRow = sheet.createRow(1);
        handerRow.setHeight((short)(1.4f*256));
        createCell(handerRow,1,"合同号",commonStyleHander);
        createCell(handerRow,2,"序号",commonStyleHander);
        createCell(handerRow,3,"合同名称",commonStyleHander);
        createCell(handerRow,4,"甲方单位",commonStyleHander);
        createCell(handerRow,5,"乙方单位",commonStyleHander);
        createCell(handerRow,6,"采购内容",commonStyleHander);
        createCell(handerRow,7,"签订日期",commonStyleHander);
        createCell(handerRow,8,"采购负责人",commonStyleHander);
        createCell(handerRow,9,"合同金额",commonStyleHander);
        createCell(handerRow,10,"已付款金额",commonStyleHander);
        createCell(handerRow,11,"已付款金额批注",commonStyleHander);
        createCell(handerRow,12,"未付款金额",commonStyleHander);
        createCell(handerRow,13,"未付款金额批注",commonStyleHander);
        createCell(handerRow,14,"付款条件",commonStyleHander);
        createCell(handerRow,15,"备注",commonStyleHander);

        //填充数据
        if(list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i + 2);
                row.setHeight((short)(1.4f*256));
                PaymentDTO payment = list.get(i);
                createCell(row, 1, payment.getContractNO(), commonStyle);
                createCell(row, 2, String.valueOf(i+1), commonStyle);
                createCell(row, 3, payment.getContractName(), commonStyle);
                createCell(row, 4, payment.getCompanyFirst(), commonStyle);
                createCell(row, 5, payment.getCompanySecond(), commonStyle);
                createCell(row, 6, payment.getPurchaseContent(), commonStyle);
                createCell(row, 7, payment.getSignDate(), commonStyle);
                createCell(row, 8, payment.getPurchasePerson(), commonStyle);
                if(payment.getContractSum() == null){
                    createCell(row, 9, "", commonStyle);
                } else{
                    createCell(row, 9, String.format("%.2f",payment.getContractSum()), commonStyle);
                }
                if(payment.getPayment() == null){
                    createCell(row, 10, "", commonStyle);
                } else{
                    createCell(row, 10, String.format("%.2f",payment.getPayment()), commonStyle);
                }
                if(payment.getPaymentPostil() == null){
                    createCell(row, 11, "", commonStyle);
                }else{
                    createCell(row, 11, payment.getPaymentPostil().getPaymentPostil(), commonStyle);
                }
                if(payment.getPaymentNo() == null){
                    createCell(row, 12, "", commonStyle);
                } else{
                    createCell(row, 12, String.format("%.2f",payment.getPaymentNo()), commonStyle);
                }
                if(payment.getPaymentPostil() == null){
                    createCell(row, 13, "", commonStyle);
                }else{
                    createCell(row, 13, payment.getPaymentPostil().getPaymentNoPostil(), commonStyle);
                }
                createCell(row, 14, payment.getCondition(), commonStyle);
                createCell(row, 15, payment.getNote(), commonStyle);
                createCell(row, 16, "", null);
            }
        }

        //调整列宽（适应中文）
        for(int i=0;i<16;i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,(int)(sheet.getColumnWidth(i)*1.25));
        }
        sheet.setColumnWidth(15,25*256);
        //以流的形式将文件提交至前台
        resp.setContentType("application/x-download");
        resp.addHeader("Content-Disposition", "attachment;filename=" + "workbook.xls");
        resp.setCharacterEncoding("utf-8");
        OutputStream fileOut = resp.getOutputStream();
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }

    /**
     * 导出售后维修记录列表信息
     * @param list 导出数据
     * @throws IOException
     */
    public static void exportSoftMaintenance(List<SoftMaintenanceDTO> list) throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        //创建Excel对象
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet("售后维修记录");

        //表格样式
        CellStyle commonStyle = wb.createCellStyle();               //数据样式
        CellStyle commonStyleHander = wb.createCellStyle();         //表头样式
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        commonStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderRight(BorderStyle.valueOf((short) 1));
        //设置字体
        Font fontHander = wb.createFont();          //表头字体
        Font font = wb.createFont();                //数据字体
        fontHander.setFontHeightInPoints((short) 10);//设置字体大小
        fontHander.setBold(true);       //设置加粗
        font.setFontHeightInPoints((short) 10);//设置字体大小

        commonStyle.setFont(font);
        commonStyleHander.cloneStyleFrom(commonStyle);
        commonStyleHander.setFont(fontHander);

        //空一行
        sheet.createRow(0);

        //设置表头
        Row handerRow = sheet.createRow(1);
        handerRow.setHeight((short)(1.4f*256));
        createCell(handerRow,1,"项目名称",commonStyleHander);
        createCell(handerRow,2,"产品名称",commonStyleHander);
        createCell(handerRow,3,"编号",commonStyleHander);
        createCell(handerRow,4,"登录人",commonStyleHander);
        createCell(handerRow,5,"登录日期",commonStyleHander);
        createCell(handerRow,6,"要求完成日期",commonStyleHander);
        createCell(handerRow,7,"项目联系人",commonStyleHander);
        createCell(handerRow,8,"联系方式",commonStyleHander);
        createCell(handerRow,9,"故障现象",commonStyleHander);
        createCell(handerRow,10,"最终结论",commonStyleHander);
        createCell(handerRow,11,"总结人",commonStyleHander);
        createCell(handerRow,12,"总结日期",commonStyleHander);

        //填充数据
        if(list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i + 2);
                row.setHeight((short)(1.4f*256));
                SoftMaintenanceDTO maintenance = list.get(i);
                createCell(row, 1, maintenance.getProjectName(), commonStyle);
                createCell(row, 2, maintenance.getProductName(), commonStyle);
                createCell(row, 3, maintenance.getNumber(), commonStyle);
                createCell(row, 4, maintenance.getRegPersonName(), commonStyle);
                createCell(row, 5, maintenance.getRegDate(), commonStyle);
                createCell(row, 6, maintenance.getHopeEndDate(), commonStyle);
                createCell(row, 7, maintenance.getContractsName(), commonStyle);
                createCell(row, 8, maintenance.getPhoneNo(), commonStyle);
                createCell(row, 9, maintenance.getFaultDesc(), commonStyle);
                createCell(row, 10, maintenance.getResult(), commonStyle);
                createCell(row, 11, maintenance.getResultPersonName(), commonStyle);
                createCell(row, 12, maintenance.getResultDate(), commonStyle);
                createCell(row, 13, "", null);
            }
        }
        //调整列宽（适应中文）
        for(int i=0;i<13;i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,(int)(sheet.getColumnWidth(i)*1.25));
        }
        sheet.setColumnWidth(9,25*256);     //故障现象
        sheet.setColumnWidth(10,25*256);        //最终结论

        //以流的形式将文件提交至前台
        resp.setContentType("application/x-download");
        resp.addHeader("Content-Disposition", "attachment;filename=" + "workbook.xls");
        resp.setCharacterEncoding("utf-8");
        OutputStream fileOut = resp.getOutputStream();
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }

    /**
     * 导出产品维修记录列表信息
     * @param list 导出数据
     * @throws IOException
     */
    public static void exportHandMaintenance(List<HandMaintenanceDTO> list) throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        //创建Excel对象
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet("产品维修记录");

        //表格样式
        CellStyle commonStyle = wb.createCellStyle();               //数据样式
        CellStyle commonStyleHander = wb.createCellStyle();         //表头样式
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        commonStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderRight(BorderStyle.valueOf((short) 1));
        //设置字体
        Font fontHander = wb.createFont();          //表头字体
        Font font = wb.createFont();                //数据字体
        fontHander.setFontHeightInPoints((short) 10);//设置字体大小
        fontHander.setBold(true);       //设置加粗
        font.setFontHeightInPoints((short) 10);//设置字体大小

        commonStyle.setFont(font);
        commonStyleHander.cloneStyleFrom(commonStyle);
        commonStyleHander.setFont(fontHander);

        //空一行
        sheet.createRow(0);

        //设置表头
        Row handerRow = sheet.createRow(1);
        handerRow.setHeight((short)(1.4f*256));
        createCell(handerRow,1,"项目名称",commonStyleHander);
        createCell(handerRow,2,"编号",commonStyleHander);
        createCell(handerRow,3,"产品名称型号",commonStyleHander);
        createCell(handerRow,4,"数量",commonStyleHander);
        createCell(handerRow,5,"登记人",commonStyleHander);
        createCell(handerRow,6,"登记日期",commonStyleHander);
        createCell(handerRow,7,"要求完成日期",commonStyleHander);
        createCell(handerRow,8,"批准人",commonStyleHander);
        createCell(handerRow,9,"批准日期",commonStyleHander);
        createCell(handerRow,10,"故障上报现象\\测试内容",commonStyleHander);
        createCell(handerRow,11,"上报人",commonStyleHander);
        createCell(handerRow,12,"产品编号",commonStyleHander);
        createCell(handerRow,13,"固件版本号",commonStyleHander);
        createCell(handerRow,14,"硬件版本号",commonStyleHander);
        createCell(handerRow,15,"维修\\测试依据",commonStyleHander);
        createCell(handerRow,16,"维修\\测试类别",commonStyleHander);
        createCell(handerRow,17,"故障核实情况",commonStyleHander);
        createCell(handerRow,18,"核实人",commonStyleHander);
        createCell(handerRow,19,"技术分析",commonStyleHander);
        createCell(handerRow,20,"分析人",commonStyleHander);
        createCell(handerRow,21,"维修内容",commonStyleHander);
        createCell(handerRow,22,"维修人",commonStyleHander);
        createCell(handerRow,23,"维修结果",commonStyleHander);
        createCell(handerRow,24,"测试人",commonStyleHander);
        createCell(handerRow,25,"检测费",commonStyleHander);
        createCell(handerRow,26,"工时费",commonStyleHander);
        createCell(handerRow,27,"材料费",commonStyleHander);
        createCell(handerRow,28,"维修成本(合计)",commonStyleHander);

        //填充数据
        if(list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i + 2);
                row.setHeight((short)(1.4f*256));
                HandMaintenanceDTO maintenance = list.get(i);
                createCell(row, 1, maintenance.getProjectName(), commonStyle);
                createCell(row, 2, maintenance.getNumber(), commonStyle);
                createCell(row, 3, maintenance.getProductName(), commonStyle);
                createCell(row, 4, String.valueOf(maintenance.getQuantity()), commonStyle);
                createCell(row, 5, maintenance.getRegisterPersonName(), commonStyle);
                createCell(row, 6, maintenance.getRegisterDate(), commonStyle);
                createCell(row, 7, maintenance.getHopeEndDate(), commonStyle);
                createCell(row, 8, maintenance.getApprovePersonName(), commonStyle);
                createCell(row, 9, maintenance.getApproveDate(), commonStyle);
                createCell(row, 10, maintenance.getFaultContent(), commonStyle);
                createCell(row, 11, maintenance.getReportPersonName(), commonStyle);
                createCell(row, 12, maintenance.getProductNo(), commonStyle);
                createCell(row, 13, maintenance.getFirmwareVersion(), commonStyle);
                createCell(row, 14, maintenance.getHandwareVersion(), commonStyle);
                createCell(row, 15, maintenance.getRepairBasis(), commonStyle);
                createCell(row, 16, maintenance.getRepairType(), commonStyle);
                createCell(row, 17, maintenance.getFaultVerify(), commonStyle);
                createCell(row, 18, maintenance.getVerifyPersonName(), commonStyle);
                createCell(row, 19, maintenance.getTechAnalysis(), commonStyle);
                createCell(row, 20, maintenance.getAnalyPersonName(), commonStyle);
                createCell(row, 21, maintenance.getRepairContent(), commonStyle);
                createCell(row, 22, maintenance.getRepairPersonName(), commonStyle);
                createCell(row, 23, maintenance.getRepairResult(), commonStyle);
                createCell(row, 24, maintenance.getTestPersonName(), commonStyle);
                createCell(row, 25, maintenance.getCheckCost(), commonStyle);
                createCell(row, 26, maintenance.getManhourCost(), commonStyle);
                createCell(row, 27, maintenance.getMaterialsCost(), commonStyle);
                createCell(row, 28, maintenance.getRepairCost(), commonStyle);
                createCell(row, 29, "", null);
            }
        }
        //调整列宽（适应中文）
        for(int i=0;i<29;i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,(int)(sheet.getColumnWidth(i)*1.25));
        }
        sheet.setColumnWidth(10,25*256);        //故障上报现象\测试内容
        sheet.setColumnWidth(15,25*256);        //维修\测试依据
        sheet.setColumnWidth(16,25*256);        //维修\测试类别
        sheet.setColumnWidth(17,25*256);        //故障核实情况
        sheet.setColumnWidth(19,25*256);        //技术分析
        sheet.setColumnWidth(21,25*256);        //维修内容
        sheet.setColumnWidth(23,25*256);        //维修结果

        //以流的形式将文件提交至前台
        resp.setContentType("application/x-download");
        resp.addHeader("Content-Disposition", "attachment;filename=" + "workbook.xls");
        resp.setCharacterEncoding("utf-8");
        OutputStream fileOut = resp.getOutputStream();
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }

    /**
     * 导出测试记录列表信息
     * @param list 导出数据
     * @throws IOException
     */
    public static void exportProductTest(List<ProductTestDTO> list) throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        //创建Excel对象
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet("产品测试记录");

        //表格样式
        CellStyle commonStyle = wb.createCellStyle();               //数据样式
        CellStyle commonStyleHander = wb.createCellStyle();         //表头样式
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        commonStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        commonStyle.setBorderRight(BorderStyle.valueOf((short) 1));
        //设置字体
        Font fontHander = wb.createFont();          //表头字体
        Font font = wb.createFont();                //数据字体
        fontHander.setFontHeightInPoints((short) 10);//设置字体大小
        fontHander.setBold(true);       //设置加粗
        font.setFontHeightInPoints((short) 10);//设置字体大小

        commonStyle.setFont(font);
        commonStyleHander.cloneStyleFrom(commonStyle);
        commonStyleHander.setFont(fontHander);

        //空一行
        sheet.createRow(0);

        //设置表头
        Row handerRow = sheet.createRow(1);
        handerRow.setHeight((short)(1.4f*256));
        createCell(handerRow,1,"编号",commonStyleHander);
        createCell(handerRow,2,"产品名称型号",commonStyleHander);
        createCell(handerRow,3,"产品提取位置",commonStyleHander);
        createCell(handerRow,4,"登记人",commonStyleHander);
        createCell(handerRow,5,"登记日期",commonStyleHander);
        createCell(handerRow,6,"要求完成日期",commonStyleHander);
        createCell(handerRow,7,"批准人",commonStyleHander);
        createCell(handerRow,8,"批准日期",commonStyleHander);
        createCell(handerRow,9,"数量",commonStyleHander);
        createCell(handerRow,10,"测试依据",commonStyleHander);
        createCell(handerRow,11,"测试类别",commonStyleHander);
        createCell(handerRow,12,"测试功能项描述",commonStyleHander);
        createCell(handerRow,13,"测试人",commonStyleHander);
        createCell(handerRow,14,"计划开始时间",commonStyleHander);
        createCell(handerRow,15,"计划结束时间",commonStyleHander);
        createCell(handerRow,16,"实际开始时间",commonStyleHander);
        createCell(handerRow,17,"实际结束时间",commonStyleHander);
        createCell(handerRow,18,"已解决BUG编号",commonStyleHander);
        createCell(handerRow,19,"未解决BUG编号",commonStyleHander);
        createCell(handerRow,20,"最终型号名称",commonStyleHander);
        createCell(handerRow,21,"最终提取位置",commonStyleHander);
        createCell(handerRow,22,"总结",commonStyleHander);
        createCell(handerRow,23,"备注",commonStyleHander);

        //填充数据
        if(list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i + 2);
                row.setHeight((short)(1.4f*256));
                ProductTestDTO productTest = list.get(i);
                createCell(row, 1, productTest.getNumber(), commonStyle);
                createCell(row, 2, productTest.getProductName(), commonStyle);
                createCell(row, 3, productTest.getExtractPosition(), commonStyle);
                createCell(row, 4, productTest.getRegisterPersonName(), commonStyle);
                createCell(row, 5, productTest.getRegisterDate(), commonStyle);
                createCell(row, 6, productTest.getHopeEndDate(), commonStyle);
                createCell(row, 7, productTest.getApprovePersonName(), commonStyle);
                createCell(row, 8, productTest.getApproveDate(), commonStyle);
                createCell(row, 9, String.valueOf(productTest.getQuantity()), commonStyle);
                createCell(row, 10, productTest.getTestBasis(), commonStyle);
                createCell(row, 11, productTest.getTestType(), commonStyle);
                createCell(row, 12, productTest.getTestDesc(), commonStyle);
                createCell(row, 13, productTest.getTestPersonName(), commonStyle);
                createCell(row, 14, productTest.getPlanBegDate(), commonStyle);
                createCell(row, 15, productTest.getPlanEndDate(), commonStyle);
                createCell(row, 16, productTest.getBegDate(), commonStyle);
                createCell(row, 17, productTest.getEndDate(), commonStyle);
                createCell(row, 18, productTest.getFinishedBug(), commonStyle);
                createCell(row, 19, productTest.getUnfinishedBug(), commonStyle);
                createCell(row, 20, productTest.getFinallyName(), commonStyle);
                createCell(row, 21, productTest.getFinallyPosition(), commonStyle);
                createCell(row, 22, productTest.getSummany(), commonStyle);
                createCell(row, 23, productTest.getNote(), commonStyle);
                createCell(row, 24, "", null);
            }
        }
        //调整列宽（适应中文）
        for(int i=0;i<24;i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,(int)(sheet.getColumnWidth(i)*1.25));
        }
        sheet.setColumnWidth(10,25*256);        //测试依据
        sheet.setColumnWidth(11,25*256);        //测试类别
        sheet.setColumnWidth(12,25*256);        //测试项描述
        sheet.setColumnWidth(18,25*256);        //已解决BUG编号
        sheet.setColumnWidth(19,25*256);        //未解决BUG编号
        sheet.setColumnWidth(22,25*256);        //最后结论
        sheet.setColumnWidth(23,25*256);        //备注

        //以流的形式将文件提交至前台
        resp.setContentType("application/x-download");
        resp.addHeader("Content-Disposition", "attachment;filename=" + "workbook.xls");
        resp.setCharacterEncoding("utf-8");
        OutputStream fileOut = resp.getOutputStream();
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }
}


