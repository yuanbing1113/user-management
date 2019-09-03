package com.fsd.um.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.fsd.um.common.UserManagementConstants;
import com.google.code.kaptcha.Constants;

public class KaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger logger = LoggerFactory.getLogger(KaptchaAuthenticationFilter.class);

    
    private String servletPath;

    public KaptchaAuthenticationFilter(String servletPath, String failureUrl) {
        super(servletPath);
        this.servletPath = servletPath;
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getServletPath();
        logger.debug("kaptch filter: {} {}", req.getMethod(), url);
        
        if ("POST".equalsIgnoreCase(req.getMethod()) && servletPath.equals(url)) {
            String expect = (String) req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (expect != null && !expect.equalsIgnoreCase(req.getParameter("kaptcha"))) {
                unsuccessfulAuthentication(req, res, new InsufficientAuthenticationException("Incorrect captcha code."));
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        return null;
    }
}