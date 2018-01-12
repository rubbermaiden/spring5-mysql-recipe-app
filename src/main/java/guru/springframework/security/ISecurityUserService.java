package guru.springframework.security;

public interface ISecurityUserService {

  String validatePasswordResetToken(long id, String token);

}
