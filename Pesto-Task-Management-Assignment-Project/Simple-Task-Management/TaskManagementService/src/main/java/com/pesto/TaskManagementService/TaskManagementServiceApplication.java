package com.pesto.TaskManagementService;

import com.pesto.TaskManagementService.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@SpringBootApplication
@EnableFeignClients
public class TaskManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementServiceApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean<JwtFilter> filterUrl() {
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtFilter());

        filterRegistrationBean.addUrlPatterns("/api/v1/user/*");

        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }
}
