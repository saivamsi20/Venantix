package com.expense.Venantix.security;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class TenantHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String tenantHeader = httpRequest.getHeader("X-Tenant-ID");

            if (tenantHeader != null && !tenantHeader.isBlank()) {
                TenantContext.setCurrentTenant(Long.parseLong(tenantHeader));
            }

            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
