package com.example.filter;

import jakarta.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    private String encoding;
    private boolean forceEncoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("encoding");
        String forceEncodingParam = filterConfig.getInitParameter("forceEncoding");
        forceEncoding = forceEncodingParam != null && Boolean.parseBoolean(forceEncodingParam);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (encoding != null && (forceEncoding || request.getCharacterEncoding() == null)) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Нет ресурсов для освобождения
    }
} 