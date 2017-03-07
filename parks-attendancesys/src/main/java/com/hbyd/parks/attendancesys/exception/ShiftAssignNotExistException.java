package com.hbyd.parks.attendancesys.exception;

/**
 * 运行时异常：当月的排班记录不存在
 */
public class ShiftAssignNotExistException extends RuntimeException{

    public ShiftAssignNotExistException() {
    }

    public ShiftAssignNotExistException(String message) {
        super(message);
    }

    public ShiftAssignNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShiftAssignNotExistException(Throwable cause) {
        super(cause);
    }

    public ShiftAssignNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
