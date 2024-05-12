package com.example.imgdownload.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**") // URL, по которому будут доступны ваши файлы
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/main/upload/");

        // Добавляем стандартный путь к папке static.
        //Т.к тут переопределение, то стандартная настройка Spring Boot затирается. Придётся ручками вернуть.
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/",
                        "classpath:/static/", "classpath:/public/");
    }
}
