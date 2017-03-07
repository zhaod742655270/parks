package com.hbyd.parks.attendancesys.util;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by allbutone on 2014/10/31.
 */
public class FileTest {

    private String fileName = System.getProperty("os.name") + ".txt";;
    private String path = System.getProperty("user.home");

    @Test
    public void testCreateFile(){
        File file = new File(path, fileName);

//      设置文件权限：只读
        file.setReadable(true);
        file.setExecutable(false);
        file.setWritable(false);

        if(file.exists()){
            file.delete();
        }

        try {
            if(file.createNewFile()){
                System.out.println("file created successfully");
            }else{
                System.out.println("file with the same name already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 JDK 7 新特性：try with resources
     */
    @Test
    public void testTryWithResources(){
//      using JDK 7
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path, fileName)))){
            bw.write("hello world");
//            bw.close();//bw 自动关闭
        } catch (IOException e) {
            e.printStackTrace();
        }


//      before JDK 7
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(new File(path, fileName), true));
            bw.write(" hello China");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
