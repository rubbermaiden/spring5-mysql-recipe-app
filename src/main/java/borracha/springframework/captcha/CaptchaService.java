package borracha.springframework.captcha;

import borracha.springframework.error.ReCaptchaInvalidException;

public interface CaptchaService {
  void processResponse(final String response) throws ReCaptchaInvalidException;

  String getReCaptchaSite();

  String getReCaptchaSecret();
}