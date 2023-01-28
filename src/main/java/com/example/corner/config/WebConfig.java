package com.example.corner.config;

import com.example.corner.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Time : 2023/1/6-22:22
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *配置拦截器*
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
