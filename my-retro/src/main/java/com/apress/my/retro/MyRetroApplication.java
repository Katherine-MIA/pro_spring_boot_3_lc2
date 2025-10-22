package com.apress.my.retro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
// Must suppress warnings before every push or else sonar qube loses it, and in turn, makes me lose it as well.
// But noting that these projects are meant to document my progress through the book and leave some references and
// explanations for a confused and in trouble future me, who might very much benefit from a recap.
//I do agree with sonar that this is a preposterous way to write code in general though.
@SuppressWarnings("all")
//"When the MyretroApplication.java class is executed by the SpringApplication.run call,
// it triggers the auto-configuration feature." This class looks for any class that has @Configuration annotation
//and the arguments (that get passed from the commandline at execution)
//It is passing MyRetroApplication.class because the annotation on the class @SpringBootApplication contains
//@SpringBootConfiguration (which is still a @Configuration annotation). This annotation in turn searches for any
//@Bean declarations that exist in the class
@SpringBootApplication
// Removed -> (exclude = {WebMvcAutoConfiguration.class}) When WebMvc goes brr maybe worth checking this too
// just in case I forgot I excluded it... x_X
public class MyRetroApplication {

    static Logger logger = LoggerFactory.getLogger(MyRetroApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(MyRetroApplication.class, args);
        // This is another way to initialize and run a spring application and by creating an instance of
        // SpringApplication you can also add features using its list of non-static methods.
        // SpringApplication springApp = new SpringApplication(MyRetroApplication.class);
        // Replace Spring banner with a custom one
        //springApp.setBanner(new Banner() {
        //    @Override
        //    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        //        out.println("\n\n\tThis is Kat Banner!\n\n".toUpperCase());
        //    }
        //});
        // Or construct a new one in a .txt file and set it as banner replacement in the .proerties file
        // Or get rid of banner completely:
        //springApp.setBannerMode(Banner.Mode.OFF);
        // Spring Application features
        //springApp.run(args);

        /*
        // Another way to set up and run a custom spring application is using SpringApplicationBuilder,
        // (by spring Builder API), for example:

        new SpringApplicationBuilder()
                // this is where all components and necessary configuration classes are added (@Component, @Configuration, @Service, etc)
                .sources(MyRetroApplication.class)
                // prints to console all the logging information about the startup if set to true
                .logStartupInfo(false)
                // this is the see banner method already talked about above
                // .CONSOLE -> read argument from console
                .bannerMode(Banner.Mode.CONSOLE) //I like the banner too much to exclude it, thx https://www.patorjk.com/
                // specifies that beans will be created only when needed, this is usually by default false which means all beans will be initialised at startup
                .lazyInitialization(true)
                // specifies the type of the application to be web, also receives types so: WebApplication
                // .NONe -> "application should not run as a web application and should not start an embedded web server"
                // .SERVLET -> "application should run as a servlet-based web app and start an embedded servlet web server" (automatically set by spring-boot-starter-web)
                // .REACTIVE -> " application should run as a reactive web application and should start an embedded reactive web server"
                // app will only work if it has spring-boot-starter-webflux dependency/classpath
                .web(WebApplicationType.NONE)
                // used to specify all profiles that can be used (receives a list of strings)
                .profiles("cloud")
                // for defining spring Events, here the app listens for all implementations of ApplicationListener
                .listeners(event -> logger.info("Event: {}", event.getClass().getCanonicalName()))
                // use arguments passed to the program from the console
                .run("--spring.banner.location=classpath:/META-INF/banner.txt");

         */
    }
}
//@EnableAutoConfiguration reads META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports file,
//then imports and executes all classes that have a name ending with AutoConfiguration (so based on a naming convention)
//this identifies any defaults and sets everything up. The file is part of spring-boot-starter which is packaged into
//the spring-boot-autoconfigure.jar dependency

/* In order to exclude default configurations for spring boot application use the annotation:
 * @SpringBootApplication(exclude = {<whatever_tool>AutoConfiguration.class})
 * When using Spring Framework alone, in order to call autoconfiguration for any spring technology you can use:
 * @Enable<Technology> (eg. @EnableRabbit, @EnableWebSecurity, etc)
 * With spring boot all the technologies that have AutoConfigure classes will be called and used for setup,
 * most through the use of spring-boot-starter-<indicator_of_that_technology>.
 * Extra: For dependencies it doesn't have (build.gradle,pom.xml) "Did not match:
 * @ConditionalOnClass did not find required class..." (they are hardcoded to autoconfigure,
 * and they don't because they are not included in the project). For specified exclusions:
 * "Exclusions: org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration..."
 */