package com.example.learn.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 基本配置类
 */
@ConfigurationProperties(prefix = "online")
@Configuration
@Data
public class BaseProperties {
    private String test;
}
