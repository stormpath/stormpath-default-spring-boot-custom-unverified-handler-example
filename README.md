## Unverified Account Handler Example

By default, the `stormpath-default-spring-boot-starter`, which includes the Stormpath Spring Security and Spring Boot Web MVC integrations, will show:
`Invalid login or password` for any authentication error condition. This is by design so as to not "leak" any information to potential attackers.

This example shows how to override that default behavior to respond with different error messages when the authentication failure is the result of an
Account being unverified.

The example demonstrates custom messages both on the `login` view and on the `forgot` view.

**NOTE:** This is for demonstration purposes. It is untested and will *only* work in Spring Security, Spring Boot WebMvc environments.

### Custom Password Reset Validation

This example demonstrates how you can override the default validation behavior in Stormpath's standard Forgot Password Controller.

The example takes advantage of Stormpath's pluggable architecture in conjunction with Spring Boot's bean override
capabilities.

The key elements are:

* a `CustomForgotPasswordController` that extends Stormpath's default `ForgotPasswordController`. It implements the new
validation logic and then calls the existing validation logic from the parent class.

* a `CustomWebMvcConfigutation` Spring Boot Configuration that extends Stormpath's `AbstractStormpathWebMvcConfiguration`. 
It exposes a `stormpathForgotPasswordController` bean, which overrides the default. Internally, the `stormpathForgotPasswordController`
method instantiates and returns the `CustomForgotPasswordController`.

The custom validation logic, in this case, ensures that the account has been verified before allowing a password reset.

### Custom Login Handling

This example demonstrates how you can override the default validation behavior in Stormpath's standard Login flow.

The example takes advantage of Stormpath's pluggable architecture in conjunction with Spring Boot's bean override
capabilities.

The key elements are:

* a `CustomWebSecurityConfiguration` Spring Boot Configuration that extends Stormpath's `AbstractStormpathWebSecurityConfiguration`. 
It exposes `stormpathAuthenticationFailureHandler` and `stormpathLoginErrorModelFactory` which override the defaults.
 
* a `UnverifiedAuthenticationHandler` that extends the Spring `SimpleUrlAuthenticationFailureHandler`. If the authentication exception
is due to the account being unverified, the user is redirected to `/login?unverified`. For any other authentication exception, the 
user is redirected to the default: `/login?error`.

* a `CustomSpringSecurityLoginErrorModel` that extends Stormpath's default `SpringSecurityLoginErrorModelFactory`. If the incoming query
string is equal to `unverified`, it will add a special message to the `errors`. In all other cases, it defers to the default behavior.

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

### Verify

This is a very simple, bare bones Spring Security, Spring Boot WebMVC application. All paths are locked down and require
authentication.

To exercise the functionality in this example app, click the `Create Account` link on the login page. A verification email will be sent when you register.

From the login page, you can attempt to login and you'll get the message `Please verify your account before attempting to reset the password.`

From the login page, you can click the `Forgot Password?` link. When you enter the email address and click the "Send Email" button, you'll get the message:
`Please verify your account before attempting to reset the password.`
