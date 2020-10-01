package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    // todo: see if you want to use jsonpath here
    @Test
    @WithMockUser
    void testGetItems() throws Exception {
        mvc.perform(get("/api/item"))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetItemById() throws Exception {
        long itemId = 1;
        mvc.perform(get("/api/item/" + itemId))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetItemsByName() throws Exception {
        String itemName = "Round Widget";
        mvc.perform(get("/api/item/name/" + itemName))
           .andDo(print())
           .andExpect(status().isOk());
    }
}
