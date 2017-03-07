package com.hbyd.parks.client.util;

import com.hbyd.parks.ws.officesys.AcceptanceWS;
import com.hbyd.parks.ws.officesys.QuotationProjectWS;
import com.hbyd.parks.ws.officesys.QuotationWS;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/4/21
 */
public class ImportExcelHelper {
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    @Resource
    private AcceptanceWS acceptanceWS;

    @Resource
    private QuotationWS quotationWS;

    @Resource
    private QuotationProjectWS projectWS;

    /**
     * 读取Excel表格表头的内容
     * @param is
     * @return String 表头内容的数组
     *
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        //标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i=0; i<colNum; i++) {
            title[i] = getTitleValue(row.getCell((short) i));
        }
        return title;
    }


    /**
     * 获取单元格数据内容为字符串类型的数据
     * @param cell Excel单元格
     * @return String 单元格数据内容，若为字符串的要加单引号
     */
    public String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = "'" + cell.getStringCellValue() + "'";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
            strCell = "''";
                break;
            default:strCell = "''";
            break;
    }
    if (strCell.equals("''") || strCell == null) {
        return "";
    }
    if (cell == null) {
        return "";
    }
    return strCell;
}

    public String getTitleValue(HSSFCell cell) {
        String strCell =  cell.getStringCellValue();
        return strCell;
    }

}