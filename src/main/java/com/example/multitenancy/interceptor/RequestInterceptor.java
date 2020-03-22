package com.example.multitenancy.interceptor;

import com.example.multitenancy.context.TenantContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterceptor extends HandlerInterceptorAdapter {

    private final String X_TENANT_ID = "X-TenantID";
    private final String TENANT_NOT_FOUND_ERROR = "X-TenantID not present in the Request Header";
    private final int BAD_REQUEST_HTTP_CODE_ERROR = 400;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String tenantId = request.getHeader(X_TENANT_ID);

        System.out.println("RequestURI::" + requestURI + " || Search for X-TenantID  :: " + tenantId);

        if (tenantId == null) {
            response.getWriter().write(TENANT_NOT_FOUND_ERROR);
            response.setStatus(BAD_REQUEST_HTTP_CODE_ERROR);
            return false;
        }

        TenantContext.setCurrentTenant(tenantId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}
