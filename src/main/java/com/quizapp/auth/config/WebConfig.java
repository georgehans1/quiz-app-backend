package com.quizapp.auth.config;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
//    public CookieSameSiteSupplier cookieSameSiteSupplier(){
//        return CookieSameSiteSupplier.ofNone();
//    }

    //    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setCookieName("JSESSIONID");
//        serializer.setDomainName("https://quiz-app-backend-uwt7.onrender.com");
//        serializer.setCookiePath("/");
//        serializer.setSameSite("None");
//        serializer.setUseSecureCookie(true); // Set to true for HTTPS
//        return serializer;
//    }

//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
//        return (serverFactory) -> serverFactory.addContextCustomizers(
//                (context) -> context.setCookieProcessor(new Rfc6265CookieProcessor()));
//    }
}