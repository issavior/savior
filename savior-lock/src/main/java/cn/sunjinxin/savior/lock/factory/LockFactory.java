package cn.sunjinxin.savior.lock.factory;

import cn.sunjinxin.savior.lock.config.LockProperties;
import cn.sunjinxin.savior.lock.convert.LockCommon;
import cn.sunjinxin.savior.lock.exception.LockException;
import cn.sunjinxin.savior.lock.locker.LockAction;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Lock factory
 *
 * @author sunjinxin
 * @since 2023/11/15 14:09
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LockFactory {

    LockProperties lockProperties;
    List<LockAction> lockers;

    public LockAction build() {
        LockCommon.checkLockFactory(lockProperties);
        return Optional.ofNullable(lockers).orElse(Lists.newArrayList())
                .stream()
                .filter(r -> Objects.equals(r.getStrategy().getCode(), lockProperties.getStrategy().getCode()))
                .findFirst()
                .orElseThrow(() -> new LockException("not find lock action"));
    }

}
