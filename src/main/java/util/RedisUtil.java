package util;

import redis.clients.jedis.Jedis;
public class RedisUtil {
    private static final Jedis jedis = new Jedis("localhost", 6379);

    public static void set(String key, String value) {
        jedis.set(key, value);
    }

    public static String get(String key) {
        return jedis.get(key);
    }

    public static void delete(String key) {
        jedis.del(key);
    }
}
