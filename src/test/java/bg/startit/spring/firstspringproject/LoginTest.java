package bg.startit.spring.firstspringproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
  @Autowired
  private MockMvc mvc;

  @Test
  public void Login_with_admin_should_succeed() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/login")
        .param("username", "admin")
        .param("password", "admin"))
        .andDo(MockMvcResultHandlers.print());
  }
}
