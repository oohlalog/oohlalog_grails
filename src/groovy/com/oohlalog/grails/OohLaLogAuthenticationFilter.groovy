package com.oohlalog.grails
 
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OohLaLogAuthenticationFilter {
    def h

    void init(FilterConfig filterConfig) throws ServletException  {
        try {
            h = Class.forName('org.springframework.security.core.context.SecurityContextHolder')
        } catch (java.lang.ClassNotFoundException e) {}
    }
     
    void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (h && h.context.authentication?.principal) {
            org.apache.log4j.MDC.put('token', h.context.authentication.principal.toString())
        }
        chain.doFilter(req, res)
    }

    void destroy() {} 
}