package com.hbyd.parks.attendancesys.SPEL;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
//@PropertySource("/properties/spel.properties")
public class AppConfig {
    @Bean(name = "stu")
    public Student getStudent(){
        Student student = new Student();
        student.setId(10);
        student.setName("jack");
        student.setTeam(getTeam());

        return student;
    }

    @Bean(name = "mathTeam")
    public Team getTeam(){
        Team team = new Team();
        team.setName("mathTeam");

        return team;
    }

    /**解析 ${...} 占位符时，从 @PropertySource 指定的配置中查找
     *
     * 参照 PropertySourcesPlaceholderConfigurer 的 javadoc:
     *
     * As of Spring 3.1, PropertySourcesPlaceholderConfigurer should be used preferentially
     * over PropertyPlaceholderConfigurer; it is more flexible through taking advantage of
     * the Environment and PropertySource mechanisms also made available in Spring 3.1.
     *
     * Spring 3.1 之前，需要使用 PropertyPlaceholderConfigurer 搭配 ClassPathResource() 使用
     * Spring 3.1 之后，推荐使用 PropertySourcesPlaceholderConfigurer 搭配 @PropertySource 使用
     */
//    @Bean(name = "spelProps")
//    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

    @Bean(name = "spelProps")
    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer(){
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource("properties/spel.properties"));
        return propertyPlaceholderConfigurer;
    }
}
