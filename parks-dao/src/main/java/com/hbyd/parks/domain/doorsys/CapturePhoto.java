package com.hbyd.parks.domain.doorsys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;

/**
 * 报警照片
 */
@Entity
@Table(name = "door_capture_photo", schema = "dbo", catalog = "parks")
@Access(AccessType.PROPERTY)
public class CapturePhoto extends BaseEntity{
    private String firstPath;
    private String secondPath;
    private String bigPhotoName;
    private String smallPhotoName;

    @Basic
    @Column(name = "firstPath")
    public String getFirstPath() {
        return firstPath;
    }

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    @Basic
    @Column(name = "secondPath")
    public String getSecondPath() {
        return secondPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    @Basic
    @Column(name = "bigPhotoName")
    public String getBigPhotoName() {
        return bigPhotoName;
    }

    public void setBigPhotoName(String bigPhotoName) {
        this.bigPhotoName = bigPhotoName;
    }

    @Basic
    @Column(name = "smallPhotoName")
    public String getSmallPhotoName() {
        return smallPhotoName;
    }

    public void setSmallPhotoName(String smallPhotoName) {
        this.smallPhotoName = smallPhotoName;
    }
}
