package ba.newsportal.validation.validator;

import ba.newsportal.validation.annotation.ValueOfEnumRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumRoleValidator implements ConstraintValidator<ValueOfEnumRole, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnumRole annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return acceptedValues.contains(value.toString());
    }
}
