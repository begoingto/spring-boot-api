package com.begoingto.springbootapi.api.user.validator.password;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = PasswordMatchConstraintValidator.class)
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface PasswordMatch {
    String message() default "The password field is not match confirm password.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String password() default "password";

    String confirmPassword() default "confirmPassword";

    @Target({ ElementType.TYPE })
    @Retention(RUNTIME)
    @Documented
     @interface List {
        PasswordMatch[] value();
    }
}
