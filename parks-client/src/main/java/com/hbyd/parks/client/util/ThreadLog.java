package com.hbyd.parks.client.util;

import com.hbyd.parks.dto.managesys.OperLogDTO;

/**
 * 封装日志 ThreadLocal
 */
public class ThreadLog {
    private static final ThreadLocal<OperLogDTO> threadLocal = new ThreadLocal<>();

    public static OperLogDTO get(){
        return threadLocal.get();
    }

    public static void set(OperLogDTO operLogDTO){
        threadLocal.set(operLogDTO);
//     threadLocal.set(new WeakReference<>(operLogDTO).get());//虽然避免 PerGen 内存溢出，但是可能导致 NullPointerException
    }

    public static void remove(){
        threadLocal.remove();
    }
}
