package fr.cenotelie.training.movies.constraints;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstLetterUpperValidator.class)
public @interface FirstLetterUppderConstraint {
    String message() default "{cenotelie.training.validation.constraints.FirstLetterUpper.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
