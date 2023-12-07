package cn.sunjinxin.savior.lock.config;

import cn.sunjinxin.savior.lock.convert.LockCommon;
import cn.sunjinxin.savior.lock.factory.LockFactory;
import cn.sunjinxin.savior.lock.locker.LockAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.*;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * lock auto configuration
 *
 * @author sunjinxin
 * @since 2023/11/15 11:52
 */
@Configuration
public class LockAutoConfiguration {

    @Bean
    public LockProperties lockProperties() {
        return new LockProperties();
    }

    @Bean(name = "saviorLockJedisCluster")
    public JedisCluster jedisCluster(LockProperties lockProperties) {
        if (StringUtils.isEmpty(lockProperties.getHost())) {
            return null;
        }
        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        jedisClusterNode.add(new HostAndPort(lockProperties().getHost(), lockProperties().getPort()));
        return new JedisCluster(jedisClusterNode, lockProperties().getTimeout(), lockProperties().getSoTimeout(), lockProperties().getMaxAttempts(), lockProperties().getPassword(), "clientName", LockCommon.buildConnectionPoolConfig());
    }

    @Bean
    public LockClient lockClient(JedisCluster jedisCluster, LockProperties lockProperties) {
        return new LockClient(jedisCluster, lockProperties);
    }

    @Bean
    public List<LockAction> lockActions() {
        return LockCommon.buildLockActions();
    }

    @Bean
    public LockFactory lockFactory(LockProperties lockProperties, List<LockAction> lockActions) {
        return new LockFactory(lockProperties, lockActions);
    }

}
