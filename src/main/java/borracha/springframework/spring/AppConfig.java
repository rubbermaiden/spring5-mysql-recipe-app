package borracha.springframework.spring;

import borracha.springframework.security.ActiveUserStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AppConfig {
  // beans

  @Bean
  public ActiveUserStore activeUserStore() {
    return new ActiveUserStore();
  }

}