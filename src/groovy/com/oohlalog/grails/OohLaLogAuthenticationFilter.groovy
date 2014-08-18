package com.oohlalog.grails

import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.commons.logging.LogFactory
import org.springframework.web.context.support.WebApplicationContextUtils

class OohLaLogAuthenticationFilter implements javax.servlet.Filter {
    def contextHolder
    def sharedSecurityService
    private static final log = LogFactory.getLog(this)

    def getContextHolder() {
        h = h ?: Class.forName('org.springframework.security.core.context.SecurityContextHolder')
        return h
    }


    void init(FilterConfig filterConfig) throws ServletException  {
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.servletContext)
        sharedSecurityService = applicationContext['sharedSecurityService']
    }

    void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            if (getContextHolder() && getContextHolder().context.authentication?.principal) {
                org.apache.log4j.MDC.put('token', h.context.authentication.principal.username)
            } else if(sharedSecurityService) {
                org.apache.log4j.MDC.put('token', "${sharedSecurityService.userIdentity}-${sharedSecurityService.accountIdentity}")
            } else {
                // Lets try and get the jSessionID
                def sessionCookie = request.cookies.find({it.name == 'JSESSIONID'})
                if(sessionCookie) {
                    org.apache.log4j.MDC.put('token', sessionCookie.value)
                }
            }
        } catch(RuntimeException re) {
            log.error('Unable to add OohLaLog principal token: '+re.toString())
        }
        chain.doFilter(req, res)
    }

    void destroy() {}
}
