package cn.sunjinxin.savior.retry.factory.strategy;

import com.google.common.collect.Lists;
import cn.sunjinxin.savior.retry.exception.StrategyException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author sunjinxin
 * @since 2023/11/14 14:04
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RetryType {

    /**
     * GUAVA
     */
    GUAVA("guava"),

    /**
     * DEFAULT
     */
    DEFAULT("default");

    @Getter
    String code;

    public RetryType of(String code) {
        return Lists.newArrayList(RetryType.values()).stream()
                .filter(r -> r.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new StrategyException("not find strategy"));
    }
}
