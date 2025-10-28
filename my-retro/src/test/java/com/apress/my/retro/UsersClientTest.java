package com.apress.my.retro;

import com.apress.my.retro.client.User;
import com.apress.my.retro.client.UsersClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UsersClientTest {
    @Autowired
    UsersClient usersClient;

    @Test
    void findUserTest() {
        User user = usersClient.findUserByEmail("norma@email.com");
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Norma");
        assertThat(user.getEmail()).isEqualTo("norma@email.com");
    }
}
