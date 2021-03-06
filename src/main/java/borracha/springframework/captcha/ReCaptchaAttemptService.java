package borracha.springframework.captcha;

public interface ReCaptchaAttemptService {

  void reCaptchaSucceeded(final String key);

  void reCaptchaFailed(final String key);

  boolean isBlocked(final String key);
}
