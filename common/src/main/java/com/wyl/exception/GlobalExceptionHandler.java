package com.wyl.exception;

import com.wyl.vo.ResultVo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param e 业务异常
     * @return 结果对象
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVo<?> handleBusinessException(BusinessException e) {
        return ResultVo.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常 - @Valid
     *
     * @param e 方法参数校验异常
     * @return 结果对象
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResultVo.error(400, errorMsg);
    }

    /**
     * 参数校验异常 - @Validated (类上)
     *
     * @param e 约束验证异常
     * @return 结果对象
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVo<?> handleConstraintViolationException(ConstraintViolationException e) {
        return ResultVo.error(400, e.getMessage());
    }

    /**
     * 参数绑定异常
     *
     * @param e 绑定异常
     * @return 结果对象
     */
    @ExceptionHandler(BindException.class)
    public ResultVo<?> handleBindException(BindException e) {
        String errorMsg = e.getAllErrors().stream()
                .map(err -> err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResultVo.error(400, errorMsg);
    }

    /**
     * 系统通用异常
     *
     * @param e 异常
     * @return 结果对象
     */
    @ExceptionHandler(Exception.class)
    public ResultVo<?> handleException(Exception e) {
        e.printStackTrace(); // 开发阶段建议打印
        return ResultVo.error(500, "系统异常：" + e.getMessage());
    }
}
