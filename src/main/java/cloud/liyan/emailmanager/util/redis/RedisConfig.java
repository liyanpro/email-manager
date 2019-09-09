package cloud.liyan.emailmanager.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author liyan
 * @description
 * @date 2019-09-09 下午5:45
 */
public class RedisConfig {
    private final static Logger logger= LoggerFactory.getLogger(RedisConfig.class);
    private static Properties props = new Properties();
    static {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("redis.properties"));
        } catch (FileNotFoundException e) {
           logger.error("RedisConfig FileNotFoundException",e);
        } catch (IOException e) {
            logger.error("RedisConfig IOException",e);
        }
    }

    public static String getValue(String key) {
        return props.getProperty(key);
    }

}
