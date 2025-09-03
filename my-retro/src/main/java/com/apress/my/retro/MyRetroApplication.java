package com.apress.my.retro;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

//"When the MyretroApplication.java class is executed by the SpringApplication.run call,
// it triggers the auto-configuration feature." This class looks for any class that has @Configuration annotation
//and the arguments (that get passed from the commandline at execution)
//It is passing MyRetroApplication.class because the annotation on the class @SpringBootApplication contains
//@SpringBootConfiguration (which is still a @Configuration annotation). This annotation in turn searches for any
//@Bean declarations that exist in the class
@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
public class MyRetroApplication {

	public static void main(String[] args) {

        SpringApplication.run(MyRetroApplication.class, args);
        //This is another way to initialize and run a spring application and by creating an instance of
        //SpringApplication you can also add features using its list of non-static methods.
        //SpringApplication springApp = new SpringApplication(MyRetroApplication.class);
        //Replace Spring banner with a custom one
        //springApp.setBanner(new Banner() {
        //    @Override
        //    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        //        out.println("\n\n\tThis is Kat Banner!\n\n".toUpperCase());
        //    }
        //});
        //Or construct a new one in a .txt file and set it as banner replacement in the .proerties file
        //Or get rid of banner completely:
        //springApp.setBannerMode(Banner.Mode.OFF);
        //Spring Application features
        //springApp.run(args);
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