package com.hbyd.parks.client.util;

import com.hbyd.parks.dto.supportsys.EmployeeDTO;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Len on 2015/1/6.
 */
public class PhotoHelper {
    public static EmployeeDTO relatedPhoto(String uploadFileName, EmployeeDTO emp) {

        if (uploadFileName != null) {
            //得到后缀名
            String[] names = uploadFileName.split("[.]");
            String extensions = names[names.length - 1];
                //生成uid
                UUID uuid = UUID.randomUUID();
                //拼接照片名字
                String photoName = uuid.toString() + "." + extensions;
                emp.setPhotoName(photoName);
        }


        return emp;
    }

    private static String targetDirectory = ServletActionContext.getServletContext().getRealPath("/upload");

    public static void delPhoto(String photoName) {
        File file = new File(targetDirectory+"\\"+photoName);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    public static void savePhoto(File upload, String photoName) throws IOException {

        if (upload != null) {
          //  String targetDirectory = ServletActionContext.getServletContext().getRealPath("/upload");
            File target = new File(targetDirectory, photoName);
            FileUtils.copyFile(upload, target);
        }

    }
}
