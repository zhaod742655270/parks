package com.hbyd.parks.domain.supportsys;

import com.hbyd.parks.common.base.RecoverableEntity;

import javax.persistence.*;

/**
 * 设备
 */
@Entity
@Table(name = "base_device", schema = "dbo", catalog = "parks")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
//@DiscriminatorValue(DiscValue.设备)
@SecondaryTable(name = "base_device_desc_relation", schema = "dbo", catalog = "parks")
@Access(AccessType.PROPERTY)
public class Device extends RecoverableEntity {
    private String oneType;
    private String deviceId;
    private String name;
    private String descInfo;
    private String position;
    private String maker;
    private String state;

    /*
    * Hibernate 使用更复杂的实现，避免了区分列的使用，@DiscriminatorColumn 注解的 javadoc 也指明区分列并非 "required"
    * 即使使用 JPA 注解 @DiscriminatorColumn 和 @DiscriminatorValue 指定区分列，Hibernate 也不会使用，更不会为区分列
    * 填充对应的 discriminator value.
    *
    * Hibernate Manual:
    * 10.1.3. Table per subclass: using a discriminator
    *   Hibernate's implementation of table per subclass does not require a discriminator column.
    * Other object/relational mappers use a different implementation of table per subclass that
    * requires a type discriminator column in the superclass table. The approach taken by
    * Hibernate is much more difficult to implement, but arguably more correct from a relational
    * point of view.
    * */

//    /**
//     * 区分列：该记录是哪种具体类别
//     */
//    private String discriminator;

    /**
     * 上级设备
     */
    private Device parent;

    @ManyToOne
    @JoinColumn(name = "parentFk", referencedColumnName = "id")
    public Device getParent() {
        return parent;
    }

    public void setParent(Device parent) {
        this.parent = parent;
    }

    private Region region;

    @ManyToOne
    @JoinColumn(name = "regionFk", referencedColumnName = "id")
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    private DeviceDescType deviceType;

    @ManyToOne
    @JoinColumn(name = "devTypeFk", referencedColumnName = "id")
    public DeviceDescType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceDescType deviceType) {
        this.deviceType = deviceType;
    }

    @Basic
    @Column(name = "oneType")
    public String getOneType() {
        return oneType;
    }

    public void setOneType(String oneType) {
        this.oneType = oneType;
    }

    @Basic
    @Column(name = "deviceId")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "descInfo")
    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "maker")
    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    @Basic//普通属性，默认使用 @Basic 标注
//    @Column(name = "discriminator")
//    public String getDiscriminator() {
//        return discriminator;
//    }
//
//    public void setDiscriminator(String discriminator) {
//        this.discriminator = discriminator;
//    }


//    外键信息填充到一对一的关系表中,不做实体关联
    private String roomFK;

    @Basic
    @Column(name = "roomFK", table = "base_device_desc_relation")
    public String getRoomFK() {
        return roomFK;
    }

    public void setRoomFK(String roomFK) {
        this.roomFK = roomFK;
    }

    private String deptFK;

    @Basic
    @Column(name = "deptFK", table = "base_device_desc_relation")
    public String getDeptFK() {
        return deptFK;
    }

    public void setDeptFK(String deptFK) {
        this.deptFK = deptFK;
    }
}
