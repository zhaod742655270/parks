package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 * Created by Zhao_d on 2017/7/10.
 * 验收管理附加文件
 */
@Entity
@Table(name = "oa_procuremen_acceptance_file")
@Audited
public class AttachedFile extends RecoverableEntity {

    private String name;        //文件名称

    private String uploadDate;      //上传日期

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceptanceFK")
    @NotAudited
    private Acceptance acceptance;      //所属验收记录

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

    public Acceptance getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(Acceptance acceptance) {
        this.acceptance = acceptance;
    }
}
