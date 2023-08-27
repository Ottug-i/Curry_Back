package com.ottugi.curry.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Getter
@Slf4j
public class GlobalConfig {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ResourceLoader resourceLoader;

    private String key;
    private String header;
    private String prefix;

    private String redis_host;
    private int redis_port;
    private String redis_password;

    private String flask_host;
    private int flask_port;

    private boolean local;
    private boolean dev;
    private boolean prod;

    @PostConstruct
    public void init() {
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        String activeProfile = "local";
        if (ObjectUtils.isNotEmpty(activeProfiles)) {
            activeProfile = activeProfiles[0];
        }
        String resourcePath = String.format("classpath:application-%s.properties", activeProfile);
        try {
            Resource resource = resourceLoader.getResource(resourcePath);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);

            key = properties.getProperty("jwt.security.key");
            header = properties.getProperty("jwt.response.header");
            prefix = properties.getProperty("jwt.token.prefix");

            redis_host = properties.getProperty("spring.redis.host");
            redis_port = Integer.parseInt(properties.getProperty("spring.redis.port"));
            redis_password = properties.getProperty("spring.redis.password");

            flask_host = properties.getProperty("flask.host");
            flask_port = Integer.parseInt(properties.getProperty("flask.port"));

            this.local = activeProfile.equals("local");
            this.dev = activeProfile.equals("dev");
            this.prod = activeProfile.equals("prod");

        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public boolean isLocal() {
        return local;
    }

    public boolean isDev() {
        return dev;
    }

    public boolean isProd() {
        return prod;
    }
}
