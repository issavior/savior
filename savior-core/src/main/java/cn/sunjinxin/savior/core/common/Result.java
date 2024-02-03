package cn.sunjinxin.savior.core.common;

import cn.sunjinxin.savior.core.constant.CommonConstants;
import cn.sunjinxin.savior.core.constant.ResultEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

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
    private String code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static <T> Result<T> ok() {
        return assemble(null, ResultEnum.SUCCESS, "200", null);
    }

    public static <T> Result<T> ok(T data) {
        return assemble(data, ResultEnum.SUCCESS, "200", null);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return assemble(data, ResultEnum.SUCCESS, "200", msg);
    }

    public static <T> Result<T> failed() {
        return assemble(null, ResultEnum.FAIL, "400", null);
    }

    public static <T> Result<T> failed(String msg) {
        return assemble(null, ResultEnum.FAIL, "400", msg);
    }

    public static <T> Result<T> failed(T data) {
        return assemble(data, ResultEnum.FAIL, "400", null);
    }

    public static <T> Result<T> failed(T data, String msg) {
        return assemble(data, ResultEnum.FAIL, "400", msg);
    }


    private static <T> Result<T> assemble(T data, ResultEnum mark, String code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setSuccess(Optional.of(mark).filter(c -> c.equals(ResultEnum.SUCCESS)).isPresent());
        apiResult.setFail(Optional.of(mark).filter(c -> c.equals(ResultEnum.FAIL)).isPresent());
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public <F> Result<F> copy(Function<T, F> function) {
        return Result.<F>builder()
                .success(this.isSuccess())
                .fail(this.isFail())
                .code(this.getCode())
                .msg(this.getMsg()).data(Optional.ofNullable(this.getData()).map(function).orElse(null))
                .build();
    }

}
