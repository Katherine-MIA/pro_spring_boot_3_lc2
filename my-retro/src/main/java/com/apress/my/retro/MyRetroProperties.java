package com.apress.my.retro;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

//Signals that the fields in this class should be binding to
// application.properties/.yaml properties values,
// provided that the fields have the same name as the properties ofc
@ConfigurationProperties(prefix="service")// spring can identify this class as a service
//Relaxed binding: any notation for the fields goes: camel case(fieldName), kebab case (field-name), underscore (field_name), uppercase (FIELD_NAME)
public class MyRetroProperties {
    Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}

class Users {
    String server;
    Integer port;
    String username;
    String password;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
