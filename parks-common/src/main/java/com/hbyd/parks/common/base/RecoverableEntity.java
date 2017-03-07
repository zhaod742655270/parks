package com.hbyd.parks.common.base;

import javax.persistence.*;

/**
 * 可恢复的实体
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class RecoverableEntity extends BaseEntity {
    @Basic
    @Column(name = "isValid")
    protected boolean isValid = true;//默认为有效数据,保存/更新服务均不涉及此字段，只有 delFake 会修改该字段

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
