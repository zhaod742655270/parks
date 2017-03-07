package com.hbyd.parks.common.base;

import javax.jws.WebParam;

/**
 * 定义可恢复的服务操作
 */
public interface RecoverableWS {
    /**
     * 伪删除实体
     *
     * @param id 实体ID
     */
    public void delFake(@WebParam(name = "id") String id);
}
