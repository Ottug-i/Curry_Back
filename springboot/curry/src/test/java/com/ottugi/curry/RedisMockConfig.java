package com.ottugi.curry;

import java.io.IOException;
import javax.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

@TestConfiguration
public class RedisMockConfig {
    private static final String HOST = "localhost";
    private static final Integer PORT_NUMBER = 6379;
    
    private final RedisServer redisServer;

    public RedisMockConfig() throws IOException {
        redisServer = new RedisServer(PORT_NUMBER);
        redisServer.start();
    }

    @Bean
    @Primary
    public Jedis jedis() {
        return new Jedis(HOST, PORT_NUMBER);
    }

    @PreDestroy
    public void stopRedisServer() {
        if (redisServer.isActive()) {
            redisServer.stop();
        }
    }
}
