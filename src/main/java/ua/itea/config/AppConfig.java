package ua.itea.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ua.itea.parser")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${parser.name}")
    private String parserName;

    public String getParserName() {
        return parserName;
    }
}
