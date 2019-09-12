package ru.otus.spring.homework0105;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring.homework0105.service.QuizRunner;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        quizRunner.startQuiz();
    }
}
