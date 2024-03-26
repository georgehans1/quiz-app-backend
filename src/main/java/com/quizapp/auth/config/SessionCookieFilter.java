package com.quizapp.auth.config;


import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SessionCookieFilter extends GenericFilterBean {

    private final List<String> PATHS_TO_IGNORE_SETTING_SAMESITE = Arrays.asList("resources");
    private final String SESSION_COOKIE_NAME = "JSESSIONID";
    private final String SESSION_PATH_ATTRIBUTE = ";Path=";
    private final String ROOT_CONTEXT = "/";
    private final String SAME_SITE_ATTRIBUTE_VALUES = ";HttpOnly;Secure;SameSite=None";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestUrl = req.getRequestURL().toString();
        boolean isResourceRequest = requestUrl != null ? StringUtils.isNotBlank(PATHS_TO_IGNORE_SETTING_SAMESITE.stream().filter(s -> requestUrl.contains(s)).findFirst().orElse(null)) : null;
        if (!isResourceRequest) {
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            if (cookies != null && cookies.length > 0) {
                List<Cookie> cookieList = Arrays.asList(cookies);
                Cookie sessionCookie = cookieList.stream().filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName())).findFirst().orElse(null);
                if (sessionCookie != null) {
                    String contextPath = request.getServletContext() != null && StringUtils.isNotBlank(request.getServletContext().getContextPath()) ? request.getServletContext().getContextPath() : ROOT_CONTEXT;
                    resp.setHeader(HttpHeaders.SET_COOKIE, sessionCookie.getName() + "=" + sessionCookie.getValue() + SESSION_PATH_ATTRIBUTE + contextPath + SAME_SITE_ATTRIBUTE_VALUES);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
