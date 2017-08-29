package cn.com.yto56.coresystem.common.stl.framework.redis;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用Jedis创建连接池
 *
 * @author lihui
 *         2016-02-24
 */
public class JedisPoolStore {
    /**
     * Jedis连接池配置
     */
    private static JedisPoolConfig jedisPoolConfig;

    /**
     * 存Jedis对应的连接池
     */
    private static Map<String, JedisPool> jedisPoolMap = new HashMap<String, JedisPool>();


    private static class JedisPoolStoreHolder {
        private static final JedisPoolStore INSTANCE = new JedisPoolStore();
    }

    public JedisPoolStore(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    private JedisPoolStore() {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMaxWaitMillis(100);
        jedisPoolConfig.setTestOnBorrow(true);
    }

    public static JedisPoolStore getInstance() {
        return JedisPoolStoreHolder.INSTANCE;
    }


    private String hostPort(String host, int port) {
        return host + ":" + port;
    }

    private JedisPool createJedisPool(String host, int port) {
        System.out.println(">>> jedis pool map not found [" + host + ":" + port + "],begin create");
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port);
        jedisPoolMap.put(hostPort(host, port), jedisPool);
        return jedisPool;
    }

    public Jedis getJedis(String host, String port) {
        return getJedis(host, Integer.parseInt(port));
    }

    public synchronized Jedis getJedis(String host, int port) {
        JedisPool pool = null;
        pool = jedisPoolMap.get(hostPort(host, port));
        if (pool == null) {
            createJedisPool(host, port);
            return jedisPoolMap.get(hostPort(host, port)).getResource();
        }
        return pool.getResource();
    }

    public void returnJedis(Jedis jedis) {
        if (jedis == null) {
            return;
        }
        Client client = jedis.getClient();
        String key = hostPort(client.getHost(), client.getPort());
        JedisPool jedisPool = jedisPoolMap.get(key);

        if (jedisPool == null) {
            return;
        }
        jedisPool.returnResource(jedis);
    }

    public void returnBrokenJedis(Jedis jedis) {
        if (jedis == null) {
            return;
        }
        Client client = jedis.getClient();
        String key = hostPort(client.getHost(), client.getPort());
        JedisPool jedisPool = jedisPoolMap.get(key);

        if (jedisPool == null) {
            return;
        }
        jedisPool.returnBrokenResource(jedis);
    }


    public static void main(String[] args) {
        Jedis jedis1 = JedisPoolStore.getInstance().getJedis("127.0.0.1", 6379);
        System.out.println(jedis1.get("reywgn"));
        JedisPoolStore.getInstance().returnJedis(jedis1);
    }


}
