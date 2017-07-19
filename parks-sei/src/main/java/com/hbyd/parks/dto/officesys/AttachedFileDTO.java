package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Zhao_d on 2017/7/10.
 */
public class AttachedFileDTO extends BaseDTO {
    private String name;

    private String uploadDate;

    private String acceptanceFK;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAcceptanceFK() {
        return acceptanceFK;
    }

    public void setAcceptanceFK(String acceptanceFK) {
        this.acceptanceFK = acceptanceFK;
    }
}
