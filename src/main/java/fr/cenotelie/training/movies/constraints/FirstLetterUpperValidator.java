package fr.cenotelie.training.movies.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FirstLetterUpperValidator implements ConstraintValidator<FirstLetterUppderConstraint, CharSequence> {
    
    @Override
    public boolean isValid(final CharSequence value, final ConstraintValidatorContext context) {
        return Character.isUpperCase(value.charAt(0));
    }


}
