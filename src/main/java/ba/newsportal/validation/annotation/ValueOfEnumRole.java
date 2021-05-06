package ba.newsportal.validation.annotation;

import ba.newsportal.validation.validator.ValueOfEnumRoleValidator;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ANNOTATION_TYPE, CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfEnumRoleValidator.class)
public @interface ValueOfEnumRole {
    Class<? extends Enum<?>> enumClass();
    String message() default "Must be any of enum Role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

