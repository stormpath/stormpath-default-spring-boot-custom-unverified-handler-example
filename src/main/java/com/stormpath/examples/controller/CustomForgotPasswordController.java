package com.stormpath.examples.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.account.Accounts;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.servlet.form.Form;
import com.stormpath.sdk.servlet.mvc.ForgotPasswordController;
import com.stormpath.sdk.servlet.mvc.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CustomForgotPasswordController extends ForgotPasswordController {

    private static final Logger log = LoggerFactory.getLogger(CustomForgotPasswordController.class);

    protected ViewModel onValidSubmit(HttpServletRequest request, HttpServletResponse response, Form form) {

        Application application = (Application) request.getAttribute(Application.class.getName());

        String email = form.getFieldValue("email");

        // probably should deal with AccountStore

        // this will blow up if there's more than one occurrence of the email across mapped directories
        Account account = application.getAccounts(Accounts.where(Accounts.email().eqIgnoreCase(email))).single();

        if (account != null && AccountStatus.UNVERIFIED.equals(account.getStatus())) {
            throw new UnverifiedAccountException("Please verify your account before attempting to reset the password.");
        }
        return super.onValidSubmit(request, response, form);
    }

    protected static class UnverifiedAccountException extends RuntimeException {

        public UnverifiedAccountException(String msg) {
            super(msg);
        }
    }

    @Override
    protected List<String> toErrors(HttpServletRequest request, Form form, Exception e) {
        
        List<String> errors = new ArrayList<String>();

        if (e instanceof UnverifiedAccountException) {
            errors.add(e.getMessage());
        } else {
            errors = super.toErrors(request, form, e);
        }

        return errors;
    }

}
