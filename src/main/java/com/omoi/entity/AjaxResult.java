package com.omoi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author omoi
 * @date 2023/7/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResult {
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private Integer code;
    private String message;
    private Object data;

    public static AjaxResult success() {
        return new AjaxResult(200, SUCCESS, null);
    }

    public static AjaxResult success(String message) {
        return new AjaxResult(200, message, null);
    }

    public static AjaxResult success(Object data) {
        return new AjaxResult(200, SUCCESS, data);
    }

    public static AjaxResult success(String message, Object data) {
        return new AjaxResult(200, message, data);
    }

    public static AjaxResult error() {
        return new AjaxResult(500, ERROR, null);
    }

    public static AjaxResult error(String message) {
        return new AjaxResult(500, message, null);
    }
}
