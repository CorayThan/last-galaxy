package com.corinthgames.lastgalaxy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ImportResource(value = { "/WEB-INF/spring-security.xml" })
public class SpringSecurityConfig {

}
