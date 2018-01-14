package guru.springframework.captcha;

import guru.springframework.error.ReCaptchaInvalidException;

public interface CaptchaService {
  void processResponse(final String response) throws ReCaptchaInvalidException;

  String getReCaptchaSite();

  String getReCaptchaSecret();
}