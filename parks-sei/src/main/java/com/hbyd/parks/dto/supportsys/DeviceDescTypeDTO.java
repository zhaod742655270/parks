package com.hbyd.parks.dto.supportsys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 设备类型
 */
public class DeviceDescTypeDTO extends BaseDTO {
    private String oneType;
    private String typeDesc;
    private String isAlarm;
    private String resAppFk;
    private String discriminator;

    public String getOneType() {
        return oneType;
    }

    public void setOneType(String oneType) {
        this.oneType = oneType;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(String isAlarm) {
        this.isAlarm = isAlarm;
    }

    public String getResAppFk() {
        return resAppFk;
    }

    public void setResAppFk(String resAppFk) {
        this.resAppFk = resAppFk;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }
}
