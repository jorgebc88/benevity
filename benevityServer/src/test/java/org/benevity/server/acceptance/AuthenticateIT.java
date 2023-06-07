package org.benevity.server.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.benevity.server.PostsApplicationTest;
import org.benevity.server.openapi.model.AuthResponse;
import org.benevity.server.openapi.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.benevity.server.helper.FileHelper.fromFile;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = {PostsApplicationTest.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/data/posts/schema.sql", "/data/posts/insert.sql"})
public class AuthenticateIT {

    @LocalServerPort
    private String port;
    @Autowired
    private MockMvc mockMvc;
    private String url;
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        url = String.format("http://localhost:%s/", this.port);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void proper_user_flow() throws Exception {
        MvcResult createResult = mockMvc.perform(post(url + "users").contentType(APPLICATION_JSON)
                                                                    .content(fromFile("acceptance/NewUserAcceptance.json")))
                                        .andExpect(status().isCreated())
                                        .andReturn();
        UserDTO userResponse = this.mapper.readValue(createResult.getResponse().getContentAsString(), UserDTO.class);

        Assertions.assertEquals("jorgebc88", userResponse.getUsername());
        Assertions.assertEquals("email", userResponse.getEmail());
        Assertions.assertNotNull(userResponse.getId());
        Assertions.assertNull(userResponse.getPassword());

        MvcResult authResult = mockMvc.perform(post(url + "authenticate").contentType(APPLICATION_JSON)
                                                                         .content(fromFile("acceptance/AuthenticationAcceptance.json")))
                                      .andExpect(status().isOk())
                                      .andReturn();
        AuthResponse authResponse = this.mapper.readValue(authResult.getResponse().getContentAsString(), AuthResponse.class);

        MvcResult postsResult = mockMvc.perform(get(url + "posts").header("Authorization", "Bearer " + authResponse.getJwtToken())
                                                                  .contentType(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        assertNotNull(postsResult.getResponse().getContentAsString());
    }
}
