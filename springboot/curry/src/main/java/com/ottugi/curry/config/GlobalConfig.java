package com.ottugi.curry.config;

import java.util.Properties;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Slf4j
@Getter
public class GlobalConfig {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ResourceLoader resourceLoader;

    private String jwt_key;
    private String jwt_header;
    private String jwt_prefix;

    private String redis_host;
    private int redis_port;
    private String redis_password;

    private String flask_host;
    private int flask_port;

    private String file_Path;
    private String file_delimiter;
    private String[] field_Names;

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

            jwt_key = properties.getProperty("jwt.security.key");
            jwt_header = properties.getProperty("jwt.response.header");
            jwt_prefix = properties.getProperty("jwt.token.prefix");

            redis_host = properties.getProperty("spring.redis.host");
            redis_port = Integer.parseInt(properties.getProperty("spring.redis.port"));
            redis_password = properties.getProperty("spring.redis.password");

            flask_host = properties.getProperty("flask.host");
            flask_port = Integer.parseInt(properties.getProperty("flask.port"));

            file_Path = properties.getProperty("file.path");
            file_delimiter = properties.getProperty("file.delimiter");
            String fieldNamesString = properties.getProperty("file.field.names");
            field_Names = fieldNamesString.split(",");

            this.local = activeProfile.equals("local");
            this.dev = activeProfile.equals("dev");
            this.prod = activeProfile.equals("prod");

        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
