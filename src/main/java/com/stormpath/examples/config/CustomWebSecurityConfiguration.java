package com.stormpath.examples.config;

import com.stormpath.examples.auth.UnverifiedAuthenticationHandler;
import com.stormpath.sdk.servlet.mvc.ErrorModelFactory;
import com.stormpath.spring.config.AbstractStormpathWebSecurityConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class CustomWebSecurityConfiguration extends AbstractStormpathWebSecurityConfiguration {

    @Bean
    @Override
    public AuthenticationFailureHandler stormpathAuthenticationFailureHandler() {

        return new UnverifiedAuthenticationHandler(loginUri + "?error", loginUri + "?unverified");
    }

    @Bean
    @Override
    public ErrorModelFactory stormpathLoginErrorModelFactory() {

        return new CustomSpringSecurityLoginErrorModelFactory();
    }

}
