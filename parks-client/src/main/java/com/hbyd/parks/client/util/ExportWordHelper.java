package com.hbyd.parks.client.util;


import com.google.common.base.Strings;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoda on 2016/12/6.
 */
public class ExportWordHelper {
    /**
     * 填充Word文档(通过替换模板文件中的标记文本)
     *
     * @param tempPath 模板文件地址
     * @param fileName 生成文件的名称(包括扩展名)
     * @param map      替换标识与替换文本对
     */
    public static void writeWord(String tempPath, String fileName, Map<String, String> map) throws Exception {
            if(tempPath.endsWith("docx")){                      //word2007及以后
                writeWordDocx(tempPath,fileName,map);
            }else{                                              //word2003
                writeWordDoc(tempPath,fileName,map);
            }
    }

    private static void writeWordDocx(String tempPath, String fileName, Map<String, String> map) throws Exception{
        //读取模板
        XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(tempPath));

        // 替换标头
        List<XWPFParagraph> paragraphList = docx.getParagraphs();
        processParagraphs(paragraphList, map);

        //替换表格
        Iterator<XWPFTable> it = docx.getTablesIterator();
        while (it.hasNext()) {
            XWPFTable table = it.next();                //找到表格
            List<XWPFTableRow> rows = table.getRows();      //找到行
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();        //找到单元格
                for (XWPFTableCell cell : cells) {
                    List<XWPFParagraph> paragraphListTable = cell.getParagraphs();      //找到单元格内的段落
                    processParagraphs(paragraphListTable,map);          //替换段落内容
                }
            }
        }

        //输出文件流
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

        OutputStream fileOut = response.getOutputStream();
        docx.write(fileOut);
        fileOut.close();
    }

    private static void writeWordDoc(String tempPath, String fileName, Map<String, String> map) throws Exception{
        //读取模板文件
        FileInputStream temp = new FileInputStream(new File(tempPath));
        HWPFDocument doc = new HWPFDocument(temp);

        //替换内容
        Range bodyRange = doc.getRange();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            bodyRange.replaceText("%" + entry.getKey() + "%",
                    entry.getValue());
        }

        //输出文件流
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

        OutputStream fileOut = response.getOutputStream();
        doc.write(fileOut);
        fileOut.close();
    }

    private static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, String> map) {
        for (XWPFParagraph paragraph : paragraphList) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String text = run.getText(0);
                Boolean isReplace = false;          //表示是否有替换
                if(!Strings.isNullOrEmpty(text)) {
                    if(text.equals("$")){
                        text = text.replace("$","");        //多余的$去掉
                        isReplace = true;
                    }else{
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (text.indexOf(entry.getKey()) != -1) {
                                text = text.replace("$","");
                                if(!Strings.isNullOrEmpty(entry.getValue())) {
                                    String valueText = entry.getValue().replace("\r\n","\n");
                                    text = text.replace(entry.getKey(), valueText);
                                }else{
                                    text = text.replace(entry.getKey(),"");
                                }
                                isReplace = true;
                            }
                        }
                    }
                }
                if(isReplace) {
                    run.setText(text,0);
                }
            }
        }
    }
}

