package ru.focusstart.tomsk;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AutoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AutoApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
