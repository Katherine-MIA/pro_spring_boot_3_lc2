package com.apress.my.retro.config;

import com.apress.my.retro.board.Card;
import com.apress.my.retro.board.CardType;
import com.apress.my.retro.board.RetroBoard;
import com.apress.my.retro.service.RetroBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@SuppressWarnings("all")
@EnableConfigurationProperties({MyRetroProperties.class})
@Configuration
public class MyRetroConfiguration {
    // Now this is an interesting use for a configuration
    // So basically what this does is to hardcode the first set of data (a RetroBoard complete with Cards),
    // by saving it into the db, the very first thing after the application is ready.
    @Bean
    ApplicationListener<ApplicationReadyEvent> ready(RetroBoardService retroBoardService) {
        return ApplicationReadyEvent -> {
            UUID retroBoardId = UUID.fromString("9dc9b71b-a07e-418b-b972-40225449aff2");
            RetroBoard retroBoard = RetroBoard.builder()
                    .id(retroBoardId)
                    .name("Spring Boot Conference")
                    .card(UUID.fromString("bb2a80a5-a0f5-4180-a6dc-80c84bc014c9"),
                            Card.builder()
                                    .id(UUID.fromString("bb2a80a5-a0f5-4180-a6dc-80c84bc014c9"))
                                    .comment("Spring Boot Rocks!")
                                    .cardType(CardType.HAPPY)
                                    .build())
                    .card(UUID.fromString("f9de7f11-5393-4b5b-8e9d-10eca5f50189"),
                            Card.builder()
                                    .id(UUID.fromString("f9de7f11-5393-4b5b-8e9d-10eca5f50189"))
                                    .comment("Meet everyone in person")
                                    .cardType(CardType.HAPPY)
                                    .build())
                    .card(UUID.fromString("6cdb30d6-43f2-42b7-b0db-f3acbc53d46"),
                            Card.builder()
                                    .id(UUID.fromString("6cdb30d6-43f2-42b7-b0db-f3acbc53d46"))
                                    .comment("When is the next one?")
                                    .cardType(CardType.MEH)
                                    .build())
                    .card(UUID.fromString("9de1f7f9-2470-4c8d-86f2-371203620fcd"),
                            Card.builder()
                                    .id(UUID.fromString("9de1f7f9-2470-4c8d-86f2-371203620fcd"))
                                    .comment("Not enough time to talk to everyone")
                                    .cardType(CardType.SAD)
                                    .build())
                    .build();
            retroBoardService.save(retroBoard);
        };
    }
}



// --> FIRST VERSION OF MY-RETRO CONFIG THEORY

//@Configuration //-> A configuration can be made like this as well. The book keeps changing the class
// But I'll leave the examples in the project for me to reference later I'll just comment the @Configuration so
// that Spring won't try to use them.
/*
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
 */