## Custom Password Reset Validation

This example demonstrates how you can override the default validation behavior in Stormpath's standard Forgot Password Controller.

The example takes advantage of Stormpath's pluggable architecture in conjunction with Spring Boot's bean override
capabilities.

The key elements are:

* a `CustomForgotPasswordController` that extends Stormpath's default `ForgotPasswordController`. It implements the new
validation logic and then calls the existing validation logic from the parent class.

* a Spring Boot Configuration that extends Stormpath's `AbstractStormpathWebMvcConfiguration` and
exposes a `stormpathForgotPasswordController` bean, which overrides the default. Internally, the `stormpathForgotPasswordController`
method instantiates and returns the `CustomForgotPasswordController`.

The custom validation logic, in this case, ensures that the account has been verified before allowing a password reset.

This is a very simple, bare bones Spring Security, Spring Boot WebMVC application. All paths are locked down and require
authentication. From the login page, you can click the `Forgot Password?` link.

### Stormpath Setup

Create a Stormpath Account and setup a Stormpath Application as outlined
[here](https://docs.stormpath.com/rest/product-guide/latest/setup.html)

Note: Make sure that the settings for the Stormpath `Directory` mapped to your Stormpath `Application` include setting `Enabled` on the `Verification Email`
tab of the `Account Registration & Verification` section.

### Build

```
mvn clean package
```

### Run

```
STORMPATH_API_KEY_FILE=<path to your api key file> \
STORMPATH_APPLICATION_HREF=<your application href> \
java -jar target/*.jar
```