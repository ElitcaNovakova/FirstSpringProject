package bg.startit.spring.firstspringproject.dto;

public class CreateUserRequest {

  private String username;
  private String password;
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
