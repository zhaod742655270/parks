package com.hbyd.parks.common.enums;

/**
 * 班次类型
 */
public enum ShiftType {
    /**
     * 正常班次
     * 之前没有考虑到班次类型，因此，前台添加班次时没有指定班次类型的地方，也没这个必要
     * 因此，就想到：表 shift 的 shiftType 字段默认为 null, 表示 NORMAL
     * 可是，如果 shift.shiftType 为 null，在转换为 shiftDTO.shiftType 时就会报错：
     *  Enum.valueOf(...)//转换时调用这行代码
     * 最终，还是决定加上 NORMAL 这个类型，需要前台代码相应的修改，保存/更新 ShiftDTO 时设定 shiftType
     *
     * 这里只预定义特殊类型的班次
     */
    NORMAL,
    /**
     * 休息班次
     */
    REST,
    /**
     * 班次未指定
     */
    NONE,
    /**
     * 全局班次
     */
    GLOBAL
}
