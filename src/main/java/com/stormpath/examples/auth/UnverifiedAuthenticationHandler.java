package com.stormpath.examples.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnverifiedAuthenticationHandler extends SimpleUrlAuthenticationFailureHandler {

    private String unverifiedFailureUri;

    public UnverifiedAuthenticationHandler(String defaultFailureUri, String unverifiedFailureUri) {
        super(defaultFailureUri);

        this.unverifiedFailureUri = unverifiedFailureUri;
    }

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
    ) throws IOException, ServletException {

        // unverified: http://docs.stormpath.com/errors/7102/
        if (exception.getMessage().contains("Stormpath 7102")) {
            getRedirectStrategy().sendRedirect(request, response, unverifiedFailureUri);
        }
        else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
