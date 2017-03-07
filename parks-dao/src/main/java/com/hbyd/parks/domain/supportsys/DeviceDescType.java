package com.hbyd.parks.domain.supportsys;

import com.hbyd.parks.common.base.RecoverableEntity;
import com.hbyd.parks.domain.managesys.ResApp;

import javax.persistence.*;

/**
 * 设备类型
 */
@Entity
@Table(name = "base_device_desc_type", schema = "dbo", catalog = "parks")
@Access(AccessType.PROPERTY)
public class DeviceDescType extends RecoverableEntity {
    private String oneType;
    private String typeDesc;
    private String isAlarm;

    private ResApp resApp;

    @ManyToOne(fetch = FetchType.LAZY)//子系统太庞大，最好懒加载
    @JoinColumn(name = "resAppFk", referencedColumnName = "id")
    public ResApp getResApp() {
        return resApp;
    }

    public void setResApp(ResApp resApp) {
        this.resApp = resApp;
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
    @Column(name = "typeDesc")
    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    @Basic
    @Column(name = "isAlarm")
    public String getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(String isAlarm) {
        this.isAlarm = isAlarm;
    }
}
