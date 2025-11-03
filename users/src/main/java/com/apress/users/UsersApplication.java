package com.apress.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* When running the app on localhost:8080/h2-console, check that
 * JDBC URL value is jdbc:h2:mem:test-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
 * For localhost:8080/users -> it brings all users in db in json format.
 */
@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}
