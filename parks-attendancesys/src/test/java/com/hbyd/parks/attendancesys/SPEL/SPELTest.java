package com.hbyd.parks.attendancesys.SPEL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.Properties;

/**
 * test SPEL:
 * 1. systemProperties and systemEnvironment are both pre-defined implicit objects
 *  1.1 systemProperties
 *      System.getProperties();
 *  1.2 systemEnvironment
 *      System.getEnv();
 *
 * 2.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/spring/applicationContext_SPEL.xml")
@ContextConfiguration(classes = {AppConfig.class})
public class SPELTest {

    @Value("#{systemProperties['java.vendor']}")
    private String javaVendor;

    @Value("#{systemEnvironment['JAVA_HOME']}")
    private String javaHome;

    @Value("#{stu.id}")
    private String stuID;

    @Value("#{stu.name}")
    private String stuName;

    @Value("#{stu.team.name}")
    private String teamName;

//    从 PropertyPlaceholderConfigurer / PropertySourcesPlaceholderConfigurer 中读取配置
//    <context:property-placeholder location="properties/spel.properties"/>
    @Value("${sampleKey}")
    private String sampleValue;

//    从 Properties 中读取配置，两种情况：
//    1. 直接配置的 Properties 实例
//    <util:properties id="spelProps" location="properties/spel.properties"/>
//    2. 工厂生产的 Properties 实例
//    <bean id="spelProps" class="org.springframework.beans.factory.config.PropertiesFactoryBean">
//        <property name="location" value="properties/spel.properties"/>
//    </bean>
//    @Value("#{spelProps.sampleKey}")
//    private String sampleValue;

    @Test
    public void testSystem(){
//      获取系统属性
        Properties properties = System.getProperties();
        System.out.println(properties.get("java.vendor"));//Oracle Corporation
        System.out.println(System.getProperty("java.vendor"));//Oracle Corporation

//      获取环境变量
        Map<String, String> envs = System.getenv();
        System.out.println(envs.get("JAVA_HOME"));//D:\Program Files Dev Port\Java\jdk1.7.0_60
        System.out.println(System.getenv("JAVA_HOME"));//D:\Program Files Dev Port\Java\jdk1.7.0_60
    }

    @Test
    public void testSystemWithSPEL(){
        System.out.println(javaVendor);//Oracle Corporation
        System.out.println(javaHome);//D:\Program Files Dev Port\Java\jdk1.7.0_60
    }

    @Test
    public void testBeanWithSPEL(){
        System.out.println(stuID);//10
        System.out.println(stuName);//"jack"
        System.out.println(teamName);//"mathTeam"
        System.out.println(sampleValue);//this is sampleValue!
    }
}
