package cn.sunjinxin.savior.core.common;

import cn.sunjinxin.savior.core.constant.CommonConstants;
import lombok.*;

import java.io.Serializable;
import java.util.Optional;

/**
 * result
 *
 * @author issavior
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result<T> implements Serializable {

    @Getter
    @Setter
    private boolean success;

    @Getter
    @Setter
    private boolean fail;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static <T> Result<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> Result<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> Result<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> Result<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }


    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setSuccess(Optional.of(code).filter(c -> c.equals(CommonConstants.SUCCESS)).isPresent());
        apiResult.setSuccess(Optional.of(code).filter(c -> c.equals(CommonConstants.FAIL)).isPresent());
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
