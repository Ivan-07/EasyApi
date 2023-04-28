package com.ivan.easyapicommon.exception;

import com.ivan.easyapicommon.common.ErrorCode;

/**
 * 自定义异常类
 *
 * @author ivan
 */
public class BusinessException extends RuntimeException {

    private final int code;

    private ErrorCode errorCode;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }

    public ErrorCode getErrorCode() {return errorCode;}
}
