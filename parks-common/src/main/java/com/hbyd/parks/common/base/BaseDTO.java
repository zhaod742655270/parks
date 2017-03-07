package com.hbyd.parks.common.base;

import java.io.Serializable;

public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;
//    protected Long version;
    // version 必须有 setter, 更新表单中隐藏的 version 字段必须填充到 DTO 中，才能调用更新服务/Action
    // 这样后台就知道要更新的 DTO 版本是否高于数据库中对应记录的版本并作出处理：
    // 要么覆盖高版本的数据库记录；要么重新加载更新表单，由用户重新填写再更新。

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public Long getVersion() {
//        return version;
//    }
//
//    public void setVersion(Long version) {
//        this.version = version;
//    }
}
