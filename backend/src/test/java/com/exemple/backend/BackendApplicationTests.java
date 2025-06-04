package com.exemple.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@SpringBootTest
@AutoConfigureTestDatabase // Adicione esta anotação
class BackendApplicationTests {
    @Test
    void contextLoads() {
    }
}