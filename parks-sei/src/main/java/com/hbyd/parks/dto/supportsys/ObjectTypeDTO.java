package com.hbyd.parks.dto.supportsys;

import com.hbyd.parks.common.base.RecoverableDTO;

/**
 * 对象类型，传输实体
 */
public class ObjectTypeDTO extends RecoverableDTO {
    private String typeCode;
    private String typeName;

    private String objectName;//仅显示用
    private String objectFK;//新增/修改用

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

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectFK() {
        return objectFK;
    }

    public void setObjectFK(String objectFK) {
        this.objectFK = objectFK;
    }
}
