package ua.itea;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.itea.config.AppConfig;
import ua.itea.parser.Parser;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context;
        Parser parser;
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            parser = context.getBean("parser", Parser.class);
            parser.parse();
        } catch (BeansException ignore) {
            System.out.println("Parser from xml setting not found");
        }
        try {
            context = new AnnotationConfigApplicationContext(AppConfig.class);
            String parserName = context.getBean(AppConfig.class).getParserName();
            parser = context.getBean(parserName, Parser.class);
            parser.parse();
        } catch (BeansException ignore) {
            System.out.println("Parser from properties setting not found");
        }
    }
}
