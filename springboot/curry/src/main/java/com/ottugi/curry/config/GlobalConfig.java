package com.ottugi.curry.config;

import java.io.IOException;
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
    private static final String DEFAULT_ACTIVE_PROFILE = "local";

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ResourceLoader resourceLoader;

    private String jwtKey;
    private String jwtHeader;
    private String jwtPrefix;

    private String redisHost;
    private int redisPort;
    private String redisPassword;

    private String flaskHost;
    private int flaskPort;

    private String csvFilePath;
    private String csvFileDelimiter;
    private String[] csvFieldColumns;

    private boolean isLocal;
    private boolean isDev;
    private boolean isProd;

    @PostConstruct
    public void init() {
        String activeProfile = determineActiveProfile();
        loadPropertiesByActiveProfile(activeProfile);
    }

    private String determineActiveProfile() {
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        if (ObjectUtils.isNotEmpty(activeProfiles)) {
            return activeProfiles[0];
        }
        return DEFAULT_ACTIVE_PROFILE;
    }

    private void loadPropertiesByActiveProfile(String activeProfile) {
        String resourcePath = String.format("classpath:application-%s.properties", activeProfile);
        try {
            Resource resource = resourceLoader.getResource(resourcePath);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);

            jwtKey = properties.getProperty("jwt.security.key");
            jwtHeader = properties.getProperty("jwt.response.header");
            jwtPrefix = properties.getProperty("jwt.token.prefix");

            redisHost = properties.getProperty("spring.redis.host");
            redisPort = Integer.parseInt(properties.getProperty("spring.redis.port"));
            redisPassword = properties.getProperty("spring.redis.password");

            flaskHost = properties.getProperty("flask.host");
            flaskPort = Integer.parseInt(properties.getProperty("flask.port"));

            csvFilePath = properties.getProperty("file.path");
            csvFileDelimiter = properties.getProperty("file.delimiter");
            csvFieldColumns = properties.getProperty("file.field.columns").split(",");

            isLocal = activeProfile.equals("local");
            isDev = activeProfile.equals("dev");
            isProd = activeProfile.equals("prod");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
