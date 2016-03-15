package com.stormpath.examples.config;

import com.stormpath.sdk.lang.Strings;
import com.stormpath.sdk.servlet.form.Form;
import com.stormpath.spring.config.SpringSecurityLoginErrorModelFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class CustomSpringSecurityLoginErrorModelFactory extends SpringSecurityLoginErrorModelFactory {

    @Override
    public List<String> toErrors(HttpServletRequest request, Form form, Exception exception) {

        String query = Strings.clean(request.getQueryString());
        if (query != null && query.contains("unverified")) {
            return Collections.singletonList("Please verify your account before attempting to login.");
        }

        return super.toErrors(request, form, exception);
    }
}
