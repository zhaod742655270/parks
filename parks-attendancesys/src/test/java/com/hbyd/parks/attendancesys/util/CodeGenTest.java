package com.hbyd.parks.attendancesys.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CodeGenTest {
    @Test
    public void genSupportsys() {
//    1. 模块名称
//        String moduleName = "supportsys";
//        String moduleName = "attendancesys";
//        String moduleName = "managesys";
        String moduleName = "supportsys";
//        String moduleName = "meetsys";

//    2. 模板目录
        String tempPath = "D:\\GitRepo\\parks\\parks-attendancesys\\src\\main\\java\\com\\hbyd\\parks\\attendancesys\\util\\";

//    3. 要处理的实体名称列表
        List<String> entityNameList = Arrays.asList(
//            "Device",
//            "DeviceCamera",
//            "DeviceController",
//            "DeviceDoor",
//            "DeviceElecfence",
//            "DeviceExtmod",
//            "DeviceFirehost",
//            "DeviceFireprobe",
//            "DeviceIo",
//            "DeviceReader",
//            "DeviceTerminal",

//            "DeviceDescComm",
//            "DeviceDescFiregroup",
//            "DeviceDescType"

//            "MeetRoom"

            "DeviceDescRelation"
        );

        CodeGen gen = new CodeGen(moduleName, tempPath, entityNameList);

//        gen.genDaoInter();
//        gen.genDaoImpl();
//        gen.genWsInter();
//        gen.genWsImpl();
//        gen.genWsImplTest();

        gen.genAll();

        String endConfig = gen.getSpringEndpointConfig();
        String cliConfig = gen.getSpringClientConfig();

        System.out.println("客户端配置: ");
        System.out.println(cliConfig);
        System.out.println("=====================================");
        System.out.println("服务端配置: ");
        System.out.println(endConfig);
    }
}