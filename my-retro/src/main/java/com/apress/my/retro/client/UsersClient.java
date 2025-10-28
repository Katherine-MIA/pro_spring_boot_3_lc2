package com.apress.my.retro.client;

import com.apress.my.retro.config.MyRetroProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@SuppressWarnings("unused")
@Component
@AllArgsConstructor
public class UsersClient {

    private final String USERS_URL = "/users";
    // Creates a connection, deals with exceptions and provides methods for getting the expected object from a request.
    private final RestTemplate restTemplate = new RestTemplate();
    // These are specified as ConfigurationProperties to be found in a properties file with the prefix "service"
    private MyRetroProperties myRetroProperties;

    // Constructs the uri (path) the request will be made on. Then sends it with Users.class as parameter,
    // in order to specify what (type of object) it expects to receive. Doesn't retrieve the password.
    public User findUserByEmail(String email) {
        String uri = MessageFormat.format("{0}:{1,number,#}{2}/{3}",
                myRetroProperties.getUsers().getServer(),
                myRetroProperties.getUsers().getPort(),
                USERS_URL, email); // the myRetroProperties.getUsers().getPort().toString() -> is called already by the
        // formatter; there's no need to call toString(), says Sonar Qube. But formatter adds separator for big numbers
        // so either call .toString() on the object (in your face Sonar Qube!) or specify to the formatter that
        // you want a number, hence -> {1,number,#}.
        return restTemplate.getForObject(uri, User.class);
    }
}
