package com.apress.my.retro.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings(value = "unused")
// --> FIRST VERSION OF MY-RETRO CONFIG EXAMPLE
//@EnableConfigurationProperties({MyRetroProperties.class}) // -> "annotation that accepts an array of
// ConfigurationProperties marked classes"
//@Configuration
public class MyRetroConfigurationProperties {
//    Logger logger = LoggerFactory.getLogger(MyRetroConfigurationProperties.class);
//
//    //@Bean //-> MyRetroConfigurationProperties.class will automatically be injected by Spring
//    // -> " In this way you can access these properties like any other regular class with its getters."
//    ApplicationListener<ApplicationReadyEvent> init(MyRetroProperties myRetroProperties){
//        return event -> logger.info("""
//                    The users service properties are:
//                    - Server: {}
//                    - Port: {}
//                    - Username: {}
//                    - Password: {}
//                    """, myRetroProperties.getUsers().getServer(),
//                    myRetroProperties.getUsers().getPort(),
//                    myRetroProperties.getUsers().getUsername(),
//                    myRetroProperties.getUsers().getPassword());
//    }
}
