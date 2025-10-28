package com.apress.my.retro.config;

import lombok.Data;

// Called it UsersProperties instead of UsersConfiguration
@Data
public class UsersProperties {
    String server;
    Integer port;
    String username;
    String password;
}
