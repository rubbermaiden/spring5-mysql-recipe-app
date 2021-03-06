package borracha.springframework.captcha;

import borracha.springframework.error.ReCaptchaInvalidException;
import borracha.springframework.error.ReCaptchaUnavailableException;
import java.net.URI;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

@Service("captchaService")
public class CaptchaServiceImpl implements CaptchaService {
  private final static Logger LOGGER = LoggerFactory.getLogger(CaptchaService.class);

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private CaptchaSettings captchaSettings;

  @Autowired
  private ReCaptchaAttemptServiceImpl reCaptchaAttemptServiceImpl;

  @Autowired
  private RestOperations restTemplate;

  private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

  @Override
  public void processResponse(final String response) {
    LOGGER.debug("Attempting to validate response {}", response);

    if (reCaptchaAttemptServiceImpl.isBlocked(getClientIP())) {
      throw new ReCaptchaInvalidException("Client exceeded maximum number of failed attempts");
    }

    if (!responseSanityCheck(response)) {
      throw new ReCaptchaInvalidException("Response contains invalid characters");
    }

    final URI verifyUri = URI.create(String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s", getReCaptchaSecret(), response, getClientIP()));
    try {
      final GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
      LOGGER.debug("Google's response: {} ", googleResponse.toString());

      if (!googleResponse.isSuccess()) {
        if (googleResponse.hasClientError()) {
          reCaptchaAttemptServiceImpl.reCaptchaFailed(getClientIP());
        }
        throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
      }
    } catch (RestClientException rce) {
      throw new ReCaptchaUnavailableException("Registration unavailable at this time.  Please try again later.", rce);
    }
    reCaptchaAttemptServiceImpl.reCaptchaSucceeded(getClientIP());
  }

  private boolean responseSanityCheck(final String response) {
    return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
  }

  @Override
  public String getReCaptchaSite() {
    return captchaSettings.getSite();
  }

  @Override
  public String getReCaptchaSecret() {
    return captchaSettings.getSecret();
  }

  private String getClientIP() {
    final String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      return request.getRemoteAddr();
    }
    return xfHeader.split(",")[0];
  }
}

