package borracha.springframework.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "borracha.springframework.service" })
public class ServiceConfig {
}
