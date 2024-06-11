package com.final_exam.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class CacheControlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 특정 URL 패턴에 대해 캐시 예외 처리
        String requestURI = httpRequest.getRequestURI();
        if (!isCacheExempt(requestURI)) {
            expireCache(httpResponse);
        }

        chain.doFilter(request, response);
    }

    private void expireCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    private boolean isCacheExempt(String requestURI) {
        // 캐시 예외 처리를 할 URL 패턴 정의
        return requestURI.startsWith("/index") || requestURI.startsWith("/custom-orders/list") || requestURI.startsWith("/product-list");
    }
}
