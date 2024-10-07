package org.example.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.taskmanagementsystem.dto.StatusCode;
import org.example.taskmanagementsystem.dto.comment.CommentRq;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${api.endpoint.base-url}")
    String baseUrl;
    String tokenAdmin;
    String tokenUser;

    @BeforeEach
    void setUp() throws Exception {
        ResultActions resultActionsAdmin = mockMvc.perform(post(baseUrl + "/user/login")
                .with(httpBasic("admin", "admin")));
        MvcResult mvcResultAdmin = resultActionsAdmin.andDo(print()).andReturn();
        String contentAsStringAdmin = mvcResultAdmin.getResponse().getContentAsString();
        JSONObject jsonAdmin = new JSONObject(contentAsStringAdmin);
        tokenAdmin = "Bearer " + jsonAdmin.getJSONObject("data").getString("token");

        ResultActions resultActionsUser = mockMvc.perform(post(baseUrl + "/user/login")
                .with(httpBasic("user", "user")));
        MvcResult mvcResultUser = resultActionsUser.andDo(print()).andReturn();
        String contentAsStringUser = mvcResultUser.getResponse().getContentAsString();
        JSONObject jsonUser = new JSONObject(contentAsStringUser);
        tokenUser = "Bearer " + jsonUser.getJSONObject("data").getString("token");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void testFindAllByAdminSuccess() throws Exception {
        mockMvc.perform(get(baseUrl + "/comment")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find all success"))
                .andExpect(jsonPath("$.data[0].comment").value("Comment1"))
                .andExpect(jsonPath("$.data[1].comment").value("Comment2"))
                .andExpect(jsonPath("$.data[2].comment").value("Comment3"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(4)));
    }

    @Test
    void testFindAllByUserSuccess() throws Exception {
        mockMvc.perform(get(baseUrl + "/comment")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenUser))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find all success"))
                .andExpect(jsonPath("$.data[0].comment").value("Comment1"))
                .andExpect(jsonPath("$.data[1].comment").value("Comment2"))
                .andExpect(jsonPath("$.data[2].comment").value("Comment3"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(4)));
    }

    @Test
    void testFindAllNoLoginFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/comment")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.FORBIDDEN))
                .andExpect(jsonPath("$.message").value("No permission"));
    }

    @Test
    void testFindByIdByAdminSuccess() throws Exception {
        mockMvc.perform(get(baseUrl + "/comment/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find one success"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.comment").value("Comment2"));
    }

    @Test
    void testFindByIdByUserSuccess() throws Exception {
        mockMvc.perform(get(baseUrl + "/comment/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenUser))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find one success"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.comment").value("Comment2"));
    }

    @Test
    void testFindByIdNoLoginFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/comment/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.FORBIDDEN))
                .andExpect(jsonPath("$.message").value("No permission"));
    }

    @Test
    void testFindByIdFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/comment/14")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Comment with id 14 not found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void testCreateByAdminSuccess() throws Exception {

        CommentRq rq = new CommentRq("Comment1", 1L, 1L);

        this.mockMvc.perform(post(baseUrl + "/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq))
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Create success"))
                .andExpect(jsonPath("$.data.comment").exists())
                .andExpect(jsonPath("$.data.comment").value("Comment1"));
    }

    @Test
    void testCreateByAdminFail() throws Exception {
        CommentRq rq = new CommentRq("", null, null);

        this.mockMvc.perform(post(baseUrl + "/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq))
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.timestamp").value(Matchers.any(String.class)))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are not valid"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.comment").value("Length must be from 3 to 30"))
                .andExpect(jsonPath("$.data.authorId").value("author id required"))
                .andExpect(jsonPath("$.data.taskId").value("task id required"));
    }

    @Test
    void testUpdateByAdminSuccess() throws Exception {
        CommentRq rq = new CommentRq("CommentUp", 1L, 1L);

        this.mockMvc.perform(put(baseUrl + "/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq))
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update success"))
                .andExpect(jsonPath("$.data.comment").value("CommentUp"));
    }

    @Test
    void testUpdateByUserFail() throws Exception {

        CommentRq rq = new CommentRq("", null, null);

        this.mockMvc.perform(put(baseUrl + "/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq))
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenUser))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are not valid"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.comment").value("Length must be from 3 to 30"))
                .andExpect(jsonPath("$.data.authorId").value("author id required"))
                .andExpect(jsonPath("$.data.taskId").value("task id required"));
    }

    @Test
    void testDeleteByIdByAdminSuccess() throws Exception {

        this.mockMvc.perform(delete(baseUrl + "/comment/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete success"))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testDeleteByIdByAdminFail() throws Exception {

        this.mockMvc.perform(delete(baseUrl + "/comment/14")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("comment not found"))
                .andExpect(jsonPath("$.data").isEmpty());

    }
}
