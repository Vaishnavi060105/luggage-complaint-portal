package com.college.luggageportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/** Entry point. Extends SpringBootServletInitializer so the WAR also runs on Tomcat. */
@SpringBootApplication
public class LuggagePortalApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LuggagePortalApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(LuggagePortalApplication.class, args);
    }
}
