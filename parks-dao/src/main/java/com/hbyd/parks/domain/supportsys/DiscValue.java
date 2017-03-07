package com.hbyd.parks.domain.supportsys;

/**
 * 区分列的值，Hibernate 可以不使用区分列，其他 JPA 实现可能会用到
 */
public class DiscValue {
    public static final String 设备 = "0";
    public static final String 控制器 = "1";
    public static final String 读头 = "2";
    public static final String 电子围栏 = "3";
    public static final String 门 = "4";
    public static final String 摄像机 = "5";
    public static final String 探头 = "6";
    public static final String 主机 = "7";
    public static final String 终端 = "8";
    public static final String IO = "9";
    public static final String 扩展模块 = "10";
}
