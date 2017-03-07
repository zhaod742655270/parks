package com.hbyd.parks.daoImpl.hibernate;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 测试 DAO 实现时使用的 Spring 配置
 */
public @Configuration
@ComponentScan(basePackages = {
        "com.hbyd.parks.daoImpl",
        "com.hbyd.parks.dao",
//        "com.hbyd.parks.domain",//已在 applicationContext_Hibernate.xml 内指定
})
@ImportResource("/spring/applicationContext_Hibernate.xml")//使用通配符：不和模块名称耦合
class AppConfig{

}