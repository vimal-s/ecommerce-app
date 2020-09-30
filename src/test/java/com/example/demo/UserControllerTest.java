package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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

    private final String USERNAME = "testUser";
    private final String PASSWORD = "testPassword";
    private final String REQUEST_BODY =
            "{\"username\": \"" + USERNAME + "\", \"password\": \"" + PASSWORD + "\"}";

    // todo: replace this with pre created user in the test database
    private ResultActions createUser() throws Exception {
        return
                mvc.perform(
                        post("/api/user/create")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(REQUEST_BODY));
    }

    // todo: performing a post request, still WithMockUser annotation not required. why?
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
    void testLogin() throws Exception {
        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(REQUEST_BODY))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testFindById() throws Exception {
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        User user = new ObjectMapper().readValue(responseBody, User.class);

        mvc.perform(get("/api/user/id/" + user.getId()))
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
