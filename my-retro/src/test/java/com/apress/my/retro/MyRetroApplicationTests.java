package com.apress.my.retro;

import com.apress.my.retro.service.RetroBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyRetroApplicationTests {
    @Autowired
    RetroBoardService service;

    @Test
	void contextLoads() {
        //todo: tests
	}

}
