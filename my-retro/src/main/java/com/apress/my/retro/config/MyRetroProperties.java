package com.apress.my.retro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@SuppressWarnings(value = "unused")
//Signals that the fields in this class should be binding to
// application.properties/.yaml properties values,
// provided that the fields have the same name as the properties ofc
@ConfigurationProperties(prefix="service")// spring can identify this class as a service
//Relaxed binding: any notation for the fields goes: camel case(fieldName), kebab case (field-name), underscore (field_name), uppercase (FIELD_NAME)
@Data
public class MyRetroProperties {
    UsersProperties users;
}
