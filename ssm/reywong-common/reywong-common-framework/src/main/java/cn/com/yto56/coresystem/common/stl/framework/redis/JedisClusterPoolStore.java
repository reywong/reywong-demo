package cn.com.yto56.coresystem.common.stl.framework.redis;

import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedHashSet;
import java.util.Set;

public class JedisClusterPoolStore {
    /**
     * Jedis连接池配置
     */
    private JedisPoolConfig jedisPoolConfig;
    private JedisCluster jedisCluster;
    private Jedis jedis;

    public JedisClusterPoolStore(JedisPoolConfig jedisPoolConfig, String url) {
        this.jedisPoolConfig = jedisPoolConfig;
        setJedisCluster(jedisPoolConfig, url);
    }

    public JedisClusterPoolStore(String url) {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMaxWaitMillis(1000);
        jedisPoolConfig.setTestOnBorrow(true);

    }

    /**
     * 设置jedisCluster
     *
     * @param jedisPoolConfig 连接池
     * @param url             集群连接
     */
    public void setJedisCluster(JedisPoolConfig jedisPoolConfig, String url) {
        if (StringUtils.isNotBlank(url)) {
            Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
            String[] redisUrl = url.split(",");
            if (redisUrl != null && redisUrl.length > 0) {
                for (int i = 0; i < redisUrl.length; i++) {
                    String[] hostUrl = redisUrl[i].split(":");
                    String host = hostUrl[0];
                    int port = Integer.valueOf(hostUrl[1]);
                    nodes.add(new HostAndPort(host, port));
                }
                jedisCluster = new JedisCluster(nodes, jedisPoolConfig);
            }
        }

    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

}
