package com.hbyd.parks.common.base;

import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    protected static Logger logger = Logger.getLogger(BaseEntity.class);

//1.1 使用自定义的 UUID
//    @Id
//    protected String id;

//1.2 Hibernate 提供的 UUID Strategy
//    JPA 的主键生成策略没有提供 UUID, 但 Hibernate 提供了：

//    [WARN]: Using org.hibernate.id.UUIDHexGenerator which does not generate IETF RFC 4122 compliant UUID values; consider using org.hibernate.id.UUIDGenerator instead
//    @Id @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")//Hibernate 提供的注解
//    private String id;

//    使用 uuid2 生成策略，这样就不再提示 WARN
//    参考 http://docs.jboss.org/hibernate/core/3.6/reference/en-US/html/mapping.html#d0e5294
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;


//2. 添加乐观锁控制
//    The application must not alter the version number set up by Hibernate in any way.
//    version 不应该有 Setter 字段，不应被应用修改，而由 Hibernate 管理
//    @Version
//    @Column(name = "version", nullable = false)//如果数据迁移时将此字段填充为 NULL, 就会报 NullPointerException, LongType.next()...
//    protected Long version;

//    这样无需引入 Hibernate 注解，就可以实现 UUID 主键生成策略
//    public BaseEntity() {
//        this.id = UUID.randomUUID().toString();
//    }

//Getter And Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
