package com.hbyd.parks.common.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

/**
 * 异常处理切面
 */
public class ExceptionAspect {

    private Logger logger = Logger.getLogger(this.getClass());

    /**异常处理方法
     * @param jp 接入点
     * @param e 拦截到的异常
     */
    public void doException(JoinPoint jp, RuntimeException e){
        logger.error(e.getMessage(), e);
        throw e;
    }

}
