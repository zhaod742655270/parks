package com.hbyd.parks.common.base;

/**
 * {@link RecoverableEntity} 对应的传输实体
 * 前台伪删秩序调用 fakeDel() 即可，无需使用 isValid 字段，因此 RecoverableDTO 没有用，暂不删除。
 */
public class RecoverableDTO extends BaseDTO{
    protected boolean isValid;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
