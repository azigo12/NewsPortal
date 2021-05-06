package ba.newsportal.validation.annotation;

import ba.newsportal.validation.validator.ValueOfEnumGenderValidator;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfEnumGenderValidator.class)
public @interface ValueOfEnumGender {
    Class<? extends Enum<?>> enumClass();
    String message() default "Must be any of enum Gender";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

