package com.apress.users;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    /* @Configuration makes sure that classes get executed (@Bean, @Value) and saved into context.
     * Because SimpleRepository has just UserRepository as implementation that will be automatically injected
     * by Spring into the @Bean. (So careful if other implementations are required.)
     * So for this Bean, it will be created after the application has finished setting things up (application is "ready").
     * The two users that are created here will be stored in H2 (in-memo) database and H2 will be set up as datasource
     * if no driver, username and password are specified in the properties.
     * If the properties are specified: driver for db server connection, username and password (connection credentials),
     * then it will use those to create a DataSource object in order to establish connections to the database and
     * negotiate transactions (perform SQL statements in our case).
     */
    @Bean
    ApplicationListener<ApplicationReadyEvent> init(SimpleRepository userRepository) {
        return applicationReadyEvent -> {
            User ximena = User.builder()
                    .email("ximena@email.com")
                    .name("Ximena")
                    .password("aw2s0meR!")
                    .active(true)
                    .role(UserRole.USER)
                    .build();
            userRepository.save(ximena);
            User norma = User.builder()
                    .email("norma@email.com")
                    .name("Norma")
                    .password("aw2s0meR!")
                    .active(true)
                    .role(UserRole.USER)
                    .build();
            userRepository.save(norma);
        };
    }
}
