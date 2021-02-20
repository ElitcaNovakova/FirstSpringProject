package bg.startit.spring.firstspringproject;

import bg.startit.spring.firstspringproject.model.User;
import bg.startit.spring.firstspringproject.repository.UserRepository;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// In LoginTest, we are calling the Spring Application through the
// presentation->service->db layers.
//
// With this test, we will illustrate how we can replace the DB service
// inside Spring, with a mock object.
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest2 {

  @Autowired
  private MockMvc mvc;

  // This replaces the UserRepository in Spring context, with the
  // mock object initialized here.
  @MockBean
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void Login_with_admin_should_succeed() throws Exception {
    // in UserService Test, we didn't have to create a valid user
    // in our case, we need to set the password, because it is
    // validated by the Spring security filters, during login
    final User user = new User().setId(1);
    user.setUsername("admin");
    user.setPassword(passwordEncoder.encode("admin"));

    // setup user repository mock repository
    // this is almost identical to UserServiceTest
    when(userRepository.count()).thenReturn(0L);
    when(userRepository.findByUsername("admin"))
        .thenReturn(Optional.empty())     // first invocation
        .thenReturn(Optional.of(user));   // second invocation
    when(userRepository.save(any()))
        .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

    // perform the actual test
    mvc.perform(post("/login")
        .param("username", "admin")
        .param("password", "admin"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        // if not / then it probably redirects to error page
        // e.g '/login?error'
        .andExpect(redirectedUrl("/"));
  }
}

