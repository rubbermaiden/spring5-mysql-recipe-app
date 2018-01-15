package borracha.springframework.service;

import borracha.springframework.commands.UserCommand;
import borracha.springframework.domain.PasswordResetToken;
import borracha.springframework.domain.User;

import borracha.springframework.domain.VerificationToken;
import borracha.springframework.error.UserAlreadyExistException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
  String TOKEN_INVALID = "invalidToken";
  String TOKEN_EXPIRED = "expired";
  String TOKEN_VALID = "valid";

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
