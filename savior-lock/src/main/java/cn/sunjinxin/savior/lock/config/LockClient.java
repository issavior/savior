package cn.sunjinxin.savior.lock.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.SetParams;

/**
 * @author sunjinxin
 * @since 2023/11/15 13:55
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LockClient {

    JedisCluster saviorLockJedisCluster;
    LockProperties lockProperties;

    static String SPLIT = "_";

    public String get(String key) {
        return saviorLockJedisCluster.get(lockProperties.getLockKeyPrefix() + SPLIT + key);
    }

    public String set(String key, String value) {
        return saviorLockJedisCluster.set(lockProperties.getLockKeyPrefix() + SPLIT + key, value);
    }

    public String set(String key, String value, int secondsToExpire) {
        SetParams setParams = new SetParams();
        setParams.ex(secondsToExpire);
        return saviorLockJedisCluster.set(lockProperties.getLockKeyPrefix() + SPLIT + key, value, setParams);
    }

    public Long setnx(String key, String value, int secondsToExpire) {
        long result = saviorLockJedisCluster.setnx(lockProperties.getLockKeyPrefix() + SPLIT + key, value);
        if (result == 1L) {
            saviorLockJedisCluster.expire(key, secondsToExpire);
        }
        return result;
    }

    public void delete(String key) {
        saviorLockJedisCluster.del(lockProperties.getLockKeyPrefix() + SPLIT + key);
    }
}

