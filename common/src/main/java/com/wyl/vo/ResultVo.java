package com.wyl.vo;

import lombok.Getter;
import lombok.ToString;

/**
 * 通用响应体
 */
@Getter
@ToString
public class ResultVo<T> {
    private final int code;
    private final String msg;
    private final T data;

    private ResultVo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功，无返回数据
    public static ResultVo<Void> success() {
        return new ResultVo<>(200, "success", null);
    }

    // 成功，有返回数据
    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(200, "success", data);
    }

    public static <T> ResultVo<T> success(String msg, T data) {
        return new ResultVo<>(200, msg, data);
    }

    // 失败，无额外数据
    public static ResultVo<Void> error(int code, String msg) {
        return new ResultVo<>(code, msg, null);
    }

    public static ResultVo<Void> error(String msg) {
        return new ResultVo<>(500, msg, null);
    }
}