package com.example.demo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.model.requests.ModifyCartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CartControllerTest {

    private final String USERNAME = "testUser";

    @Autowired
    private MockMvc mvc;

    private ModifyCartRequest createCartRequest() {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setItemId(2);
        cartRequest.setQuantity(1);
        cartRequest.setUsername(USERNAME);
        return cartRequest;
    }

    @Test
    @WithMockUser
    void testAddToCart() throws Exception {
        ModifyCartRequest cartRequest = createCartRequest();

        mvc.perform(
                post("/api/cart/addToCart")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(cartRequest)))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testRemoveFromCart() throws Exception {
        ModifyCartRequest cartRequest = createCartRequest();

        mvc.perform(
                post("/api/cart/removeFromCart")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(cartRequest)))
           .andDo(print())
           .andExpect(status().isOk());
    }
}
