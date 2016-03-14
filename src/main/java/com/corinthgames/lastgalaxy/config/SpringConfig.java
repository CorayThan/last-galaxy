package com.corinthgames.lastgalaxy.config;

import com.corinthgames.lastgalaxy.annotations.UserHandlerResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Random;

/**
 * base spring configuration class
 *
 * @author nwestl
 */
@Configuration
@ComponentScan(SpringConfig.BASE_PACKAGE)
@EnableWebMvc
@EnableScheduling
@EnableJpaRepositories(SpringConfig.BASE_PACKAGE)
public class SpringConfig extends WebMvcConfigurerAdapter {

	public static final String SECURE_NAMESPACE = "/api";
	public static final String PUBLIC_NAMESPACE = "/api/public";

	/**
	 * You probably shouldn't change the base package name, but if you do, update it in here!
	 */
	protected static final String BASE_PACKAGE = "com.corinthgames.lastgalaxy";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/ui/**").addResourceLocations("classpath:/ui/");
	}

	/**
	 * env properties (only the proper one should be on the classpath thanks to maven pom profiles)
	 *
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {

		PropertySourcesPlaceholderConfigurer props = new PropertySourcesPlaceholderConfigurer();
		props.setLocations(new Resource[]{new ClassPathResource("service.properties")});
		return props;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userEmailMethodResolver());
	}

	@Bean
	public HandlerMethodArgumentResolver userEmailMethodResolver() {
		return new UserHandlerResolver();
	}

	@Bean
	public ObjectMapper jacksonMapper() {
		return new ObjectMapper();
	}

	@Bean
	public Random random() {
		return new Random();
	}
}
