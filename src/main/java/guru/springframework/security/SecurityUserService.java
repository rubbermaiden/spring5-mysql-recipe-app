package guru.springframework.security;

public interface SecurityUserService {

  String validatePasswordResetToken(long id, String token);

}
