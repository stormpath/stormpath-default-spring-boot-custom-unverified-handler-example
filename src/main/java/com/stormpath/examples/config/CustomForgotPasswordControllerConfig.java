package com.stormpath.examples.config;

import com.stormpath.examples.controller.CustomForgotPasswordController;
import com.stormpath.spring.config.AbstractStormpathWebMvcConfiguration;
import com.stormpath.spring.mvc.SpringController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.Controller;

@Configuration
public class CustomForgotPasswordControllerConfig extends AbstractStormpathWebMvcConfiguration {

    @Bean
    public Controller stormpathForgotPasswordController() {

        if (idSiteEnabled) {
            return createIdSiteController(idSiteForgotUri);
        }

        CustomForgotPasswordController controller = new CustomForgotPasswordController();

        controller.setUri(forgotUri);
        controller.setView(forgotView);
        controller.setCsrfTokenManager(stormpathCsrfTokenManager());
        controller.setAccountStoreResolver(stormpathAccountStoreResolver());
        controller.setNextView(forgotNextUri);
        controller.setLoginUri(loginUri);
        controller.init();

        SpringController springController = new SpringController(controller);
        if (urlPathHelper != null) {
            springController.setUrlPathHelper(urlPathHelper);
        }
        return springController;
    }
}
