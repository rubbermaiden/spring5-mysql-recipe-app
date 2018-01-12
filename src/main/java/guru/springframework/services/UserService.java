package guru.springframework.services;

import guru.springframework.commands.UserCommand;
import guru.springframework.domain.PasswordResetToken;
import guru.springframework.domain.User;

import guru.springframework.domain.VerificationToken;
import guru.springframework.error.UserAlreadyExistException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

  User registerNewUserAccount(UserCommand accountDto) throws UserAlreadyExistException;

  User getUser(String verificationToken);

  void saveRegisteredUser(User user);

  void deleteUser(User user);

  void createVerificationTokenForUser(User user, String token);

  void deleteVerificationToken(String token);

  VerificationToken getVerificationToken(String VerificationToken);

  VerificationToken generateNewVerificationToken(String token);

  void createPasswordResetTokenForUser(User user, String token);

  User findUserByEmail(String email);

  PasswordResetToken getPasswordResetToken(String token);

  User getUserByPasswordResetToken(String token);

  void deletePasswordResetToken(User user);

  User getUserByID(Long id);

  void changeUserPassword(User user, String password);

  boolean checkIfValidOldPassword(User user, String password);

  String validateVerificationToken(String token);

  String generateQRUrl(User user) throws UnsupportedEncodingException;

  User updateUser2FA(boolean use2FA);

  List<String> getUsersFromSessionRegistry();

}
