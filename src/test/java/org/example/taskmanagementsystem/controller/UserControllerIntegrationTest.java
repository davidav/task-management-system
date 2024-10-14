package org.example.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.taskmanagementsystem.dto.StatusCode;
import org.example.taskmanagementsystem.dto.user.UserRq;
import org.example.taskmanagementsystem.entity.RoleType;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

import static org.hamcrest.Matchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "dev")
public class UserControllerIntegrationTest {

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

//    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
//    void testFindAllByAdminSuccess() throws Exception {
//        mockMvc.perform(get(baseUrl + "/user")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .header("Authorization", tokenAdmin))
//                .andExpect(jsonPath("$.flag").value(true))
//                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
//                .andExpect(jsonPath("$.message").value("Found all"))
//                .andExpect(jsonPath("$.data[0].username").value("admin"))
//                .andExpect(jsonPath("$.data[1].username").value("user"))
//                .andExpect(jsonPath("$.data", Matchers.hasSize(2)));
//    }

    @Test
    void testFindAllByUserFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenUser))
                .andDo(print())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.FORBIDDEN))
                .andExpect(jsonPath("$.message").value("No permission"));
    }

    @Test
    void testFindAllNoLoginFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.FORBIDDEN))
                .andExpect(jsonPath("$.message").value("No permission"));
    }

    @Test
    void testFindByIdByAdminSuccess() throws Exception {
        mockMvc.perform(get(baseUrl + "/user/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Found one"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.username").value("admin"));
    }

    @Test
    void testFindByIdByUserFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/user/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenUser))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.FORBIDDEN))
                .andExpect(jsonPath("$.message").value("No permission"));
    }

    @Test
    void testFindByIdNoLoginFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/user/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.FORBIDDEN))
                .andExpect(jsonPath("$.message").value("No permission"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void testFindByIdFail() throws Exception {
        mockMvc.perform(get(baseUrl + "/user/3")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("User with id 3 not found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testCreateByAdminSuccess() throws Exception {
        UserRq rq = new UserRq("user1", "user1@mail.com", "user1", Set.of(RoleType.ROLE_USER));

        mockMvc.perform(post(baseUrl + "/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.timestamp").value(any(String.class)))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("User created"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.username").value("user1"))
                .andExpect(jsonPath("$.data.email").value("user1@mail.com"))
                .andExpect(jsonPath("$.data.roles").value("ROLE_USER"))
                .andExpect(jsonPath("$.data.password").doesNotExist());
        mockMvc.perform(get(baseUrl + "/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.data", Matchers.hasSize(3)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testCreateByAdminFail() throws Exception {
        UserRq fakeRq = new UserRq("", "use", "", null);

        mockMvc.perform(
                        post(baseUrl + "/user")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(fakeRq))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.timestamp").value(any(String.class)))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are not valid"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.username").value("Username must be from 3 to 10 symbols"))
                .andExpect(jsonPath("$.data.email").value("The email address must be in the format user@example.com"))
                .andExpect(jsonPath("$.data.password").value("The password length must be from 4 no more than 255 characters."))
                .andExpect(jsonPath("$.data.roles").value("RoleType must not be null"));

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdateByAdminSuccess() throws Exception {

        UserRq rq = new UserRq("userUp", "userUp@mail.com", "userUp", Set.of(RoleType.ROLE_USER));

        this.mockMvc.perform(put(baseUrl + "/user/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq))
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update success"))
                .andExpect(jsonPath("$.data.username").value("userUp"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdateFail() throws Exception {

        UserRq rq = new UserRq("", "", "", Set.of());

        this.mockMvc.perform(put(baseUrl + "/user/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq))
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are not valid"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.username").value("Username must be from 3 to 10 symbols"))
                .andExpect(jsonPath("$.data.password").value("The password length must be from 4 no more than 255 characters."))
                .andExpect(jsonPath("$.data.email").value("Email address cannot be empty"))
                .andExpect(jsonPath("$.data.roles").value("RoleType must not be null"));
    }

//    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
//    void testDeleteByIdSuccess() throws Exception {
//
//
//        this.mockMvc.perform(delete(baseUrl + "/user/2")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .header("Authorization", tokenAdmin))
//                .andExpect(jsonPath("$.flag").value(true))
//                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
//                .andExpect(jsonPath("$.message").value("Delete success"))
//                .andExpect(jsonPath("$.data").isEmpty());
//    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void testDeleteByIdFail() throws Exception {

        this.mockMvc.perform(delete(baseUrl + "/user/3")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenAdmin))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("user not found"))
                .andExpect(jsonPath("$.data").isEmpty());

    }

}
