package com.hbyd.parks.client.util;

import com.google.gson.Gson;
import com.hbyd.parks.common.model.AjaxMessage;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * JSON 辅助类
 * @author len, ren_xt
 */
public class JsonHelper {
    private static Gson gson = new Gson();

    /**将对象序列化后输出到标准输出流
     * @param obj 目标对象
     */
    public static void writeToStd(Object obj){
        System.out.println(gson.toJson(obj));
    }

    /**输出字符串内容
     * @param content 字符串
     */
    public static void writeJson(String content) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Content-type","text/html;charset=UTF-8");

        try(PrintWriter out = response.getWriter()){
            out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**将对象序列化后输出
     * @param obj 目标对象
     */
    public static void writeJson(Serializable obj){
        writeJson(gson.toJson(obj));
    }

    /**
     * @param msg {@link com.hbyd.parks.common.model.AjaxMessage}
     */
    public static void writeMsg(AjaxMessage msg){
        writeJson(gson.toJson(msg));
    }
}
