package com.hbyd.parks.domain.supportsys;

import com.hbyd.parks.common.base.RecoverableEntity;

import javax.persistence.*;

/**
 * 对象类型，为混合表，包含：职务类型
 */
@Entity
@Table(name = "base_object_type", schema = "dbo", catalog = "parks")
public class ObjectType extends RecoverableEntity{

    @Basic
    @Column(name = "typeId")
    private Integer typeId;//旧库使用，新库留空即可

    @Basic
    @Column(name = "typeCode")
    private String typeCode;//用户指定

    @Basic
    @Column(name = "typeName")
    private String typeName;//用户指定

    @ManyToOne
    @JoinColumn(name = "objectFK", referencedColumnName = "id")
    private PredefObject preObj;//用户指定

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public PredefObject getPreObj() {
        return preObj;
    }

    public void setPreObj(PredefObject object) {
        this.preObj = object;
    }
}
