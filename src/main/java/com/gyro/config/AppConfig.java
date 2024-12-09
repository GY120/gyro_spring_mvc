package com.gyro.config;
import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.gyro.interceptor.FileUploadInterceptor;
import com.gyro.interceptor.LoginInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.gyro")
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/myPages/");  // 视图路径
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();  // 设置会话级别的 Locale
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");  // 通过请求参数 "lang" 来改变语言
        return interceptor;
    }
    
    //可能导致只能上传一张图片
//    @Bean
//    public FileUploadInterceptor fileUploadInterceptor() {
//        FileUploadInterceptor interceptor = new FileUploadInterceptor();
//        interceptor.setMaxSize(1048576000);  // 设置最大上传文件大小为1000MB
//        return interceptor;
//    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
        registry.addInterceptor(localeChangeInterceptor());  // 注册 LocaleChangeInterceptor
        //可能除非统一异常抛出
//        registry.addInterceptor(fileUploadInterceptor())
//        .addPathPatterns("/fileupload", "/download");  // 只拦截文件上传和下载路径
        
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/main");  // 只拦截 /main 请求
    }

    // 配置 JSON 处理器（FastJson）
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        return new FastJsonHttpMessageConverter();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");  // 加载 messages.properties 文件
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(1000000); // 设置文件上传大小限制1m
     // 设置临时文件存储路径 
     // C:/Users/Administrator/eclipse-workspace2/.metadata/plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MVC/upload

        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
