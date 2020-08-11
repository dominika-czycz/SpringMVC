package pl.coderslab.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@ComponentScan(basePackageClasses = AppConfig.class)
@EnableWebMvc
@EnableTransactionManagement
public class AppConfig {
}
