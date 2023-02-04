package com.example.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    @Profile("ws-dev")
    public static PropertySourcesPlaceholderConfigurer devProperties(){
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer
                = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[ ]
                { new ClassPathResource( "endpoint/ws-dev.properties" ) };
        propertySourcesPlaceholderConfigurer.setLocations( resources );
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders( true );
        return propertySourcesPlaceholderConfigurer;
    }
    @Bean
    @Profile("ws-dev")
    public static PropertySourcesPlaceholderConfigurer uatProperties(){
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer
                = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[ ]
                { new ClassPathResource( "endpoint/ws-uat.properties" ) };
        propertySourcesPlaceholderConfigurer.setLocations( resources );
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders( true );
        return propertySourcesPlaceholderConfigurer;
    }
    @Bean
    @Profile("ws-uat")
    public static PropertySourcesPlaceholderConfigurer pilotProperties(){
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer
                = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[ ]
                { new ClassPathResource( "endpoint/ws-pilot.properties" ) };
        propertySourcesPlaceholderConfigurer.setLocations( resources );
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders( true );
        return propertySourcesPlaceholderConfigurer;
    }
    @Bean
    @Profile("ws-pro")
    public static PropertySourcesPlaceholderConfigurer proProperties(){
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer
                = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[ ]
                { new ClassPathResource( "endpoint/ws-production.properties" ) };
        propertySourcesPlaceholderConfigurer.setLocations( resources );
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders( true );
        return propertySourcesPlaceholderConfigurer;
    }
}
