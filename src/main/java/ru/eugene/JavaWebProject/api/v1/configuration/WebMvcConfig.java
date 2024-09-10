package ru.eugene.JavaWebProject.api.v1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.eugene.JavaWebProject.api.v1.servlet.BearerTokenInterceptor;

// Класс для расширения конфигурации Spring WebMvc
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private BearerTokenInterceptor interceptor;

    // Добавляем кастомный перехватчик, который отвечает за обработку запросов по пути /users/**
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**/users/**");
    }
}