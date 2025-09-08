package com.apress.my.retro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("all")
@Configuration
public class MyRetroConfiguration {
    Logger logger = LoggerFactory.getLogger(MyRetroConfiguration.class);

    //@Value -> specifies a value between "" to  be injected into the variable it annotates
    // it uses the SpEL -> Spring Expression Language
    // During the Spring app lifecycle it will search for the values of the labels
    // specified between {}, in the .properties or .yaml file to inject into the variable
    // @value annotates.
    @Value("${users.server}")
    String server;

    @Value("${usres.port}")
    Integer port;

    @Value("${users.username}")
    String username;

    @Value("${users.password}")
    String password;

    // This will be executed when ApplicationReadyEvent is fired
    @Bean
    ApplicationListener<ApplicationReadyEvent> init(){
        return event ->
          logger.info("""
                  The Users service properties are: 
                  - Server: {}
                  - Port: {}
                  - Username: {}
                  - Password: {} """, server,port,username,password);
    }
}
