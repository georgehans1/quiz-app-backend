package com.quizapp.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final OAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/","/login", "/logout", "/actuator/**").permitAll()
                            .anyRequest().authenticated();
                })
//                .headers(headers -> {
//                    headers
//                            .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
//                            .xssProtection(HeadersConfigurer.XXssConfig::disable);
//                })
//                .addFilterAfter(new SessionCookieFilter(), BasicAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2.
                        userInfoEndpoint(
                                infoEndpoint -> infoEndpoint.oidcUserService(oAuth2UserService))
                        .defaultSuccessUrl("https://hans-quizapp.web.app/dashboard"))
                .exceptionHandling(handler -> handler.defaultAuthenticationEntryPointFor((request,response, authenticationException) ->{
                    log.info("Unauthorized User");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }, AnyRequestMatcher.INSTANCE))
                .logout(handler -> {
                    handler.logoutUrl("/logout").permitAll();
                    handler.clearAuthentication(true);
                    handler.invalidateHttpSession(true);
                    handler.deleteCookies("JSESSIONID");
                })
                .build();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID");
        serializer.setDomainName("https://quiz-app-backend-uwt7.onrender.com");
        serializer.setCookiePath("/");
        serializer.setSameSite("None");
        serializer.setUseSecureCookie(true); // Set to true for HTTPS
        return serializer;
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (serverFactory) -> serverFactory.addContextCustomizers(
                (context) -> context.setCookieProcessor(new Rfc6265CookieProcessor()));
    }

    private static CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200","https://hans-quizapp.web.app","https://hans-quizapp.firebaseapp.com"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}

