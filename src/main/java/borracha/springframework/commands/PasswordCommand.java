package borracha.springframework.commands;

import borracha.springframework.validation.ValidPassword;
import borracha.springframework.validation.ValidPassword;

public class PasswordCommand {

  private String oldPassword;

  @ValidPassword
  private String newPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

}