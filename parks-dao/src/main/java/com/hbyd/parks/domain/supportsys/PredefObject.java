package com.hbyd.parks.domain.supportsys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;

/**
 * 预定义信息
 */
@Entity
@Table(name = "base_predef_object", schema = "dbo", catalog = "parks")
public class PredefObject extends BaseEntity {
    @Basic
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
