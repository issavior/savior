package cn.sunjinxin.savior.lock.factory.strategy;

import cn.sunjinxin.savior.lock.exception.LockException;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Lock Strategy
 *
 * @author sunjinxin
 * @since 2023/11/15 14:14
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum LockStrategy {

    /**
     * Default Redis implementation
     */
    DEFAULT("default"),

    /**
     * redis_lua
     */
    REDIS_LUA("redis_lua"),

    /**
     * zookeeper implementation
     */
    ZOOKEEPER("zookeeper");

    @Getter
    String code;

    public LockStrategy of(String code) {
        return Lists.newArrayList(LockStrategy.values()).stream()
                .filter(r -> r.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new LockException("not find strategy"));
    }
}
