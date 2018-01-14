package guru.springframework.commands;

import guru.springframework.validation.PasswordMatches;
import guru.springframework.validation.ValidEmail;
import guru.springframework.validation.ValidPassword;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@PasswordMatches
public class UserCommand {
  @NotNull
  @Size(min = 1)
  private String firstName;

  @NotNull
  @Size(min = 1)
  private String lastName;

  @ValidPassword
  private String password;

  @NotNull
  @Size(min = 1)
  private String matchingPassword;

  @ValidEmail
  @NotNull
  @Size(min = 1)
  private String email;

  @ColumnDefault("false")
  private boolean isUsing2FA;

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  private Integer role;

  public Integer getRole() {
    return role;
  }

  public void setRole(final Integer role) {
    this.role = role;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getMatchingPassword() {
    return matchingPassword;
  }

  public void setMatchingPassword(final String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }

  public boolean isUsing2FA() {
    return isUsing2FA;
  }

  public void setUsing2FA(boolean isUsing2FA) {
    this.isUsing2FA = isUsing2FA;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("UserCommand [firstName=").append(firstName).append(", lastName=").append(lastName).append(", password=").append(password).append(", matchingPassword=").append(matchingPassword).append(", email=").append(email).append(", isUsing2FA=")
        .append(isUsing2FA).append(", role=").append(role).append("]");
    return builder.toString();
  }

}

