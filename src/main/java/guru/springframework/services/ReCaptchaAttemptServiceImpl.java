package guru.springframework.services;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class ReCaptchaAttemptServiceImpl implements ReCaptchaAttemptService{
  private final int MAX_ATTEMPT = 4;
  private LoadingCache<String, Integer> attemptsCache;

  public ReCaptchaAttemptServiceImpl() {
    super();
    attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(4, TimeUnit.HOURS).build(new CacheLoader<String, Integer>() {
      @Override
      public Integer load(final String key) {
        return 0;
      }
    });
  }

  @Override
  public void reCaptchaSucceeded(final String key) {
    attemptsCache.invalidate(key);
  }

  @Override
  public void reCaptchaFailed(final String key) {
    int attempts = attemptsCache.getUnchecked(key);
    attempts++;
    attemptsCache.put(key, attempts);
  }

  @Override
  public boolean isBlocked(final String key) {
    return attemptsCache.getUnchecked(key) >= MAX_ATTEMPT;
  }

}
