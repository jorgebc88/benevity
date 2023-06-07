package org.benevity.server.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.benevity.server.PostsApplicationTest;
import org.benevity.server.openapi.model.PostResponse;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = {PostsApplicationTest.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/data/posts/schema.sql", "/data/posts/insert.sql"})
public class PostIT {

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
        url = String.format("http://localhost:%s/posts", this.port);
        validToken = "Bearer " + generateToken("1234", "username");
        validTokenOtherUser = "Bearer " + generateToken("123", "user");
        notValidTokenOtherUser = "Bearer " + generateToken("12", "us");
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void get_posts_wrong_token() throws Exception {
        mockMvc.perform(get(url).header("Authorization", "validToken").contentType(APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    void get_posts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(url).header("Authorization", validToken).contentType(APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andReturn();

        assertEquals(fromFile("acceptance/GetPostsAcceptance.json"), mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
    }

    @Test
    void get_post_by_id() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(url + "/UUID").header("Authorization", validToken).contentType(APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andReturn();

        assertEquals(fromFile("acceptance/GetPostAcceptance.json"), mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
    }

    @Test
    void add_new_post() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(url).header("Authorization", validToken)
                                                       .contentType(APPLICATION_JSON)
                                                       .content(fromFile("acceptance/NewPostAcceptance.json")))
                                     .andExpect(status().isCreated())
                                     .andReturn();
        PostResponse postResponse = this.mapper.readValue(mvcResult.getResponse().getContentAsString(), PostResponse.class);
        Assertions.assertEquals("name2", postResponse.getName());
        Assertions.assertEquals("title2", postResponse.getTitle());
        Assertions.assertEquals("content2", postResponse.getContent());
        Assertions.assertEquals("title2", postResponse.getSlug());
        Assertions.assertNull(postResponse.getImageUrl());
        Assertions.assertEquals("1234", postResponse.getUser().getId());
    }

    @Test
    void delete_post() throws Exception {
        mockMvc.perform(delete(url + "/UUID").header("Authorization", validToken)).andExpect(status().isOk());
    }

    @Test
    void delete_post_not_valid_user() throws Exception {
        mockMvc.perform(delete(url + "/UUID").header("Authorization", notValidTokenOtherUser)).andExpect(status().isUnauthorized());
    }

    @Test
    void delete_post_no_authorization() throws Exception {
        mockMvc.perform(delete(url + "/UUID")).andExpect(status().isUnauthorized());
    }

    @Test
    void delete_post_not_owner() throws Exception {
        mockMvc.perform(delete(url + "/UUID").header("Authorization", validTokenOtherUser)).andExpect(status().isUnauthorized());
    }
}
