package com.hbyd.parks.attendancesys.dozer;

import com.hbyd.parks.domain.attendancesys.Holiday;
import com.hbyd.parks.dto.attendancesys.HolidayDTO;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 测试 Dozer 的全局配置是否有效，测试项包括：
 * 1. map-null=fasle;
 * 2. date-format="yyyy-MM-dd HH-mm-ss"
 *
 * 注意：Dozer 如果开启了 EL 功能，需要提供 EL 接口和实现
 * tomcat/libs 下提供了：el-api.jar 和 jasper-el.jar
 * 测试时，需要额外提供接口和实现，从 dozer.5.5.1.pom 中提取即可，将 provided 修改为 test, 如下：
 * {@code
 * <dependency>
 *      <groupId>javax.el</groupId>
 *      <artifactId>el-api</artifactId>
 *      <version>2.2</version>
 *      <scope>provided</scope>
 * </dependency>
 * <dependency>
 *      <groupId>org.glassfish.web</groupId>
 *      <artifactId>el-impl</artifactId>
 *      <version>2.2</version>
 *      <scope>provided</scope>
 * </dependency>
 * }
 * </pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/applicationContext_Dozer.xml"})
public class GlobalConfigTest {
    /**
     * Spring 注入的 dozerMapper
     */
    @Resource
    private DozerBeanMapper dozerMapper;

    /**
     * 主动创建的 dozerMapper
     */
    private DozerBeanMapper mapper;

    //    测试数据
    private Holiday h;
    private HolidayDTO hd;
    private HolidayDTO hd2;

    /**
     * 准备测试数据
     */
    @Before
    public void init() {
        h = new Holiday();
        h.setName(null);
        h.setEndDate(new java.sql.Date(new Date().getTime()));

        hd = new HolidayDTO();
        hd.setName("hd_name");
        hd.setEndDate(null);

//        ---------------------
        List mappingFiles = new ArrayList();
        mappingFiles.add("com/hbyd/parks/dto/attendancesys.mapping.xml");

        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(mappingFiles);
    }


    /**
     * 正向测试全局配置
     */
    @Test
    public void testForward_spring() {
        dozerMapper.map(h, hd);

        System.out.println(hd.getName());
        System.out.println(hd.getEndDate());

        Assert.assertEquals("hd_name", hd.getName());//属性值未被覆盖
        Assert.assertNotNull(hd.getEndDate());//属性值被覆盖
    }

    /**
     * map-null: 如果源属性为 null, 就不调用目标属性的 setter 方法，和目标属性值是否为 NULL 没关系
     * <p/>
     * map(srcObj, destObj);
     * map(srcObj, destClass);
     * map-null 不对 srcObj 的 NULL 属性进行映射
     * <p/>
     * <pre>{@code
     * <mapping>
     *      <class-a>org.dozer.vo.AnotherTestObject</class-a>
     *      <!--class-b 作为映射目标，不映射来源为 empty string 的源属性-->
     *      <class-b map-empty-string="false">org.dozer.vo.AnotherTestObjectPrime</class-b>
     *      <field>
     *          <a>field4</a>
     *          <b>to.one</b>
     *      </field>
     * </mapping>
     * }</pre>
     */
    @Test
    public void testForward_manual() {
        mapper.map(h, hd);
        System.out.println(hd.getName());//hd_name
        System.out.println(hd.getEndDate());//2014-08-12

        Assert.assertEquals("hd_name", hd.getName());//属性值未被覆盖
        Assert.assertNotNull(hd.getEndDate());//属性值被覆盖

//        -----------------------------------

        hd2 = mapper.map(h, HolidayDTO.class);//1. 先实例化 HolidayDTO (name=null, endDate=null) 2. 映射属性
        System.out.println(hd2.getName());//null(源属性为 NULL, 未映射)
        System.out.println(hd2.getEndDate());//2014-08-12(源属性有值，已映射)

        Assert.assertNull(hd2.getName());
        Assert.assertNotNull(hd2.getEndDate());
    }

    @Test
    public void testStringObjInitialVal() {
//        字符串属性默认为 NULL
        System.out.println(new HolidayDTO().getEndDate());//null
    }
}
