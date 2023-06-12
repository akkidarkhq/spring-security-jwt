package com.unoveo.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@ComponentScan
public class SecurityWebAppInitializer  extends AbstractSecurityWebApplicationInitializer {

   public SecurityWebAppInitializer(){
        super(SecurityConfiguration.class);
    }
    @Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }
}
