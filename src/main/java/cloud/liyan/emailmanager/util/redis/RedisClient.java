package cloud.liyan.emailmanager.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author liyan
 * @description
 * @date 2019-09-09 下午5:32
 */
public class RedisClient {

   private final static Logger logger=LoggerFactory.getLogger(RedisClient.class);
    private static JedisPool redis_pool;

    private static void createJedisPool() {
        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxIdle(Integer.valueOf(RedisConfig.getValue("redis.maxIdle")));
            poolConfig.setMaxTotal(Integer.valueOf(RedisConfig.getValue("redis.maxActive")));
            poolConfig.setMaxWaitMillis(Integer.valueOf(RedisConfig.getValue("redis.maxWait")));
            redis_pool = new JedisPool(poolConfig, RedisConfig.getValue("redis.host"),
                    Integer.valueOf(RedisConfig.getValue("redis.port")));
        } catch (Exception e) {
            logger.error("初始化redis异常", e);
        }
    }


    private static synchronized void poolInit() {
        if (redis_pool == null) {
            createJedisPool();
        }
    }


    public static Jedis getJedis() {
        try {
            if (redis_pool == null) {
                poolInit();
            }
            return redis_pool.getResource();
        } catch (Exception e) {
            logger.error("获取redis对象异常", e);
            redis_pool = null;
        }
        return null;
    }


    public static void returnRes(Jedis jedis) {
        try {
            redis_pool.returnResource(jedis);
        } catch (Exception e) {
            logger.error("归还redis对象异常", e);
            redis_pool = null;
        }
    }

}
