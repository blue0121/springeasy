package io.jutil.springeasy.redis.pubsub;

/**
 * @author Jin Zheng
 * @since 2025-05-07
 */
public record RedisSubscribeTopic(String pattern, String channel) {
}
