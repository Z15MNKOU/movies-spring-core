package fr.cenotelie.training.movies.constraints;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DatePrecedenceValidator.class)
public @interface DatePrecedenceConstraint {
    String message() default "Date precedence error";
    String before();
    String after();
    boolean strict() default true;
}
