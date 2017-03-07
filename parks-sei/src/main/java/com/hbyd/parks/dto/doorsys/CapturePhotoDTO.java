package com.hbyd.parks.dto.doorsys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 报警照片
 */
public class CapturePhotoDTO extends BaseDTO {
    private String carTime;
    private String carId;
    private String insertTime;
    private String imageName;
    private String plate;
    private String ip;
    private String emptyFlag;
    private String bz;
    private String flag;

    public String getCarTime() {
        return carTime;
    }

    public void setCarTime(String carTime) {
        this.carTime = carTime;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEmptyFlag() {
        return emptyFlag;
    }

    public void setEmptyFlag(String emptyFlag) {
        this.emptyFlag = emptyFlag;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
