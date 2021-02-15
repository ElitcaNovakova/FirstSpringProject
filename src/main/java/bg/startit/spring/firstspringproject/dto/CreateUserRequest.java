package bg.startit.spring.firstspringproject.dto;

import bg.startit.spring.firstspringproject.validation.ValidPassword;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserRequest {

  @NotNull
  @Size(min = 5, max = 10)
  @Pattern(regexp = "[a-zA-Z0-9]+")
  private String username;

  @ValidPassword
  private String password;

  @ValidPassword
  private String passConfirmation;


  public String getUsername() {
    return username;
  }

  public CreateUserRequest setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public CreateUserRequest setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getPassConfirmation() {
    return passConfirmation;
  }

  public CreateUserRequest setPassConfirmation(String passConfirmation) {
    this.passConfirmation = passConfirmation;
    return this;
  }
}
