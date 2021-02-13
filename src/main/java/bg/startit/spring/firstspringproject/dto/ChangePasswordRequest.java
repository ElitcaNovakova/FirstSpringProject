package bg.startit.spring.firstspringproject.dto;

public class ChangePasswordRequest {
  private  String oldPassword;
  private  String newPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public ChangePasswordRequest setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
    return this;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public ChangePasswordRequest setNewPassword(String newPassword) {
    this.newPassword = newPassword;
    return this;
  }
}
