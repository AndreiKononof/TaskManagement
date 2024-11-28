package com.example.TaskManagement.configuration.openApi;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port}")
    private String port;

    @Bean
    public OpenAPI openAPI(){
        Server localhost = new Server();
        localhost.setUrl("http://localhost:"+port);
        localhost.setDescription("Local env");

        Contact contact = new Contact();
        contact.setName("Andrei Kononov");
        contact.setEmail("Andrecononoff@yandex.ru");
        contact.setUrl("http://yellow-cubes.online.");

        Info info = new Info();
        info.title("Приложение по управлению задачами")
                .version("1.0")
                .contact(contact)
                .description("Приложение создано для хранения и отслеживания задач. " +
                        "Позволяет систематизировать выдачу задач и отслеживать исполнение.");

        return new OpenAPI().info(info).servers(List.of(localhost));

    }

}
