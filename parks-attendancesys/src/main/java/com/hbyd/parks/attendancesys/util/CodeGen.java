package com.hbyd.parks.attendancesys.util;

import com.sun.org.apache.xml.internal.serialize.LineSeparator;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * 根据当前项目约定实现的代码生成器
 *
 * @author ren_xt
 */
public class CodeGen {
    //需要自定义的三个实例变量：
    // 1. 模块名称
    private String moduleName;
    // 2. 模板目录
    public String tempPath;
    // 3. 要处理的实体名称列表
    private List<String> entityNameList;

    //  源码输出目录：生产用
    private File daoInterFolder;
    private File daoImplFolder;
    private File wsInterFolder;
    private File wsImplFolder;
    private File wsImplTestFolder;

    //  源码后缀
    private static final String daoInterSuffix = "Dao.java";
    private static final String daoImplSuffix = "DaoImpl.java";
    private static final String wsInterSuffix = "WS.java";
    private static final String wsImplSuffix = "WSImpl.java";
    private static final String wsImplTestSuffix = "WSImplTest.java";

    //  包路径
    private String daoInterPath;
    private String daoImplPath;
    private String wsInterPath;
    private String wsImplPath;
    private String dtoPath;
    private String entityPath;

    //  模板文件路径
    private File daoInterTempFile;
    private File daoImplTempFile;
    private File wsInterTempFile;
    private File wsImplTempFile;
    private File wsImplTestTempFile;

    /**
     * 只提供这个唯一的构造方法，确保代码生成时必备的参数已设定
     *
     * @param moduleName     模块名称
     * @param tempPath       模板目录
     * @param entityNameList 要处理的实体名称列表
     */
    public CodeGen(String moduleName, String tempPath, List<String> entityNameList) {
        this.moduleName = moduleName;
        this.tempPath = tempPath;
        this.entityNameList = entityNameList;

        setDerivedFields(moduleName, tempPath);
    }

    /**
     * 设定衍生字段，所谓衍生：就是依赖于 muduleName 和 tempPath 的字段
     *
     * @param moduleName 模块名称
     * @param tempPath   模板目录
     */
    private void setDerivedFields(String moduleName, String tempPath) {
//      源码输出目录：生产用
        daoInterFolder = new File("D:\\GitRepo\\parks\\parks-dao\\src\\main\\java\\com\\hbyd\\parks\\dao\\" + moduleName);
        daoImplFolder = new File("D:\\GitRepo\\parks\\parks-dao\\src\\main\\java\\com\\hbyd\\parks\\daoImpl\\hibernate\\" + moduleName);
        wsInterFolder = new File("D:\\GitRepo\\parks\\parks-sei\\src\\main\\java\\com\\hbyd\\parks\\ws\\" + moduleName);
        wsImplFolder = new File("D:\\GitRepo\\parks\\parks-" + moduleName + "\\src\\main\\java\\com\\hbyd\\parks\\" + moduleName + "\\wsImpl");
        wsImplTestFolder = new File("D:\\GitRepo\\parks\\parks-" + moduleName + "\\src\\test\\java\\com\\hbyd\\parks\\" + moduleName + "\\wsImpl");

//      源码输出目录：测试用
//        daoInterFolder = new File("D:\\test");
//        daoImplFolder = new File("D:\\test");
//        wsInterFolder = new File("D:\\test");
//        wsImplFolder = new File("D:\\test");
//        wsImplTestFolder = new File("D:\\test");

//      包路径
        daoInterPath = "com.hbyd.parks.dao." + moduleName;
        daoImplPath = "com.hbyd.parks.daoImpl.hibernate." + moduleName;
        wsInterPath = "com.hbyd.parks.ws." + moduleName;
        wsImplPath = "com.hbyd.parks." + moduleName + ".wsImpl";
        dtoPath = "com.hbyd.parks.dto." + moduleName;
        entityPath = "com.hbyd.parks.domain." + moduleName;

//      模板文件路径
        daoInterTempFile = new File(tempPath, "DaoInterTemp.txt");
        daoImplTempFile = new File(tempPath, "DaoImplTemp.txt");
        wsInterTempFile = new File(tempPath, "WsInterTemp.txt");
        wsImplTempFile = new File(tempPath, "WsImplTemp.txt");
//        wsImplTempFile = new File(tempPath, "WsImplTempWithCode.txt");
        wsImplTestTempFile = new File(tempPath, "WsImplTestTemp.txt");
    }

    /**
     * 生成 DAO 接口，替换变量：
     * daoInterPath
     * entityPath
     * entityName
     */
    public void genDaoInter() {
        HashMap<String, String> map = new HashMap<>();//<variableName, variableValue>
        map.put("\\$\\{daoInterPath\\}", daoInterPath);
        map.put("\\$\\{entityPath\\}", entityPath);

        replaceVars(map, entityNameList, daoInterTempFile, daoInterFolder, daoInterSuffix);
    }

    /**
     * 生成 DAO 实现，替换变量：
     * daoInterPath
     * daoImplPath
     * entityPath
     * entityName
     */
    public void genDaoImpl() {
        HashMap<String, String> map = new HashMap<>();//<variableName, variableValue>
        map.put("\\$\\{daoInterPath\\}", daoInterPath);
        map.put("\\$\\{daoImplPath\\}", daoImplPath);
        map.put("\\$\\{entityPath\\}", entityPath);

        replaceVars(map, entityNameList, daoImplTempFile, daoImplFolder, daoImplSuffix);
    }

    /**
     * 生成 WS 接口，替换变量：
     * wsInterPath
     * dtoPath
     * entityName
     */
    public void genWsInter() {
        HashMap<String, String> map = new HashMap<>();//<variableName, variableValue>
        map.put("\\$\\{wsInterPath\\}", wsInterPath);
        map.put("\\$\\{dtoPath\\}", dtoPath);

        replaceVars(map, entityNameList, wsInterTempFile, wsInterFolder, wsInterSuffix);
    }

    /**
     * 生成 WS 实现，替换变量：
     * wsImplPath
     * wsInterPath
     * daoInterPath
     * entityPath
     * dtoPath
     * entityName
     * entityNameLowerCase
     */
    public void genWsImpl() {
        HashMap<String, String> map = new HashMap<>();//<variableName, variableValue>
        map.put("\\$\\{wsImplPath\\}", wsImplPath);
        map.put("\\$\\{wsInterPath\\}", wsInterPath);
        map.put("\\$\\{daoInterPath\\}", daoInterPath);
        map.put("\\$\\{entityPath\\}", entityPath);
        map.put("\\$\\{dtoPath\\}", dtoPath);

        replaceVars(map, entityNameList, wsImplTempFile, wsImplFolder, wsImplSuffix);
    }

    /**
     * 生成 WSImpl 测试类，替换变量：
     * wsInterPath
     * wsImplPath
     * entityName
     * dtoPath
     * entityNameLowerCase
     */
    public void genWsImplTest() {
        HashMap<String, String> map = new HashMap<>();//<variableName, variableValue>
        map.put("\\$\\{wsInterPath\\}", wsInterPath);
        map.put("\\$\\{wsImplPath\\}", wsImplPath);
        map.put("\\$\\{dtoPath\\}", dtoPath);

        replaceVars(map, entityNameList, wsImplTestTempFile, wsImplTestFolder, wsImplTestSuffix);
    }

    /**
     * WS 客户端的 Spring 配置
     *
     * @return Spring 配置字符串
     */
    public String getSpringClientConfig() {
        StringBuilder sb = new StringBuilder();
        for (String eName : entityNameList) {
            String eNameWithFirstLowerCase = firstLetterLowerCase(eName);
            sb.append("<jaxws:client bus=\"bus_client\" id=\"" + eNameWithFirstLowerCase + "WS\" serviceClass=\"com.hbyd.parks.ws." + moduleName + "." + eName + "WS\" address=\"${" + moduleName + ".ws}/" + eNameWithFirstLowerCase + "WS\" />");
            sb.append(LineSeparator.Windows);
        }
        return sb.toString();
    }

    /**
     * WS Endpoint 的 Spring 配置
     *
     * @return Spring 的配置字符串
     */
    public String getSpringEndpointConfig() {
        StringBuilder sb = new StringBuilder();
        for (String eName : entityNameList) {
            String eNameWithFirstLowerCase = firstLetterLowerCase(eName);
            sb.append("    <bean id=\""+eNameWithFirstLowerCase+"WSImpl\" class=\"com.hbyd.parks."+moduleName+".wsImpl."+eName+"WSImpl\"/>\n" +
                    "    <jaxws:server bus=\"cxf\" id=\""+eNameWithFirstLowerCase+"WS\" address=\"/"+eNameWithFirstLowerCase+"WS\"\n" +
                    "              serviceClass=\"com.hbyd.parks.ws."+moduleName+"."+eName+"WS\">\n" +
                    "        <jaxws:serviceBean>\n" +
                    "            <ref bean=\""+eNameWithFirstLowerCase+"WSImpl\"/>\n" +
                    "        </jaxws:serviceBean>\n" +
                    "    </jaxws:server>\n");

            sb.append(LineSeparator.Windows);
        }
        return sb.toString();
    }

    /**
     * 一次性生成: DaoInter, DaoImpl, WS, WSImpl, WSImplTest
     */
    public void genAll() {
        genDaoInter();
        genDaoImpl();
        genWsInter();
        genWsImpl();
        genWsImplTest();
    }

    /**
     * 替换模板中的变量
     *
     * @param map            变量映射 <variableName, variableValue>
     * @param entityNameList 实体名称列表
     * @param tempFile       模板文件
     * @param outFolder      输出目录
     * @param suffix         生成的源码后缀名称，如：Dao.java, DaoImpl.java, WS.java, WSImpl.java
     */
    private void replaceVars(HashMap<String, String> map, List<String> entityNameList, File tempFile, File outFolder, String suffix) {
        if (tempFile.isDirectory()) {
            throw new RuntimeException("tempFile 不是文件！");
        }

        File tempFolder = tempFile.getParentFile();
        if (!tempFolder.exists()) {
            throw new RuntimeException("模板目录不存在：" + tempFolder.getPath());
        }

        if (!outFolder.exists()) {
            throw new RuntimeException("输出目录不存在：" + outFolder.getPath());
        }

//      替换除 ${entityName} 和 ${entityNameLowerCase} 之外的变量
        StringBuilder sb = new StringBuilder();
        String s = null;
        try (
                BufferedReader br = new BufferedReader(new FileReader(tempFile));
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(LineSeparator.Windows);
            }

//          变量替换
            s = sb.toString();
            for (String variableName : map.keySet()) {
                s = s.replaceAll(variableName, map.get(variableName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//      替换 ${entityName} 和 ${entityNameLowerCase}
        for (String eName : entityNameList) {
//          实体名称首字母小写，如：DeviceCamera -> deviceCamera
            String eNameWithFirstLowerCase = firstLetterLowerCase(eName);
            try (
                    BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outFolder, eName + suffix)))
            ) {
                String str = new String(s);//确保每次迭代使用的 s 都是一样的
                str = str.replaceAll("\\$\\{entityName\\}", eName);//例如 Device
                str = str.replaceAll("\\$\\{entityNameLowerCase\\}", eNameWithFirstLowerCase);

                bw.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将字符串首字母转为小写
     *
     * @param str 源字符串
     * @return 转换后的字符串
     */
    private String firstLetterLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
