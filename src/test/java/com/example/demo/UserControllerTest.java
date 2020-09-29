package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private ResultActions resultActions;

    private final String username = "testUser";

    private ResultActions createUser() throws Exception {
        String requestBody = "{\"username\": \"" + username + "\"}";

        return
                mvc.perform(
                        post("/api/user/create")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(requestBody));
    }

    @BeforeEach
    void setUp() throws Exception {
        resultActions = createUser();
    }

    @Test
    void testCreateUser() throws Exception {
        resultActions
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        mvc.perform(get("/api/user/id/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testFindByUsername() throws Exception {
        mvc.perform(get("/api/user/" + username))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
