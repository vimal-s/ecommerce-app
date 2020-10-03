package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String USERNAME = "testUser";

    @Test
    @WithMockUser
    void testSubmit() throws Exception {
        mvc.perform(post("/api/order/submit/" + USERNAME))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetOrdersForUser() throws Exception {
        mvc.perform(get("/api/order/history/" + USERNAME))
           .andDo(print())
           .andExpect(status().isOk());
    }
}
