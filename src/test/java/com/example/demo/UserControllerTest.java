package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String USERNAME = "testUser";
    private final String PASSWORD = "testPassword";

    private CreateUserRequest createUserRequest() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("testPassword2");
        userRequest.setUsername("testUser2");
        userRequest.setConfirmPassword("testPassword2");
        return userRequest;
    }

    // todo: performing a post request, still WithMockUser annotation not required. why?
    @BeforeEach
    void setUp() throws Exception {
//        resultActions = createUser();
    }

    @Test
    void testCreateUser() throws Exception {
        CreateUserRequest userRequest = createUserRequest();

        mvc.perform(
                post("/api/user/create")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testLogin() throws Exception {
        String requestBody = "{\"username\": \"" + USERNAME + "\", \"password\": \"" + PASSWORD + "\"}";

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testFindById() throws Exception {
        mvc.perform(get("/api/user/id/" + 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testFindByUsername() throws Exception {
        mvc.perform(get("/api/user/" + USERNAME))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
