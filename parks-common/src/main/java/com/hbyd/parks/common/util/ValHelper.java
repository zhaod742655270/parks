package com.hbyd.parks.common.util;

import java.util.Collection;

/**
 * 封装所有的检验操作
 */
public class ValHelper {
    /** 断言参数非 NULL
     * @param arg 参数
     * @param message 参数不合法时的提示信息
     */
    public static void notNull(Object arg, String message) {
        if (arg == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**断言参数为 NULL
     * @param arg 参数
     * @param message 参数不合法时的提示信息
     */
    public static void assertNull(Object arg, String message){
        if(arg != null){
            throw new IllegalArgumentException(message);
        }
    }

    /** 断言参数非 NULL
     * @param arg 参数
     */
    public static void notNull(Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException("arg cannot be null");
        }
    }
    /** 断言参数非 NULL
     * @param arg   参数
     * @param argIndex    参数索引，从 0 开始
     */
    public static void notNull(Object arg, int argIndex) {
        if(arg == null){
            throw new IllegalArgumentException("arg value at index " + argIndex + " cannot be null");
        }
    }


    /** 断言参数非 NULL 或 EMPTY(空字符串)
     * @param args 参数列表
     * @param argNames 参数名称列表，和参数一一对应
     */
    public static void notNullOrEmpty(Object[] args, String... argNames) {

        if(args.length != argNames.length){
            throw new IllegalArgumentException("the length of args and argNames does not match!");
        }

        for(int i = 0; i < args.length; i++){
            Object arg = args[i];
            String argName = argNames[i];

            notNullOrEmpty(arg, argName);
        }
    }

    /**断言参数非 NULL 或 EMPTY(空字符串)
     * @param arg 参数值
     * @param argName 参数名称
     */
    public static void notNullOrEmpty(Object arg, String argName) {
        if(arg == null){
            throw new IllegalArgumentException(argName + " cannot be null");
        }
        if(arg instanceof String && "".equals(((String)arg).trim())){
            throw new IllegalArgumentException(argName + " cannot be empty string");
        }
    }

    /**断言参数非 NULL 或 EMPTY(空字符串)
     * @param arg 参数值
     * @param argIndex 参数索引，从 0 开始
     */
    public static void notNullOrEmpty(Object arg, int argIndex) {
        if(arg == null){
            throw new IllegalArgumentException("arg value at index " + argIndex + " cannot be null");
        }
        if(arg instanceof String && "".equals(arg)){
            throw new IllegalArgumentException("arg value at index " + argIndex + " cannot be empty string");
        }
    }

    /**
     * 断言更新时：实体的ID 非 NULL 或 EMPTY(空字符串)
     * @param arg 要验证的参数
     */
    public static void notNullOrEmpty(Object arg){
        if(arg == null){
            //null
            throw new IllegalArgumentException("arg cannot be NULL");
        }else if(arg instanceof  String && "".equals(arg)){
            //empty string
            throw new IllegalArgumentException("arg cannot be EMPTY");
        }else if(arg instanceof Collection && ((Collection)arg).size()==0){
            //empty collection
        }
    }

    /**断言保存时：实体的 ID 不存在
     * @param id
     */
    public static void idNotExist(String id){
        if(id == null){
            //id 不存在
        }else if("".equals(id)){
            //id 不存在
        }else{//id 存在
            throw new IllegalArgumentException(("ID should not exist when perform save operation"));
        }
    }
}
