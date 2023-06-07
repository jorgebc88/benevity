package org.benevity.server.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.benevity.server.PostsApplicationTest;
import org.benevity.server.openapi.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.benevity.server.helper.AuthenticationUtils.generateToken;
import static org.benevity.server.helper.FileHelper.fromFile;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = {PostsApplicationTest.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/data/posts/schema.sql", "/data/posts/insert.sql"})
public class UserIT {

    @LocalServerPort
    private String port;
    @Autowired
    private MockMvc mockMvc;
    private String url;
    private String validToken;
    private String validTokenOtherUser;
    private String notValidTokenOtherUser;
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        url = String.format("http://localhost:%s/users", this.port);
        validToken = "Bearer " + generateToken("1234", "username");
        validTokenOtherUser = "Bearer " + generateToken("123", "user");
        notValidTokenOtherUser = "Bearer " + generateToken("12", "us");
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void get_users_wrong_token() throws Exception {
        mockMvc.perform(get(url).header("Authorization", notValidTokenOtherUser).contentType(APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    void get_users() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(url).header("Authorization", validToken).contentType(APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andReturn();

        assertEquals(fromFile("acceptance/GetUsersAcceptance.json"), mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
    }

    @Test
    void get_user_by_id() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(url + "/1234").header("Authorization", validToken).contentType(APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andReturn();

        assertEquals(fromFile("acceptance/GetUserAcceptance.json"), mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
    }

    @Test
    void add_new_user() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(url).header("Authorization", validToken)
                                                       .contentType(APPLICATION_JSON)
                                                       .content(fromFile("acceptance/NewUserAcceptance.json")))
                                     .andExpect(status().isCreated())
                                     .andReturn();
        UserDTO userResponse = this.mapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        Assertions.assertEquals("jorgebc88", userResponse.getUsername());
        Assertions.assertEquals("email", userResponse.getEmail());
        Assertions.assertNotNull(userResponse.getId());
        Assertions.assertNull(userResponse.getPassword());
    }

    @Test
    void update_user() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put(url + "/1234").header("Authorization", validToken)
                                                                .contentType(APPLICATION_JSON)
                                                                .content(fromFile("acceptance/NewUserAcceptance.json")))
                                     .andExpect(status().isOk())
                                     .andReturn();
        UserDTO userResponse = this.mapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        Assertions.assertEquals("jorgebc88", userResponse.getUsername());
        Assertions.assertEquals("email", userResponse.getEmail());
        Assertions.assertEquals("1234", userResponse.getId());
        Assertions.assertNull(userResponse.getPassword());
    }

    @Test
    void delete_user() throws Exception {
        mockMvc.perform(delete(url + "/1234").header("Authorization", validTokenOtherUser)).andExpect(status().isOk());
    }

    @Test
    void delete_userlogged_error() throws Exception {
        mockMvc.perform(delete(url + "/1234").header("Authorization", validToken)).andExpect(status().isUnauthorized());
    }
}
