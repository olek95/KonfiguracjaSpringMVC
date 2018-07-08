package spittr.web;

import org.springframework.boot.SpringApplication;
import spittr.config.WebConfig;

public class SpitterApplication {
    public static void main(String[] args) {
        // Umo¿liwia uruchomienie strony internetowej. 
        SpringApplication.run(WebConfig.class, args);
    }
}
