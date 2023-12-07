package cn.sunjinxin.savior.retry.factory;

import cn.sunjinxin.savior.retry.exception.RetryException;
import cn.sunjinxin.savior.retry.config.RetryProperties;
import cn.sunjinxin.savior.retry.retryer.RetryAction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;

/**
 * @author sunjinxin
 * @since 2023/11/14 11:24
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RetryFactory {

    RetryProperties retryProperties;
    List<RetryAction> retryers;

    public RetryAction build() {
        return retryers.stream()
                .filter(r -> Objects.equals(r.getStrategy(), retryProperties.getType()))
                .findFirst()
                .orElseThrow(() -> new RetryException("not find retryer"));
    }

}
