package bg.startit.spring.firstspringproject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bg.startit.spring.firstspringproject.model.User;
import bg.startit.spring.firstspringproject.repository.UserRepository;
import bg.startit.spring.firstspringproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = userService.register("testUser", "ABCdef_123", "ABCdef_123");
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  @WithAnonymousUser
  void given_mismatching_passwords_When_register_user_Then_fail() throws Exception {
    mvc.perform(post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(
            "{\"username\":\"user1\",\"password\":\"ABcde_123\", \"passConfirmation\":\"ABcde_1234\"}"))
        .andDo(print())
        .andExpect(status().isInternalServerError());
  }

  // invalid password
  @Test
  @WithAnonymousUser
  void given_invalid_passwords_When_register_user_Then_fail() throws Exception {
    mvc.perform(post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(
            "{\"username\":\"user1\",\"password\":\"abc\", \"passConfirmation\":\"abc\"}"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithAnonymousUser
  void given_user_already_exists_When_register_user_Then_fail() throws Exception {
    mvc.perform(post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(
            "{\"username\":\"testUser\",\"password\":\"ABcde_123\", \"passConfirmation\":\"ABcde_123\"}"))
        .andExpect(status().isInternalServerError());
  }


  @Test
  @WithMockUser(roles = "USER")
  void given_valid_paging_When_list_users_Then_pass() throws Exception {
    // create 10 users
    for (int i = 0; i < 10; i++) {
      userService.register("list" + i, "ABCdef_123", "ABCdef_123");
    }

    mvc.perform(get("/api/v1/users")
        .queryParam("page", "1")
        .queryParam("size", "5"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.length()").value(5))
        .andExpect(jsonPath("$[*].username", Matchers.containsInAnyOrder("list4", "list5", "list6", "list7", "list8")));
    mvc.perform(get("/api/v1/users")
        .queryParam("size", "5"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.length()").value(5))
        .andExpect(jsonPath("$[*].username", Matchers.containsInAnyOrder("testUser","list0","list1","list2","list3")));
    mvc.perform(get("/api/v1/users")
        .queryParam("page", "1")
        .queryParam("size", "8"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.length()").value(3))
        .andExpect(jsonPath("$[*].username", Matchers.containsInAnyOrder("list7","list8","list9")));
    mvc.perform(get("/api/v1/users")
        .queryParam("page", "2")
        .queryParam("size", "8"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.length()").value(0));

    //    public List<UserResponse> listUsers(
//    @PositiveOrZero @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
//    @Min(5) @Max(100) @RequestParam(name = "size", required = false, defaultValue = "20") int pageSize) {

  }

  @Test
  @WithAnonymousUser
  void given_anonymous_user_When_list_users_Then_no_access() throws Exception {
    mvc.perform(get("/api/v1/users"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  void given_negative_page_When_list_users_Then_fails() throws Exception {
    mvc.perform(get("/api/v1/users")
        .queryParam("page", "-2"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "USER")
  void given_smaller_size_When_list_users_Then_fails() throws Exception {
    mvc.perform(get("/api/v1/users")
        .queryParam("size", "4"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "USER")
  void given_bigger_size_When_list_users_Then_fails() throws Exception {
    mvc.perform(get("/api/v1/users")
        .queryParam("size", "101"))
        .andExpect(status().isBadRequest());
  }



  @Test
  @WithAnonymousUser
  void given_anonymous_When_update_current_password_Then_fail_unauthorized() throws Exception {
    mvc.perform(post("/api/v1/users/current")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(
            "{\"oldPassword\":\"ABCdef_123\",\"newPassword\":\"ABCdef_1234\"}"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void given_admin_user_When_update_password_Then_succeed() throws Exception {
    mvc.perform(post("/api/v1/users/{userId}", testUser.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(
            "{\"oldPassword\":\"ABCdef_123\",\"newPassword\":\"ABCdef_1234\"}"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "USER")
  void given_regular_user_When_update_password_Then_fail_forbidden() throws Exception {
    mvc.perform(post("/api/v1/users/{userId}", testUser.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(
            "{\"oldPassword\":\"ABCdef_123\",\"newPassword\":\"ABCdef_1234\"}"))
        .andExpect(status().isUnauthorized());
  }
}