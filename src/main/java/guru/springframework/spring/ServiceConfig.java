package guru.springframework.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "guru.springframework.service" })
public class ServiceConfig {
}
