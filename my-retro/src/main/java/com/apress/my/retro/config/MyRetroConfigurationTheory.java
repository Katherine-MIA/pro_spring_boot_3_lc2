package com.apress.my.retro.config;

import com.apress.my.retro.MyRetroApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SuppressWarnings("all")
// arguments passed to the main class can be accessed through ApplicationArguments
// @Configuration -> dangling class for now
public class MyRetroConfigurationTheory {
    Logger logger = LoggerFactory.getLogger(MyRetroApplication.class);

    //  Works without constructor too because the ApplicationRunner interface brings
    //  application arguments
    MyRetroConfigurationTheory(ApplicationArguments args){
        // --<argument_name> with or without = example: [enable, remote, option]
        logger.info("Option Args: {}", args.getOptionNames());
        // --<string_passed_to_the_get_method>=<the_value_the_get_method_will_return>
        //in this case --option=value1 and --option=value2 it will return [value1, value2]
        logger.info("Option Arg Values: {}", args.getOptionValues("option"));
        // plain parameters [update, upgrade]
        logger.info("Non Option: {}", args.getNonOptionArgs());
    }

    // Specify @Bean for spring to know it needs to create this
    // CommandLineRunner -> functional interface with a callback (run method) that accepts arguments
    // passed to the app
    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> logger.info("[CLR] Args: {}", Arrays.toString(args));
    }

    // ApplicationRunner -> functional interface that has a callback (run method) that has
    // ApplicationArguments as a parameter (to make args usable);
    // this implementation gets called before CommandLineRunner
    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            logger.info("[AR] Option Args: {}", args.getOptionNames());
            logger.info("[AR] Option Arg Values: {}", args.getOptionValues("option"));
            logger.info("[AR] Non Option: {}", args.getNonOptionArgs());
        };
    }

    // ApplicationReadyEvent -> event that gets called when Spring Boot app has finished wiring
    // everything up and is ready to interact.
    // ApplicationListener<Event> -> Listens for the call of the specified Event
    @Bean
    ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(){
        return event -> logger.info("[AL] Locked and loaded...");
    }
}
//run this app with the following args: --enable --remote --option=value1 --option=value2
//update upgrade
//app will logg the arguments passed to SpringApplication.run()
//options are denoted by -- and some have = to attribute values
// plain parameters are non-option arguments

// This is actually a fun fact to remember: gradle creates an executable JAR in the buil/libs
//directory as a result of the command: " ./gradlew build "; then the jar can be run:
//java -jar build/libs/my-retro-0.0.1-SNAPSHOT.jar --maybe --some=1 --arguments=5 also

/*
    "Note that the ApplicationRunner is called first, then the CommandLineRunner, and
lastly the ApplicationReadyEvent. Now, you can have multiple CommandLineRunner
classes that implement the run method, and they must be marked as @Component and,
if needed, listed in order is required, the you can use the @Order annotation (with the
Ordered enum as a parameter depending of the precedence)."
 */