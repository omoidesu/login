package com.omoi.exception;

/**
 * @author omoi
 * @date 2023/7/2
 */
public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
